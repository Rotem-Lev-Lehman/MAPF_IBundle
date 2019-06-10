import java.util.ArrayList;
import java.util.List;

public class MDD_Path {

    private Bid bid;
    private List<MDD_Vertex> vertexes;

    public MDD_Path(Bid bid) {
        this.bid = bid;
        this.vertexes = new ArrayList<>();
    }

    public List<MDD_Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<MDD_Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public void addVertex(MDD_Vertex vertex){
        vertexes.add(vertex);
    }

    public void addFromStart(MDD_Vertex vertex){
        vertexes.add(0, vertex);
    }

    public void printPath() {
        System.out.println("The path is:");
        for(MDD_Vertex vertex : vertexes){
            System.out.println(vertex);
        }
    }

    public int size() {
        return vertexes.size();
    }

    public MDD_Vertex get(int index){
        return vertexes.get(index);
    }

    public Bid getBid() {
        return bid;
    }

    public MDD_Vertex getLast() {
        if(vertexes.size()>0)
            return vertexes.get(vertexes.size()-1);
        return null;
    }
}
