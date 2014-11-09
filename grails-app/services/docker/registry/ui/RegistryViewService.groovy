package docker.registry.ui

import docker.registry.web.Registry
import docker.registry.web.support.RegistryReposView
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RegistryViewService {

    def repositoryService

    def viewRegistries() {
        def registries = [] as Set<RegistryReposView>
        Registry.all.each { registry ->
            def repositories = []
            def reachable = true
            try {
                repositories = repositoryService.index(registry)

            } catch (errorRetrievingReposFromRegistry) {
                reachable = false
                log.error("The registry ${registry.toUrl()} is unreachable")
            }
            registries.add(RegistryReposView.make(registry, repositories, reachable))
        }
        registries
    }

}
