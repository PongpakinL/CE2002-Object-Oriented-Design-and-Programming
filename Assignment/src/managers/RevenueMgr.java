package managers;


import java.util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import org.json.simple.parser.ParseException;


/**
 * This class manages the functions required by the Revenue Report module
 * @author Lum Pongpakin
 */
public class RevenueMgr {

    /**
     * Reads the relevant data stored in the Invoice data file, and displays the revenue report of the selected day
     */
    public static void viewDay() {
        Scanner sc = new Scanner(System.in);

        JSONArray invoice = (JSONArray) MenuMgr.ReadJsonArray("invoice.json");

        try {
            System.out.println("SALE REVENUE BY DAY");
            System.out.println("Enter date (yyyy-mm-dd): ");
            String request = sc.next();

            double totalRevenue = 0.0;
            for (int i=0; i<invoice.size(); i++){
                JSONObject receipt = (JSONObject) invoice.get(i);
                String date = (String) receipt.get("date");
                if (request.equals(date)) {
                    double price = Double.parseDouble((String)receipt.get("grandTotal"));
                    totalRevenue += price;
                    JSONArray itemsOrdered = (JSONArray) receipt.get("itemsOrdered");
                    for (int j=0; j<itemsOrdered.size(); j++) {
                        JSONObject items = (JSONObject) itemsOrdered.get(j);
                        if (items.containsKey("promotionItemList")) {
                            JSONArray promotionItems = (JSONArray) items.get("promotionItemList");
                            for (int k=0; k<promotionItems.size(); k++) {
                                JSONObject finalItem = (JSONObject) promotionItems.get(k);
                                System.out.print("Promotion Item\t\t");
                                System.out.println(finalItem.get("name"));
                            }
                        }
                        else {
                            System.out.print("Non-promotion Item\t");
                            System.out.println(items.get("name"));
                        }
                    }
                }
            }
            if (totalRevenue == 0.0){
                System.out.println("Invoice list is empty. There is no sales to display.");
            }
            else {
                System.out.println("==============================");
                System.out.println("Total sale revenue of " + request + " equals to $" + totalRevenue);
            }

        } catch (InputMismatchException ex) {
            System.out.println("Invalid input! ");
            System.out.println("Failed to display sale revenue report.");
            sc.nextLine(); // clear input
            return;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("Failed to display sale revenue report.");
            sc.nextLine(); // clear input
            return;
        }
    }

    /**
     * Reads the relevant data stored in the Invoice data file, and displays the revenue report of the selected month
     */
    public static void viewMonth() {
        Scanner sc = new Scanner(System.in);

        JSONArray invoice = (JSONArray) MenuMgr.ReadJsonArray("invoice.json");

        try {
            System.out.println("SALE REVENUE BY MONTH");
            System.out.println("Enter month (yyyy-mm): ");
            String request = sc.next();

            double totalRevenue = 0.0;
            for (int i=0; i<invoice.size(); i++){
                JSONObject receipt = (JSONObject) invoice.get(i);
                String date = (String) receipt.get("date");
                String month = date.substring(0, 7);
                if (request.equals(month)) {
                    double price = Double.parseDouble((String)receipt.get("grandTotal"));
                    totalRevenue += price;
                    JSONArray itemsOrdered = (JSONArray) receipt.get("itemsOrdered");
                    for (int j=0; j<itemsOrdered.size(); j++) {
                        JSONObject items = (JSONObject) itemsOrdered.get(j);
                        if (items.containsKey("promotionItemList")) {
                            JSONArray promotionItems = (JSONArray) items.get("promotionItemList");
                            for (int k=0; k<promotionItems.size(); k++) {
                                JSONObject finalItem = (JSONObject) promotionItems.get(k);
                                System.out.print("Promotion Item\t\t");
                                System.out.println(finalItem.get("name"));
                            }
                        }
                        else {
                            System.out.print("Non-promotion Item\t");
                            System.out.println(items.get("name"));
                        }
                    }
                }
            }
            if (totalRevenue == 0.0){
                System.out.println("Invoice list is empty. There is no sales to display.");
            }
            else {
                System.out.println("==============================");
                System.out.println("Total sale revenue of " + request + " equals to $" + totalRevenue);
            }


        } catch (InputMismatchException ex) {
            System.out.println("Invalid input! ");
            System.out.println("Failed to display sale revenue report.");
            sc.nextLine(); // clear input
            return;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("Failed to display sale revenue report.");
            sc.nextLine(); // clear input
            return;
        }
    }

    /**
     * Reads the all data stored in the Invoice data file, and displays the revenue report of everything
     */
    public static void viewAll() {

        JSONArray invoice = (JSONArray) MenuMgr.ReadJsonArray("invoice.json");
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("ALL SALE REVENUE");

            if (invoice.size() == 0) {
                System.out.println("Invoice list is empty. There is no sales to display.");
            }
            else {
                double totalRevenue = 0.0;
                for (int i=0; i<invoice.size(); i++){
                    JSONObject receipt = (JSONObject) invoice.get(i);
                    double price = Double.parseDouble((String)receipt.get("grandTotal"));
                    totalRevenue += price;
                    JSONArray itemsOrdered = (JSONArray) receipt.get("itemsOrdered");
                    for (int j=0; j<itemsOrdered.size(); j++) {
                        JSONObject items = (JSONObject) itemsOrdered.get(j);
                        if (items.containsKey("promotionItemList")) {
                            JSONArray promotionItems = (JSONArray) items.get("promotionItemList");
                            for (int k=0; k<promotionItems.size(); k++) {
                                JSONObject finalItem = (JSONObject) promotionItems.get(k);
                                System.out.print("Promotion Item\t\t");
                                System.out.println(finalItem.get("name"));
                            }
                        }
                        else {
                            System.out.print("Non-promotion Item\t");
                            System.out.println(items.get("name"));
                        }
                    }
                }
                System.out.println("==============================");
                System.out.println("Total sale revenue of all time equals to $" + totalRevenue);
            }


        } catch (InputMismatchException ex) {
            System.out.println("Invalid input! ");
            System.out.println("Failed to display sale revenue report.");
            sc.nextLine(); // clear input
            return;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("Failed to display sale revenue report.");
            sc.nextLine(); // clear input
            return;
        }
    }

}
