package managers;

import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class manages the functions that are needed by the invoice module.
 * @author Ong Jun Siang
 */
public class InvoiceMgr {

    JSONArray order = (JSONArray) MenuMgr.ReadJsonArray("order.json");

    /**
     * Looks through order array and finds the order with the matching table number.
     * @param order JSONArray of all current orders as read from the JSON file.
     * @param table number of table to look for.
     * @return index of the table's corresponding order in the ORDER data file.
     */
    public static int FindOrder(JSONArray order, long table) {
        System.out.println("Finding order...");
        for (int j = 0; j < order.size(); j++) {
            JSONObject temp = (JSONObject) order.get(j);
            long curTable = (long) temp.get("tableID");
            if (curTable == table) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Looks through ArrayList of Tables for given table number, and returns the corresponding reservation ID if it is booked.
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     * @param tableNumber given table number to check for reservation ID
     * @return reservation ID of table if it has been booked; error code 999 if it hasn't been, or error code -1 if table number does not exist.
     */
    public static int FindReservationFromTable(ArrayList<Table> tables, int tableNumber) {
        System.out.println("Fetching corresponding reservation...");
        for (int i = 0; i < tables.size(); i++) {
            Table temp = (Table) tables.get(i);
            int curTable = (int) temp.getID();
            if (curTable == tableNumber) {
                boolean booked = (boolean) temp.isBooked();
                if (booked) {
                    return (int) temp.getResID();
                } else {
                    return 999;
                }
            }
        }
        return -1;
    }

    /**
     * Prints the required invoice for the table to be billed.
     * @param reservations An ArrayList of Reservations containing the most up-to-date records of the restaurant's reservations.
     * @param order JSONArray of all current orders as read from the JSON file.
     * @param billedTable JSONObject of the order for this invoice
     * @param resID Reservation ID of the reservation associated with the invoice to be printed.
     * @param tableID Table number of the table asscociated with the invoice to be printed.
     * @param member Whether the customer paying is a member of the restaurant
     */
    public static void PrintInvoice(ArrayList<Reservation> reservations, JSONArray order, JSONObject billedTable, int resID, int tableID, boolean member) {

        JSONArray itemsOrdered = new JSONArray();
        JSONObject curItem = new JSONObject();
        JSONArray promoItem = new JSONArray();
        JSONArray promoArray = new JSONArray();
        JSONArray itemPricing = new JSONArray();
        JSONObject priceString = new JSONObject();

        DecimalFormat df = new DecimalFormat("#.##");
        ArrayList<Invoice> invoices = Restaurant.invoiceArrayList;

        double subTotal = 0.0;
        double grandtotal = 0.0;
        int count = 0;
        String date = "", time = "";
        for (int i = 0; i < reservations.size(); i++) {
            Reservation temp = (Reservation) reservations.get(i);
            if ((int) temp.getResID() == resID) {
                date = (String) temp.getDate();
                time = (String) temp.getTime();
            }
        }

        System.out.println("===================== INVOICE =====================");
        System.out.println("Table No: " + billedTable.get("tableID"));
        System.out.println("Staff : " + billedTable.get("staffName"));
        System.out.println("Date : " + date);
        System.out.println("Time : " + time);

        System.out.println("===================== ORDERS MADE =====================");
        itemsOrdered = GetItemsOrdered(order, tableID);
        for (int i = 0; i < itemsOrdered.size(); i++) {
            curItem = (JSONObject) itemsOrdered.get(i);
            if (curItem.containsKey("promotionItemList")) {
                promoItem = (JSONArray) curItem.get("promotionItemList");
                for (int j = 0; j < promoItem.size(); j++) {
                    count++;
                    JSONObject p = (JSONObject) promoItem.get(j);
                    itemPricing = (JSONArray) p.get("pricing");
                    priceString = (JSONObject) itemPricing.get(0);
                    System.out.println(count + ". " + p.get("name") + "(Promo): " + priceString.get("priceString"));
                }
            } else {
                count++;
                itemPricing = (JSONArray) curItem.get("pricing");
                priceString = (JSONObject) itemPricing.get(0);
                System.out.println(count + ". " + curItem.get("name") + ": " + priceString.get("priceString"));
            }
            subTotal += Float.valueOf(priceString.get("price").toString());
        }
        System.out.println(String.format("Subtotal : $" + subTotal, "%.2f"));
        if (member) {
            double discount = (double) (0.1 * subTotal);
            System.out.println("MEMBER'S DISCOUNT : -$" + discount);
            subTotal -= discount;
        }
        double gst = (double) 0.07 * subTotal;
        String gstString = df.format(gst);
        double serviceCharge = (double) 0.1 * subTotal;
        String scString = df.format(serviceCharge);

        // Formatting to 2 decimal places
        System.out.println("incl. 7% GST : $" + gstString);
        System.out.println("incl. 10% S.C : $" + scString);

        grandtotal = subTotal + gst + serviceCharge;
        String grandString = df.format(grandtotal);
        System.out.println("Grand Total : $" + grandString);
        System.out.println("===================== INVOICE END =====================");
        System.out.println("Have a nice day! We wish to serve you again.");
        System.out.println();
        UpdateInvoices(tableID, date, time, itemsOrdered, grandString, invoices);
    }

    /**
     * Gets the JSONArray of the food items ordered by the given table number
     * @param order JSONArray of all current orders as read from the JSON file.
     * @param tableID Table number of current invoice to be printed.
     * @return JSONArray of food items ordered by the given table.
     */
    public static JSONArray GetItemsOrdered(JSONArray order, int tableID) {
        JSONArray itemList = new JSONArray();
        for (int i = 0; i < order.size(); i++) {
            JSONObject temp = (JSONObject) order.get(i);
            if ((long) temp.get("tableID") == tableID) {
                itemList = (JSONArray) temp.get("itemList");
            }
        }
        return itemList;
    }

    /**
     * Updates ArrayList of Invoices with the latest printed invoice
     * Following are all the fields required to create a new Invoice object
     * @param tableID table number of printed invoice
     * @param date date of printed invoice
     * @param time time of printed invoice
     * @param itemList list of food items ordered included in invoice
     * @param grandTotal grand total amount of printed invoice
     * @param invoices ArrayList of all printed invoices
     */
    public static void UpdateInvoices(int tableID, String date, String time, JSONArray itemList, String grandTotal, ArrayList<Invoice> invoices) {
        Invoice newInvoice = new Invoice(tableID, date, time, itemList, grandTotal);
        invoices.add(newInvoice);
        SaveInvoiceToFile(invoices);
    }

    /**
     * Saves the update invoice ArrayList into the invoice JSON data file
     * @param invoices ArrayList of all printed invoices
     */
    public static void SaveInvoiceToFile(ArrayList<Invoice> invoices) {
        JSONArray updatedInvoices = new JSONArray();
        for (int i = 0; i < invoices.size(); i++) {
            JSONObject thisInvoice = new JSONObject();
            thisInvoice.put("tableID", invoices.get(i).getTableID());
            thisInvoice.put("date", invoices.get(i).getDate());
            thisInvoice.put("time", invoices.get(i).getTime());
            thisInvoice.put("itemsOrdered", invoices.get(i).getItemList());
            thisInvoice.put("grandTotal", invoices.get(i).getGrandTotal());
            updatedInvoices.add(thisInvoice);
        }
        try (FileWriter file = new FileWriter("invoice.json")) {
            file.write(updatedInvoices.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
