package knapsack.handlers.datamanager;

import knapsack.entities.Item;

import java.util.Arrays;

/**
 * Class responsible for storing and managing data for a knapsack problem.
 */
public class DataManager {
    // Size of the list of items
    private int size;

    // Maximum weight supported by the knapsack
    private int maxWeight;

    // Array of items that can be added to the knapsack
    private Item[] items;

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
    public Item[] getItems() {
        return items;
    }

    /**
     * Method to set a new value for the array of items that can be added to the knapsack.
     *
     * @param items New array of items that can be added to the knapsack
     */
    public void setItems(Item[] items) {
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

    /**
     * Method to print the data stored in the DataManager instance.
     */
    public void printData() {
        // Print the size of the list of items
        System.out.println("Size of the list of items: " + size);

        // Print the maximum weight supported by the knapsack
        System.out.println("Maximum weight supported by the knapsack: " + maxWeight);

        // Print the array of items that can be added to the knapsack
        System.out.println("Array of items that can be added to the knapsack: " + Arrays.toString(items));

        // Print the ideal value to be achieved with the items added to the knapsack
        System.out.println("Ideal value to be achieved with the items added to the knapsack: " + idealValue);
    }
}
