package docker.registry.web

class Repository {
    String name

    static constraints = {
    }

    static belongsTo = [Registry]

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
