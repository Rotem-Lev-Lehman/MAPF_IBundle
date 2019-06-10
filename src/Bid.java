import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Bid {
    private MDD_Agent agent;
    private List<MDD> MDDs;

    public Bid(MDD_Agent agent, List<MDD> MDDs) {
        this.agent = agent;
        this.MDDs = MDDs;
        sortMdds();
    }

    private void sortMdds() {
        Collections.sort(MDDs, new Comparator<MDD>() {
            @Override
            public int compare(MDD o1, MDD o2) {
                return Double.compare(o2.getBidding_cost(),o1.getBidding_cost());
            }
        });
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

    public int getMaxLength() {
        int maxLength = 0;
        for (MDD mdd : MDDs){
            int length = mdd.getTotal_time();
            if(length>maxLength)
                maxLength=length;
        }
        return maxLength;
    }

    public void normalizeTotalTime(int maxLength) {
        for (MDD mdd : MDDs)
            MDD_Builder.normalizeTotalTime(mdd,maxLength);
    }
}
