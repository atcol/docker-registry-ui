package docker.registry.web

import docker.registry.web.support.Image
import docker.registry.web.support.Search
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SearchService {

    def search(final Search search) {
        log.info("Search query is ${search.query}")
        final searchResults = [:]

        Registry.all.each { Registry registry ->
            final uri = registry.url
            log.info("search URI is $uri")
            def imgList = []
            withHttp (uri: uri) {
                def result = get(
                        path: "/${registry.apiVersion}/search",
                        query: [q: search.query.toString()])
                log.info("Search result is $result")
                result.results.each {
                    imgList.add(new Image(name: it.name))
                }
                searchResults.put(registry, imgList)
            }
        }
        log.info("Results are $searchResults")
        searchResults
    }
}
