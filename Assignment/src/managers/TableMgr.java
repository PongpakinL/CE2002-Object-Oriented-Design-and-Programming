package managers;

import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class manages all the functions that deal with the Table objects in the restaurant.
 * @author Ong Jun Siang
 */
public class TableMgr {

    /**
     * This function looks through the tableArrayList and shows the ID of the tables that are available.
     * The total number of available tables is also displayed.
     */
    public static void ShowTableOptions() {
        ArrayList<Table> tables = Restaurant.tableArrayList;

        // Look through sorted data and print tables that are still unbooked.
        int count = 0;
        System.out.println("The following tables are available: ");
        System.out.println("====================================");
        for (int i = 0; i < tables.size(); i++) {
            Table cur = (Table) tables.get(i);
            boolean booked = cur.isBooked();
            if (!booked) {
                System.out.println("TableID " + cur.getID());
                count++;
            }
        }
        System.out.println("====================================");
        System.out.println("Total available tables: " + count);

    }

    /**
     * This function updates the Tables JSON file with the latest data from the tables ArrayList, to keep it concurrent.
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     */
    public static void UpdateTables(ArrayList<Table> tables) {
        JSONArray updatedTables = new JSONArray();
        for (int i = 0; i < tables.size(); i++) {
            JSONObject newTable = new JSONObject();
            newTable.put("booked", tables.get(i).isBooked());
            newTable.put("customerContact", tables.get(i).getCustomerContact());
            newTable.put("ID", tables.get(i).getID());
            newTable.put("resID", tables.get(i).getResID());
            newTable.put("capacity", tables.get(i).getCapacity());
            updatedTables.add(newTable);
        }
        try (FileWriter file = new FileWriter("tables.json")) {
            file.write(updatedTables.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function looks for an available table based on the number of pax required, and then returns the index of that table in the ArrayList
     * @param pax
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     * @return Returns the index of the available table in the tables ArrayList.
     */
    public static int CheckTableAvailability(int pax, ArrayList<Table> tables) {
        for (int i = 0; i < tables.size(); i++) {
            Table currentTable = (Table) tables.get(i);
            boolean curBooked = (boolean) currentTable.isBooked();
            if (!curBooked) {
                long curPax = (long) currentTable.getCapacity();
                if (curPax >= pax) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * This function frees up the table based on the table number passed in.
     * This includes setting it to not booked, and clearing up its associated reservation ID and Customer Contact.
     * @param tableNum ID of table to be freed up.
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     * @return Returns the reservation ID of the reservation that was last occupying this table.
     */
    public static int ReleaseTable(int tableNum, ArrayList<Table> tables) {
        int oldResID = tables.get(tableNum-1).getResID();
        tables.get(tableNum-1).setBooked(false);
        tables.get(tableNum-1).setResID(0);
        tables.get(tableNum-1).setCustomerContact("");
        UpdateTables(tables);
        return oldResID;
    }
}
