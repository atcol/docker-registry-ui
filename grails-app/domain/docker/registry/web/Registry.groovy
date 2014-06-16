package docker.registry.web

class Registry {
    String url
    String apiVersion

    static constraints = {
    }

    @Override
    public String toString() {
        return "Registry{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", version=" + version +
                '}';
    }
}
