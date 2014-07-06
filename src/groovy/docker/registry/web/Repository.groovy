package docker.registry.web

import docker.registry.web.support.Image

class Repository {
    String name
    Set<Image> images = [] as Set
}
