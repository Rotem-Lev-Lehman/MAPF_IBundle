import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map_To_Array {
    private static char dark_void = '@';
    private static char tree = 'T';
    private static char free_pass = '.';

    public static int[][] map_To_Array(File file){
        int[][] array=null;
        try {
            FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            bufferedReader.readLine();
            String height_line=bufferedReader.readLine();
            int height=Integer.parseInt(height_line.split(" ")[1]);
            String width_line=bufferedReader.readLine();
            int width=Integer.parseInt(width_line.split(" ")[1]);
            String nextLine=bufferedReader.readLine();
            array=new int[width][height];
            int row=0;
            while ((nextLine=bufferedReader.readLine())!=null && row<height){
                for(int column=0;column<width;column++){
                    char symbol = nextLine.charAt(column);
                    if(symbol == dark_void || symbol == tree){
                        array[row][column]=1;
                    }
                    else if(symbol == free_pass){
                        array[row][column]=0;
                    }
                }
                row++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
