package docker.registry.web

import docker.registry.web.support.Image
import docker.registry.web.support.RegistryAction
import docker.registry.web.support.Repository
import docker.registry.web.support.Tag
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.apache.http.HttpResponse

@Transactional
class RepositoryService {

    List<Repository> index(final Registry registry) {
        search(registry, null)
    }

    def detail(final Registry registry, final String repoName) {
        def tags = getTags(registry, repoName)
        def repo = new Repository()
        tags.each { tag ->
            log.info("tag is ${tag}")
            def imgDetail = getImageDetail(registry, repoName, tag.imageId)
            imgDetail.displayName = "${repoName}:${tag.name}"
            imgDetail.tag = tag.name
            imgDetail.name = repoName
            imgDetail.pullName = buildPullName(registry, repoName, tag.name)
            if (imgDetail) {
                repo.images.add(imgDetail)
            } else {
                repo.images.add(new Image(description: "Failed to retrieve image detail"))
            }
        }
        repo
    }

    List<Repository> search(final Registry registry, final String query) {
        log.info("Searching for images from $registry")
        final repoList = []
        def url = "${registry.toUrl(RegistryAction.SEARCH).toString()}"

        log.info("Query provided for search is ${query}")
        if (query) {
            url += "?q=${query}"
        }

        def http = new HTTPBuilder(url)

        log.info("Getting repositories from ${http.getUri()}")

        http.request(Method.GET, ContentType.JSON) {
            response.success = { resp, search ->
                log.info("response data for repo list $search $resp")
                if ("v2" == registry.apiVersion) {
                    search.repositories.each { repo ->
                        final repository = new Repository(name: repo, tags: getTags(registry, repo))
                        repoList.add(repository)
                    }
                } else {
                    search.results.each { repo ->
                        final repository = new Repository(name: repo.name.toString(), tags: getTags(registry, repo.name.toString()))
                        repoList.add(repository)
                    }
                }
                log.info("response data for repo mapped $repoList")
            }
        }
        repoList
    }

    List<Tag> getTags(final Registry registry, final repoName) {
        def tagList = []
        log.info("Getting tags for $repoName")
        def url
        if ("v2" == registry.apiVersion) {
            url = "${registry.toUrl()}/${repoName}/tags/list"
        } else {
            url = "${registry.toUrl()}/repositories/${repoName}/tags"
        }
        log.info("tags url $url")
        def http = new HTTPBuilder(url)
        http.request(Method.GET, ContentType.JSON) {
            response.failure = { HttpResponse resp ->
                log.error("Could not retrieve tags: ${resp.statusLine}")
            }
            response.success = { resp, tags ->
                log.info("Got tags $tags")
                if ("v2" == registry.apiVersion) {
                    if (tags.tags) {
                        tagList = tags.tags.collect { k ->
                            new Tag(name: k, imageId: k)
                        }
                    }
                }else {
                    tagList = tags.collect { k, v ->
                        new Tag(name: k.toString(), imageId: v.toString())
                    }
                }
            }
        }
        tagList
    }

    Image getImageDetail(final Registry registry,final String repoName, final String imgId) {
        log.info("getting image $imgId")
        def img = null
        def http
        if ("v2" == registry.apiVersion) {
            http = new HTTPBuilder("${registry.toUrl()}/${repoName}/manifests/${imgId}")
        } else {
            http = new HTTPBuilder("${registry.toUrl()}/images/${imgId}/json")
        }
        http.request (Method.GET, ContentType.JSON) {
            response.success = { imgResp, Map imgData  ->
                log.info("Image data (${imgData.getClass()}) is $imgData")
                img = new Image(imgData)
            }

            response.failure = { resp ->
                log.info("Failed to get img ${repoName}:$imgId $resp")
                img = null
            }
        }
        img
    }

    boolean delete(final Registry registry, final String repoName, final String tag) {
        final uri = "${registry.toUrl()}/repositories/$repoName/tags/$tag"
        log.info("Deleting repo at $uri")
        def http = new HTTPBuilder(uri)
        def result = true
        http.request(Method.DELETE, groovyx.net.http.ContentType.JSON) {
            response.success = { resp, json ->
                log.info("Repo $repoName deleted: $json")
            }

            response.failure = { resp ->
                log.error("Failed to delete $uri: $resp")
                result = false
            }
        }
        result
    }

    String buildPullName(Registry registry, String repoName, String tag) {
        def url = registry.toUrl().toURL()
        "${url.authority}/${repoName}:${tag}"
    }

    boolean ping(Registry registry) {
        def url = registry.toUrl(RegistryAction.PING)
        def http = new HTTPBuilder(url)
        def result = true
        try {

            http.getClient().getParams().setParameter("http.connection.timeout", new Integer(10000))
            http.getClient().getParams().setParameter("http.socket.timeout", new Integer(10000))

            http.request(Method.GET, groovyx.net.http.ContentType.JSON) {
                response.success = { resp, json ->
                    log.info("Ping of $registry succeeded")
                }

                response.failure = { resp ->
                    log.error("Failed to ping $url: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}")
                    result = false
                }
            }
        } catch (final IOException e) {
            log.info("Ping failed: $e")
            result = false
        } finally {
            http.shutdown()
        }
        result
    }
}
