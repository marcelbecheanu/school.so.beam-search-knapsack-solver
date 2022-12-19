/*
 * Copyright (c) 2022 Marcel Gheorghe Becheanu
 * All rights reserved.
 *
 * This code is proprietary and cannot be copied or distributed without the express written permission of Marcel Gheorghe Becheanu.
 */

package knapsack.control;

import knapsack.handlers.datamanager.DataManager;
import knapsack.handlers.filemanager.FileManager;

public class Main {
    private static DataManager manager;
    public static void main(String[] args) {
        if(args.length != 3){
            if(!FileManager.exists(args[0])){
                System.out.println("File " + args[0] + " does not exist.");
            }
            return;
        }

        registers();
        loaders(args);

    }

    public static void registers(){
        manager = new DataManager();
    }

    public static void loaders(String[] args){
        FileManager.load(args[0], manager);
    }
}
