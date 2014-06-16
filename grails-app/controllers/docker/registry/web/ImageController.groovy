package docker.registry.web

import docker.registry.web.Repository

class ImageController {

    def imageService
    static allowedMethods = [delete: 'DELETE']

    def index() {
        def imageList = []
        Registry.all.each { registry ->
            imageList.addAll(imageService.index(registry))
        }
        render view: "index", model: [images: imageList]
    }

    def delete(final String id) {
        log.info("Deleting image $id")
        imageService.deleteImage(id)
        render view: "delete"
    }
}
