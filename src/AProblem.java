public abstract class AProblem {
    private Graph graph;
    private Vertex start;
    private Vertex goal;

    public AProblem(Graph graph, Vertex start, Vertex goal) {
        this.graph = graph;
        this.start = start;
        this.goal = goal;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getGoal() {
        return goal;
    }

    public void setGoal(Vertex goal) {
        this.goal = goal;
    }

    public abstract double getHeuristic(Vertex vertex);
}
