package docker.registry.web

import docker.registry.web.support.Search

class SearchController {

    def searchService

    def index() { }

    def search(String q) {
        final resultMap = searchService.searchAll(new Search(query: q))
        render view: "/repository/index", model: [registryToRepoMap: resultMap]
    }
}
