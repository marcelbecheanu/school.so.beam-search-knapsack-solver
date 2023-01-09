package knapsack.handlers.datamanager;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Class responsible for storing and managing data for a knapsack problem.
 */
public class DataManager {
    // Size of the list of items
    private int size;

    // Maximum weight supported by the knapsack
    private int maxWeight;

    // Array of items that can be added to the knapsack
    private int[][] items;

    // Ideal value to be achieved with the items added to the knapsack
    private int idealValue;

    /**
     * Constructor to initialize the class attributes.
     */
    public DataManager() {
    }

    /**
     * Method to get the size of the list of items.
     *
     * @return Size of the list of items
     */
    public int getSize() {
        return size;
    }

    /**
     * Method to set a new value for the size of the list of items.
     *
     * @param size New size of the list of items
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Method to get the maximum weight supported by the knapsack.
     *
     * @return Maximum weight supported by the knapsack
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * Method to set a new value for the maximum weight supported by the knapsack.
     *
     * @param maxWeight New maximum weight supported by the knapsack
     */
    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    /**
     * Method to get the array of items that can be added to the knapsack.
     *
     * @return Array of items that can be added to the knapsack
     */
    public int[][] getItems() {
        return items;
    }

    /**
     * Method to set a new value for the array of items that can be added to the knapsack.
     *
     * @param items New array of items that can be added to the knapsack
     */
    public void setItems(int[][] items) {
        this.items = items;
    }

    /**
     * Method to get the ideal value to be achieved with the items added to the knapsack.
     *
     * @return Ideal value to be achieved with the items added to the knapsack
     */
    public int getIdealValue() {
        return idealValue;
    }

    /**
     * Method to set a new value for the ideal value to be achieved with the items added to the knapsack.
     *
     * @param idealValue New ideal value to be achieved with the items added to the knapsack
     */
    public void setIdealValue(int idealValue) {
        this.idealValue = idealValue;
    }

    public void sort(){
        // Sort the array using a custom comparator
        Arrays.sort(items, new Comparator<int[]>() {
            public int compare(int[] item1, int[] item2) {
                double ratio1 = (double) item1[0] / item1[1];
                double ratio2 = (double) item2[0] / item2[1];
                return Double.compare(ratio2, ratio1);
            }
        });
    }

    /**
     * Method to print the data stored in the DataManager instance.
     */
    public void printData() {
        // Print the size of the list of items
        System.out.println("Size of the list of items: " + getSize());

        // Print the maximum weight supported by the knapsack
        System.out.println("Maximum weight supported by the knapsack: " + getMaxWeight());

        // Print the array of items that can be added to the knapsack
        System.out.println("Array of items that can be added to the knapsack: ");
        for(int row = 0; row < getSize(); row++)
                System.out.println(row + " : { Value: " + getItems()[row][0] + ", Weight: " + getItems()[row][1] + " }");

        // Print the ideal value to be achieved with the items added to the knapsack
        System.out.println("Ideal value to be achieved with the items added to the knapsack: " + getIdealValue());
    }
}
