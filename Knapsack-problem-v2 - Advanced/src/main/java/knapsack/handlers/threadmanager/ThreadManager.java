package knapsack.handlers.threadmanager;

import knapsack.algorithm.Algorithm;
import knapsack.control.Main;
import knapsack.handlers.datamanager.DataManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

public class ThreadManager {
    private static DataManager dataManager;
    public static ArrayList<ThreadAlgorithm> threads;
    public static Semaphore semaphoreToAccessToGlobal;

    public static int[] globalSolution;
    public static int globalValue;

    public static long runtime;
    public static int runs;


    public static void initialize(int processors, int time, int percent){
        dataManager = Main.getManager();
        semaphoreToAccessToGlobal = new Semaphore(1);

        threads = new ArrayList<ThreadAlgorithm>();

        int[] solutionOfLowerBound = dataManager.getLowerBound();
        int valueOfLowerBound = dataManager.eval(dataManager.getLowerBound());

        globalSolution = solutionOfLowerBound;
        globalValue = valueOfLowerBound;
        runtime = 0;
        runs = 0;

        for (int processor = 0; processor < processors; processor++) {
            ThreadAlgorithm thread = new ThreadAlgorithm(solutionOfLowerBound, valueOfLowerBound, time, percent);
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

        double seconds = runtime / 1e9;
        DecimalFormat df = new DecimalFormat("#.####");

        System.out.println(" > Número de iterações necessárias: " + runs);
        System.out.println(" > Tempo total de execução: " + df.format(seconds));
    }
}
