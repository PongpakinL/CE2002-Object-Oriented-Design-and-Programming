package managers;

import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class manages the functions that are required by the reservations module
 * @author Ong Jun Siang
 */
public class ReservationMgr {

    ArrayList<Reservation> reservations = Restaurant.resArrayList;
    ArrayList<Table> tables = Restaurant.tableArrayList;


    /**
     * Creates a new Reservation object with the user's input.
     * This object is then added to the reservations ArrayList.
     * The respective Table in the tables ArrayList are also updated with the Reservation ID and Customer Contact if the booking is successful.
     * @param reservations An ArrayList of Reservations containing the most up-to-date records of the restaurant's reservations.
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     */
    public static void CreateReservation(ArrayList<Reservation> reservations, ArrayList<Table> tables) {
        // Get persistent reservation ID from JSON
        int resCount = 0;
        JSONObject resCountFile = (JSONObject) MenuMgr.ReadJson("resCount.json");
        String resCountString = resCountFile.get("resCount").toString();
        resCount = Integer.parseInt(resCountString);

        int pax;
        Scanner sc = new Scanner(System.in);
        // Making a copy of the table array to modify the booked table later
        ArrayList<Table> t = tables;

        System.out.println("Creating reservation...");
        System.out.println("Enter the number of pax: ");
        pax = sc.nextInt();
        sc.nextLine();
        int available = TableMgr.CheckTableAvailability(pax, tables);
        if (available >= 0) {
            // Table is available, create new JSONObject to contain all reservation details.
            Reservation newReservation = new Reservation();
            newReservation.setPax(pax);

            // Getting input for reservation DATE
            System.out.println("Enter the date of the reservation in YYYY-MM-DD: ");
            LocalDate date = LocalDate.parse(sc.nextLine());
            LocalDate today = LocalDate.now();
            while (date.isBefore(today)) {
                System.out.println("Sorry, you must enter a date in the future.");
                date = LocalDate.parse(sc.nextLine());
            }
            newReservation.setDate(date.toString());

            // Getting input for reservation TIME
            System.out.println("Enter the time of the reservation (24-hour format) in hh:mm : ");
            LocalTime time = LocalTime.parse(sc.nextLine());
            while (time.isBefore(LocalTime.parse("11:00")) || time.isAfter(LocalTime.parse("21:00"))) {
                System.out.println("Sorry, we only accept reservations from 11:00-21:00");
                time = LocalTime.parse(sc.nextLine());
            }
            newReservation.setTime(time.toString());

            // Getting input for NAME and CONTACT
            System.out.println("Enter the customer's name: ");
            String custName = sc.nextLine();
            newReservation.setName(custName);
            System.out.println("Enter the customer's contact number: ");
            String custCon = sc.nextLine();
            newReservation.setContact(custCon);
            newReservation.setResID(resCount+1);

            // Adding complete details to reservations ArrayList.
            reservations.add(newReservation);


            // Updating status of booked table.
            tables.get(available).setBooked(true);
            tables.get(available).setResID(resCount+1);
            tables.get(available).setCustomerContact(custCon);
            TableMgr.UpdateTables(tables);

            // Now we update the JSON file with our new JSONObject that contains the newly added reservation.
            UpdateReservationFile(reservations);
            resCountFile.put("resCount", resCount+1);
            try (FileWriter file = new FileWriter("resCount.json")) {
                file.write(resCountFile.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Reservation Created!");
        } else {
            // Table is unavailable, display error message
            System.out.println("Sorry, we have no tables at the moment for the required pax.");
        }

    }

    /**
     * Checks if the reservation exists based on the Contact Number given by the user.
     * Details are printed if the reservation is valid, which includes the Customer Name, Pax no, Date and Time.
     * @param reservations An ArrayList of Reservations containing the most up-to-date records of the restaurant's reservations.
     * @return Returns the index of the relevant reservation inside the reservations ArrayList.
     */
    public static int CheckReservation(ArrayList<Reservation> reservations) {
        String contact;
        Scanner sc = new Scanner(System.in);

        System.out.println("Checking reservations...");
        System.out.println("Enter the customer's contact number: ");
        contact = sc.nextLine();
        for (int i = 0; i < reservations.size(); i++) {
            Reservation currentReservation = (Reservation) reservations.get(i);
            String curContact = (String) currentReservation.getContact();
            if (curContact.equals(contact)) {
                System.out.println("Reservation Found for " + currentReservation.getName());
                System.out.println("No. of Pax: " + currentReservation.getPax());
                String curDate = (String) currentReservation.getDate();
                System.out.println("Date: " + curDate);
                String curTime = (String) currentReservation.getTime();
                System.out.println("Time: " + curTime);
                return i;
            }
        }
        return -1;
    }


    /**
     * This function updates the status of the associated Table in the tables ArrayList, to show that it has been freed up,
     * by setting its ReservationID to 0, and removing its corresponding Customer Contact.
     * It also then removes the Reservation associated with the passed-in index from the reservations ArrayList.
     * @param reservations An ArrayList of Reservations containing the most up-to-date records of the restaurant's reservations.
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     * @param index Index of the Reservation object to be removed, as returned by the CheckReservation function.
     */
    public static void RemoveReservation(ArrayList<Reservation> reservations, ArrayList<Table> tables, int index) {
        // Get reservation from arraylist with index
        Reservation temp = (Reservation) reservations.get(index);
        int tableToRemove = temp.getResID();

        // Look through list of tables to find one with matching resID
        // Free up that table
        for (int i = 0; i < tables.size(); i++) {
            Table cur = tables.get(i);
            if (cur.getResID() == tableToRemove) {
                tables.get(i).setResID(0);
                tables.get(i).setBooked(false);
                tables.get(i).setCustomerContact("");
            }
        }
        TableMgr.UpdateTables(tables);
        reservations.remove(index);
        UpdateReservationFile(reservations);
        System.out.println("Reservation Removed.");
    }

    /**
     * This function has the same functionality as the RemoveReservation above, except that ReservationID is passed in instead of index,
     * This is to make it easier to implement removal of the reservation once its invoice has been printed.
     * @param reservations An ArrayList of Reservations containing the most up-to-date records of the restaurant's reservations.
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     * @param resID ID of the Reservation, given at the time the Reservation object is created.
     */
    public static void RemoveReservationWithID(ArrayList<Reservation> reservations, ArrayList<Table> tables, int resID) {
        Table temp = new Table();
        int resToRemove = -1;
        for (int i = 0; i < tables.size(); i++) {
             temp = tables.get(i);
             int curID = (int) temp.getResID();
             if (curID == resID) {
                 resToRemove = i;
                 tables.get(i).setResID(0);
                 tables.get(i).setBooked(false);
                 tables.get(i).setCustomerContact("");
             }
        }
        TableMgr.UpdateTables(tables);
        reservations.remove(resToRemove);
        UpdateReservationFile(reservations);
        System.out.println("Reservation Removed.");
    }

    /**
     * This function updates the Reservations JSON file with the current reservations ArrayList to keep the stored data up-to-date.
     * @param reservations An ArrayList of Reservations containing the most up-to-date records of the restaurant's reservations.
     */
    public static void UpdateReservationFile(ArrayList<Reservation> reservations) {
        JSONArray updatedReservations = new JSONArray();
        for (int i = 0; i < reservations.size(); i++) {
            JSONObject thisReservation = new JSONObject();
            thisReservation.put("date", reservations.get(i).getDate());
            thisReservation.put("pax", reservations.get(i).getPax());
            thisReservation.put("contact", reservations.get(i).getContact());
            thisReservation.put("name", reservations.get(i).getName());
            thisReservation.put("time", reservations.get(i).getTime());
            thisReservation.put("resID", reservations.get(i).getResID());
            updatedReservations.add(thisReservation);
        }
        try (FileWriter file = new FileWriter("reservations.json")) {
            file.write(updatedReservations.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This function removes all reservations that have expired by checking their booked timing with the current time, if they have been exceeded 20 minutes.
     * @param reservations An ArrayList of Reservations containing the most up-to-date records of the restaurant's reservations.
     * @param tables An ArrayList of Tables containing the current status of all tables in the restaurant.
     */
    public static void ClearExpiredReservations(ArrayList<Reservation> reservations, ArrayList<Table> tables) {
        Reservation temp = new Reservation();
        for (int i = 0; i < reservations.size(); i++) {
            temp = reservations.get(i);
            if (LocalTime.now().isAfter(LocalTime.parse(temp.getTime()).plusMinutes(20)) && LocalDate.now().isEqual(LocalDate.parse(temp.getDate()))) {
                RemoveReservation(reservations, tables, i);
            }
        }
    }


}
