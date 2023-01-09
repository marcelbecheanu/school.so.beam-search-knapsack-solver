package knapsack.algorithm;

import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class BeamSearch {

    private static ArrayList<int[]> sols;
    private static DataManager dataManager;

    public static void initialize(){
        dataManager = Main.getManager();
        sols = new ArrayList<int[]>();
        int[] global = new int[dataManager.getSize()];

        for (int i = 0; i < Math.pow(dataManager.getSize(),2); i++) {

            int[] result = loop();
            for (int c = 0; c < result.length; c++) {
                if(result[c] == -1){
                    result[c] = 0;
                }
            }

            if(dataManager.eval(global) <= dataManager.eval(result)){
                global = result;
            }

            System.out.println("Result: " + Arrays.toString(result));
            System.out.println("Value: " + dataManager.eval(result));
            System.out.println("Weight: " + dataManager.sumWeights(result));
        }
        System.out.println("Global----");
        System.out.println("Result: " + Arrays.toString(global));
        System.out.println("Value: " + dataManager.eval(global));
        System.out.println("Weight: " + dataManager.sumWeights(global));

    }

    public static int[] loop(){
        sols = InitialSolution();
        int[] lowerBoundSol = lowerBound();
        int lowerBoundEval = dataManager.eval(lowerBoundSol);

        while(sols.size() > 0){
            ArrayList<int[]> childs = getChilds(sols);
            Iterator<int[]> iterator = childs.iterator();
            while (iterator.hasNext()) {
                int[] child = iterator.next();

                if(dataManager.sumWeights(child) > dataManager.getMaxWeight()){
                    iterator.remove();
                    continue;
                }

                int ub = upperBound(child);
                if(ub >= lowerBoundEval){
                    if(dataManager.eval(child) >= lowerBoundEval) {
                        lowerBoundSol = child;
                        lowerBoundEval = dataManager.eval(lowerBoundSol);
                    }
                } else {
                    iterator.remove();
                }
            }
            sols = selectSolution(childs);  // temos um problema aqui....
             //sols = childs;
        }
        return lowerBoundSol;
    }

    public static ArrayList<int[]> selectSolution(ArrayList<int[]> sols) {
        ArrayList<int[]> temp = new ArrayList<>(sols);
        long seed = System.currentTimeMillis();
        Random random = new Random();
        random.setSeed(seed);
        while(temp.size() > (int) Math.ceil((double)sols.size() / 2)){
            int randomInt = random.nextInt(0, sols.size());
            temp.remove(randomInt);
        }
        return temp;
    }

    public static ArrayList<int[]> getChilds(ArrayList<int[]> sols) {
        ArrayList<int[]> temp = new ArrayList<int[]>();
        for (int i = 0; i < sols.size(); i++) {

            int[] solTemp = sols.get(i);
            int level = -1;

            for (int j = 0; j < solTemp.length; j++) {
                if (solTemp[j] == -1) {
                    level = j;
                    break;
                }
            }

            if(level != -1){
                int[] temp1 = solTemp.clone();
                temp1[level] = 1;
                temp.add(temp1);

                int[] temp2 = solTemp.clone();
                temp2[level] = 0;
                temp.add(temp2);

            }
        }
        return temp;
    }

    public static ArrayList<int[]> InitialSolution(){
        ArrayList<int[]> initial = new ArrayList<int[]>();
        initial.add(new int[dataManager.getSize()]);
        Arrays.fill(initial.get(0), -1);
        return initial;
    }

    public static int[] lowerBound(){
        int sol[] = new int[dataManager.getSize()];
        int capacity = dataManager.getMaxWeight();

        for (int i = 0; i < dataManager.getSize(); i++) {
            if((capacity -= dataManager.getItems()[i][1]) >= 0){
                sol[i] = 1;
            }else{
                sol[i] = -1;
            }
        }

        return sol;
    }

    public static int upperBound(int[] sol){
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
        if(c == -1 || c+1 >= dataManager.getSize()){
            return sumVal + sumValInSol;
        } else {
            int MaxIntStep2Part1 = (int) Math.floor(Wmax * ((double) dataManager.getItems()[c+1][0] / dataManager.getItems()[c+1][1]));
            MaxIntStep2Part1 = (sumVal + sumValInSol  + MaxIntStep2Part1);

            int MaxIntStep2Part2 = (int) Math.floor(dataManager.getItems()[c][0] - (dataManager.getItems()[c][1] - Wmax) * ((double) dataManager.getItems()[c-1][0] /  dataManager.getItems()[c-1][1]));
            MaxIntStep2Part2 = (sumVal + sumValInSol  + MaxIntStep2Part2);

            return Math.max(MaxIntStep2Part1, MaxIntStep2Part2);
        }
    }


}
