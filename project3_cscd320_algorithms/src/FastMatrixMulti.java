import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class FastMatrixMulti {
    public static void main(String[] args) {
        //System.out.println("hello world");
        //I wasn't sure how long the file would be, so I go through it twice, once to count and again to save data
        File matrixFile = new File(args[0]);
        int count = 0;
        if (!matrixFile.exists())
        {
            System.out.println("the file does not exist please try again");
            System.exit(1);
        }
            try{
            BufferedReader counter = new BufferedReader(new FileReader(matrixFile));
            while (counter.readLine() != null) {
                count++;
            }
            counter.close();
        }catch(IOException e){
            System.out.println("file not found" + e);
        }

            int[] matrices = new int[count];
            //now actually save the data
            try {
                int i = 0;
               Scanner readMatrix = new Scanner(matrixFile);
               while (readMatrix.hasNext()){
                   matrices[i] = Integer.parseInt(readMatrix.nextLine());
                   i++;
               }
            }catch (FileNotFoundException e){
                System.out.println("file not found" + e);
            }

        System.out.print("the optimal position is: ");
        System.out.println("\nthe time cost is: " + matrixChainOrder(matrices));

    }

    public static int matrixChainOrder(int[] inputMatrices){
        int numOfMatricies = inputMatrices.length;
        int[][] optimalTime = new int[numOfMatricies][numOfMatricies];
        int[][] optimalPosition = new int[numOfMatricies][numOfMatricies];
        int subStart, end, k, subLength, q;
        for(int i = 1; i < numOfMatricies; i++) {
            optimalTime[i][i] = 0;
        }

            for(subLength = 2; subLength < numOfMatricies; subLength++)
            {
                for(subStart = 1; subStart < numOfMatricies - subLength + 1; subStart++)
                {
                    end = subStart + subLength - 1;
                    if(end == numOfMatricies)
                        continue;
                    optimalTime[subStart][end] = Integer.MAX_VALUE;
                    for(k = subStart; k <= end-1; k++)
                    {
                        q = optimalTime[subStart][k] + optimalTime[k+1][end]
                                + inputMatrices[subStart-1] * inputMatrices[k] * inputMatrices[end];
                        if (q < optimalTime[subStart][end])
                        {
                            optimalTime[subStart][end] = q;
                            optimalPosition[subStart][end] = k;
                        }
                    }
                }
            }
        print_optimal_parens(optimalPosition,1,optimalPosition.length-1);
        return optimalTime[1][numOfMatricies-1];
    }
    public static void print_optimal_parens(int[][] pos, int start, int end) {
        if (start == end) {
            System.out.print("A" + start);
        } else {
            System.out.print("(");
            print_optimal_parens(pos,start,pos[start][end]);
            print_optimal_parens(pos, pos[start][end] + 1, end);
            System.out.print(")");
        }
    }

}