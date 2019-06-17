import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        main.Experiment();
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
        writeAmountSolvedAndFailed(agents, solved, failed);
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

                for (int i = 0; i < listOfFiles.length; i++) {
                    File curr = listOfFiles[i];
                    List<Scenario> scenarios = Scenario_Reader.readBoundScenarios(curr, amount_of_agents.getNum(),1);
                    Scenario scenario = scenarios.get(0);
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

    private void writeAmountSolvedAndFailed(List<MyInteger> amount_of_agents, List<MyInteger> amount_solved, List<MyInteger> amount_failed) {
        try (PrintWriter writer = new PrintWriter(new File("experiment_results2.csv"))) {

            StringBuilder sb = new StringBuilder();
            sb.append("amount of agents");
            sb.append(',');
            sb.append("amount solved");
            sb.append(',');
            sb.append("amount failed");
            sb.append('\n');

            for(int i = 0; i < amount_of_agents.size(); i++){
                sb.append(amount_of_agents.get(i));
                sb.append(',');
                sb.append(amount_solved.get(i));
                sb.append(',');
                sb.append(amount_failed.get(i));
                sb.append('\n');

                System.out.println("***********************************************************************");
                System.out.println("amount of agents = " + amount_of_agents.get(i));
                System.out.println("amount solved = " + amount_solved.get(i));
                System.out.println("amount failed = " + amount_failed.get(i));
            }


            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
