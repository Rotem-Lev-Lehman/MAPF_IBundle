import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //test genarator
        int[][] ints={{1,0},{0,0}};
        Graph_Generator graph_genarator=new Graph_Generator(ints);
        Graph graph=graph_genarator.generate();
        System.out.println(graph.getEdges().size());
        System.out.println(graph.getVertexes().size());
        System.out.println("----------------------");
        //test map to array
        int[][] array= Map_Reader.readMap(new File("resources/maps/aTest1.map"));
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------");
        //test scenario
        List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/aTest1"));
        int breakpoint=5;

        /*
        AStarDeep astar = new AStarDeep();
        List<Path> paths = astar.searchDeepening(scenarios.get(0).getAgents().get(0),4,6);
        for(Path path : paths)
        {
            System.out.println("***************************************************");
            path.printPath();
        }
        */
        //*******************************************************************************************************************************
        MDD_Scenario mdd_scenario = new MDD_Scenario(scenarios.get(0));
        MDD_Agent agent = mdd_scenario.getAgents().get(0);
        agent.calculateFirstMDD();
        System.out.println(agent.getMaxMDDTotalCost());
        Bid bid = agent.MakeABid();
        bid.Decline();
        System.out.println(agent.getMaxMDDTotalCost());
    }
}
