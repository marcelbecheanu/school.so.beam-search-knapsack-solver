package knapsack.algorithm;

import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.ArrayList;

public class BeamSearch {

    private static ArrayList<int[]> sols;

    public static void initialize(){
        sols = new ArrayList<int[]>();

        int lb = lowerBound();
        System.out.println("LowerBound: " + lb);

        int teste[] = new int[Main.getManager().getSize()];
        for (int i = 0; i < Main.getManager().getSize(); i++) {
            teste[i] = -1;
        }

        teste[0] = 1;
        teste[1] = 1;

        sols.add(teste);

        int ub = upperBound(sols.get(0));
        System.out.println("UpperBound: " + ub);

    }

    public static int lowerBound(){
        DataManager dataManager = Main.getManager();

        int sum = 0;
        int capacity = dataManager.getMaxWeight();

        for (int i = 0; i < dataManager.getSize(); i++) {
            if((capacity -= dataManager.getItems()[i][1]) >= 0){
                sum += dataManager.getItems()[i][0];
            }
        }

        return sum;
    }

    public static int upperBound(int[] sol){
        DataManager dataManager = Main.getManager();

        int Wmax = dataManager.getMaxWeight();
        int capacity = dataManager.getMaxWeight();
        int sumInSol = 0;
        int sumOutSol = 0;

        // Variables of step 2
        int sumValInSol = 0;
        int c = -1;

        for (int i = 0; i < dataManager.getSize(); i++) {
            if(sol[i] == 1){ // Sum Weight inside Sol
                sumInSol += dataManager.getItems()[i][1];
            } else if(sol[i] == -1 && capacity >= 0) { // Sum weight outside sol but in valid.
                sumOutSol += dataManager.getItems()[i][1];
                sumValInSol += dataManager.getItems()[i][0]; // step 2
            }
            if (sol[i] == -1 && capacity < 0 && c == -1){
                c = i;
            }
            capacity -= dataManager.getItems()[i][1];
        }
        Wmax = Wmax - sumOutSol - sumInSol;

        // Step 2 - Max
        int sumVal = dataManager.eval(sol);

        // Possiveis melhorias criar um codigo que verifica de inicio se o item ultrapassa o valor da mochila
        if(c == -1){
            int MaxIntStep2Part1 = (int) 0;
            MaxIntStep2Part1 = (sumVal + sumValInSol  + MaxIntStep2Part1);

            int MaxIntStep2Part2 = (int) 0;
            MaxIntStep2Part2 = (sumVal + sumValInSol  + MaxIntStep2Part2);

            return Math.max(MaxIntStep2Part1, MaxIntStep2Part2);
        } else {
            int MaxIntStep2Part1 = (int) Math.floor(Wmax * ((double) dataManager.getItems()[c+1][0] / dataManager.getItems()[c+1][1]));
            MaxIntStep2Part1 = (sumVal + sumValInSol  + MaxIntStep2Part1);

            int MaxIntStep2Part2 = (int) Math.floor(dataManager.getItems()[c][0] - (dataManager.getItems()[c][1] - Wmax) * ((double) dataManager.getItems()[c-1][0] /  dataManager.getItems()[c-1][1]));
            MaxIntStep2Part2 = (sumVal + sumValInSol  + MaxIntStep2Part2);

            return Math.max(MaxIntStep2Part1, MaxIntStep2Part2);
        }

    }


}
