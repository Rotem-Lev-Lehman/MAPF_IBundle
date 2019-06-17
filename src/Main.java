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

        List<Scenario> scenarios = Scenario_Reader.readScenariosBounded(new File("resources/scenarios/ca_cave.map.scen"),40);
        System.out.println(scenarios.size());
        for(int i=0;i<scenarios.size();i=i+1){
            System.out.println(scenarios.get(i).getAgents().size());
            System.out.print(i+" : ");
            MDD_Scenario mdd_scenario = new MDD_Scenario(scenarios.get(i));
            Auctioneer auctioneer = new Auctioneer();
            int sum =0;
            if(auctioneer.solve(mdd_scenario.getAgents())){
                for(MDD_Agent agent1 : mdd_scenario.getAgents()){
                    /*System.out.println("***************************************************************************");
                    agent1.getFinal_path().printPath();*/
                    sum+=agent1.getFinal_path().size();
                }
                System.out.println("sum of costs - " + sum);
            }
            else {
                System.out.println("Solver failed");
            }
        }


    }
}
