/*
 * Copyright (c) 2022 Marcel Gheorghe Becheanu
 * All rights reserved.
 *
 * This code is proprietary and cannot be copied or distributed without the express written permission of Marcel Gheorghe Becheanu.
 */

package knapsack.control;

import knapsack.algorithms.BeamSearch;
import knapsack.handlers.datamanager.DataManager;
import knapsack.handlers.filemanager.FileManager;

public class Main {
    private static DataManager manager;
    public static DataManager getManager() { return manager; }

    public static void main(String[] args) {
        if(args.length != 3){
            return;
        } else {
            if(!FileManager.exists(args[0])){
                System.out.println("File " + args[0] + " does not exist.");
                return;
            }
        }

        registers();
        loaders(args);

        BeamSearch.initialize();

    }

    public static void registers(){
        manager = new DataManager();
    }
    public static void loaders(String[] args){
        FileManager.load(args[0], manager);
    }
}
