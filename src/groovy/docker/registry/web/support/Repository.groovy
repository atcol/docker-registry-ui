package docker.registry.web.support

class Repository {

    String name
    Set<Tag> tags = [] as Set
    Set<Image> images = [] as Set


    @Override
    String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", tags=" + tags +
                ", images=" + images +
                '}'
    }
}
