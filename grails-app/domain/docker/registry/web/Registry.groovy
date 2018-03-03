package docker.registry.web

import docker.registry.web.support.RegistryAction

import javax.swing.text.html.Option

class Registry {
    String host = "localhost"
    int port
    String apiVersion = "v1"
    String username
    String password
    String protocol = "http"

    def repositoryService

    static constraints = {
        username nullable: true
        password nullable: true
    }

    static transients = ['toUrl', 'repositories', 'ping', 'fromUrl']

    String toUrl() {
        def resolvedPort = this.port == 80 || this.port <= 0 ? "" : ":" + this.port;
        def urlString = "${this.protocol}://${this.host}${resolvedPort}/${this.apiVersion}"
        if (username) {
            if (password) urlString = urlString.replace("://", "://$username:$password@")
            else urlString = urlString.replace("://", "://$username@")
        }
        urlString
    }

    URL toUrl(RegistryAction registryAction) {
        switch (registryAction) {
            case RegistryAction.PING: return new URL(this.apiVersion == "v1" ? "${this.toUrl()}/_ping" : "${this.toUrl()}/_catalog")
            case RegistryAction.SEARCH: return new URL(this.apiVersion == "v1" ? "${this.toUrl()}/search" : "${this.toUrl()}/_catalog")
            default: return null;
        }
    }

    def getRepositories() {
        repositoryService.index(this)
    }

    def ping() {
        repositoryService.ping(this)
    }

    /**
     * Static factory method for creating an instance from a URL.
     * @param urlStr a url in the format: http://hostOrIP:OptionalPort/v1/
     **/
    static Optional<Registry> fromUrl(final String urlStr) {
        if (urlStr?.endsWith("/v1/") || urlStr?.endsWith("/v2/")) {
            def url = urlStr.toURL()
            if (url) {
                def auth = url.userInfo?.split(":")
                return Optional.of(new Registry(
                        protocol: url.protocol,
                        host: url.host,
                        port: url.port == -1 ? 80 : url.port,
                        apiVersion: url.path.replaceAll("\\p{Punct}", ""),
                        username: auth?.length > 0 ? auth[0] : null,
                        password: auth?.length > 1 ? auth[1] : null
                ))
            }
        }
        Optional.empty()
    }

    @Override
    public String toString() {
        return "Registry{" +
                "id=" + id +
                ", protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", apiVersion='" + apiVersion + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password?.replaceAll("(?s).", "*") + '\'' +
                ", repositoryService=" + repositoryService +
                ", version=" + version +
                '}';
    }
}
