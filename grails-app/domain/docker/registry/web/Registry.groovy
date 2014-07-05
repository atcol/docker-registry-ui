package docker.registry.web

class Registry {
    String url
    String apiVersion

    def repositoryService

    static constraints = {
    }

    static transients = ['toUrl', 'repositories']

    def toUrl() {
        return "${this.url}/${this.apiVersion}"
    }

    def getRepositories() {
        repositoryService.index(this)
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
