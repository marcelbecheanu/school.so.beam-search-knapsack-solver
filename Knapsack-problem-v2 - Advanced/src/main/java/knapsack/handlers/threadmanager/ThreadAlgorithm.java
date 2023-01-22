package knapsack.handlers.threadmanager;

import knapsack.algorithm.Algorithm;

public class ThreadAlgorithm extends Thread {

    private Algorithm algorithm;
    public int updateInterval;
    public long IntervalTime;

    public ThreadAlgorithm(int[] solutionOfLowerBound, int valueOfLowerBound, int time, int percent){
        algorithm = new Algorithm(solutionOfLowerBound, valueOfLowerBound, time);
        updateInterval = (int)(time * ((double) percent / 100));
        IntervalTime = System.nanoTime();
    }

    @Override
    public void run() {
        while(algorithm.isValidTime()) {
            algorithm.getBeamSearch(this);
            checkUpdate();
        }
    }

    public void checkUpdate(){
        if ((((long) System.nanoTime() - IntervalTime)  / 1000000000) >= updateInterval) {
            IntervalTime = System.nanoTime();
            updateGlobalSolution();
        }
    }

    public void updateGlobalSolution() {
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
        } else if(algorithm.getValueOfLowerBound() < ThreadManager.globalValue) {
            algorithm.setSolutionOfLowerBound(ThreadManager.globalSolution);
            algorithm.setValueOfLowerBound(ThreadManager.globalValue);
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
