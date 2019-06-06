import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MDD{
    private Graph original_graph;
    private List<MDD_Edge> mdd_edges;
    private List<MDD_Vertex> mdd_vertexes;
    private MDD_Vertex start_MDD_vertex;
    private double bidding_cost;
    private double MDD_cost;
    //private Map<Integer, List<MDD_Vertex>>

    public MDD(Graph original_graph, Vertex start_vertex, double bidding_cost) {
        this.original_graph = original_graph;
        //this.start_MDD_vertex = new MDD_Vertex(start_vertex, this, 0);
        this.start_MDD_vertex = null;
        this.bidding_cost = bidding_cost;
        mdd_edges = new ArrayList<>();
        mdd_vertexes = new ArrayList<>();
        MDD_cost = 0;
    }

    public MDD(Graph original_graph, Vertex start_vertex) {
        this.original_graph = original_graph;
        //this.start_MDD_vertex = new MDD_Vertex(start_vertex, this, 0);
        this.start_MDD_vertex = null;
        bidding_cost = 0;
        mdd_edges = new ArrayList<>();
        mdd_vertexes = new ArrayList<>();
        MDD_cost = 0;
    }

    public double getBidding_cost() {
        return bidding_cost;
    }

    public void setBidding_cost(double bidding_cost) {
        this.bidding_cost = bidding_cost;
    }

    public void addMinToBidding_cost() {
        bidding_cost += original_graph.getLowest_cost();
    }

    public void addMDD_Vertex(MDD_Vertex mdd_vertex){
        double vertex_cost = mdd_vertex.getCost_until_me();
        if(MDD_cost < vertex_cost)
            MDD_cost = vertex_cost;

        mdd_vertexes.add(mdd_vertex);
    }

    public void addMDD_Edge(MDD_Edge mdd_edge){
        mdd_edges.add(mdd_edge);
    }

    public Graph getOriginal_graph() {
        return original_graph;
    }

    public List<MDD_Edge> getMdd_edges() {
        return mdd_edges;
    }

    public List<MDD_Vertex> getMdd_vertexes() {
        return mdd_vertexes;
    }

    public MDD_Vertex getStart_MDD_vertex() {
        return start_MDD_vertex;
    }

    public double getMDD_cost() {
        return MDD_cost;
    }

    public double getTotalCost(){
        return MDD_cost + bidding_cost;
    }
}
