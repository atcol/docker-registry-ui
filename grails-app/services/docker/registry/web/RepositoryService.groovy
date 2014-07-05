package docker.registry.web

import docker.registry.web.support.Image
import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class RepositoryService {

    def index(final Registry registry) {
        log.info("Loading images from $registry")
        final imageList = []
        final url = "${registry.toURL()}/search"

        log.info("Getting repositories from $url")

        def http = new HTTPBuilder(url)

        http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, search ->
                log.info("response data for repo list $search $resp")
                search.results.each { repo ->
                    imageList.add(detail(registry, repo.name?.toString()))
                }
            }
        }
        imageList
    }

    def detail(final Registry registry, final String repoName) {
        def registryUri = registry.url.toURI()
        def tagToImgIdMap = getTags(registry, repoName)
        tagToImgIdMap.entrySet().each { entry ->
            log.info("tag is ${entry}")
            def imgDetail = getImageDetail(registry, entry.value)
            imgDetail.displayName = "${repoName}:${entry.key}"
            imgDetail.tag = entry.key
            imgDetail.name = repoName
            imgDetail.pullName = "${registryUri.host}${registryUri.port != -1 ? ':' + registryUri.port : ''}/${repoName}"
            if (imgDetail) {
                return imgDetail
            } else {
                return new Image(description: "Failed to retrieve image detail")
            }
        }
    }

    def search(final Registry registry, final String query) {
        final uri = registry.url
        log.info("search URI is $uri")
        def imgList = []
        withHttp(uri: uri) {
            def result = get(
                    path: "/${registry.apiVersion}/search",
                    query: [q: query.toString()])
            log.info("Search result is $result")
            result.results.each {
                imgList.add(new Image(name: it.name))
            }
        }
        imgList
    }

    def getTags(final Registry registry, final repoName) {
        def tagMap = [:]
        log.info("Getting tags for $repoName")
        def url = "${registry.toURL()}/repositories/${repoName}/tags"
        log.info("tags url $url")
        def http = new HTTPBuilder(url)
        http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
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
        def http = new HTTPBuilder("${registry.toURL()}/images/${imgId}/json")
        http.request (Method.GET, groovyx.net.http.ContentType.JSON) {
            response.success = { imgResp, imgData ->
                log.info("Image data is $imgData")
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
        final uri = "${registry.toURL()}/repositories/$repoName/tags/$tag"
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
