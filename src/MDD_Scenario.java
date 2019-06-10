import java.util.ArrayList;
import java.util.List;

public class MDD_Scenario {
    private Graph graph;
    private List<MDD_Agent> agents;

    public MDD_Scenario(Scenario scenario){
        this.graph = scenario.getGraph();
        this.agents = new ArrayList<>();
        for(Agent agent : scenario.getAgents()){
            MDD_Agent mdd_agent = new MDD_Agent(agent.graph,agent.start_vertex,agent.goal_vertex);
            this.agents.add(mdd_agent);
        }
    }

    public MDD_Scenario(Graph graph, List<MDD_Agent> agents) {
        this.graph = graph;
        this.agents = agents;
    }

    public Graph getGraph() {
        return graph;
    }

    public List<MDD_Agent> getAgents() {
        return agents;
    }

    public void addAgent(MDD_Agent agent){
        agents.add(agent);
    }
}
