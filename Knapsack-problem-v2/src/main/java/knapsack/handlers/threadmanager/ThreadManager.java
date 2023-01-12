package knapsack.handlers.threadmanager;

import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
    private static DataManager dataManager;
    private static ArrayList<ThreadAlgorithm> threads;
    public ThreadManager(int processors, int time){
        dataManager = Main.getManager();
        threads = new ArrayList<ThreadAlgorithm>();

        int[] solutionOfLowerBound = dataManager.getLowerBound();
        int valueOfLowerBound = dataManager.eval(dataManager.getLowerBound());

        for (int processor = 0; processor < processors; processor++) {
            ThreadAlgorithm thread = new ThreadAlgorithm(solutionOfLowerBound, valueOfLowerBound, time);
            threads.add(thread);
            thread.start();
        }

        System.out.println("Iniciado!!!!");
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception ex) {
                System.out.println("Processor Interrupt: " + ex.getMessage());
            }
        }
        System.out.println("Finalizado!!!!");
    }
}
