package docker.registry.web


import grails.test.mixin.*
import spock.lang.*

@TestFor(RegistryController)
@Mock(Registry)
class RegistryControllerSpec extends Specification {

    def repositoryService

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.registryInstanceList
        model.registryInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.registryInstance != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        def registry = new Registry()
        registry.repositoryService = repositoryService
        registry.validate()
        controller.save(registry)

        then: "The create view is rendered again with the correct model"
        response.status == 200
        model.registryInstance != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        registry = new Registry(params)
        registry.repositoryService = repositoryService
        controller.save(registry)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/registry/show/1'
        controller.flash.message != null
        Registry.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def registry = new Registry(params)
        registry.repositoryService = repositoryService
        controller.show(registry)

        then: "A model is populated containing the domain instance"
        model.registryInstance == registry
    }

    void "Test that the edit action returns the correct model"() {
        when: "The edit action is executed with a null domain"
        controller.edit(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the edit action"
        populateValidParams(params)
        def registry = new Registry(params)
        registry.repositoryService = repositoryService
        controller.edit(registry)

        then: "A model is populated containing the domain instance"
        model.registryInstance == registry
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        request.contentType = FORM_CONTENT_TYPE
        controller.update(null)

        then: "A 404 error is returned"
        response.status == 405
        response.redirectedUrl == '/registry/index'
        flash.message != null
    }

    void "Update rejects invalid domain instance"() {
        when: "An invalid domain instance is passed to the update action"
        def registry = new Registry()
        registry.repositoryService = repositoryService
        registry.validate()
        controller.update(registry)

        then: "The edit view is rendered again with the invalid instance"
        view == 'edit'
        model.registryInstance == registry
    }

    void "Update succcessful with a valid registry"() {
        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        def registry = new Registry(params).save(flush: true)
        registry.repositoryService = repositoryService
        controller.update(registry)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == "/registry/show/$registry.id"
        flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/registry/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def registry = new Registry(params).save(flush: true)
        registry.repositoryService = repositoryService

        then: "It exists"
        Registry.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(registry)

        then: "The instance is deleted"
        Registry.count() == 0
        response.redirectedUrl == '/registry/index'
        flash.message != null
    }
}
