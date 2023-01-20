package knapsack.handlers.threadmanager;

import knapsack.algorithm.Algorithm;
import knapsack.control.Main;

public class ThreadAlgorithm extends Thread {

    private Algorithm algorithm;

    public ThreadAlgorithm(int[] solutionOfLowerBound, int valueOfLowerBound, int time){
        algorithm = new Algorithm(solutionOfLowerBound, valueOfLowerBound, time);
    }

    @Override
    public void run() {
        while(algorithm.isValidTime()) {
            algorithm.getBeamSearch();
        }

        if(algorithm.getValueOfLowerBound() > ThreadManager.globalValue){
            try {
                ThreadManager.semaphoreToAccessToGlobal.acquire();
                if(algorithm.getValueOfLowerBound() > ThreadManager.globalValue){
                    ThreadManager.globalSolution = algorithm.getSolutionOfLowerBound();
                    ThreadManager.globalValue = algorithm.getValueOfLowerBound();
                    ThreadManager.runs = algorithm.getRunsSol();
                    ThreadManager.runtime = ((long) algorithm.getFinishedTimeOfLowerBound() - algorithm.getStartedTime());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ThreadManager.semaphoreToAccessToGlobal.release();
        }



    }
}
