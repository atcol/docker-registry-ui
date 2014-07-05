package docker.registry.web

import docker.registry.web.support.Image
import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class ImageService {

    def grailsApplication

    def index(final Registry registry) {
        log.info("Loading images from $registry")
        final imageList = []
        registry.repositories.each { repo ->
            def http = new HTTPBuilder("${registry.toUrl()}/repositories/$repo.name/images")
            http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
                response.success = { resp, res ->
                    log.info("response data for image list $res $resp")
                    res.each { img ->
                        imageList.add(getImage(registry, img.id))
                    }
                }
            }
        }
        imageList
    }

    def Image getImage(final Registry registry, def imgId) {
        final BASE_URI = getBaseURIFromConfig()
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
        }
        img
    }

    def deleteImage(final String imageId) {
        final BASE_URI = getBaseURIFromConfig()
        final uri = "$BASE_URI/${grailsApplication.config.docker.registry.web.api.version}/images/$imageId"
        def http = new HTTPBuilder(uri)
        http.request(Method.DELETE, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, json ->
                log.info("Image $imageId deleted: $json")
            }
        }
    }

    private String getBaseURIFromConfig() {
        grailsApplication.config.docker.registry.web.uri
    }
}
