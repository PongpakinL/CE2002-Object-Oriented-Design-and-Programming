package entities;

import java.util.ArrayList;

/**
 * Represents the Order created to store all the details of a table's food orders
 * @author Truong Vinh Khai
 */
public class Order {

    /**
     * The total price of all items in this Order
     */
    public String price;
    /**
     * The name of the staff who created this Order.
     */
    public String staffName;
    /**
     * The number of the table that this Order is created for.
     */
    public int tableNumber;
    /**
     * ArrayList containing all the non-promotional items included in this Order.
     */
    public static ArrayList<Item> NormalItems = new ArrayList<Item>();
    /**
     * ArrayList containing all the promotional items included in this Order.
     */
    public static ArrayList<Promotion> PromotionItems = new ArrayList<Promotion>();

    /**
     * Gets the total price of this Order.
     * @return total price of this Order.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Gets the name of the staff who created this order
     * @return the name of the staff who created this Order.
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Gets the table number that this Order is created for.
     * @return table number associated with this Order.
     */
    public int getTableNumber() {
        return tableNumber;
    }
}
