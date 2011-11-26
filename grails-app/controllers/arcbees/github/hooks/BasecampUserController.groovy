package arcbees.github.hooks

class BasecampUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [basecampUserInstanceList: BasecampUser.list(params), basecampUserInstanceTotal: BasecampUser.count()]
    }

    def create = {
        def basecampUserInstance = new BasecampUser()
        basecampUserInstance.properties = params
        return [basecampUserInstance: basecampUserInstance]
    }

    def save = {
        def basecampUserInstance = new BasecampUser(params)
        if (basecampUserInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), basecampUserInstance.id])}"
            redirect(action: "show", id: basecampUserInstance.id)
        }
        else {
            render(view: "create", model: [basecampUserInstance: basecampUserInstance])
        }
    }

    def show = {
        def basecampUserInstance = BasecampUser.get(params.id)
        if (!basecampUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            [basecampUserInstance: basecampUserInstance]
        }
    }

    def edit = {
        def basecampUserInstance = BasecampUser.get(params.id)
        if (!basecampUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [basecampUserInstance: basecampUserInstance]
        }
    }

    def update = {
        def basecampUserInstance = BasecampUser.get(params.id)
        if (basecampUserInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (basecampUserInstance.version > version) {

                    basecampUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'basecampUser.label', default: 'BasecampUser')] as Object[], "Another user has updated this BasecampUser while you were editing")
                    render(view: "edit", model: [basecampUserInstance: basecampUserInstance])
                    return
                }
            }
            basecampUserInstance.properties = params
            if (!basecampUserInstance.hasErrors() && basecampUserInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), basecampUserInstance.id])}"
                redirect(action: "show", id: basecampUserInstance.id)
            }
            else {
                render(view: "edit", model: [basecampUserInstance: basecampUserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def basecampUserInstance = BasecampUser.get(params.id)
        if (basecampUserInstance) {
            try {
                basecampUserInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'basecampUser.label', default: 'BasecampUser'), params.id])}"
            redirect(action: "list")
        }
    }
}
