import java.io.*;
import java.util.*;
public class Dijkstra {
    public static void main(String[] args) {

        //System.out.println("Hello world!");
        int[][]dag = readFile(args[0]);
        dijkstra(dag, Integer.parseInt(args[1]));
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
    public static void dijkstra(int[][] graph, int start){
        int vertices = graph.length;
        boolean[] visited = new boolean[vertices];
        int[] distance = new int[vertices];
        int[]prev = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        distance[start] = 0;

        for(int i = 0; i < vertices -1; i++){
            int minVertex = findMinVertex(distance, visited);
            visited[minVertex] = true;
            for(int j = 0; j < vertices; j++){
                if(!visited[j] && graph[minVertex][j] != -1 && distance[minVertex] != Integer.MAX_VALUE){
                    int newDistance =distance[minVertex] + graph[minVertex][j];
                    if (newDistance < distance[j]){
                        distance[j] = newDistance;
                        prev[j] = minVertex;
                    }
                }
            }
        }
        printSolution(distance, prev, start);
    }
    public static int findMinVertex(int[] distance, boolean[] visited){
        int minVertex = -1;
        for(int i = 0; i < distance.length; i++ ){
            if (!visited[i] && (minVertex == -1 || distance[i] < distance[minVertex])){
                minVertex = i;
            }
        }
        return minVertex;
    }
    public static void printSolution(int[] distance, int[] prev, int start){
        for(int i = 0; i < distance.length; i++){
            if(distance[i] == Integer.MAX_VALUE){
                System.out.println("[" + i +"]unreachable");;
            }else if (i != start){
                List<Integer> path = new ArrayList<>();
                getPath(prev, i, path);
                System.out.print("[" + i + "]shortest path: ");
                if(path.size() == 0){
                    System.out.print("unreachable");
                }else{
                    System.out.print("(" + start);
                    for(int j = 0; j < path.size(); j++){
                        System.out.print("," + path.get(j));
                    }
                    System.out.print(")");

                }
                System.out.println(" shortest distance:" + distance[i]);

            }
        }
    }
    public static void getPath(int[] prev, int vertex, List<Integer> path){
        if(vertex == -1){
            return;
        }
        getPath(prev, prev[vertex], path);
        path.add(vertex);
    }
}