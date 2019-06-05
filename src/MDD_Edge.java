public class MDD_Edge {
    private MDD_Vertex from_MDD_vertex;
    private MDD_Vertex to_MDD_vertex;
    private MDD mdd;

    public MDD_Edge(MDD_Vertex from_MDD_vertex, MDD_Vertex to_MDD_vertex, MDD mdd) {
        this.from_MDD_vertex = from_MDD_vertex;
        this.to_MDD_vertex = to_MDD_vertex;
        this.mdd = mdd;
    }

    public MDD_Vertex getFrom_MDD_vertex() {
        return from_MDD_vertex;
    }

    public MDD_Vertex getTo_MDD_vertex() {
        return to_MDD_vertex;
    }

    public MDD getMdd() {
        return mdd;
    }
}
