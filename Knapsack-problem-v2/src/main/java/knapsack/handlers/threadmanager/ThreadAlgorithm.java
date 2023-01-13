package knapsack.handlers.threadmanager;

import knapsack.algorithm.Algorithm;
import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.ArrayList;
import java.util.Arrays;


public class ThreadAlgorithm extends Thread {

    private Algorithm algorithm;

    public ThreadAlgorithm(int[] solutionOfLowerBound, int valueOfLowerBound, int time){
        algorithm = new Algorithm(solutionOfLowerBound, valueOfLowerBound, time);
    }
    @Override
    public void run() {
        while(algorithm.isValidTime()) {
            long timelocal = System.nanoTime();
            algorithm.getBeamSearchGlobal();
        }
    }
}
