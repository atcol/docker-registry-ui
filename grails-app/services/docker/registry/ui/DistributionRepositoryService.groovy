package docker.registry.ui

import docker.registry.web.Registry
import docker.registry.web.support.Image
import docker.registry.web.support.Repository
import docker.registry.web.support.Tag


class DistributionRepositoryService implements RepositorySource {
    @Override
    List<Repository> index(Registry registry) {
        return null
    }

    @Override
    def Repository detail(Registry registry, String repoName) {
        return null
    }

    @Override
    List<Repository> search(Registry registry, String query) {
        return null
    }

    @Override
    List<Tag> getTags(Registry registry, Object repoName) {
        return null
    }

    @Override
    Image getImageDetail(Registry registry, String imgId) {
        return null
    }

    @Override
    boolean delete(Registry registry, String repoName, String tag) {
        return false
    }

    @Override
    boolean ping(Registry registry) {
        def url = registry.toUrl()
        return ping(url)
    }
}
