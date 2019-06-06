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
                    Point point = new Point(column,row);
                    Vertex vertex=new Vertex(graph,point);
                    addEdges(vertex,row,column);
                    graph.addVertex(vertex);
                }
            }
        }
        return graph;
    }

    private void addEdges(Vertex vertex, int row, int column) {
        //check up
        addEdge(vertex,row-1,column);
        //check left
        addEdge(vertex,row,column-1);
        //check right
        addEdge(vertex,row,column+1);
        //check down
        addEdge(vertex,row+1,column);
    }

    private void addEdge(Vertex from, int row, int column) {
        if(inArray(row,column)){
            if(array[row][column]==free_pass){
                Point point = new Point(column,row);
                Vertex to = new Vertex(graph,point);
                Edge edge = new Edge(graph,from,to);
                from.addEdge(edge);
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
