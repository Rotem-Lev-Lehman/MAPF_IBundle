import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Scenario_Reader {

    public static Scenario readSceneNmap(File file){
        int[][] array=null;
        Graph graph=null;
        List<Agent> agents=null;
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try {
            fileReader=new FileReader(file);
            bufferedReader=new BufferedReader(fileReader);
            bufferedReader.readLine();
            bufferedReader.readLine();
            String[] sizes=bufferedReader.readLine().split(",");
            int height=Integer.parseInt(sizes[0]);
            int width=Integer.parseInt(sizes[1]);
            String nextLine;
            array=new int[height][width];
            int row=0;
            while ((nextLine=bufferedReader.readLine())!=null && nextLine.startsWith("Agents")==false){
                for(int column=0;column<width;column++){
                    char symbol = nextLine.charAt(column);
                    if(symbol == '@' || symbol == 'T'){
                        array[row][column]=1;
                    }
                    else if(symbol == '.'){
                        array[row][column]=0;
                    }
                }
                row++;
            }
            Graph_Generator graph_generator = new Graph_Generator(array);
            graph = graph_generator.generate();
            int agentsNum = Integer.parseInt(bufferedReader.readLine());
            int counter = 0;
            agents = new ArrayList<>();
            while((nextLine=bufferedReader.readLine())!=null && counter<agentsNum){
                String[] data = nextLine.split(",");
                Point start = new Point(Integer.parseInt(data[2]),Integer.parseInt(data[1]));
                Point goal = new Point(Integer.parseInt(data[4]),Integer.parseInt(data[3]));
                agents.add(new Agent(graph,graph.get_Vertex_By_Indicator(start),graph.get_Vertex_By_Indicator(goal)));
                counter++;
            }
            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(array.length);
            System.out.println(array[0].length);
        }
        return new Scenario(graph,agents);
    }

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
                        map_file_name = data[1];
                        graph = getGraph(map_file_name);
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
        return readScenarioAgentBound(file,wantedScenarioIndex,Integer.MAX_VALUE);
    }

    public static Scenario readScenarioAgentBound(File file,int wantedScenarioIndex,int agentsBound){
        try {
            FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String nextLine;
            bufferedReader.readLine() ;
            Graph graph = null;
            List<Agent> agents = null;
            boolean rightIndex = false;
            int counter =0;
            while ((nextLine=bufferedReader.readLine())!=null){
                String[] data = nextLine.split("\t");
                int scenario_index = Integer.parseInt(data[0]);
                if(scenario_index<wantedScenarioIndex){
                    continue;
                }
                else if(scenario_index>wantedScenarioIndex || counter>=agentsBound){
                    break;
                }
                else {
                    if(rightIndex==false){
                        graph = getGraph(data[1]);
                        agents = new ArrayList<>();
                        rightIndex=true;
                    }
                    Point start = new Point(Integer.parseInt(data[4]),Integer.parseInt(data[5]));
                    Point goal = new Point(Integer.parseInt(data[6]),Integer.parseInt(data[7]));
                    agents.add(new Agent(graph,graph.get_Vertex_By_Indicator(start),graph.get_Vertex_By_Indicator(goal)));
                    counter++;
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
        //map_file_name = "complicated/" + map_file_name;
        int[][] map = Map_Reader.readMap(new File("resources/maps/" + map_file_name));
        Graph_Generator graph_generator=new Graph_Generator(map);
        return graph_generator.generate();
    }

}
