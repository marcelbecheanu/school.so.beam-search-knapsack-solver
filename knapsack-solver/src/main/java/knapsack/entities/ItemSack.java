package knapsack.entities;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ItemSack extends Item {

    private boolean isOnTheBag;

    /**
     * Constructs a new Item with the given value and weight, and sets its "on the bag" status.
     *
     * @param value  the value of the item
     * @param weight the weight of the item
     * @param isOnTheBag a boolean indicating whether the item is on the bag or not
     */
    public ItemSack(int value, int weight, boolean isOnTheBag) {
        // Call the superclass constructor to set the value and weight of the item
        super(value, weight);

        // Initialize the isOnTheBag field
        this.isOnTheBag = isOnTheBag;
    }

    /**
     * Returns a boolean indicating whether the item is on the bag or not.
     *
     * @return true if the item is on the bag, false otherwise
     */
    public boolean isOnTheBag() {
        return isOnTheBag;
    }

    /**
     * Sets the "on the bag" status of the item.
     *
     * @param onTheBag a boolean indicating the new "on the bag" status of the item
     */
    public void setOnTheBag(boolean onTheBag) {
        isOnTheBag = onTheBag;
    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return "{v: " + getValue() + ", w: " + getWeight() + ", r: " + df.format(getRatio()) + ", s: "+ isOnTheBag() +"}";
    }
}
