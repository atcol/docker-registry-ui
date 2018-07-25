package docker.registry.web.support


class Tag {
    String name
    String imageId

    @Override
    String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", imageId='" + imageId + '\'' +
                '}'
    }
}
