import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<Vertex> vertexes;

    public Path() {
        this.vertexes = new ArrayList<>();
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public void addVertex(Vertex vertex){
        vertexes.add(vertex);
    }

    public void addFromStart(Vertex vertex){
        vertexes.add(0, vertex);
    }

    public void printPath() {
        System.out.println("The path is:");
        for(Vertex vertex : vertexes){
            System.out.println(vertex);
        }
    }

    public int size() {
        return vertexes.size();
    }

    public Vertex get(int index){
        return vertexes.get(index);
    }
}
