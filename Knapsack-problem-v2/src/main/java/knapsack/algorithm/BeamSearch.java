package knapsack.algorithm;

import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.*;

public class BeamSearch {

    private static ArrayList<int[]> sols;
    private static DataManager dataManager;
    private static int alpha;

    public static void initialize(){
        dataManager = Main.getManager();
        alpha = dataManager.getAlpha();

        if(true) {
            int[] global = new int[dataManager.getSize()];

            long avgtime = 0;

            for (int i = 0; i < 5; i++) {
                 long startTime = System.nanoTime();

                int[] result = loop();


                if(dataManager.eval(global) <= dataManager.eval(result)){
                    global = result;
                }

                System.out.println("Is Valid: " + dataManager.isValidWeight(result));
                System.out.println("Result: " + Arrays.toString(result));
                System.out.println("Value: " + dataManager.eval(result));
                System.out.println("Weight: " + dataManager.sumWeights(result));


                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;

                if(avgtime == 0){
                    avgtime = timeElapsed;
                }else{
                    avgtime = ((avgtime + timeElapsed) / 2);
                }



                System.out.println("Execution time in nanoseconds: " + timeElapsed);
                System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
                System.out.println("Execution avg time in nanoseconds: " + avgtime);
                System.out.println("Execution avg time in milliseconds: " + avgtime / 1000000);


            }
            System.out.println("Global----");
            System.out.println("Is Valid: " + dataManager.isValidWeight(global));
            System.out.println("Result: " + Arrays.toString(global));
            System.out.println("Value: " + dataManager.eval(global));
            System.out.println("Weight: " + dataManager.sumWeights(global));
        }
    }
    public static int[] loop(){
        sols = getInitialSolution();
        int[] lowerBoundSol = getLowerBound();
        int lowerBoundEval = dataManager.eval(lowerBoundSol);

        while(sols.size() > 0){
            ArrayList<int[]> childs = getChilds(sols);
            Iterator<int[]> iterator = childs.iterator();
            while (iterator.hasNext()) {
                int[] child = iterator.next();

                int ub = getUpperBound(child);
                if(ub >= lowerBoundEval){
                    if(dataManager.eval(child) >= lowerBoundEval) {
                        lowerBoundSol = child;
                        lowerBoundEval = dataManager.eval(lowerBoundSol);
                    }
                } else {
                    iterator.remove();
                }
            }
            sols = selectSolution(childs, alpha);
        }
        return lowerBoundSol;
    }
    public static ArrayList<int[]> selectSolution(ArrayList<int[]> sols, int alpha) {
        ArrayList<int[]> temp = new ArrayList<>(sols);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        while(temp.size() > alpha){
            temp.remove(random.nextInt(0, temp.size()));
        }
        return temp;
    }

    public static ArrayList<int[]> getChilds(ArrayList<int[]> solutions) {
        ArrayList<int[]> childs = new ArrayList<>();
        for (int[] child : solutions) {
            for (int index = 0; index < child.length; index++) {
                if(child[index] == -1){
                    int[] cloneDenied = child.clone(),
                            cloneAccepted = child.clone();

                    cloneDenied[index] = 0;
                    cloneAccepted[index] = 1;

                    childs.add(cloneDenied);
                    if(dataManager.isValidWeight(cloneAccepted))
                        childs.add(cloneAccepted);

                    break;
                }
            }
        }
        return childs;
    }
    public static ArrayList<int[]> getInitialSolution(){
        ArrayList<int[]> initial = new ArrayList<int[]>();
        initial.add(new int[dataManager.getSize()]);
        Arrays.fill(initial.get(0), -1);
        return initial;
    }
    public static int[] getLowerBound(){
        int solution[] = new int[dataManager.getSize()];
        int capacity = dataManager.getMaxWeight();
        for (int i = 0; i < dataManager.getSize(); i++) {
            if((capacity -= dataManager.getItems()[i][1]) >= 0){
                solution[i] = 1;
            }else{
                solution[i] = -1;
            }
        }
        return solution;
    }
    public static int getUpperBound(int[] sol){
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
        if(c == -1 || c+1 >= dataManager.getSize()) {
            return sumVal + sumValInSol;
        } /*
        com isto da erro...
        else if(c+1 >= dataManager.getSize()) {
            int MaxIntStep2Part2 = (int) Math.floor(dataManager.getItems()[c][0] - (dataManager.getItems()[c][1] - Wmax) * ((double) dataManager.getItems()[c-1][0] /  dataManager.getItems()[c-1][1]));
            MaxIntStep2Part2 = (sumVal + sumValInSol  + MaxIntStep2Part2);

            return MaxIntStep2Part2;
        } */else {
            int MaxIntStep2Part1 = (int) Math.floor(Wmax * ((double) dataManager.getItems()[c+1][0] / dataManager.getItems()[c+1][1]));
            MaxIntStep2Part1 = (sumVal + sumValInSol  + MaxIntStep2Part1);

            int MaxIntStep2Part2 = (int) Math.floor(dataManager.getItems()[c][0] - (dataManager.getItems()[c][1] - Wmax) * ((double) dataManager.getItems()[c-1][0] /  dataManager.getItems()[c-1][1]));
            MaxIntStep2Part2 = (sumVal + sumValInSol  + MaxIntStep2Part2);

            return Math.max(MaxIntStep2Part1, MaxIntStep2Part2);
        }
    }


}
