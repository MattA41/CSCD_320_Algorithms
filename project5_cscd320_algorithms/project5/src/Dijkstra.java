import java.io.*;
import java.util.*;
public class Dijkstra {
    public static void main(String[] args) {

        //System.out.println("Hello world!");
        int[][]dag = readFile(args[0]);
        //test to see if graph was made correctly
        /*for(int i = 0; i < dag.length; i++){
            for(int j = 0; j < dag[i].length; j++){
                if(dag[i][j] != -1) {
                    System.out.println("node " + i + " weight " + dag[i][j] + " child " + j);
                }
            }
        }*/
    }
    public static int[][] readFile(String filename){
        //creates and fills 2d array where x is the current node and y is the node it points to
        //the data at xy = the weight of that connection
        //this program DOES NOT SUPPORT NEGATIVE WEIGHTS
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
        //fill with -1 for knowing when to stop
        for(int i =0; i<dag.length; i++){
            for(int j =0; j < dag[0].length; j++){
                dag[i][j] = -1;
            }
        }
        //get data from dag file
        try
        {
            Scanner readDag = new Scanner(dagFile);
            while (readDag.hasNext()){
                String rawIn = readDag.nextLine();
                //split into node and node children
                String[] parentNode = rawIn.split(":");
                int dagx = Integer.parseInt(parentNode[0]);
                if(parentNode.length >= 2){
                    // split the list of nodes that are under parent
                    String[] childNodesString = parentNode[1].split(";");

                    for(int j =0; j < childNodesString.length; j++){
                        //split the children to their weights and nodes
                        String[] weightString = childNodesString[j].split(",");
                        int dagy = Integer.parseInt(weightString[0]);
                        dag[dagx][dagy] = Integer.parseInt(weightString[1]);
                    }

                }

            }

        }catch (FileNotFoundException e)
        {
            System.out.println("file not found " + e);
        }

        return dag;
    }
}