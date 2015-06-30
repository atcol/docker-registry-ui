package docker.registry.ui

import docker.registry.web.Registry
import docker.registry.web.support.Image
import docker.registry.web.support.Repository
import docker.registry.web.support.Tag
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method


trait RepositorySource {
    abstract List<Repository> index(final Registry registry)
    abstract detail(final Registry registry, final String repoName)
    abstract List<Repository> search(final Registry registry, final String query)
    abstract List<Tag> getTags(final Registry registry, final repoName)
    abstract Image getImageDetail(final Registry registry, final String imgId)
    abstract boolean delete(final Registry registry, final String repoName, final String tag)
    abstract boolean ping(Registry registry)

    def String buildPullName(Registry registry, String repoName, String tag) {
        def url = registry.toUrl().toURL()
        "${url.authority}/${repoName}:${tag}"
    }

    def ping(String url) {
        def http = new HTTPBuilder(url)
        def result = true
        try {

            http.getClient().getParams().setParameter("http.connection.timeout", new Integer(10000))
            http.getClient().getParams().setParameter("http.socket.timeout", new Integer(10000))

            http.request(Method.GET, ContentType.JSON) {
                response.failure = { resp ->
                    result = false
                }
            }
        } catch (final IOException ignored) {
            result = false
        } finally {
            http.shutdown()
        }
        result
    }
}