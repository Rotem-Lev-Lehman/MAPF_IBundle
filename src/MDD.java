import java.util.ArrayList;
import java.util.List;

public class MDD{
    private Graph original_graph;
    private List<MDD_Edge> mdd_edges;
    private List<MDD_Vertex> mdd_vertexes;
    private MDD_Vertex start_MDD_vertex;
    private MDD_Vertex goal_MDD_vertex;
    private double bidding_cost;
    private double MDD_cost;
    private int total_time;
    //private Map<Integer, List<MDD_Vertex>>

    public MDD(Graph original_graph, double bidding_cost) {
        this.original_graph = original_graph;
        //this.start_MDD_vertex = new MDD_Vertex(start_vertex, this, 0);
        this.start_MDD_vertex = null;
        this.goal_MDD_vertex = null;
        this.bidding_cost = bidding_cost;
        mdd_edges = new ArrayList<>();
        mdd_vertexes = new ArrayList<>();
        MDD_cost = 0;
        total_time = 0;
    }

    public MDD(Graph original_graph) {
        this.original_graph = original_graph;
        //this.start_MDD_vertex = new MDD_Vertex(start_vertex, this, 0);
        this.start_MDD_vertex = null;
        this.goal_MDD_vertex = null;
        bidding_cost = 0;
        mdd_edges = new ArrayList<>();
        mdd_vertexes = new ArrayList<>();
        MDD_cost = 0;
        total_time = 0;
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

    public void setStart_MDD_vertex(MDD_Vertex start_MDD_vertex) {
        this.start_MDD_vertex = start_MDD_vertex;
    }

    public MDD_Vertex getGoal_MDD_vertex() {
        return goal_MDD_vertex;
    }

    public void setGoal_MDD_vertex(MDD_Vertex goal_MDD_vertex) {
        this.goal_MDD_vertex = goal_MDD_vertex;
    }

    public double getMDD_cost() {
        return MDD_cost;
    }

    public double getTotalCost(){
        return MDD_cost + bidding_cost;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }
}
