import docker.registry.web.Registry
import docker.registry.web.User
import groovyx.net.http.URIBuilder

class BootStrap {

    def init = { servletContext ->
        if (!User.findByUsername("admin")) {
            def user = new User()
            user.password = "pleasechangeme"
            user.username = "admin"
            user.save();
        }

        log.info("Checking for registries in system env")
        System.getenv().each { key, urlStr ->
            if (key.matches("REG(\\d)")) {
                log.info("Found registry $urlStr. Creating...")
                def m = urlStr =~ /.*(v\d).*/

                if (m.matches()) {
                    def reg = new Registry()
                    reg.url = urlStr.replaceAll("/(v\\d)/", "") // remove API version
                    reg.apiVersion = m.group(1) // extracts e.g. v1 from url
                    reg.save()

                    if (!reg.ping()) {
                        log.warn("Registry '${reg.toUrl()}' ping failed! Check it's up!")
                    }

                } else {
                    log.error("Couldn't parse the API version from $regUrl")
                }
            }
        }
    }
    def destroy = {
    }
}
