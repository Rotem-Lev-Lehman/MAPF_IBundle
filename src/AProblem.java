public abstract class AProblem {
    protected Graph graph;
    protected Vertex start_vertex;
    protected Vertex goal_vertex;

    public AProblem(Graph graph, Vertex start, Vertex goal) {
        this.graph = graph;
        this.start_vertex = start;
        this.goal_vertex = goal;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Vertex getStart_vertex() {
        return start_vertex;
    }

    public void setStart_vertex(Vertex start_vertex) {
        this.start_vertex = start_vertex;
    }

    public Vertex getGoal_vertex() {
        return goal_vertex;
    }

    public void setGoal_vertex(Vertex goal_vertex) {
        this.goal_vertex = goal_vertex;
    }

    public abstract double getHeuristic(Vertex vertex);
}
