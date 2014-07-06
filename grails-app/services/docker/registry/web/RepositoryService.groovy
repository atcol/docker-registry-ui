package docker.registry.web

import docker.registry.web.support.Image
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class RepositoryService {

    def index(final Registry registry) {
        log.info("Loading images from $registry")
        final repoList = []
        final url = "${registry.toUrl()}/search"

        log.info("Getting repositories from $url")

        def http = new HTTPBuilder(url)

        http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, search ->
                log.info("response data for repo list $search $resp")
                search.results.each { repo ->
                    repoList.add(detail(registry, repo.name?.toString()))
                }
            }
        }
        repoList
    }

    def detail(final Registry registry, final String repoName) {
        def registryUri = registry.url.toURI()
        def tagToImgIdMap = getTags(registry, repoName)
        def repo = new Repository()
        tagToImgIdMap.each { tag, imageId ->
            log.info("tag is ${tag}=${imageId}")
            def imgDetail = getImageDetail(registry, imageId)
            imgDetail.displayName = "${repoName}:${tag}"
            imgDetail.tag = tag
            imgDetail.name = repoName
            imgDetail.pullName = "${registryUri.host}${registryUri.port != -1 ? ':' + registryUri.port : ''}/${repoName}"
            if (imgDetail) {
                repo.images.add(imgDetail)
            } else {
                repo.images.add(new Image(description: "Failed to retrieve image detail"))
            }
        }
        repo
    }

    def search(final Registry registry, final String query) {
        final uri = registry.url
        log.info("search URI is $uri")
        def repoList = []
        withHttp(uri: uri) {
            def result = get(
                    path: "/${registry.apiVersion}/search",
                    query: [q: query.toString()])
            log.info("Search result is $result")
            result.results.each { Map repo ->
                repoList.add(detail(registry, repo.name))
            }
        }
        repoList
    }

    def getTags(final Registry registry, final repoName) {
        def tagMap = [:]
        log.info("Getting tags for $repoName")
        def url = "${registry.toUrl()}/repositories/${repoName}/tags"
        log.info("tags url $url")
        def http = new HTTPBuilder(url)
        http.request(Method.GET, ContentType.JSON) {
            response.success = { resp, tags ->
                log.info("Got tags $tags")
                tagMap.putAll(tags)
            }
        }
        tagMap
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
}
