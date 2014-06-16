package docker.registry.web

import docker.registry.web.support.Image
import grails.converters.JSON
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class RepositoryService {

    @Cacheable("registryCache")
    def index(final Registry registry) {
        log.info("Loading images from $registry")
        final imageList = []
        final url = "$registry.url/$registry.apiVersion/search"

        log.info("Getting repositories from $url")

        def http = new HTTPBuilder(url)
        http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, search ->
                log.info("response data for repo list $search $resp")
                search.results.each { repo ->
                    def tags = getTags(registry, repo.name)
                    tags.each { tag ->
                        // tag is a map of tag name to its image id
                        log.info("tag is ${tag}")
                        tag.entrySet().each { entry ->
                            def imgDetail = getImageDetail(registry, entry.value)
                            imgDetail.displayName = "$repo:${entry.key}"
                            if (imgDetail) {
                                imageList.add(imgDetail)
                            } else {
                                imageList.add(new Image(description: "Failed to retrieve image detail"))
                            }
                        }
                    }
                }
            }
        }
        imageList
    }

    def getTags(final Registry registry, final repoName) {
        def tagList = []
        log.info("Getting tags for $repoName")
        def url = "${registry.url}/${registry.apiVersion}/repositories/${repoName}/tags"
        log.info("tags url $url")
        def http = new HTTPBuilder(url)
        http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, tags ->
                log.info("Got tags $tags")
                tagList.addAll(tags)
            }
        }
        tagList
    }

    @Cacheable("imageDetailCache")
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
                return null
            }
        }
        img
    }

    def deleteImage(final Registry registry, final String imageId) {
        final uri = "${registry.url}/${registry.apiVersion}/images/$imageId"
        def http = new HTTPBuilder(uri)
        http.request(Method.DELETE, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, json ->
                log.info("Image $imageId deleted: $json")
            }
        }
    }
}
