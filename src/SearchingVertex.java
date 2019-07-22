import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchingVertex implements Comparable{
    private Vertex vertex;
    //private SearchingVertex prev;
    private List<SearchingVertex> prevList;
    private List<SearchingVertex> nextList;
    private double g;
    private double h;
    private int amountWaited;

    public SearchingVertex(Vertex vertex, SearchingVertex prev, double g, double h) {
        this.vertex = vertex;
        //this.prev = prev;
        this.prevList = new ArrayList<>();
        this.nextList = new ArrayList<>();
        addPrev(prev);
        this.g = g;
        this.h = h;
        this.amountWaited = 0;
    }

    public SearchingVertex(Vertex vertex, SearchingVertex prev, double g, double h,int amountWaited) {
        this.vertex = vertex;
        //this.prev = prev;
        this.prevList = new ArrayList<>();
        this.nextList = new ArrayList<>();
        addPrev(prev);
        this.g = g;
        this.h = h;
        this.amountWaited = amountWaited;
    }

    public SearchingVertex(Vertex vertex, double g, double h) {
        this.vertex = vertex;
        //this.prev = null;
        this.prevList = new ArrayList<>();
        this.nextList = new ArrayList<>();
        this.g = g;
        this.h = h;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public List<SearchingVertex> getPrevList() {
        return this.prevList;
    }

    public void addPrev(SearchingVertex prev){
        if(prevList.contains(prev)==false){
            this.prevList.add(prev);
            prev.addNext(this);
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

    public void newBestPrev(SearchingVertex prev,double newG){
        if(newG < this.g){
            prevList.clear();
            prevList.add(prev);
            this.g = newG;
            for(SearchingVertex vertex:nextList){
                vertex.newBestPrev(this,g+1);
            }
        }
    }
}
