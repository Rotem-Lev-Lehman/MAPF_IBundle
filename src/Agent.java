public class Agent extends AProblem{
    private Path final_path;

    public Agent(Graph graph, Vertex start, Vertex goal) {
        super(graph, start, goal);
        final_path = null;
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

    @Override
    public double getHeuristic(Vertex vertex) {
        return vertex.getLocation_indicator().getHeuristic(goal_vertex.getLocation_indicator());
    }

    public Path getFinal_path() {
        return final_path;
    }

    public void setFinal_path(Path final_path) {
        this.final_path = final_path;
    }
}
