import java.util.*;

public class Graph {
    private List<Edge> edges;
    private HashMap<Location_Indicator,Vertex> vertexes;
    private double lowest_cost;

    public Graph() {
        this.edges = new ArrayList<>();
        this.vertexes = new HashMap<>();
        lowest_cost = Double.MAX_VALUE;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Collection<Vertex> getVertexes() {
        return vertexes.values();
    }

    public double getLowest_cost() {
        return lowest_cost;
    }

    public void addVertex(Location_Indicator location_indicator,Vertex vertex){
        vertexes.put(location_indicator,vertex);
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

    public Vertex get_Vertex_By_Indicator(Location_Indicator location_indicator){
        return vertexes.get(location_indicator);
    }
}
