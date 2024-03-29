import java.util.*;

public class Graph {
    private List<Edge> edges;
    private HashMap<Location_Indicator, Vertex> vertexes;
    private double lowest_cost;
    private double highest_cost;

    public Graph() {
        this.edges = new ArrayList<>();
        this.vertexes = new HashMap<>();
        lowest_cost = Double.MAX_VALUE;
        highest_cost = 0;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Collection<Vertex> getVertexes() {
        return vertexes.values();
    }

    public boolean locationExists(Location_Indicator location_indicator) {
        return vertexes.containsKey(location_indicator);
    }

    public double getLowest_cost() {
        return lowest_cost;
    }

    public void addVertex(Vertex vertex) {
        vertexes.put(vertex.getLocation_indicator(), vertex);
        if (vertex.getStay_cost() < lowest_cost)
            lowest_cost = vertex.getStay_cost();
        if (vertex.getStay_cost() > highest_cost)
            highest_cost = vertex.getStay_cost();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        if (edge.getTravel_cost() < lowest_cost)
            lowest_cost = edge.getTravel_cost();
        if (edge.getTravel_cost() > highest_cost)
            highest_cost = edge.getTravel_cost();
    }

    public void cost_updated(double updated_cost) {
        if (updated_cost < lowest_cost)
            lowest_cost = updated_cost;
        if (updated_cost > highest_cost)
            highest_cost = updated_cost;
    }

    public Vertex get_Vertex_By_Indicator(Location_Indicator location_indicator) {
        Vertex ans = vertexes.get(location_indicator);
        if(ans == null) {
            try {
                throw new Exception("The Point: " + location_indicator.toString() + " is not on the map!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ans;
    }

    public double getMaxCostForPath() {
        return vertexes.size() * 3 * highest_cost;
    }
}
