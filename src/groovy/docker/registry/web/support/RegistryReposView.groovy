package docker.registry.web.support;

import docker.registry.web.Registry;

/**
 * Defines the view of a registry and their docker repositories, with a set
 * of extra attributes from the current state of the registries in the given
 * Registry server.
 */
class RegistryReposView {

    /**
     * The registry that was contacted.
     */
    Registry registry
    /**
     * The current list of repositories of the registry.
     */
    Set<Repository> repositories = [] as Set

    /**
     * Whether this registry is reachable or not based on the attempt to reach its URL.
     */
    boolean isReachable = false

    /**
     * @return An instance of RegistryReposView that associates a registry to a set of repos
     * and the status.
     * TODO: if other attributes are needed, we may extend this to a builder method.
     */
    def static RegistryReposView make(Registry registry, List<Repository> repos, Boolean isReachable) {
        def instance = new RegistryReposView()
        instance.registry = registry
        instance.repositories.addAll(repos)
        instance.isReachable = isReachable
        instance
    }
}
