package docker.registry.web

class Registry {
    String url
    String apiVersion

    static constraints = {
    }

    static transients = ['toURL']

    def toURL() {
        return "${this.url}/${this.apiVersion}"
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
