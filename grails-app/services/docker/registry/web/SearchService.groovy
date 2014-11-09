package docker.registry.web

import docker.registry.web.support.Search
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SearchService {

    def repositoryService

    def searchAll(final Search search) {
        log.info("Search query is ${search.query}")
        final searchResults = [:]

        Registry.all.each { Registry registry ->
            searchResults.put(registry, repositoryService.search(registry, search.query))
        }
        log.info("Results are $searchResults")
        searchResults
    }
}
