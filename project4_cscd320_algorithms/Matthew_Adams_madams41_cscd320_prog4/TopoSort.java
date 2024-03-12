import java.io.*;
import java.util.*;
public class TopoSort {
    public static void main(String[] args)
    {
        //System.out.println("Hello world!");
        int[][]dag = readFile(args[0]);
        int size = 0;
        String print= "";
        for(int i =0; i< dag.length; i++) {
            String out = saveOrder(dag, i);
            //System.out.println(out);
            if(out.length() > size){
                size = out.length();
                print = out;
            }
        }
        System.out.println(print);

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
                        String[] childNodesString = parentNode[1].split(",");
                        int[] childNodes = new int[childNodesString.length];
                        for(int j =0; j < childNodesString.length;j++){
                            childNodes[j] = Integer.parseInt(childNodesString[j]);
                        }
                        Arrays.sort(childNodes);
                        for(int i = 0; i< childNodesString.length; i++) {
                            dag[dagx][i] = childNodes[i];
                        }
                    }else {
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
    //inital call is dag 0
    public static String saveOrder(int[][] dag, int index){

        String ret = Integer.toString(index);
        //check if path has been gone down before
        Boolean cont = true;
        for(int i = 0; i < dag[index].length && cont; i++){
            if (dag[index][i] != -1 ){
                ret = ret + ","+  saveOrder(dag,dag[index][i]);
                cont = false;
            }
        }
        return ret;
    }
}
