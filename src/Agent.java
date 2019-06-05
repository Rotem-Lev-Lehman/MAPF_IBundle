public class Agent {
    private Graph graph;
    private Vertex start_vertex;
    private Vertex goal_vertex;

    public Agent(Graph graph, Vertex start_vertex, Vertex goal_vertex) {
        this.graph = graph;
        this.start_vertex = start_vertex;
        this.goal_vertex = goal_vertex;
    }

    public Graph getGraph() {
        return graph;
    }

    public Vertex getStart_vertex() {
        return start_vertex;
    }

    public Vertex getGoal_vertex() {
        return goal_vertex;
    }
}
