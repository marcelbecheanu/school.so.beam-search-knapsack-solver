package knapsack.control;

import knapsack.algorithm.Algorithm;
import knapsack.handlers.datamanager.DataManager;
import knapsack.handlers.filemanager.FileManager;
import knapsack.handlers.threadmanager.ThreadManager;

import java.util.Arrays;

public class Main {
    private static DataManager dataManager;
    private static ThreadManager threadManager;
    public static DataManager getManager() { return dataManager; }
    public static ThreadManager getThreadManager() { return threadManager; }

    public static void main(String[] args) {
        if(args.length != 3){
            System.out.println("Invalid arguments!!!!");
            return;
        } else {
            if(!FileManager.exists(args[0])){
                System.out.println("File " + args[0] + " does not exist.");
                return;
            }
        }

        registers();
        loaders(args);

        getManager().sort();
        getManager().printData();

        threadManager = new ThreadManager(8, 20);
    }

    public static void registers(){
        dataManager = new DataManager();
    }

    public static void loaders(String[] args){
        FileManager.load(args[0], dataManager);
    }
}
