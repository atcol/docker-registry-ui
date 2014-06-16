package docker.registry.web

class ImageController {

    def repositoryService
    static allowedMethods = [delete: 'DELETE']

    def index() {
        def registryToImg = [:]
        Registry.all.each { registry ->
            registryToImg.put(registry, repositoryService.index(registry))
        }
        render view: "index", model: [registryToImageMap: registryToImg]
    }

    def delete(final String id) {
        log.info("Deleting image $id")
        repositoryService.deleteImage(id)
        render view: "delete"
    }
}
