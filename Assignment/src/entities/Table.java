package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a table that con accomodate customers' bookings in the restaurant
 * @author Ong Jun Siang
 */
public class Table {
    /**
     * Whether if this table is currently booked.
     */
        private boolean booked;
    /**
     * Unique TableID for this table.
     */
        private int ID;
    /**
     * Unique ID of corresponding reservation for this table.
     */
        private int resID;
    /**
     * This table's seating capacity
     */
        private int capacity;
    /**
     * The contact number of customer who booked this table, if applicable.
     */
        private String customerContact;


    /**
     * Creates a new Table with all the fields
     * @param booked This table's booking status
     * @param customerContact This table's customer contact, if booked.
     * @param ID This table's ID
     * @param resID The unique ID of the reservation associated with this table, if booked.
     * @param capacity This table's seating capacity.
     */
        public Table(boolean booked, String customerContact, int ID, int resID, int capacity) {
            this.booked = booked;
            this.customerContact = customerContact;
            this.ID = ID;
            this.resID = resID;
            this.capacity = capacity;
        }

        public Table() {

        }

    /**
     * @return Returns seating capacity of this table.
     */
        public int getCapacity() {
            return capacity;
        }

    /**
     * @return Returns ID of this table
     */
        public int getID() {
            return ID;
        }

    /**
     * @return Returns contact number of this table's associated customer.
     */
        public String getCustomerContact() {
            return customerContact;
        }

    /**
     * @return Returns ID of this table's associated reservation.
     */
        public int getResID() {
            return resID;
        }

    /**
     * @return Returns if this table is booked.
     */
        public boolean isBooked() {
            return booked;
        }

    /**
     * Changes the booking status of this table.
     * @param booked This table's new booking status.
     */
        public void setBooked(boolean booked){
            this.booked = booked;
        }

    /**
     * Changes the contact number of this table's customer.
     * @param customerContact This table's new contact number
     */
        public void setCustomerContact(String customerContact){
            this.customerContact = customerContact;
        }

    /**
     * Changes the associated reservation ID of this table.
     * @param resID This table's new associated reservation ID
     */
        public void setResID(int resID){
            this.resID = resID;
        }
}

