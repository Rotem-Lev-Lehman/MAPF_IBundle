import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Scenario_Reader {

    public static List<Scenario> readScenarios(File file){
        return readBoundScenarios(file,Integer.MAX_VALUE,Integer.MAX_VALUE) ;
    }

    public static List<Scenario> readScenariosBounded(File file,int bound){
        return readBoundScenarios(file,Integer.MAX_VALUE,bound);
    }

    public static List<Scenario> readScenariosAgentsBound(File file,int bound){
        return readBoundScenarios(file,bound,Integer.MAX_VALUE);
    }

    public static List<Scenario> readBoundScenarios(File file,int agentsBound,int scenarioBound){
        List<Scenario> scenarios = new ArrayList<>();
        try {
            FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String nextLine;
            bufferedReader.readLine();
            Graph graph = null;
            List<Agent> agents = null;
            int index = -1;
            int counter = 0;
            String map_file_name ="";
            while ((nextLine=bufferedReader.readLine())!=null){
                String[] data = nextLine.split("\t");
                int scenario_index = Integer.parseInt(data[0]);
                if(index != scenario_index){
                    index = scenario_index;
                    if(graph != null && agents != null){
                        scenarios.add(new Scenario(graph,agents));
                        if(scenario_index>=scenarioBound){
                            graph = null;
                            agents = null;
                            break;
                        }
                    }
                    counter =0;
                    if(map_file_name.equals(data[1])==false){
                        graph = getGraph(data[1]);
                    }
                    agents = new ArrayList<>();
                }
                if(counter<agentsBound){
                    Point start = new Point(Integer.parseInt(data[4]),Integer.parseInt(data[5]));
                    Point goal = new Point(Integer.parseInt(data[6]),Integer.parseInt(data[7]));
                    agents.add(new Agent(graph,graph.get_Vertex_By_Indicator(start),graph.get_Vertex_By_Indicator(goal)));
                    counter++;
                }
            }
            if(graph != null && agents != null){
                scenarios.add(new Scenario(graph,agents));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scenarios;
    }

    public static Scenario readScenario(File file,int wantedScenarioIndex){
        try {
            FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String nextLine;
            bufferedReader.readLine();
            Graph graph = null;
            List<Agent> agents = null;
            boolean rightIndex = false;
            while ((nextLine=bufferedReader.readLine())!=null){
                String[] data = nextLine.split("\t");
                int scenario_index = Integer.parseInt(data[0]);
                if(scenario_index<wantedScenarioIndex){
                    continue;
                }
                else if(scenario_index>wantedScenarioIndex){
                    break;
                }
                else{
                    if(rightIndex==false){
                        graph = getGraph(data[1]);
                        agents = new ArrayList<>();
                        rightIndex=true;
                    }
                    Point start = new Point(Integer.parseInt(data[4]),Integer.parseInt(data[5]));
                    Point goal = new Point(Integer.parseInt(data[6]),Integer.parseInt(data[7]));
                    agents.add(new Agent(graph,graph.get_Vertex_By_Indicator(start),graph.get_Vertex_By_Indicator(goal)));
                }
            }
            if(graph != null && agents != null){
                return new Scenario(graph,agents);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Graph getGraph(String map_file_name){
        int[][] map = Map_Reader.readMap(new File("resources/maps/" + map_file_name));
        Graph_Generator graph_generator=new Graph_Generator(map);
        return graph_generator.generate();
    }

}
