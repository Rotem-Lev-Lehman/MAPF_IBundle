import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private Graph graph;
    private List<Edge> edges;
    private double stay_cost;

    public Vertex(Graph graph) {
        this.graph = graph;
        edges=new ArrayList<>();
        stay_cost=1;
    }

    public Vertex(Graph graph, double stay_cost) {
        this.graph = graph;
        edges=new ArrayList<>();
        this.stay_cost = stay_cost;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public double getStay_cost() {
        return stay_cost;
    }

    public void setStay_cost(double stay_cost) {
        this.stay_cost = stay_cost;
        graph.cost_updated(stay_cost);
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }
}
