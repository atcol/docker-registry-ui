package docker.registry.web

import docker.registry.web.support.Repository
import docker.registry.web.support.RegistryReposView

class RepositoryController {

    def repositoryService
    def registryViewService
    static allowedMethods = [delete: 'DELETE']

    def index() {
        def registries = registryViewService.viewRegistries()
        render view: "index", model: [registryViewSet: registries]
    }

    def show(final int registryId, final String repoName, final String tag, final String imgId) {
        def reg = Registry.get(registryId)
        Repository repository = null

        if (reg) {
            repository = repositoryService.detail(reg, repoName)
        }

        if (!reg || !repository) {
            response.status = 404
        }

        render view: "show", model: [registry: reg, img: repository.images.find { it.id.equals(imgId)}]
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
