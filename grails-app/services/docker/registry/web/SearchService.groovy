package docker.registry.web

import docker.registry.web.support.Image
import docker.registry.web.support.Search
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SearchService {

    def search(final Search search) {
        final searchResults = []

        Registry.all.each { Registry registry ->
            final uri = registry.url
            log.info("URI is $uri")
            withHttp (uri: uri) {
                def result = get(
                        path: "/${registry.apiVersion}/search",
                        query: [q: search.query.toString()])
                log.info("Search result is $result")
                result.results.each {
                    searchResults.add(new Image(name: it.name))
                }
            }
            log.info("Results are $searchResults")
        }
        searchResults
    }
}
