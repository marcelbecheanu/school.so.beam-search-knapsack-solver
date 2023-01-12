package knapsack.handlers.threadmanager;

import knapsack.algorithm.Algorithm;

public class ThreadAlgorithm extends Thread {

    private Algorithm algorithm;
    private int time;

    public ThreadAlgorithm(int[] solutionOfLowerBound, int valueOfLowerBound, int time){
        algorithm = new Algorithm(solutionOfLowerBound, valueOfLowerBound);
        this.time = time;
    }

    @Override
    public void run() {
        long ustartTime = System.nanoTime();

        while(true){
            if((((long) System.nanoTime() - ustartTime) / 1000000000) >= time)
                break;



            System.out.println("fork id:" + this.getId());
        }
    }
}
