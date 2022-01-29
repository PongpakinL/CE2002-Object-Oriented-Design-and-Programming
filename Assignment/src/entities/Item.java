package entities;

/**
 * Represents food item that is either on the Menu or part of a customer's Order
 * @author Truong Vinh Khai
 */
public class Item {
    /**
     * The name of this item
     */
    public String name;
    /**
     * The price given to this item
     */
    public String price;
    /**
     * The description of this item
     */
    public String description;

    /**
     * Changes the name of this Item
     * @param itemName the new name to be given to this Item.
     */
    public void setName(String itemName){
        this.name = itemName;
    }

    /**
     * Changes the price given to this Item
     * @param itemPrice the new price to be given this Item
     *                  Should be a String in the format '$XX.XX'.
     */
    public void setPrice(String itemPrice){
        this.price = itemPrice;
    }

    /**
     * Changes the decription given to this Item
     * @param itemDescription the new description to be given to this Item
     *                        Should be no more than a short sentence.
     */
    public  void setDescription(String itemDescription){
        this.description = itemDescription;
    }

    /**
     * Gets the name of this Item.
     * @return the current name of this Item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of this Item
     * @return the current price of this Item
     */
    public String getPrice() {
        return price;
    }

    /**
     * Gets the current description given to this Item.
     * @return the current description of this Item.
     */
    public String getDescription() {
        return description;
    }
}
