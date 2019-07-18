import java.util.*;

public class AStarDeep implements ISearcher, IDeepening_Searcher {

    private PriorityQueue<SearchingVertex> open;
    private HashSet<Vertex> close;
    private HashMap<Vertex, Double> minGForVertex;
    private double max_cost;
    private AProblem problem;
    private List<SearchingVertex> finished;
    private int iterationMax;

    @Override
    public List<SearchingVertex> searchDeepening(AProblem problem, double minCost, double maxCost, int iterationMax) throws InterruptedException {
        /*double costJump = problem.getGraph().getLowest_cost();
        for (double currCost = minCost; currCost <= maxCost; currCost += costJump) {
            List<Path> paths = searchBFS(problem, minCost,currCost,true);
            if(paths != null)
                return paths;
        }
        return null; //There is no more*/
        return searchBFS(problem,minCost,maxCost,true, iterationMax);
    }

    @Override
    public List<SearchingVertex> search(AProblem problem, double maxCost) throws InterruptedException {
        return searchBFS(problem,0,maxCost,false, 0);
    }


    private List<SearchingVertex> searchBFS(AProblem problem, double min_cost, double max_cost, boolean deepening, int iterationMax) throws InterruptedException {
        this.problem = problem;
        this.max_cost = max_cost;
        open = new PriorityQueue<>();
        close = new HashSet<>();
        //amountForVertex = new HashMap<>();
        finished = new ArrayList<>();
        this.iterationMax = iterationMax;
        this.minGForVertex = new HashMap<>();

        double h = problem.getHeuristic(problem.getStart_vertex());
        SearchingVertex first = new SearchingVertex(problem.getStart_vertex(),0, h);

        this.minGForVertex.put(first.getVertex(), 0.0);

        addToOpen(first);
        while (!open.isEmpty()){
            if(Thread.interrupted())
                throw new InterruptedException("Auctioneer was interrupted");

            SearchingVertex curr = open.poll();
            //Vertex currVer = curr.getVertex();
            if(curr.getF() > this.max_cost) // check for the nodes that are already in the open and then we found a better goal.
                continue;
            if(curr.getVertex().equals(problem.getGoal_vertex())) {
                if(!deepening){
                    if(curr.getF()<=this.max_cost){
                        this.max_cost=curr.getF();
                        finished.add(curr);
                    }
                    else{
                        //return reconstructPaths();
                        return finished;
                    }
                }
                else if(curr.getF()>=min_cost){
                    finished.add(curr);
                }
                continue;
            }

            if(deepening){
                Double minG = minGForVertex.get(curr.getVertex());
                if(minG == null){
                    minGForVertex.put(curr.getVertex(), curr.getG());
                }
                else{
                    if(minG + iterationMax < curr.getG()) {
                        continue;
                    }
                    else if(minG > curr.getG()) {
                        minGForVertex.put(curr.getVertex(), curr.getG());
                    }
                }
            }

            double g;
            for(Edge edge : curr.getVertex().getEdges()){
                Vertex neighbor = edge.getVertex_to();
                g = curr.getG() + edge.getTravel_cost();
                checkNeighbor(curr, neighbor, g,deepening);
            }
            if(!deepening){
                close.add(curr.getVertex());
            }
            else {

                /*
                Integer amountForCurr = amountForVertex.get(curr.getVertex());

                if (amountForCurr == null) {
                    amountForCurr = 0;
                }

                if (amountForCurr > iterationMax)
                    continue;

                amountForVertex.put(curr.getVertex(), amountForCurr + 1);

                */

                g = curr.getG() + curr.getVertex().getStay_cost();
                checkNeighbor(curr, curr.getVertex(), g, deepening);
            }
        }

        //return reconstructPaths();
        return finished;
    }

    private void checkNeighbor(SearchingVertex prev, Vertex neighbor, double g, boolean deepening) {
        //int waiting = prev.getAmountWaited();
        if(!deepening) {
            if (close.contains(neighbor))
                return;
        }
        else {
            Double minG = minGForVertex.get(neighbor);
            if(minG != null){
                if(minG + iterationMax < g)
                    return;
                else if(minG > g) {
                    minGForVertex.put(neighbor, g);
                    /*
                    try {
                        throw new Exception("Weird!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    */
                }
            }
        }
        /*
        else{
            if (prev.getVertex().equals(neighbor)){
                waiting++;
            }
            else {
                SearchingVertex tmp = prev.getPrev();
                int counter = 1;
                while (tmp != null){
                    counter++;
                    if(tmp.getVertex().equals(neighbor)){
                        waiting += counter;
                        break;
                    }
                    tmp=tmp.getPrev();
                }
            }
            if(this.iterationMax < waiting){
                return;
            }
        }
        */
        double h = problem.getHeuristic(neighbor);
        SearchingVertex vertex = new SearchingVertex(neighbor, prev, g, h/*,waiting*/);
        addToOpen(vertex);
    }

    private List<Path> reconstructPaths() {
        List<Path> paths = new ArrayList<>();
        for(SearchingVertex vertex : finished){
            Path path = new Path();
            SearchingVertex curr = vertex;
            while (curr != null){
                path.addFromStart(curr.getVertex());
                curr = curr.getPrev();
            }
            paths.add(path);
        }
        return paths;
    }

    private void addToOpen(SearchingVertex vertex){
        if(vertex.getF() <= max_cost)
            open.add(vertex);
    }


}
