import docker.registry.web.Registry
import docker.registry.web.Role
import docker.registry.web.User
import docker.registry.web.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)

        def adminUser = new User(username: "admin", password: "pleasechangeme")
        adminUser.save();

        UserRole.create adminUser, adminRole, true

        log.info("Checking for registries in system env")
        System.getenv().each { key, urlStr ->
            if (key.matches("REG(\\d)")) {
                log.info("Found registry $urlStr. Creating...")
                def reg = Registry.fromUrl(urlStr)

                if (reg) {
                    if (Registry.findByHostAndApiVersion(reg.host, reg.apiVersion)) {
                        log.info("Not creating registry ${urlStr} as it already exists")
                    } else {
                        log.info("Registry ${reg} doesn't exist; saving")
                        reg.save()
                    }

                    if (!reg.ping()) {
                        log.warn("Registry '${reg.toUrl()}' ping failed! Check it's up!")
                    }

                } else {
                    log.error("Couldn't parse valid registry URL from $urlStr")
                }
            }
        }
    }

    def destroy = {
    }
}
