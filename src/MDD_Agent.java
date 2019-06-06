import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class MDD_Agent extends Agent {
    private List<MDD> current_MDDs;
    public MDD_Agent(Graph graph, Vertex start, Vertex goal) {
        super(graph, start, goal);
        current_MDDs = new ArrayList<>();
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
}
