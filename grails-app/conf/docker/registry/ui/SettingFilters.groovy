package docker.registry.ui
import docker.registry.web.Setting

class SettingFilters {

    def filters = {
        all(controller: '*', action: 'save|update|delete|edit') {
            before = {
                def setting = Setting.findByName("READ_ONLY")
                if (setting?.value) {
                    log.info("Read only mode is enabled; rejecting request")
                    response.status = 403
                    flash.message = "Read only mode enabled. Save, edit and update actions are prohibited" //FIXME externalise
                    render(view:'/forbidden')
                    return false
                }
            }
        }
    }
}
