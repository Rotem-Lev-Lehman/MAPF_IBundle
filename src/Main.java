import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/aTest1"));
        //List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/aTest3Agents1"));
        //List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/aTest3Agents2"));
        //List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/ca_cave.map.scen"));
        List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/ca_cavern1_haunted.map.scen"));

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
        /*
        MDD_Agent agent = mdd_scenario.getAgents().get(0);
        agent.calculateFirstMDD();
        System.out.println(agent.getMaxMDDTotalCost());
        Bid bid = agent.MakeABid();
        bid.Decline();
        System.out.println(agent.getMaxMDDTotalCost());
        bid.normalizeTotalTime(9);
        System.out.println(agent.getMaxMDDTotalCost());
        */
        //*******************************************************************************************************************************

        Auctioneer auctioneer = new Auctioneer();
        if(auctioneer.solve(mdd_scenario.getAgents())){
            for(MDD_Agent agent1 : mdd_scenario.getAgents()){
                System.out.println("***************************************************************************");
                agent1.getFinal_path().printPath();
            }
        }
        else {
            System.out.println("Solver failed");
        }

    }

    private void Experiment(){
        File folder = new File("resources/scenarios");
        File[] listOfFiles = folder.listFiles();

        for(int amount_of_agents = 0; amount_of_agents < 10; amount_of_agents++) {

            int amount_solved = 0;
            int amount_failed = 0;
            for (int i = 0; i < listOfFiles.length; i++) {
                File curr = listOfFiles[i];
                List<Scenario> scenarios = Scenario_Reader.readScenarios(curr);

                for (Scenario scenario : scenarios) {
                    MDD_Scenario mdd_scenario = new MDD_Scenario(scenario);

                    Auctioneer auctioneer = new Auctioneer();
                    if (auctioneer.solve(mdd_scenario.getAgents())) {
                        amount_solved++;
                    } else {
                        amount_failed++;
                    }
                }
            }
        }
    }
}
