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
        int[][] array= Map_Reader.readMap(new File("resources/maps/test"));
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------");
        //test scenario
        List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/ca_cave.map.scen"));
        int breakpoint=5;

    }
}
