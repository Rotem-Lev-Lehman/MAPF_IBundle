import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SearchingVertex implements Comparable{
    private Vertex vertex;
    private HashMap<SearchingVertex,Double> prevList;
    private List<SearchingVertex> nextList;
    private double g;
    private double h;
    private int amountWaited;
    private int potentionalWait;

    public SearchingVertex(Vertex vertex,double g, double h, SearchingVertex prev,double prevG) {
        init(vertex);
        addPrev(prev,prevG);
        this.g = g;
        this.h = h;
        this.amountWaited = 0;
    }

    public SearchingVertex(Vertex vertex,double g, double h,int potentionalWait, SearchingVertex prev, double prevG) {
        init(vertex);
        addPrev(prev,prevG);
        this.g = g;
        this.h = h;
        this.potentionalWait = potentionalWait;
    }

    public SearchingVertex(Vertex vertex, double g, double h) {
        init(vertex);
        this.g = g;
        this.h = h;
    }

    private void init(Vertex vertex){
        this.vertex = vertex;
        this.prevList = new HashMap<>();
        this.nextList = new ArrayList<>();
        this.potentionalWait = 0;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public HashMap<SearchingVertex,Double> getPrevList() {
        return this.prevList;
    }

    public void addPrev(SearchingVertex prev,double prevG){
        if(prevList.containsKey(prev)==false){
            this.prevList.put(prev,prevG);
            prev.addNext(this);
        }
        else {
            if(prevList.get(prev)>prevG)
                this.prevList.put(prev,prevG);
        }
    }

    private void addNext(SearchingVertex searchingVertex) {
        if(nextList.contains(searchingVertex)==false){
            nextList.add(searchingVertex);
        }
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getF(){
        return g+h;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof SearchingVertex){
            SearchingVertex other = (SearchingVertex)o;
            double res = this.getF() - other.getF();
            if(res < 0)
                return -1;
            if(res == 0)
                return 0;
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchingVertex that = (SearchingVertex) o;
        return Objects.equals(vertex, that.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex);
    }

    public int getAmountWaited() {
        return amountWaited;
    }

    // TODO: 22-Jul-19 check
    public void newBestPrev(SearchingVertex prev,double prevG,double newG){
        if(newG < this.g){
            prevList.clear();
            prevList.put(prev,prevG);
            this.g = newG;
            for(SearchingVertex vertex:nextList){
                vertex.newBestPrev(this,g,g+1);
            }
        }
    }

    public int getNumberOfPaths(){
        if(prevList.size()==0/* || ( prevList.size()==1 && prevList.keySet().iterator().next().equals(this))*/)
            return 1;
        int sum=0;
        for (SearchingVertex vertex:prevList.keySet()){
            if(vertex.equals(this)==false){
                sum+=vertex.getNumberOfPaths();
            }
        }
        return sum;
    }

    public void addPotentionalWait(){
        potentionalWait++;
        for (SearchingVertex vertex:nextList) {
            if(vertex.equals(this)==false)
                vertex.addPotentionalWait();
        }
    }

    public int getPotentionalWait() {
        return potentionalWait;
    }
}
