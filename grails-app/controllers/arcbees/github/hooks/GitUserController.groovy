package arcbees.github.hooks

class GitUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [gitUserInstanceList: GitUser.list(params), gitUserInstanceTotal: GitUser.count()]
    }

    def create = {
        def gitUserInstance = new GitUser()
        gitUserInstance.properties = params
        return [gitUserInstance: gitUserInstance]
    }

    def save = {
        def gitUserInstance = new GitUser(params)
        if (gitUserInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'gitUser.label', default: 'GitUser'), gitUserInstance.id])}"
            redirect(action: "show", id: gitUserInstance.id)
        }
        else {
            render(view: "create", model: [gitUserInstance: gitUserInstance])
        }
    }

    def show = {
        def gitUserInstance = GitUser.get(params.id)
        if (!gitUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gitUser.label', default: 'GitUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            [gitUserInstance: gitUserInstance]
        }
    }

    def edit = {
        def gitUserInstance = GitUser.get(params.id)
        if (!gitUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gitUser.label', default: 'GitUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [gitUserInstance: gitUserInstance]
        }
    }

    def update = {
        def gitUserInstance = GitUser.get(params.id)
        if (gitUserInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (gitUserInstance.version > version) {

                    gitUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'gitUser.label', default: 'GitUser')] as Object[], "Another user has updated this GitUser while you were editing")
                    render(view: "edit", model: [gitUserInstance: gitUserInstance])
                    return
                }
            }
            gitUserInstance.properties = params
            if (!gitUserInstance.hasErrors() && gitUserInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'gitUser.label', default: 'GitUser'), gitUserInstance.id])}"
                redirect(action: "show", id: gitUserInstance.id)
            }
            else {
                render(view: "edit", model: [gitUserInstance: gitUserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gitUser.label', default: 'GitUser'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def gitUserInstance = GitUser.get(params.id)
        if (gitUserInstance) {
            try {
                gitUserInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'gitUser.label', default: 'GitUser'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'gitUser.label', default: 'GitUser'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gitUser.label', default: 'GitUser'), params.id])}"
            redirect(action: "list")
        }
    }
}
