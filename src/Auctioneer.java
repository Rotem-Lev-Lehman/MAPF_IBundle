import java.util.ArrayList;
import java.util.List;

public class Auctioneer {

    private List<MDD_Agent> agents;
    private int makespan;

    public boolean solve(List<MDD_Agent> agentsToSolve){
        for (MDD_Agent agent : agentsToSolve)
            agent.calculateFirstMDD();
        while (solveMDD(agentsToSolve)==false){
            boolean hasChance = false;
            for (MDD_Agent agent : agentsToSolve){
                if(agent.passedMaxCostForGraph()==false){
                    hasChance = true;
                    break;
                }
            }
            if(hasChance == false)
                return hasChance;
        }
        return true;
    }

    private boolean solveMDD(List<MDD_Agent> agentsToSolve){
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

    private boolean create_Combination(List<Bid> bids, List<MDD> combination, int bidNum) {
        if(bids.size()==bidNum)
            return check_Combination(bids,combination);

        List<MDD> continue_Combination;
        for(MDD mdd : bids.get(bidNum).getMDDs()){
            continue_Combination = new ArrayList<>(combination);
            continue_Combination.add(mdd);
            if(create_Combination(bids,continue_Combination,bidNum+1)){
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
            for (int i=0;i<bids.size();i++){
                bids.get(i).Accept(paths.get(i).getPath());
            }
            return true;
        }
        if(bidNum == bids.size()){
            return create_Path_Combinations(bids,paths,time + 1,0);
        }
        for(MDD_Vertex neighbor : paths.get(bidNum).getLast().getNeighbores()){
            boolean conflict = false;
            for (int i=0;i<bidNum-1;i++){
                if (paths.get(i).getLast().equals(neighbor)){
                    conflict = true;
                    break;
                }
                if(time>0){
                    if(paths.get(i).get(paths.get(i).size()-2).getOriginal_vertex().equals(neighbor.getOriginal_vertex()) && paths.get(i).getLast().getOriginal_vertex().equals(paths.get(bidNum).get(paths.get(bidNum).size()-2).getOriginal_vertex())){
                        conflict = true;
                        break;
                    }
                }
            }
            if (conflict)
                continue;
            List<MDD_Path> continue_Path = new ArrayList<>(paths);
            MDD_Path path = continue_Path.get(bidNum);
            path.addVertex(neighbor);
            if(create_Path_Combinations(bids,continue_Path,time,bidNum + 1)){
                return true;
            }
        }
        return false;
    }
}
