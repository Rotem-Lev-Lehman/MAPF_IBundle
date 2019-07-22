import java.util.*;

public class AStarDeep implements ISearcher, IDeepening_Searcher {

    private PriorityQueue<SearchingVertex> open;
    //private HashSet<Vertex> close;
    private HashMap<Vertex, Double> minGForVertex;
    private HashMap<Vertex, SearchingVertex> vertexes;
    private double max_cost;
    private AProblem problem;
    private List<SearchingVertex> finished;
    private int iterationMax;

    @Override
    public List<SearchingVertex> searchDeepening(AProblem problem, double cost,int iterationMax) throws InterruptedException {
        return searchBFS(problem,cost,true, iterationMax);
    }

    @Override
    public List<SearchingVertex> search(AProblem problem, double maxCost) throws InterruptedException {
        return searchBFS(problem, maxCost, false, 0);
    }


    private List<SearchingVertex> searchBFS(AProblem problem, double cost, boolean deepening, int iterationMax) throws InterruptedException {
        this.problem = problem;
        this.max_cost = cost;
        open = new PriorityQueue<>();
        finished = new ArrayList<>();
        this.iterationMax = iterationMax;
        this.minGForVertex = new HashMap<>();
        this.vertexes = new HashMap<>();

        double h = problem.getHeuristic(problem.getStart_vertex());
        SearchingVertex first = new SearchingVertex(problem.getStart_vertex(), 0, h);
        this.vertexes.put(first.getVertex(), first);

        this.minGForVertex.put(first.getVertex(), 0.0);

        addToOpen(first);
        while (!open.isEmpty()) {
            if (Thread.interrupted())
                throw new InterruptedException("Auctioneer was interrupted");

            SearchingVertex curr = open.poll();
            //Vertex currVer = curr.getVertex();
            if (curr.getF() > this.max_cost) // check for the nodes that are already in the open and then we found a better goal.
                return finished;
            if (curr.getVertex().equals(problem.getGoal_vertex())) {
                if (!deepening) {
                    if (curr.getF() <= this.max_cost) {
                        this.max_cost = curr.getF();
                        finished.add(curr);
                    }
                } else if (curr.getF() + curr.getPotentionalWait() >= cost) {
                    finished.add(curr);
                }
                continue;
            }
            for (Edge edge : curr.getVertex().getEdges()) {
                Vertex neighbor = edge.getVertex_to();
                double g = curr.getG() + edge.getTravel_cost();
                checkNeighbor(curr, neighbor, g, deepening);
            }
            if (deepening) {
                addMySelf(curr);
                /*double g = curr.getG() + curr.getVertex().getStay_cost();
                checkNeighbor(curr, curr.getVertex(), g, deepening);*/
            }
        }
        return finished;
    }

    private void addMySelf(SearchingVertex curr) {
        if(curr.getF() + curr.getPotentionalWait() <= max_cost){
            open.add(curr);
            curr.addPotentionalWait();
            curr.addPrev(curr,curr.getG()+curr.getVertex().getStay_cost());
        }
    }

    private void checkNeighbor(SearchingVertex prev, Vertex neighbor, double newG, boolean deepening) {
        double h = problem.getHeuristic(neighbor);
        SearchingVertex next = vertexes.get(neighbor);
        if(newG+h>max_cost+iterationMax)
            return;
        if(next != null){
            if(next.getG() == newG || next.getG() + iterationMax == newG){
                next.addPrev(prev,prev.getG());
                addToOpen(next);
            }
            else if(!deepening){
                if(next.getG() + iterationMax > newG){
                    next.newBestPrev(prev,prev.getG(),newG);
                    addToOpen(next);
                }
            }
            else{
                if(next.getG() + iterationMax >= newG){
                    addToOpen(next);
                }

            }
        }
        else {
            SearchingVertex vertex = new SearchingVertex(neighbor,newG, h,prev.getPotentionalWait(), prev, prev.getG());
            vertexes.put(vertex.getVertex(),vertex);
            addToOpen(vertex);
        }
    }

    private void addToOpen(SearchingVertex vertex) {
        if (vertex.getF() <= max_cost)
            open.add(vertex);
    }


}
