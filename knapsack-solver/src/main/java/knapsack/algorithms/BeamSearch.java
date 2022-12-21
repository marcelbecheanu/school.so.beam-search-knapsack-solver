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

class Utils {
    public static int sumWeights(ItemSack[] items){
        return Arrays.stream(items).filter(i -> i.isOnTheBag()).mapToInt(Item::getWeight).sum();
    }

    public static int eval(ItemSack[] items){
        return Arrays.stream(items).filter(i -> i.isOnTheBag()).mapToInt(Item::getValue).sum();
    }
}

public class BeamSearch {

    public static void initialize(){
        int c = -1;
        ItemSack[] lb = lowerBound(c);
        System.out.println("LowerBound: \n -> Valor de c: " + c + "\n -> Solução:" + Arrays.toString(lb));

        System.out.println("Utils Weights Sum: " + Utils.sumWeights(lb));
        System.out.println("Utils Value Sum: " + Utils.eval(lb));

        int ub = upperBound();
        System.out.println("UpperBound: " + ub);


    }

    public static ItemSack[] lowerBound(int c) {
        DataManager manager = Main.getManager();
        ItemSack[] localSolution = new ItemSack[manager.getSize()];

        // Sort the items in the manager by their ratio in ascending order
        Arrays.sort(manager.getItems());

        int weight = 0;
        for (int index = 0; index < manager.getSize(); index++) {
            Item item = manager.getItems()[index];
            if((weight += item.getWeight()) <= manager.getMaxWeight()){
                localSolution[index] = new ItemSack(item.getValue(), item.getWeight(), true);
            } else {
                localSolution[index] = new ItemSack(item.getValue(), item.getWeight(), false);
                if(c == -1)
                    c = index;
            }
        }

        return localSolution;
    }

    public static int upperBound() {
        DataManager manager = Main.getManager();
        Arrays.sort(manager.getItems());

        // Weight Out Items
        int w =  


        return 0;
    }
}
