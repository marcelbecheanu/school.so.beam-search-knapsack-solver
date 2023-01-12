package knapsack.control;

import knapsack.algorithm.Algorithm;
import knapsack.algorithm.BeamSearch;
import knapsack.handlers.datamanager.DataManager;
import knapsack.handlers.filemanager.FileManager;

import java.util.Arrays;

public class Main {
    private static DataManager manager;
    public static DataManager getManager() { return manager; }
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

        getManager().printData();
        getManager().sort();
        getManager().printData();

        //BeamSearch.initialize();

        Algorithm algorithm = new Algorithm();
        int[] bettersol = new int[getManager().getSize()];
        int val = 0;

        for(int i = 0; i < 300; i++){
            long ustartTime = System.nanoTime();
            int[] a = algorithm.getBeamSearch();
            int temp = getManager().eval(a);
            if(val <= temp){
                val = temp;
                bettersol = a;
            }

            long uendTime = System.nanoTime();
            long utimeElapsed = uendTime - ustartTime;
            System.out.println("Values: " + getManager().eval(a));
            System.out.println("DATA: " + Arrays.toString(a));
            System.out.println("is Valid: " + getManager().isValidWeight(a));
            System.out.println("Execution time in nanoseconds: " + utimeElapsed);
            System.out.println("Execution time in milliseconds: " + utimeElapsed / 1000000);
        }

        System.out.println("GLOBAL RESULT");
        System.out.println("Values: " + val);
        System.out.println("DATA: " + Arrays.toString(bettersol));
        System.out.println("is Valid: " + getManager().isValidWeight(bettersol));


    }

    public static void registers(){
        manager = new DataManager();
    }

    public static void loaders(String[] args){
        FileManager.load(args[0], manager);
    }
}
