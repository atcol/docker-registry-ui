package docker.registry.web.support


class Search {
    String query
    List<Result> results

    @Override
    public String toString() {
        return "Search{" +
                "query='" + query + '\'' +
                ", results=" + results +
                '}';
    }
}
