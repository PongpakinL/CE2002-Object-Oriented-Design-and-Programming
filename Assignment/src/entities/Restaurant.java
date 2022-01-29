package entities;

import entities.*;
import managers.*;
import org.json.simple.*;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;


/**
 * The Restuarant entity where all the ArrayLists of objects are initialised and stored.
 */
public class Restaurant {
    public static ArrayList<Table> tableArrayList = new ArrayList<Table>();
    public static ArrayList<Reservation> resArrayList = new ArrayList<Reservation>();
    public static ArrayList<Order> orderArrayList = new ArrayList<Order>();
    public static ArrayList<MenuSections> MainMenu = new ArrayList<MenuSections>();
    public static ArrayList<Invoice> invoiceArrayList = new ArrayList<Invoice>();

    /**
     * Calls the respective initialisers so that the ArrayLists are update with the latest data from the data files
     */
    public static void initRestaurant() {
        initTables();
        initReservations();
        initInvoices();
    }

    /**
     * Reads data from the tables JSON file and converts it into a sorted ArrayList of Table objects with the correct data
     */
    public static void initTables() {
        JSONArray t = (JSONArray) MenuMgr.ReadJsonArray("tables.json");
        JSONArray sortedTables = new JSONArray();

        // Copying existing data into an ArrayList
        List tableList = new ArrayList();
        for (int i = 0; i < t.size(); i++) {
            tableList.add(t.get(i));
        }

        // Sorting the items in the list by table ID.
        Collections.sort(tableList, new Comparator<JSONObject>() {
            private static final String KEY = "ID";
            public int compare(JSONObject a, JSONObject b) {
                int compare;
                long keyA = (long) a.get(KEY);
                long keyB = (long) b.get(KEY);
                compare = Long.compare(keyA, keyB);
                return compare;
            }
        });

        for (int i = 0; i < tableList.size(); i++) {
            JSONObject currentTable = (JSONObject) tableList.get(i);
            sortedTables.add(tableList.get(i));
            boolean curBooked = (boolean) currentTable.get("booked");
            String curCustomerContact = (String) currentTable.get("customerContact");
            String curID =  currentTable.get("ID").toString();
            int cID = Integer.parseInt(curID);
            String resID =  currentTable.get("resID").toString();
            int cRes = Integer.parseInt(resID);
            String capacity = currentTable.get("capacity").toString();
            int cCap = Integer.parseInt(capacity);


            Table curTable = new Table(curBooked, curCustomerContact, cID, cRes, cCap);
            tableArrayList.add(curTable);
        }

        // Replacing existing data with sorted data
        try (FileWriter file = new FileWriter("tables.json")) {
            file.write(sortedTables.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads data from the reservations JSON file and converts it into an ArrayList of Reservation objects
     */
    public static void initReservations() {
        //Read Reservation json
        JSONArray r = (JSONArray) MenuMgr.ReadJsonArray("reservations.json");

        for (int i = 0; i < r.size(); i++) {
            JSONObject currentRes = (JSONObject) r.get(i);

            String curDate = (String) currentRes.get("date");
            String curPax = currentRes.get("pax").toString();
            int cPax = Integer.parseInt(curPax);
            String curContact = (String) currentRes.get("contact");
            String curName = (String) currentRes.get("name");
            String curTime = (String) currentRes.get("time");
            String curID = currentRes.get("resID").toString();
            int cCID = Integer.parseInt(curID);

            Reservation curRes = new Reservation(curDate, cPax, curContact, curName, curTime, cCID);
            resArrayList.add(curRes);
        }
    }

    /**
     * Reads data from the invoice JSON file and converts it into an ArrayList of Invoice objects.
     */
    public static void initInvoices() {
        // Read Invoice JSON
        JSONArray inv = (JSONArray) MenuMgr.ReadJsonArray("invoice.json");

        for (int i = 0; i < inv.size(); i++) {
            JSONObject currentInv = (JSONObject) inv.get(i);

            String curDate = (String) currentInv.get("date");
            String curTotal = (String) currentInv.get("grandTotal");
            String curTable = currentInv.get("tableID").toString();
            int cTable = Integer.parseInt(curTable);
            String curTime = (String) currentInv.get("time");
            JSONArray curItems = (JSONArray) currentInv.get("itemsOrdered");

            Invoice curInv = new Invoice(cTable, curDate, curTime, curItems, curTotal);
            invoiceArrayList.add(curInv);

        }
    }
}




