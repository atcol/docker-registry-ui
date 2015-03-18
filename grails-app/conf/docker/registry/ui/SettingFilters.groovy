package docker.registry.ui
import org.springframework.web.servlet.support.RequestContextUtils as RCU
import docker.registry.web.Setting

class SettingFilters {
    def grailsApplication

    def filters = {
        all(uri:'/registry/**') {
            before = {
                def setting = Setting.findByName("READ_ONLY")
                if (setting?.value) {
                    log.info("Read only mode is enabled; rejecting request")
                    response.status = 403
                    flash.message = "Read only mode enabled. Registry configuration is prohibited" //FIXME externalise
                    return false
                }
            }
        }
    }
}
