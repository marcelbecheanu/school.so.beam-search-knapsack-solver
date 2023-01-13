package knapsack.handlers.threadmanager;

import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ThreadManager {
    private static DataManager dataManager;
    private static ArrayList<ThreadAlgorithm> threads;
    public static Semaphore semaphoreToAccessToGlobal;

    public static int[] globalSolution;
    public static int globalValue;

    public static long runtime;
    public static int runs;


    public ThreadManager(int processors, int time){
        dataManager = Main.getManager();
        semaphoreToAccessToGlobal = new Semaphore(1);

        threads = new ArrayList<ThreadAlgorithm>();

        int[] solutionOfLowerBound = dataManager.getLowerBound();
        int valueOfLowerBound = dataManager.eval(dataManager.getLowerBound());

        for (int processor = 0; processor < processors; processor++) {
            ThreadAlgorithm thread = new ThreadAlgorithm(solutionOfLowerBound, valueOfLowerBound, time);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception ex) {
                System.out.println("Processor Interrupt: " + ex.getMessage());
            }
        }

        System.out.println(" > Valor da soma total: " + globalValue);
        System.out.println(" > Valor do peso total: " + dataManager.sumWeights(globalSolution));
        System.out.println(" > Número de iterações necessárias: " + runs);
        System.out.println(" > Tempo total de execução: " + runtime);
    }


}
