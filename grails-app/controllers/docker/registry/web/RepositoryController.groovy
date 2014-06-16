package docker.registry.web



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RepositoryController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Repository.list(params), model:[repositoryInstanceCount: Repository.count()]
    }

    def show(Repository repositoryInstance) {
        respond repositoryInstance
    }

    def create() {
        respond new Repository(params)
    }

    @Transactional
    def save(Repository repositoryInstance) {
        if (repositoryInstance == null) {
            notFound()
            return
        }

        if (repositoryInstance.hasErrors()) {
            respond repositoryInstance.errors, view:'create'
            return
        }

        repositoryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'repository.label', default: 'Repository'), repositoryInstance.id])
                redirect repositoryInstance
            }
            '*' { respond repositoryInstance, [status: CREATED] }
        }
    }

    def edit(Repository repositoryInstance) {
        respond repositoryInstance
    }

    @Transactional
    def update(Repository repositoryInstance) {
        if (repositoryInstance == null) {
            notFound()
            return
        }

        if (repositoryInstance.hasErrors()) {
            respond repositoryInstance.errors, view:'edit'
            return
        }

        repositoryInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Repository.label', default: 'Repository'), repositoryInstance.id])
                redirect repositoryInstance
            }
            '*'{ respond repositoryInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Repository repositoryInstance) {

        if (repositoryInstance == null) {
            notFound()
            return
        }

        repositoryInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Repository.label', default: 'Repository'), repositoryInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'repository.label', default: 'Repository'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
