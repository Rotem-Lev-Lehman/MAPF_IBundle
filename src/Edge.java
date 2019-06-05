public class Edge {
    private Graph graph;
    private Vertex vertex_from;
    private Vertex vertex_to;
    private double travel_cost;

    public Edge(Graph graph, Vertex vertex_from, Vertex vertex_to, double travel_cost) {
        this.graph = graph;
        this.vertex_from = vertex_from;
        this.vertex_to = vertex_to;
        this.travel_cost = travel_cost;
    }

    public Edge(Graph graph, Vertex vertex_from, Vertex vertex_to) {
        this.graph = graph;
        this.vertex_from = vertex_from;
        this.vertex_to = vertex_to;
        this.travel_cost=1;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Vertex getVertex_from() {
        return vertex_from;
    }

    public void setVertex_from(Vertex vertex_from) {
        this.vertex_from = vertex_from;
    }

    public Vertex getVertex_to() {
        return vertex_to;
    }

    public void setVertex_to(Vertex vertex_to) {
        this.vertex_to = vertex_to;
    }

    public double getTravel_cost() {
        return travel_cost;
    }

    public void setTravel_cost(double travel_cost) {
        this.travel_cost = travel_cost;
        graph.cost_updated(travel_cost);

    }
}
