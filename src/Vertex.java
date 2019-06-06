import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex {
    private Graph graph;
    private List<Edge> edges;
    private double stay_cost;
    private Location_Indicator location_indicator;

    public Vertex(Graph graph, Location_Indicator location_indicator) {
        this.graph = graph;
        this.location_indicator = location_indicator;
        stay_cost =1 ;
        edges = new ArrayList<>();
    }

    public Vertex(Graph graph, double stay_cost, Location_Indicator location_indicator) {
        this.graph = graph;
        this.stay_cost = stay_cost;
        this.location_indicator = location_indicator;
        edges = new ArrayList<>();
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
        graph.addEdge(edge);
    }

    public Location_Indicator getLocation_indicator() {
        return location_indicator;
    }

    public void setLocation_indicator(Location_Indicator location_indicator) {
        this.location_indicator = location_indicator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(location_indicator, vertex.location_indicator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location_indicator);
    }

    @Override
    public String toString() {
        return location_indicator.toString();
    }
}
