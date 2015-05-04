package docker.registry.ui

import docker.registry.web.Registry
import docker.registry.web.support.RegistryReposView
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RegistryViewService {

    def listRegistriesAndTheirRepositories() {
        def registries = [] as Set<RegistryReposView>
        Registry.all.each { registry ->
            def repositories = []
            def reachable = true
            try {
                repositories = registry.getRepositories()

            } catch (exceptionRetrievingRepos) {
                reachable = false
                log.error("The registry ${registry.toUrl()} is unreachable: ${exceptionRetrievingRepos}")
            }
            registries.add(RegistryReposView.make(registry, repositories, reachable))
        }
        registries
    }

}
