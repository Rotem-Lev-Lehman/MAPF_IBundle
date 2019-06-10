import java.util.ArrayList;
import java.util.List;

public class Auctioneer {

    private List<MDD_Agent> agents;
    private int makespan;

    public boolean solveMDD(List<MDD_Agent> agentsToSolve){
        while (solveMDD2(agentsToSolve)==false);
        return true;
    }

    public boolean solveMDD2(List<MDD_Agent> agentsToSolve){
        this.agents = agentsToSolve;
        List<Bid> bids = new ArrayList<>();
        for (MDD_Agent agent:agents) {
            bids.add(agent.MakeABid());
        }
        makespan = fixBidsLength(bids);
        if(checkForSolution(bids)){
            return true;
        }
        else {
            for (Bid bid : bids)
                bid.Decline();
            return false;
        }
    }

    private int fixBidsLength(List<Bid> bids) {
        int maxLength = 0;
        for (Bid bid:bids){
            int length=bid.getMaxLength();
            if(length>maxLength)
                maxLength=length;
        }
        for (Bid bid:bids){
            bid.normalizeTotalTime(maxLength);
        }
        return maxLength;
    }
    

    private boolean checkForSolution(List<Bid> bids) {
        if(bids.isEmpty()==false){
            List<MDD> combination = new ArrayList<>();
            if(create_Combination(bids,combination,0)){
                return true;
            }
        }
        return false;
    }

    private boolean create_Combination(List<Bid> bids, List<MDD> combination, int i) {
        if(bids.size()==i)
            return check_Combination(bids,combination);

        List<MDD> continue_Combination;
        for(MDD mdd:bids.get(i).getMDDs()){
            continue_Combination = new ArrayList<>(combination);
            continue_Combination.add(mdd);
            if(create_Combination(bids,continue_Combination,i+1)){
                return true;
            }
        }
        return false;
    }

    private boolean check_Combination(List<Bid> bids,List<MDD> mddCombination) {
        if(bids.size() == mddCombination.size() && bids.isEmpty()==false){
            List<MDD_Path> paths = new ArrayList<>();
            for(int i=0;i<bids.size();i++){
                MDD_Path path = new MDD_Path(bids.get(i));
                path.addVertex(mddCombination.get(i).getStart_MDD_vertex());
                paths.add(path);
            }
            if(create_Path_Combinations(bids,paths,1,0)){
                return true;
            }
        }
        return false;
    }

    private boolean create_Path_Combinations(List<Bid> bids, List<MDD_Path> paths, int time,int bidNum) {
        if(time == makespan){
            // TODO: 10-Jun-19
            for (int i=0;i<bids.size();i++){
                bids.get(i).Accept(paths.get(i).getPath());
            }
            return true;
        }
        if(bidNum == bids.size()){
            return create_Path_Combinations(bids,paths,time++,0);
        }
        for(MDD_Vertex neighbor : paths.get(bidNum).getLast().getNeighbores()){
            for (int i=0;i<bidNum-1;i++){
                if (paths.get(i).getLast().equals(neighbor)){
                    return false;
                }
            }
            List<MDD_Path> continue_Path = new ArrayList<>(paths);
            MDD_Path path = continue_Path.get(bidNum);
            path.addVertex(neighbor);
            if(create_Path_Combinations(bids,continue_Path,time,bidNum++)){
                return true;
            }
        }
        return false;
    }
}
