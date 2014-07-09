package docker.registry.ui

class UtilTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    static namespace = "util"

    def repositoryService

    /**
     * @attr registry the registry
     * @attr repoName
     * @attr tag
     */
    def generatePullName = { attrs, body ->
        out << repositoryService.buildPullName(attrs.registry, attrs.repoName, attrs.tag)
    }
}
