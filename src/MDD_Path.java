import javafx.util.Pair;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class MDD_Path {

    private Bid bid;
    private List<MDD_Vertex> vertexes;

    public MDD_Path(Bid bid) {
        this.bid = bid;
        this.vertexes = new ArrayList<>();
    }

    public MDD_Path(MDD_Path other) {
        this.bid = other.bid;
        this.vertexes = new ArrayList<>(other.vertexes);
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

    public Path getPath(Vertex goal){
        Path path = new Path();
        boolean added_goal = false;
        for (MDD_Vertex vertex : vertexes) {
            if(!vertex.getOriginal_vertex().equals(goal)) {
                path.addVertex(vertex.getOriginal_vertex());
            }
            else{
                if(!added_goal) {
                    path.addVertex(vertex.getOriginal_vertex());
                    added_goal = true;
                }
            }
        }
        return path;
    }

    public Path getPath(){
        Path path = new Path();
        for (MDD_Vertex vertex : vertexes) {
            path.addVertex(vertex.getOriginal_vertex());
        }
        return path;
    }

    private double getMDDCost(){
        if(vertexes.isEmpty())
            throw new InputMismatchException("There must be vertexes to get the MDD cost");
        return vertexes.get(0).getMdd().getMDD_cost();
    }

    private double getMDDBiddingCost(){
        if(vertexes.isEmpty())
            throw new InputMismatchException("There must be vertexes to get the MDD bidding cost");
        return vertexes.get(0).getMdd().getBidding_cost();
    }

    public double getMDDGrade(){
        return getMDDCost() - getMDDBiddingCost();
    }
}
