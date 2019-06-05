import java.io.File;

public class Main {

    public static void main(String[] args) {
        //test genarator
        int[][] ints={{1,0},{0,1},};
        Graph_Genarator graph_genarator=new Graph_Genarator(ints);
        Graph graph=graph_genarator.Graph_From_Array();
        System.out.println(graph.getEdges().size());
        System.out.println(graph.getVertexes().size());
        //test map to array
        int[][] array= Map_To_Array.map_To_Array(new File("E:\\stiven\\Documents\\Coding\\Java\\MAPF_IBundle\\src\\test"));
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
}
