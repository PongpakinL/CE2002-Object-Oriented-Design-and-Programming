package entities;

import org.json.simple.JSONArray;

/**
 * This class represents an invoice of an order that has been printed, and stored on the Invoice ArrayList
 * @author Ong Jun Siang
 */
public class Invoice {

    /**
     * The associated table ID for this invoice.
     */
    private int tableID;
    /**
     * The date of this invoice.
     */
    private String date;
    /**
     * The time of this invoice.
     */
    private String time;
    /**
     * The list of items ordered by the table that is linked to this invoice.
     */
    private JSONArray itemList;
    /**
     * The grand total cost of the entire transaction detailed by this invoice.
     */
    private String grandTotal;

    /**
     * Gets the table ID of this invoice.
     * @return the table ID of this invoice
     */
    public int getTableID() {
        return tableID;
    }

    /**
     * Gets the date of this invoice.
     * @return this invoice's date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the time of this invoice.
     * @return this invoice's time
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets the list of items included in this invoice.
     * @return this invoice's list of ordered items
     */
    public JSONArray getItemList() {
        return itemList;
    }

    /**
     * Gets the grand total amount reflected in this invoice.
     * @return this invoice's grand total amount
     */
    public String getGrandTotal() {
        return grandTotal;
    }

    /**
     * Changes the table ID of this invoice.
     * @param tableID this invoice's tableID
     */
    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    /**
     * Changes the date of the reservation associated with this invoice.
     * @param date this invoice's date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Changes the time of the reservation associated with this invoice.
     * @param time this invoice's time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Changes the list of food items ordered included in this invoice.
     * @param itemList this invoice's list of items
     */
    public void setItemList(JSONArray itemList) {
        this.itemList = itemList;
    }

    /**
     * Chnages the grand total amount of this invoice.
     * @param grandTotal this invoice's grand total amount
     */
    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    /**
     * Creates a new Invoice object with all the fields
     * @param tableID this invoice's table ID
     * @param date the date of this invoice
     * @param time the time of the reservation associated with the invoice
     * @param itemList the list of food items included in this invoice
     * @param grandTotal the grand total amount of the order, including service charge, GST, and any member discount.
     */
    public Invoice(int tableID, String date, String time, JSONArray itemList, String grandTotal) {
        this.tableID = tableID;
        this.date = date;
        this.time = time;
        this.itemList = itemList;
        this.grandTotal = grandTotal;
    }
}
