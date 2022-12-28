package knapsack.algorithms;

import knapsack.control.Main;
import knapsack.entities.Sack;
import knapsack.handlers.datamanager.DataManager;

public class BeamSearch {
    public static void initialize(){
        int lb = lowerBound();
        System.out.println("LowerBound: " + lb);

        //Criar solução de test
        Sack sol = new Sack(Main.getManager().getItems(), 0);
        console.log();

    }

    public static int lowerBound(){
        DataManager manager = Main.getManager();
        manager.sort();
        int sum = 0, weight = 0;
        for (int row = 0; row < manager.getSize(); row++){
            if((weight += manager.getItems()[row][1]) <= manager.getMaxWeight()){
                sum += manager.getItems()[row][0];
            }
        }
        return sum;
    }

    public static int upperBound(Sack sack){
        DataManager manager = Main.getManager();
        int Wmax = manager.getMaxWeight();
        int c = 0; // Value of the last valid weight sum;
        Sack temp = sack;

        if(sack.sumWeights() >= Wmax) {
            for (int row = 0; row < manager.getSize(); row++){

            }
        }
        return 0;
    }

}
