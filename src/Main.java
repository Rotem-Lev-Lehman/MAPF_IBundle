import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        /*
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

        //MDD_Scenario mdd_scenario = new MDD_Scenario(scenarios.get(0));
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

        /*
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
        */
        Main main = new Main();
        /*double time = System.currentTimeMillis();
        main.main_Experiment();
        System.out.println((System.currentTimeMillis()-time)/3600+" minutes");*/
        //main.Experiment2("resources/scenarios",false);
        List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/ca_cave.map.scen"));
        Graph graph = scenarios.get(0).getGraph();
        Agent agent = new Agent(graph,graph.get_Vertex_By_Indicator(new Point(82,164)),graph.get_Vertex_By_Indicator(new Point(95,207)));
        Agent agent1 = new Agent(graph,graph.get_Vertex_By_Indicator(new Point(82,164)),graph.get_Vertex_By_Indicator(new Point(116,164)));
        AStarDeep aStarDeep = new AStarDeep();
        try {
            List<SearchingVertex> aaa = aStarDeep.search(agent,100);
            double solution = aaa.get(0).getG();

            System.out.println(solution);
            System.out.println(aaa.size());
            /*List<SearchingVertex> aaa1 = aStarDeep.searchDeepening(agent,solution,solution,0);
            double solution1 = aaa1.get(0).getG();
            System.out.println(solution1);
            System.out.println(aaa1.size());*/

        }
        catch (Exception e){
            e.printStackTrace();
        }
        //main.oneScenarioSolver("complicated/maze512-1-0");
    }

    private void main_Experiment(){
        this.Experiment2("resources/instances_for_experiment",true);
    }
    private void oneScenarioSolver(String scenario_name){
        List<Scenario> scenarios = Scenario_Reader.readScenarios(new File("resources/scenarios/"+scenario_name+".map.scen"));
        MDD_Scenario mdd_scenario = new MDD_Scenario(scenarios.get(0));
        Auctioneer auctioneer = new Auctioneer();
        try {
            System.out.println("Starting!");
            long start = System.currentTimeMillis();
            if(auctioneer.solve(mdd_scenario.getAgents())){
                int sumOfCosts =0;
                for(MDD_Agent agent1 : mdd_scenario.getAgents()){
                    System.out.println("***************************************************************************");
                    Path path = agent1.getFinal_path();
                    path.printPath();
                    sumOfCosts += path.getNumberOfSteps();
                }
                System.out.print("-> ");
                System.out.println(sumOfCosts);
            }
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("Done, time elapsed:");
            System.out.println(timeElapsed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void Experiment2(String Path,boolean dorIntances){
        File folder = new File(Path);
        File[] listOfFiles = folder.listFiles();

        List<Integer> amount_of_agents_list = new ArrayList<>();
        List<Integer> amount_solved_list = new ArrayList<>();
        List<Integer> amount_failed_list = new ArrayList<>();
        List<Double> time = new ArrayList<>();

        for(int amount_of_agents = 10; amount_of_agents <= 10; amount_of_agents++) {

            int amount_solved = 0;
            int amount_failed = 0;
            double curr_time = 0;

            for (int i = 0; i < listOfFiles.length; i++) {
                System.out.println("Reading file " + i);
                File curr = listOfFiles[i];
                if(curr.isFile() == false)
                    continue;
                List<Scenario> scenarios = new ArrayList<>();
                if(dorIntances){
                    scenarios.add(Scenario_Reader.readSceneNmap(curr));
                }
                else {
                    scenarios = Scenario_Reader.readScenarios(curr);
                }
                System.out.println("Done Reading file " + i);
                for (int j=0;j<scenarios.size();j++) {
                    Scenario scenario = scenarios.get(j);
                    MDD_Scenario mdd_scenario = new MDD_Scenario(scenario);
                    Exp2Thread exp2Thread = new Exp2Thread(mdd_scenario, Thread.currentThread());
                    Thread thread = new Thread(exp2Thread);
                    double this_time = System.currentTimeMillis();
                    thread.start();
                    try {
                        int minutes = 1;
                        int seconds = 0;
                        Thread.sleep((minutes*60+seconds)*1000);

                        amount_failed++;
                        thread.interrupt();
                        System.out.println(j+" failed");
                    } catch (InterruptedException e) {
                        amount_solved++;
                        System.out.println("Completed "+j);
                        //System.out.println("yay");
                        curr_time += System.currentTimeMillis() - this_time;
                    }
                }
            }
            amount_of_agents_list.add(amount_of_agents);
            amount_solved_list.add(amount_solved);
            amount_failed_list.add(amount_failed);
            time.add(curr_time);
        }
        writeAmountSolvedAndFailed(amount_of_agents_list, amount_solved_list, amount_failed_list, time);
    }

    private class Exp2Thread implements Runnable{
        private MDD_Scenario scenario;
        private Thread prev;

        public Exp2Thread(MDD_Scenario scenario, Thread thread) {
            this.scenario = scenario;
            this.prev = thread;
        }

        @Override
        public void run() {
            try {
                Auctioneer auctioneer = new Auctioneer();
                if (auctioneer.solve(scenario.getAgents())) {
                    prev.interrupt();
                    return;
                } else {
                    Thread.sleep(10000);
                }

            }
            catch (Exception e){
                //System.out.println("Interrupted");
            }
        }
    }

    private void Experiment(){
        File folder = new File("resources/scenarios");
        File[] listOfFiles = folder.listFiles();

        List<Integer> amount_of_agents_list = new ArrayList<>();
        List<Integer> amount_solved_list = new ArrayList<>();
        List<Integer> amount_failed_list = new ArrayList<>();
        List<MyInteger> agents = new ArrayList<>();
        List<MyInteger> solved = new ArrayList<>();
        List<MyInteger> failed = new ArrayList<>();

        Semaphore semaphore = new Semaphore(0);
        int index = 0;

        for(int amount_of_agents = 2; amount_of_agents <= 10; amount_of_agents++) {

            int amount_solved = 0;
            int amount_failed = 0;
            agents.add(new MyInteger(amount_of_agents));
            solved.add(new MyInteger(amount_solved));
            failed.add(new MyInteger(amount_failed));
            myThread thread = new myThread(agents.get(index),solved.get(index),failed.get(index),listOfFiles, Thread.currentThread(),semaphore);
            index++;

            Thread thread1 = new Thread(thread);
            thread1.start();
            try {
                Thread.sleep(1000);
                thread1.interrupt();
                //System.out.println(solved.getNum());
            } catch (InterruptedException e) {
                System.out.println("Finished early :)");
            }
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
            amount_of_agents_list.add(agents.getNum());
            amount_solved_list.add(solved.getNum());
            amount_failed_list.add(failed.getNum());
            */
        }
        //writeAmountSolvedAndFailed(agents, solved, failed);
    }



    private class myThread implements Runnable {
        private MyInteger amount_of_agents;
        private MyInteger amount_solved;
        private MyInteger amount_failed;
        private File[] listOfFiles;
        private Thread currentThread;
        private Semaphore semaphore;

        public myThread(MyInteger amount_of_agents, MyInteger amount_solved, MyInteger amount_failed, File[] listOfFiles, Thread current_thread, Semaphore semaphore) {
            this.amount_of_agents = amount_of_agents;
            this.amount_solved = amount_solved;
            this.amount_failed = amount_failed;
            this.listOfFiles = listOfFiles;
            this.currentThread = current_thread;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {

                for (int scenIndex = 0; scenIndex < 40; scenIndex++) {
                    for (int i = 0; i < listOfFiles.length; i++) {
                        File curr = listOfFiles[i];
                        //List<Scenario> scenarios = Scenario_Reader.readBoundScenarios(curr, amount_of_agents.getNum(),1);
                        Scenario scenario = Scenario_Reader.readScenarioAgentBound(curr, scenIndex, amount_of_agents.getNum());
                        //Scenario scenario = scenarios.get(0);
                        //System.out.println(scenario.getAgents().size());
                        //for (Scenario scenario : scenarios) {
                        if (Thread.interrupted())
                            throw new InterruptedException("only read file");
                        MDD_Scenario mdd_scenario = new MDD_Scenario(scenario);

                        Auctioneer auctioneer = new Auctioneer();
                        if (auctioneer.solve(mdd_scenario.getAgents())) {
                            amount_solved.addOne();
                        } else {
                            amount_failed.addOne();
                        }
                        //}
                    }
                }
                currentThread.interrupt();
            }
            catch (Exception e){
                System.out.println("Interrupted");
                System.out.println(e.getMessage());
                System.out.println(amount_solved.getNum());
                amount_failed.addOne();
            }
            semaphore.release();
        }
    }

    private void writeAmountSolvedAndFailed(List<Integer> amount_of_agents, List<Integer> amount_solved, List<Integer> amount_failed, List<Double> time) {
        try (PrintWriter writer = new PrintWriter(new File("experiment_results6.csv"))) {

            StringBuilder sb = new StringBuilder();
            sb.append("amount of agents");
            sb.append(',');
            sb.append("amount solved");
            sb.append(',');
            sb.append("amount failed");
            sb.append(',');
            sb.append("time of success");
            sb.append('\n');

            for(int i = 0; i < amount_of_agents.size(); i++){
                sb.append(amount_of_agents.get(i));
                sb.append(',');
                sb.append(amount_solved.get(i));
                sb.append(',');
                sb.append(amount_failed.get(i));
                sb.append(',');
                sb.append(time.get(i));
                sb.append('\n');

                System.out.println("***********************************************************************");
                System.out.println("amount of agents = " + amount_of_agents.get(i));
                System.out.println("amount solved = " + amount_solved.get(i));
                System.out.println("amount failed = " + amount_failed.get(i));
                System.out.println("time = " + time.get(i));
            }


            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
