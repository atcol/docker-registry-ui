package docker.registry.web.support

class Repository {

    String name
    Set<Tag> tags = [] as Set
    Set<Image> images = [] as Set
}
