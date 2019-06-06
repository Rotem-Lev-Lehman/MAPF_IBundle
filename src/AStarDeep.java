import java.util.*;

public class AStarDeep implements ISearcher, IDeepening_Searcher {

    private PriorityQueue<SearchingVertex> open;
    private double max_cost;
    private AProblem problem;
    private List<SearchingVertex> finished;

    @Override
    public List<Path> searchDeepening(AProblem problem, double minCost, double maxCost) {
        /*double costJump = problem.getGraph().getLowest_cost();
        for (double currCost = minCost; currCost <= maxCost; currCost += costJump) {
            List<Path> paths = searchBFS(problem, minCost,currCost,true);
            if(paths != null)
                return paths;
        }
        return null; //There is no more*/
        return searchBFS(problem,minCost,maxCost,true);
    }

    @Override
    public List<Path> search(AProblem problem, double maxCost) {
        return searchBFS(problem,0,maxCost,false);
    }


    private List<Path> searchBFS(AProblem problem, double min_cost, double max_cost, boolean deepening) {
        this.problem = problem;
        this.max_cost = max_cost;
        open = new PriorityQueue<>();
        finished = new ArrayList<>();

        double h = problem.getHeuristic(problem.getStart_vertex());
        SearchingVertex first = new SearchingVertex(problem.getStart_vertex(),0, h);

        addToOpen(first);

        while (!open.isEmpty()){
            SearchingVertex curr = open.poll();
            //Vertex currVer = curr.getVertex();

            if(curr.getVertex().equals(problem.getGoal_vertex())) {
                if(!deepening){
                    if(curr.getF()<=max_cost){
                        max_cost=curr.getF();
                        finished.add(curr);
                    }
                    else{
                        return reconstructPaths();
                    }
                }
                else if(curr.getF()>=min_cost){
                    finished.add(curr);
                }
                continue;
            }
            double g;
            for(Edge edge : curr.getVertex().getEdges()){
                Vertex neighbor = edge.getVertex_to();
                g = curr.getG() + edge.getTravel_cost();
                checkNeighbor(curr, neighbor, g);
            }
            g = curr.getG() + curr.getVertex().getStay_cost();
            checkNeighbor(curr, curr.getVertex(), g);

        }

        return reconstructPaths();
    }

    private void checkNeighbor(SearchingVertex prev, Vertex neighbor, double g) {
        double h = problem.getHeuristic(neighbor);
        SearchingVertex vertex = new SearchingVertex(neighbor, prev, g, h);
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
