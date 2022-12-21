/*
 * Copyright (c) 2022 Marcel Gheorghe Becheanu
 * All rights reserved.
 *
 * This code is proprietary and cannot be copied or distributed without the express written permission of Marcel Gheorghe Becheanu.
 */

package knapsack.entities;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Item implements Comparable<Item> {
    // The value of the item.
    private int value;
    // The weight of the item.
    private int weight;
    // The value-to-weight ratio of the item.
    private double ratio;

    /**
     * Constructs a new Item with the given value and weight.
     *
     * @param value the value of the item
     * @param weight the weight of the item
     */
    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
        this.ratio = (double) value / weight;
    }

    /**
     * Returns the value of the item.
     *
     * @return the value of the item
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the item.
     *
     * @param value the new value of the item
     */
    public void setValue(int value) {
        this.value = value;
        // Recalculate the ratio.
        this.ratio = (double) value / weight;
    }

    /**
     * Returns the weight of the item.
     *
     * @return the weight of the item
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the item.
     *
     * @param weight the new weight of the item
     */
    public void setWeight(int weight) {
        this.weight = weight;
        // Recalculate the ratio.
        this.ratio = (double) value / weight;
    }

    /**
     * Returns the value-to-weight ratio of the item.
     *
     * @return the value-to-weight ratio of the item
     */
    public double getRatio() {
        return ratio;
    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return "{v: " + value + ", w: " + weight + ", r: " + df.format(ratio) + "}";
    }

    @Override
    public int compareTo(Item item) {
        return Double.compare(item.ratio, this.ratio);
    }
}