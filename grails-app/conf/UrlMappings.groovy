import docker.registry.web.ImageController

class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/image/$id?" (
            resource: 'Image'
        )

        "/"(view:"/index")
        "/about"(view:"/about")
        "500"(view:'/error')
	}
}
