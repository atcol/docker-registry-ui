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

    def delete(final int registry, final String id) {
        log.info("Deleting image $id for registry $registry")
        repositoryService.deleteImage(Registry.get(registry), id)
        render view: "delete"
    }
}
