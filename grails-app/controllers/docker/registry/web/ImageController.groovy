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

    def delete() {
        def repoName = params.repoName
        def registry = params.registry
        log.info("Deleting image $repoName for registry $registry")
        def reg = Registry.get(registry)
        log.info("Got registry by id $registry ? $reg")
        if (reg) {
            repositoryService.delete(reg, repoName)
        } else {
            response.status = 404
        }
        render view: "delete"
    }
}
