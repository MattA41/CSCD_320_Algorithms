import java.io.*;
import java.util.*;
public class TopoSort {
    public static void main(String[] args)
    {
        //System.out.println("Hello world!");
        int[][]dag = readFile(args[0]);
        for(int i =0; i<dag.length; i++){
            for(int j =0; j < dag[0].length; j++){
                System.out.println(dag[i][j] + " " + i);
            }
        }
    }
    public static int[][] readFile(String filename){
        File dagFile = new File(filename);
            //count num of lines to make array
            int count = 0;
            try {
                BufferedReader counter = new BufferedReader(new FileReader(dagFile));
                while (counter.readLine() != null) {
                    count++;
                }
                counter.close();

            }catch (IOException j) {
                System.out.println("file does not exits " + j);
            }
            // create array of size count, count = num of nodes
            int[][] dag = new int[count][count];
            //get data from dag file
            try
            {
                Scanner readDag = new Scanner(dagFile);
                while (readDag.hasNext()){
                    String rawIn = readDag.nextLine();
                    String[] parentNode = rawIn.split(":");
                    int dagx = Integer.parseInt(parentNode[0]);
                    if(parentNode.length >= 2){
                        String[] childNodes = parentNode[1].split(",");
                        //System.out.println(parentNode[0] + " " + childNodes[0]);

                        for(int i = 0; i< childNodes.length; i++)
                        {
                            dag[dagx][i] = Integer.parseInt(childNodes[i]);
                        }
                    }else
                    {
                        //if it does not have children it gets -1
                        dag[dagx][0] = -1;
                    }

                }

            }catch (FileNotFoundException e)
            {
                System.out.println("file not found " + e);
            }

        return dag;
    }
}
