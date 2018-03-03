package docker.registry.web



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RegistryController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Registry.list(params), model:[registryInstanceCount: Registry.count()]
    }

    def show(Registry registryInstance) {
        //FIXME this is really inefficient
        boolean isUp = registryInstance != null && registryInstance.ping()
        respond registryInstance, model: [registryIsUp: isUp]
    }

    def create() {
        respond new Registry(params)
    }

    @Transactional
    def save(Registry registryInstance) {
        if (registryInstance == null) {
            notFound()
            return
        }

        if (registryInstance.hasErrors()) {
            respond registryInstance.errors, view:'create'
            return
        }

        registryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'registry.label', default: 'Registry'), registryInstance.id])
                redirect registryInstance
            }
            '*' { respond registryInstance, [status: CREATED] }
        }
    }

    def edit(Registry registryInstance) {
        respond registryInstance
    }

    @Transactional
    def update(Registry registryInstance) {
        if (registryInstance == null) {
            notFound()
            return
        }

        if (registryInstance.hasErrors()) {
            respond registryInstance.errors, view:'edit'
            return
        }

        registryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Registry.label', default: 'Registry'), registryInstance.id])
                redirect registryInstance
            }
            '*'{ respond registryInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Registry registryInstance) {

        if (registryInstance == null) {
            notFound()
            return
        }

        registryInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Registry.label', default: 'Registry'), registryInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'registry.label', default: 'Registry'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
