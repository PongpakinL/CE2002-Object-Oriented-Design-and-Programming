package entities;

import java.util.ArrayList;

/**
 * Represents a Promotion item available on the restaurant's menu
 * @author Truong Vinh Khai
 */
public class Promotion {
    /**
     * The ArrayList of food Items included in this Promotion
     */
    public static ArrayList<Item> promotionItem = new ArrayList<Item>();
    /**
     * The name given to this Promotion
     */
    public static String promotionName;
    /**
     * The special, usually discounted price, given to this Promotion if ordered by the customer
     */
    public static String price;

    /**
     * Gets the ArrayList of food Items included in this Promotion.
     * @return ArrayList of food Items in this Promotion
     */
    public static ArrayList<Item> getPromotionItem() {
        return promotionItem;
    }

    /**
     * Gets the name given to this Promotion.
     * @return the name of this Promotion
     */
    public static String getPromotionName() {
        return promotionName;
    }

    /**
     * Gets the special price given to this Promotion.
     * @return price of this Promotion
     */
    public static String getPrice() {
        return price;
    }

    /**
     * Changes the list of food items inclued in this Promotion.
     * @param promotionItem ArrayList of food Items to be included in this Promotion
     */
    public static void setPromotionItem(ArrayList<Item> promotionItem) {
        Promotion.promotionItem = promotionItem;
    }

    /**
     * Changes the name of this Promotion.
     * @param promotionName the name to be given to this Promotion
     */
    public static void setPromotionName(String promotionName) {
        Promotion.promotionName = promotionName;
    }

    /**
     * Changes the price of this Promotion
     * @param price the new price of this Promotion
     *              it should be a String in the formot of "$XX.XX"
     */
    public static void setPrice(String price) {
        Promotion.price = price;
    }
}
