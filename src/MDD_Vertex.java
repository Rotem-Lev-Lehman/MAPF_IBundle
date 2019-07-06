import java.util.*;

public class MDD_Vertex {
    private Vertex original_vertex;
    private MDD mdd;
    private List<MDD_Edge> going_out_edges;
    private HashMap<Vertex,MDD_Vertex> neighbors;
    private double cost_until_me;
    private int time;

    public MDD_Vertex(Vertex original_vertex, MDD mdd, int time, double cost_until_me) {
        this.original_vertex = original_vertex;
        this.mdd = mdd;
        going_out_edges = new ArrayList<>();
        this.cost_until_me = cost_until_me;
        this.time = time;
        this.neighbors = new HashMap<>();
    }

    public Vertex getOriginal_vertex() {
        return original_vertex;
    }

    public MDD getMdd() {
        return mdd;
    }
    /*
    public List<MDD_Edge> getGoing_out_edges() {
        return going_out_edges;
    }
    */

    public double getCost_until_me() {
        return cost_until_me;
    }

    public void addMDD_Edge(MDD_Edge edge){
        going_out_edges.add(edge);
        MDD_Vertex to = edge.getTo_MDD_vertex();
        neighbors.put(to.original_vertex,to);
    }

    public int getTime() {
        return time;
    }

    public Collection<MDD_Vertex> getNeighbors(){
        return neighbors.values();
    }

    public boolean checkIfNeighbor(MDD_Vertex to){
        MDD_Vertex vertex = neighbors.get(to.original_vertex);
        return to.equals(vertex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MDD_Vertex vertex = (MDD_Vertex) o;
        return time == vertex.time &&
                Objects.equals(original_vertex, vertex.original_vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original_vertex, time);
    }
}
