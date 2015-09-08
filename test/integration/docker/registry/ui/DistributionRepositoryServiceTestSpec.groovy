package docker.registry.ui

import docker.registry.web.Registry
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(DistributionRepositoryService)
class DistributionRepositoryServiceTestSpec extends Specification {

    def distributionRepositoryService

    def "Index"() {
        given: "I have a valid registry"
        final registry = new Registry(host: "localhost", port: 5000, apiVersion: "v2")

        when: "I request its index"
        final index = distributionRepositoryService.index(registry)

        then: "The index should contain repositories"
        index != null
        !index.isEmpty()

        and: "The repositories should have images"
        index[0]
        !index[0].images.isEmpty()

    }

    def "Detail"() {
        fail()
    }

    def "Search"() {
        fail()
    }

    def "GetTags"() {
        fail()
    }

    def "GetImageDetail existing"() {
        given: "I have a valid registry"
        final registry = new Registry(host: "localhost", port: 5000, apiVersion: "v2")

        and: "An existing image"


        when: "I retrieve its detail"
        def detail = distributionRepositoryService.detail(registry, "Bob")

        then: "The detail should be populated"
        detail != null
        detail.images.size() > 0
        detail.name.length() > 0
    }

    def "Delete existing"() {
        given: "I have a valid registry"
        final registry = new Registry(host: "localhost", port: 5000, apiVersion: "v2")

        and: "An existing image"
        // create the image or mock ?

        when: "I delete it"
        def deleted = distributionRepositoryService.delete(registry, "Bob", "latest")

        then: "The delete should succeed"
        deleted

    }

    def "Delete non-existing"() {
        given: "I have a valid registry"
        final registry = new Registry(host: "localhost", port: 5000, apiVersion: "v2")

        and: "No existing image"

        when: "I delete it"
        def deleted = distributionRepositoryService.delete(registry, "Bob", "latest")

        then: "The delete should not succeed"
        !deleted
    }

    def "Ping valid"() {
        given: "I have a valid registry"
        final registry = new Registry(host: "localhost", port: 5000, apiVersion: "v2")

        when: "I ping it"
        def ping = distributionRepositoryService.ping(registry)

        then: "The ping should succeed"
        ping
    }

    def "Ping invalid"() {
        given: "I have an invalid registry"
        final registry = new Registry(host: "boo", port: -1)

        when: "I ping it"
        def ping = distributionRepositoryService.ping(registry)

        then: "The ping should fail"
        !ping
    }

    def "BuildPullName"() {

    }
}
