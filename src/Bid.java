import java.util.List;

public class Bid {
    private MDD_Agent agent;
    private List<MDD> MDDs;

    public Bid(MDD_Agent agent, List<MDD> MDDs) {
        this.agent = agent;
        this.MDDs = MDDs;
    }

    public MDD_Agent getAgent() {
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
