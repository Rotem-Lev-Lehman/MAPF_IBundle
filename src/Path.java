import java.util.LinkedList;
import java.util.List;

public class Path {
    List<Vertex> vertexes;

    public Path() {
        this.vertexes = new LinkedList<>();
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
}
