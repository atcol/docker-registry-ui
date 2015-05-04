package docker.registry.ui

import docker.registry.web.Registry
import docker.registry.web.RepositoryService
import docker.registry.web.support.RegistryReposView
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RegistryViewService)
class RegistryViewServiceSpec extends Specification {

    def registryViewService

    def setup() {
    }

    def cleanup() {
    }

    void "test viewRepositories yields valid set"() {
        given:
        mockDomain(Registry,
                [host: "localhost", port: 5000, apiVersion: "v1", username: "", password: "", protocol: "http"])
        def repoSvcMock = mockFor(RepositoryService)
        repoSvcMock.demand.index { Registry -> [new RegistryReposView(null, null, false)]}
        registryViewService.repositoryService = repoSvcMock

        when: "I view repositories"
        final repositories = registryViewService.listRegistriesAndTheirRepositories()

        then:
        repositories != null
        repositories.size() == 1
        !repositories[0].isReachable
        repositories[0].registry == null
        repositories[0].repositories == null
    }
}
