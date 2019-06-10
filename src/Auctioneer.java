import java.util.ArrayList;
import java.util.List;

public class Auctioneer {

    private List<MDD_Agent> agents;
    private int makespan;
    private double highestProfit;
    private List<MDD_Agent> bestList;
    private List<Bid> bestBids;
    private List<Path> solutions;

    public boolean solve(List<MDD_Agent> agentsToSolve){
        for (MDD_Agent agent : agentsToSolve){
            agent.calculateFirstMDD();
        }
        while (true){
            solutions=null;
            List<Bid> allBids = new ArrayList<>();
            for (MDD_Agent agent : agentsToSolve) {
                allBids.add(agent.MakeABid());
            }
            for(int i=agentsToSolve.size();i>0;i--){
                boolean solved = false;
                List<List<MDD_Agent>> agentsCombinations = getAgentsCombinations(agentsToSolve,i);
                for (List<MDD_Agent> agentsList : agentsCombinations){
                    if(solveMDD(agentsList)) {
                        solved = true;
                    }
                }
                if(solved){
                    if(bestList.size() == agentsToSolve.size()){
                        for(int k=0;k<bestBids.size();k++){
                            bestBids.get(k).Accept(solutions.get(k));
                        }
                        return true;
                    }
                    else{
                        for (int j=0;j<agentsToSolve.size();j++){
                            if (bestList.contains(agentsToSolve.get(j))==false){
                                allBids.get(j).Decline();
                            }
                        }
                        break;
                    }
                }
            }
            boolean hasChance = false;
            for (MDD_Agent agent : agentsToSolve){
                if(agent.passedMaxCostForGraph()==false){
                    hasChance = true;
                    break;
                }
            }
            if(hasChance == false)
                return false;
        }
    }

    private List<List<MDD_Agent>> getAgentsCombinations(List<MDD_Agent> agentsToSolve, int size) {
        // TODO: 10-Jun-19 need to test, not sure about the "list"
        List<List<MDD_Agent>> list = new ArrayList<>();
        List<MDD_Agent> currentList = new ArrayList<>();
        createAgentsCombination(agentsToSolve,list,currentList,size,0);
        return list;
    }

    private void createAgentsCombination(List<MDD_Agent> agentsToSolve, List<List<MDD_Agent>> list, List<MDD_Agent> currentList, int size, int i) {
        if(currentList.size()==size){
            list.add(currentList);
            return;
        }
        if(i>=agentsToSolve.size() || (currentList.size()+(agentsToSolve.size()-i)<size)){
            return;
        }
        List<MDD_Agent> noCurrentList = new ArrayList<>(currentList);
        List<MDD_Agent> yesCurrentList = new ArrayList<>(currentList);
        yesCurrentList.add(agentsToSolve.get(i));
        createAgentsCombination(agentsToSolve,list,noCurrentList,size,i+1);
        createAgentsCombination(agentsToSolve,list,yesCurrentList,size,i+1);
    }

    private boolean solveMDD(List<MDD_Agent> agentsToSolve){
        this.agents = agentsToSolve;
        List<Bid> bids = new ArrayList<>();
        for (MDD_Agent agent:agents) {
            bids.add(agent.MakeABid());
        }
        makespan = fixBidsLength(bids);
        if(checkForSolution(agentsToSolve,bids)){
            return true;
        }
        else {
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

    private boolean checkForSolution(List<MDD_Agent> agentsToSolve, List<Bid> bids) {
        if(bids.isEmpty()==false){
            List<MDD> combination = new ArrayList<>();
            if(create_Combination(agentsToSolve,bids,combination,0)){
                return true;
            }
        }
        return false;
    }

    private boolean create_Combination(List<MDD_Agent> agentsToSolve, List<Bid> bids, List<MDD> combination, int bidNum) {
        if(bids.size()==bidNum)
            return check_Combination(agentsToSolve,bids,combination);

        List<MDD> continue_Combination;
        for(MDD mdd : bids.get(bidNum).getMDDs()){
            continue_Combination = new ArrayList<>(combination);
            continue_Combination.add(mdd);
            if(create_Combination(agentsToSolve, bids,continue_Combination,bidNum+1)){
                return true;
            }
        }
        return false;
    }

    private boolean check_Combination(List<MDD_Agent> agentsToSolve, List<Bid> bids, List<MDD> mddCombination) {
        if(bids.size() == mddCombination.size() && bids.isEmpty()==false){
            List<MDD_Path> paths = new ArrayList<>();
            for(int i=0;i<bids.size();i++){
                MDD_Path path = new MDD_Path(bids.get(i));
                path.addVertex(mddCombination.get(i).getStart_MDD_vertex());
                paths.add(path);
            }
            if(create_Path_Combinations(agentsToSolve,bids,paths,1,0)){
                return true;
            }
        }
        return false;
    }

    private boolean create_Path_Combinations(List<MDD_Agent> agentsToSolve, List<Bid> bids, List<MDD_Path> paths, int time, int bidNum) {
        if(time == makespan){
            if(betterThanSolution(paths)){
                bestList = agentsToSolve;
                solutions = new ArrayList<>();
                bestBids=bids;
                for (int i=0;i<bids.size();i++){
                    Bid curr = bids.get(i);
                    Vertex goal = curr.getAgent().getGoal_vertex();
                    solutions.add(paths.get(i).getPath(goal));
                }
            }
            return true;
        }
        if(bidNum == bids.size()){
            return create_Path_Combinations(agentsToSolve, bids,paths,time + 1,0);
        }
        for(MDD_Vertex neighbor : paths.get(bidNum).getLast().getNeighbores()){
            boolean conflict = false;
            for (int i=0;i<bidNum;i++){
                if (paths.get(i).getLast().equals(neighbor)){
                    conflict = true;
                    break;
                }
                if(time>0){
                    if(paths.get(i).get(paths.get(i).size()-2).getOriginal_vertex().equals(neighbor.getOriginal_vertex()) && paths.get(i).getLast().getOriginal_vertex().equals(paths.get(bidNum).getLast().getOriginal_vertex())){
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
            if(create_Path_Combinations(agentsToSolve, bids,continue_Path,time,bidNum + 1)){
                return true;
            }
        }
        return false;
    }

    private boolean betterThanSolution(List<MDD_Path> paths) {
        if(solutions == null){
            return true;
        }
        else {
            // TODO: 10-Jun-19

            //naive try
            if(paths.size()>solutions.size())
                return true;
            else if(paths.size()<solutions.size())
                return false;
            else {
                int current =0;
                int newSol =0;
                for (int i=0;i<paths.size();i++){
                    for(int j=solutions.get(i).getVertexes().size()-2;j>0;j--){
                        if(solutions.get(i).getVertexes().get(j).equals(solutions.get(i).getVertexes().get(j+1)))
                            current++;
                    }
                    for(int j=paths.get(i).getVertexes().size()-2;j>0;j--){
                        if(paths.get(i).getVertexes().get(j).getOriginal_vertex().equals(paths.get(i).getVertexes().get(j+1).getOriginal_vertex()))
                            newSol++;
                    }
                }
                if(newSol<current)
                    return true;
                else
                    return false;
            }
            //\naive try
        }
    }
}
