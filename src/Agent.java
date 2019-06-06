import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Agent extends AProblem{

    private List<MDD> current_MDDs;
    private Path final_path;

    public Agent(Graph graph, Vertex start, Vertex goal) {
        super(graph, start, goal);
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

    @Override
    public double getHeuristic(Vertex vertex) {
        return vertex.getLocation_indicator().getHeuristic(goal_vertex.getLocation_indicator());
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
