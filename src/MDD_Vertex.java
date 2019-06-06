import java.util.ArrayList;
import java.util.List;

public class MDD_Vertex {
    private Vertex original_vertex;
    private MDD mdd;
    private List<MDD_Edge> going_out_edges;
    private double cost_until_me;
    private int time;

    public MDD_Vertex(Vertex original_vertex, MDD mdd, int time, double cost_until_me) {
        this.original_vertex = original_vertex;
        this.mdd = mdd;
        going_out_edges = new ArrayList<>();
        this.cost_until_me = cost_until_me;
        this.time = time;
    }

    public Vertex getOriginal_vertex() {
        return original_vertex;
    }

    public MDD getMdd() {
        return mdd;
    }

    public List<MDD_Edge> getGoing_out_edges() {
        return going_out_edges;
    }

    public double getCost_until_me() {
        return cost_until_me;
    }

    public void addMDD_Edge(MDD_Edge edge){
        going_out_edges.add(edge);
    }

    public int getTime() {
        return time;
    }
}
