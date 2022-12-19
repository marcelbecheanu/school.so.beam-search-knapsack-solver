/*
 * Copyright (c) 2022 Marcel Gheorghe Becheanu
 * All rights reserved.
 *
 * This code is proprietary and cannot be copied or distributed without the express written permission of Marcel Gheorghe Becheanu.
 */

package knapsack.algorithms;

import knapsack.control.Main;
import knapsack.entities.Item;
import knapsack.entities.ItemSack;
import knapsack.handlers.datamanager.DataManager;

import java.util.Arrays;

public class BeamSearch {

    private static ItemSack[] solution;

    public static void initialize(){
       int lb = lowerBound();
        System.out.println("LowerBound: " + lb);

    }

    public static int lowerBound() {
        DataManager manager = Main.getManager();
        solution = new ItemSack[manager.getSize()];

        // Sort the items in the manager by their ratio in ascending order
        Arrays.sort(manager.getItems(), (previous, next) -> Double.compare(previous.getRatio(), next.getRatio()));

        int weight = 0;
        int sum = 0;
        for (int index = 0; index < manager.getSize(); index++) {
            Item item = manager.getItems()[index];
            if((weight += item.getWeight()) <= manager.getMaxWeight()){
                solution[index] = new ItemSack(item.getValue(), item.getWeight(), true);
                sum += item.getValue();
            } else {
                solution[index] = new ItemSack(item.getValue(), item.getWeight(), false);
            }
        }

        return sum;
    }

    public static int UpperBound() {
        
    }
}
