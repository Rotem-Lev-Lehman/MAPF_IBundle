public class Graph_Generator {
    private int[][] array;
    private Graph graph;
    private int free_pass = 0;
    private int wall = 1;

    public Graph_Generator(int[][] array) {
        this.array = array;
    }

    public Graph generate(){

        graph=new Graph();
        int count;
        for(int row=0;row<array.length;row++){
            for(int column=0;column<array[row].length;column++){
                if(array[row][column]==free_pass){
                    Vertex vertex=new Vertex(graph);
                    Point point = new Point(row,column);
                    addVertexes(vertex,row,column);
                    graph.addVertex(point,vertex);
                }
            }
        }
        return graph;
    }

    private void addVertexes(Vertex vertex, int row, int column) {
        //check up
        addVertex(vertex,row-1,column);
        //check left
        addVertex(vertex,row,column-1);
        //check right
        addVertex(vertex,row,column+1);
        //check down
        addVertex(vertex,row+1,column);
    }

    private void addVertex(Vertex from, int row, int column) {
        if(inArray(row,column)){
            if(array[row][column]==free_pass){
                Vertex to = new Vertex(graph);
                Edge edge = new Edge(graph,from,to);
                graph.addEdge(edge);
            }
        }
    }

    private boolean inArray(int row,int column) {
        if (row < array.length && row >= 0 && column < array[row].length && column >= 0) {
            return true;
        }
        return false;
    }
}
