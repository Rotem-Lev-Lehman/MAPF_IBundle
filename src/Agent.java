import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Agent {
    private Graph graph;
    private Vertex start_vertex;
    private Vertex goal_vertex;
    private List<MDD> current_MDDs;
    private Path final_path;

    public Agent(Graph graph, Vertex start_vertex, Vertex goal_vertex) {
        this.graph = graph;
        this.start_vertex = start_vertex;
        this.goal_vertex = goal_vertex;
        current_MDDs = new ArrayList<>();
        final_path = null;
    }

    public Graph getGraph() {
        return graph;
    }

    public Vertex getStart_vertex() {
        return start_vertex;
    }

    public Vertex getGoal_vertex() {
        return goal_vertex;
    }

    public List<MDD> getCurrent_MDDs() {
        return current_MDDs;
    }

    public void addMinToCurrentMDDs(){
        for(MDD mdd : current_MDDs){
            mdd.addMinToBidding_cost();
        }
    }

    public double getMaxMDDTotalCost(){
        double max = 0;
        for(MDD mdd : current_MDDs){
            double curr = mdd.getTotalCost();
            if(max < curr)
                max = curr;
        }
        return max;
    }

    public void addMDD(MDD mdd){
        current_MDDs.add(mdd);
    }

    public Bid MakeABid(){
        return new Bid(this, current_MDDs);
    }

    public void calculateMoreMDDs() {
        //todo calculate with Searching algorithm until the depth of getMaxMDDTotalCost()
        throw new NotImplementedException();
    }

    public Path getFinal_path() {
        return final_path;
    }

    public void setFinal_path(Path final_path) {
        this.final_path = final_path;
    }
}
