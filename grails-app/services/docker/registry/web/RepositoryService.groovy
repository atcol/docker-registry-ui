package docker.registry.web

import docker.registry.web.support.Image
import docker.registry.web.support.Repository
import docker.registry.web.support.Tag
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class RepositoryService {

    def index(final Registry registry) {
        search(registry, null)
    }

    def detail(final Registry registry, final String repoName) {
        def tagToImgIdMap = getTags(registry, repoName)
        def repo = new Repository()
        tagToImgIdMap.each { tag, imageId ->
            log.info("tag is ${tag}=${imageId}")
            def imgDetail = getImageDetail(registry, imageId)
            imgDetail.displayName = "${repoName}:${tag}"
            imgDetail.tag = tag
            imgDetail.name = repoName
            imgDetail.pullName = buildPullName(registry, repoName, tag)
            if (imgDetail) {
                repo.images.add(imgDetail)
            } else {
                repo.images.add(new Image(description: "Failed to retrieve image detail"))
            }
        }
        repo
    }

    def search(final Registry registry, final String query) {
        log.info("Searching for images from $registry")
        final repoList = []
        def url = "${registry.toUrl()}/search"
        def http = new HTTPBuilder(url)

        log.info("Query provided for search is ${query}")
        if (query) {
            url += "?q=${query}"
        }

        log.info("Getting repositories from ${http.getUri()}")

        http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, search ->
                log.info("response data for repo list $search $resp")
                search.results.each { repo ->
                    final repository = new Repository(name: repo.name.toString(), tags: getTags(registry, repo.name.toString()))
                    repoList.add(repository)
                }
            }
        }
        repoList
    }

    def getTags(final Registry registry, final repoName) {
        def tagList = []
        log.info("Getting tags for $repoName")
        def url = "${registry.toUrl()}/repositories/${repoName}/tags"
        log.info("tags url $url")
        def http = new HTTPBuilder(url)
        http.request(Method.GET, ContentType.JSON) {
            response.success = { resp, tags ->
                log.info("Got tags $tags")
                tagList = tags.collect { k, v ->
                    new Tag(name: k.toString(), imageId: metaPropertyValues.toString())
                }
            }
        }
        tagList
    }

    def Image getImageDetail(final Registry registry, final String imgId) {
        log.info("getting image $imgId")
        def img = null
        def http = new HTTPBuilder("${registry.toUrl()}/images/${imgId}/json")
        http.request (Method.GET, ContentType.JSON) {
            response.success = { imgResp, Map imgData  ->
                log.info("Image data (${imgData.getClass()}) is $imgData")
                img = new Image(imgData)
            }

            response.failure = { resp ->
                log.info("Failed to get img $imgId: $resp")
                img = null
            }
        }
        img
    }

    def delete(final Registry registry, final String repoName, final String tag) {
        final uri = "${registry.toUrl()}/repositories/$repoName/tags/$tag"
        log.info("Deleting repo at $uri")
        def http = new HTTPBuilder(uri)
        def result = true
        http.request(Method.DELETE, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, json ->
                log.info("Repo $repoName deleted: $json")
            }

            response.failure = { resp ->
                log.error("Failed to delete $uri: $resp")
                result = false
            }
        }
        result
    }

    String buildPullName(Registry registry, String repoName, String tag) {
        def url = registry.url.toURL()
        "${url.host}${url.port != -1 ? ':' + url.port : ''}/${repoName}:${tag}"
    }

}
