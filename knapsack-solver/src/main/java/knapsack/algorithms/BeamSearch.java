/*
 * Copyright (c) 2022 Marcel Gheorghe Becheanu
 * All rights reserved.
 *
 * This code is proprietary and cannot be copied or distributed without the express written permission of Marcel Gheorghe Becheanu.
 */

package knapsack.algorithms;

import knapsack.control.Main;
import knapsack.entities.ItemSack;

import java.util.Arrays;

public class BeamSearch {

    private static ItemSack[] solution;

    public static void initialize(){
        solution = new ItemSack[Main.getManager().getSize()];
        lowerBound();

    }

    public static void lowerBound() {
        // Sort the items in the manager by their ratio in ascending order
        Arrays.sort(Main.getManager().getItems(), (previous, next) -> Double.compare(previous.getRatio(), next.getRatio()));
    }

}
