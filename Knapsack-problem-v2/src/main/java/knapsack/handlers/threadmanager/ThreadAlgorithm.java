package knapsack.handlers.threadmanager;

import knapsack.algorithm.Algorithm;
import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.ArrayList;
import java.util.Arrays;


public class ThreadAlgorithm extends Thread {

    private Algorithm algorithm;

    public ThreadAlgorithm(int[] solutionOfLowerBound, int valueOfLowerBound, int time){
        algorithm = new Algorithm(solutionOfLowerBound, valueOfLowerBound, 60);
    }

    @Override
    public void run() {
        ThreadManager threadManager = Main.getThreadManager();
        while(algorithm.isValidTime()){
            int[] solution = algorithm.getBeamSearch();
            System.out.println(this.getId() + ": " + Arrays.toString(solution) + " - " +Main.getManager().isValidWeight(solution) + " - " + algorithm.getValueOfLowerBound());
            if(algorithm.getValueOfLowerBound() >= threadManager.globalValue){
                threadManager.globalValue = algorithm.getValueOfLowerBound();
                threadManager.globalSolution = algorithm.getSolutionOfLowerBound();
            }

            System.out.println("fork id:" + this.getId());
        }
        System.out.println("TIME: " + ((long) System.nanoTime() - algorithm.getStartedTime()) / 1000000000);
    }
}
