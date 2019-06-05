import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Edge> edges;
    private List<Vertex> vertexes;
    private double lowest_cost;

    public Graph() {
        this.edges = new ArrayList<>();
        this.vertexes = new ArrayList<>();
        lowest_cost = Double.MAX_VALUE;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public double getLowest_cost() {
        return lowest_cost;
    }

    public void addVertex(Vertex vertex){
        vertexes.add(vertex);
        if(vertex.getStay_cost()<lowest_cost)
            lowest_cost = vertex.getStay_cost();
    }

    public void addEdge(Edge edge){
        edges.add(edge);
        if(edge.getTravel_cost()<lowest_cost)
            lowest_cost = edge.getTravel_cost();
    }

    public void cost_updated(double updated_cost) {
        if(updated_cost<lowest_cost)
            lowest_cost=updated_cost;
    }
}
