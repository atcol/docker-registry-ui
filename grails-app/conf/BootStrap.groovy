import docker.registry.web.User

class BootStrap {

    def init = { servletContext ->
        if (!User.findByUsername("admin")) {
            def user = new User()
            user.password = "pleasechangeme"
            user.username = "admin"
            user.save();
        }
    }
    def destroy = {
    }
}
