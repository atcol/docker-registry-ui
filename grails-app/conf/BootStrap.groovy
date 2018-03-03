import docker.registry.web.Registry
import docker.registry.web.Setting
import docker.registry.web.User

public class BootStrap {

    def init = { servletContext ->
        if (!User.findByUsername("admin")) {
            def user = new User()
            user.password = "pleasechangeme"
            user.username = "admin"
            user.save();
        }

        log.info("Checking for registries in system env")
        System.getenv().each { key, val ->
            if (key.matches("REG(\\d)")) {
                handleRegistry(val)
            } else if ("READ_ONLY".equalsIgnoreCase(key)) {
                handleReadOnlyMode(val)
            }
        }
    }

    def handleReadOnlyMode(String val) {
        if (val?.matches("true|false")) {
            new Setting(name: "READ_ONLY", value: val.toString()).save()
            log.info("Read only mode enabled")
        } else {
            log.error("Ignoring value ${val} for READ_ONLY mode; it's not valid")
        }
    }

    def handleRegistry(final String val) {
        log.info("Found registry $val. Creating...")
        def reg = Registry.fromUrl(val)

        if (reg.isPresent()) {
            reg = reg.get()
            if (Registry.findByHostAndApiVersion(reg.host, reg.apiVersion)) {
                log.info("Not creating registry ${val} as it already exists")
            } else {
                log.info("Registry ${reg} doesn't exist; saving")
                reg.save()
            }

            if (!reg.ping()) {
                log.warn("Registry '${reg.toUrl()}' ping failed! Check it's up!")
            }
        } else {
            log.error("Couldn't parse valid registry URL from $val")
        }
    }

    def destroy = {
    }
}
