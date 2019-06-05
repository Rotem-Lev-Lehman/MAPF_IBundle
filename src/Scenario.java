import java.util.List;

public class Scenario {
    private Graph graph;
    private List<Agent> agents;

    public Scenario(Graph graph, List<Agent> agents) {
        this.graph = graph;
        this.agents = agents;
    }

    public Graph getGraph() {
        return graph;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void addAgent(Agent agent){
        agents.add(agent);
    }
}
