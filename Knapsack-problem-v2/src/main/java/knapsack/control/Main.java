package knapsack.control;

import knapsack.algorithm.Algorithm;
import knapsack.handlers.datamanager.DataManager;
import knapsack.handlers.filemanager.FileManager;
import knapsack.handlers.threadmanager.ThreadManager;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static DataManager dataManager;
    public static DataManager getManager() { return dataManager; }

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

        ThreadManager.initialize(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }

    public static void registers(){
        dataManager = new DataManager();
    }

    public static void loaders(String[] args){
        FileManager.load(args[0], dataManager);
    }
}
