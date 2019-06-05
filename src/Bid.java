import java.util.List;

public class Bid {
    private Agent agent;
    private List<MDD> MDDs;

    public Bid(Agent agent, List<MDD> MDDs) {
        this.agent = agent;
        this.MDDs = MDDs;
    }

    public Agent getAgent() {
        return agent;
    }

    public List<MDD> getMDDs() {
        return MDDs;
    }

    public void Decline(){
        agent.addMinToCurrentMDDs();
        agent.calculateMoreMDDs();
    }

    public void Accept(Path path){
        agent.setFinal_path(path);
    }
}
