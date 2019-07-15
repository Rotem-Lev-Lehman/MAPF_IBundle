public class SearchingVertex implements Comparable{
    private Vertex vertex;
    private SearchingVertex prev;
    private double g;
    private double h;
    private int amountWaited;

    public SearchingVertex(Vertex vertex, SearchingVertex prev, double g, double h) {
        this.vertex = vertex;
        this.prev = prev;
        this.g = g;
        this.h = h;
        this.amountWaited = 0;
    }

    public SearchingVertex(Vertex vertex, SearchingVertex prev, double g, double h,int amountWaited) {
        this.vertex = vertex;
        this.prev = prev;
        this.g = g;
        this.h = h;
        this.amountWaited = amountWaited;
    }

    public SearchingVertex(Vertex vertex, double g, double h) {
        this.vertex = vertex;
        this.prev = null;
        this.g = g;
        this.h = h;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public SearchingVertex getPrev() {
        return prev;
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

    public int getAmountWaited() {
        return amountWaited;
    }
}
