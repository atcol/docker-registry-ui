package docker.registry.web

import docker.registry.web.support.Image

class RepositoryController {

    def repositoryService
    static allowedMethods = [delete: 'DELETE']

    def index() {
        def registryToImg = [:]
        Registry.all.each { registry ->
            registryToImg.put(registry, repositoryService.index(registry))
        }
        render view: "index", model: [registryToImageMap: registryToImg]
    }

    def show(final int registryId, final String repoName, final String tag, final String imgId) {
        def reg = Registry.get(registryId)
        Image image = null

        if (reg) {
            image = repositoryService.detail(reg, repoName)
            image.id = imgId
        }

        if (!reg || !image) {
            response.status = 404
        }

        render view: "show", model: [registry: reg, img: image]
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
