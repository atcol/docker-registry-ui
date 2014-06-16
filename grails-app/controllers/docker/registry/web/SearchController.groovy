package docker.registry.web

import docker.registry.web.support.Result
import docker.registry.web.support.Search

class SearchController {

    def searchService

    def index() { }

    def search(String q) {
        final List<Result> resultList = searchService.search(new Search(query: q))
        render view: "results", model: [results: resultList]
    }
}
