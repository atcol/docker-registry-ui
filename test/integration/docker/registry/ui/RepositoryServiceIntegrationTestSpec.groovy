package docker.registry.ui

import docker.registry.web.Registry
import docker.registry.web.RepositoryService
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(RepositoryService)
class RepositoryServiceIntegrationTestSpec extends Specification {

    final VALID_REGISTRY_URL = "http://localhost:5000/v1/"
    final NO_SUCH_REGISTRY = "http://bad.registry/v1/"
    final VALID_REGISTRY = Registry.fromUrl(VALID_REGISTRY_URL)
    final BAD_REGISTRY = Registry.fromUrl(NO_SUCH_REGISTRY)

    def repositoryService

    def setup() {
    }

    def cleanup() {
    }

    void "test ping true on valid registry url"() {
        when:
        def pingResult = repositoryService.ping(VALID_REGISTRY)
        then:
        pingResult == true
    }

    void "test ping false on invalid registry url"() {
        when:
        def pingResult = repositoryService.ping(BAD_REGISTRY)
        then:
        !pingResult
    }

    void "test search returns non-empty collection"() {
        when:
        def search = repositoryService.search(VALID_REGISTRY, "ui")
        then:
        !search.isEmpty()
    }

    void "test index returns non-empty collection"() {
        when:
        def index = repositoryService.index(VALID_REGISTRY)
        then:
        !index.isEmpty()
        def repo = index.pop()
        repo.name != null
        repo.tags != null
        repo.tags.size() > 0
        repo.images.size() == 0
    }
}
