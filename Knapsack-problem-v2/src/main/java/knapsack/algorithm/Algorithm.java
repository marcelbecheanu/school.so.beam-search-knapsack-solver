package knapsack.algorithm;

import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Algorithm {
    private DataManager dataManager;
    private Random random;
    private ArrayList<int[]> solutions;

    private long startedTime;
    private int time;
    private int alpha;
    private int[] solutionOfLowerBound;
    private int valueOfLowerBound;

    public Algorithm(){
        this.dataManager = Main.getManager();
        this.solutions = new ArrayList<int[]>();
        this.alpha = dataManager.getAlpha();

        this.random = new Random();
        this.random.setSeed(System.currentTimeMillis() / (random.nextInt(100) + 1));

        this.solutionOfLowerBound = this.getLowerBound();
        this.valueOfLowerBound = dataManager.eval(this.solutionOfLowerBound);
        this.startedTime = -1;
        this.time = -1;
    }

    public Algorithm(int[] solutionOfLowerBound, int valueOfLowerBound, int time){
        this.dataManager = Main.getManager();
        this.solutions = new ArrayList<int[]>();
        this.alpha = dataManager.getAlpha();

        this.random = new Random();
        this.random.setSeed(System.currentTimeMillis() / (random.nextInt(100) + 1));

        this.solutionOfLowerBound = solutionOfLowerBound;
        this.valueOfLowerBound = valueOfLowerBound;
        this.startedTime = System.nanoTime();
        this.time = time;
    }

    public void reset() {
        this.solutionOfLowerBound = getLowerBound();
        this.valueOfLowerBound = dataManager.eval(solutionOfLowerBound);
    }

    public int[] getBeamSearch(){
        reset();
        solutions = getInitialSolution();
        while(solutions.size() > 0 && isValidTime()){
            ArrayList<int[]> childs = getChilds(solutions);
            Iterator<int[]> iterator = childs.iterator();
            while (iterator.hasNext()) {
                int[] child = iterator.next();
                int valueOfUpperBound = getUpperBound(child);
                if(valueOfUpperBound >= valueOfLowerBound){
                    int cacheOfValueChild = dataManager.eval(child);
                    if(cacheOfValueChild >= valueOfLowerBound) {
                        solutionOfLowerBound = child;
                        valueOfLowerBound = cacheOfValueChild;
                    }
                } else {
                    iterator.remove();
                }
            }
            solutions = selectSolution(childs, alpha);
        }
        return solutionOfLowerBound;
    }

    public boolean isValidTime(){
        return time == -1 ? true : ((((long) System.nanoTime() - startedTime) / 1000000000) <= time);
    }

    public ArrayList<int[]> selectSolution(ArrayList<int[]> solutions, int alpha) {
        ArrayList<int[]> filteredSolutions = new ArrayList<>(solutions);
        while(filteredSolutions.size() > alpha){
            filteredSolutions.remove(random.nextInt(0, filteredSolutions.size()));
        }
        return filteredSolutions;
    }

    public ArrayList<int[]> getChilds(ArrayList<int[]> solutions) {
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
    public ArrayList<int[]> getInitialSolution(){
        ArrayList<int[]> initial = new ArrayList<int[]>();
        initial.add(new int[dataManager.getSize()]);
        Arrays.fill(initial.get(0), -1);
        return initial;
    }
    public int[] getLowerBound(){
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
    public int getUpperBound(int[] solution){
        int Wmax = dataManager.getMaxWeight();
        int capacity = dataManager.getMaxWeight();
        int sumInSol = 0;
        int sumOutSol = 0;

        // Variables of step 2
        int sumValInSol = 0;
        int c = -1;
        for (int i = 0; i < dataManager.getSize(); i++) {
            if(solution[i] == 1){ // Sum Weight inside Sol
                sumInSol += dataManager.getItems()[i][1];
            } else if(solution[i] == -1 && capacity >= 0) { // Sum weight outside sol but in valid.
                sumOutSol += dataManager.getItems()[i][1];
                sumValInSol += dataManager.getItems()[i][0]; // step 2
            }
            if (solution[i] == -1 && capacity < 0 && c == -1){
                c = i;
            }
            capacity -= dataManager.getItems()[i][1];
        }
        Wmax = Wmax - sumOutSol - sumInSol;

        // Step 2 - Max
        int sumVal = dataManager.eval(solution);
        if(c == -1) {
            return sumVal + sumValInSol;
        }
        else if(c+1 >= dataManager.getSize()) {
            int MaxIntStep2Part2 = (int) Math.floor(dataManager.getItems()[c][0] - (dataManager.getItems()[c][1] - Wmax) * ((double) dataManager.getItems()[c-1][0] /  dataManager.getItems()[c-1][1]));
            MaxIntStep2Part2 = (sumVal + sumValInSol  + MaxIntStep2Part2);

            return MaxIntStep2Part2;
        } else {
            int MaxIntStep2Part1 = (int) Math.floor(Wmax * ((double) dataManager.getItems()[c+1][0] / dataManager.getItems()[c+1][1]));
            MaxIntStep2Part1 = (sumVal + sumValInSol  + MaxIntStep2Part1);

            int MaxIntStep2Part2 = (int) Math.floor(dataManager.getItems()[c][0] - (dataManager.getItems()[c][1] - Wmax) * ((double) dataManager.getItems()[c-1][0] /  dataManager.getItems()[c-1][1]));
            MaxIntStep2Part2 = (sumVal + sumValInSol  + MaxIntStep2Part2);

            return Math.max(MaxIntStep2Part1, MaxIntStep2Part2);
        }
    }

    public int[] getSolutionOfLowerBound() {
        return solutionOfLowerBound;
    }

    public int getValueOfLowerBound() {
        return valueOfLowerBound;
    }

    public long getStartedTime() {
        return startedTime;
    }
}
