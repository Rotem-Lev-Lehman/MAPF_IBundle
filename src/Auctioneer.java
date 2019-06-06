import java.util.ArrayList;
import java.util.List;

public class Auctioneer {
    List<MDD_Agent> agents;

    public boolean solveMDD(List<MDD_Agent> agentsToSolve){
        this.agents = agentsToSolve;
        List<Bid> bids = new ArrayList<>();
        for (MDD_Agent agent:agents) {
            bids.add(agent.MakeABid());
        }
        if(checkForSolution(bids)){
            return true;
        }

        return false;
    }

    private boolean checkForSolution(List<Bid> bids) {
        if(bids.isEmpty()==false){
            List<MDD> combination;
            for(MDD mdd:bids.get(0).getMDDs()){
                combination = new ArrayList<>();
                combination.add(mdd);
                if(create_Combination(bids,combination,1)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean create_Combination(List<Bid> bids, List<MDD> combination, int i) {
        if(bids.size()==i)
            return check_Combination(combination);
        List<MDD> continue_Combination;
        for(MDD mdd:bids.get(i).getMDDs()){
            continue_Combination = combination;
            continue_Combination.add(mdd);
            if(create_Combination(bids,combination,i+1)){
                return true;
            }
        }
        return false;
    }

    private boolean check_Combination(List<MDD> combination) {
        // TODO: 06-Jun-19
        return false;
    }
}
