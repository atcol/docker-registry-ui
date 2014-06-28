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
        final url = "$registry.url/$registry.apiVersion/search"

        log.info("Getting repositories from $url")

        def http = new HTTPBuilder(url)
        def registryUri = registry.url.toURI()

        http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, search ->
                log.info("response data for repo list $search $resp")
                search.results.each { repo ->
                    def tags = getTags(registry, repo.name)
                    tags.entrySet().each { entry ->
                        // tag is a map of tag name to its image id
                        log.info("tag is ${entry}")
                        def imgDetail = getImageDetail(registry, entry.value)
                        imgDetail.displayName = "${repo.name}:${entry.key}"
                        imgDetail.name = repo.name
                        imgDetail.pullName = "${registryUri.host}:${registryUri.port}/${repo.name}"
                        if (imgDetail) {
                            imageList.add(imgDetail)
                        } else {
                            imageList.add(new Image(description: "Failed to retrieve image detail"))
                        }
                    }
                }
            }
        }
        imageList
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
        def url = "${registry.url}/${registry.apiVersion}/repositories/${repoName}/tags"
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
        def http = new HTTPBuilder(registry.url)
        http.request (Method.GET, groovyx.net.http.ContentType.JSON) {
            uri.path = "/$registry.apiVersion/images/${imgId}/json"
            log.info("uri is $uri.path")
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

    def delete(final Registry registry, final String repoName) {
        final uri = "${registry.url}/${registry.apiVersion}/repositories/$repoName/"
        log.info("Deleting repo at $uri")
        def http = new HTTPBuilder(uri)
        http.request(Method.DELETE, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, json ->
                log.info("Repo $repoName deleted: $json")
            }
        }
    }
}
