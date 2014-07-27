package docker.registry.web

class Registry {
    String url
    String apiVersion

    def repositoryService

    static constraints = {
    }

    static transients = ['toUrl', 'repositories', 'ping', 'fromUrl']

    def toUrl() {
        return "${this.url}/${this.apiVersion}"
    }

    def getRepositories() {
        repositoryService.index(this)
    }

    def ping() {
        repositoryService.ping(this)
    }

    /**
     * Static factory method for creating an instance from a URL
     * @param urlStr a url in the format: http://hostOrIP:OptionalPort/apiVersion/
     **/
    static def fromUrl(def urlStr) {
        def m = urlStr =~ /(.*)(v\d).*/
        if (m.matches()) {
            return new Registry(url: m.group(1), apiVersion: m.group(2))
        }
        null
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
