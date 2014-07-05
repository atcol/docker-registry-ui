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

    def show(final int registryId, final String imgId) {
        def reg = Registry.get(registryId)
        def img = null

        if (reg) {
            img = repositoryService.getImageDetail(reg, imgId)
        } else {
            response.status = 404
        }

        render view: "show", model: [registry: reg, image: img]
    }

    def delete() {
        def repoName = params.repoName
        def registry = params.registry
        def tag = params.tag
        log.info("Deleting image $repoName for registry $registry and tag $tag")
        def reg = Registry.get(registry)
        log.info("Got registry by id $registry ? $reg")
        if (reg) {
            repositoryService.delete(reg, repoName, tag)
        } else {
            response.status = 404
        }
        render view: "delete"
    }
}
