package entities;

/**
 * Represents a reservation with complete details made for a customer.
 * @author Ong Jun Siang
 */
public class Reservation {

    /**
     * The date for this reservation.
     */
    private String date;
    /**
     * Number of pax for this reservation.
     */
    private int pax;
    /**
     * Customer's contact number associated with this reservation.
     */
    private String contact;
    /**
     * Name of customer associated with this reservation
     */
    private String name;
    /**
     * The time for this reservation
     */
    private String time;
    /**
     * ReservationID for this reservation, used for identification
     */
    private int resID;

    /**
     * Creates a new Reservation with all the fields
     * @param date This reservation's date
     * @param pax The no. of pax this reservation is for.
     * @param contact The contact number of the customer this reservation is for.
     * @param name The name of the customer this reservation is for.
     * @param time This reservation's time.
     * @param resID This reservations's ID.
     */
    public Reservation(String date, int pax, String contact, String name, String time, int resID) {
        this.date = date;
        this.pax = pax;
        this.contact = contact;
        this.name = name;
        this.time = time;
        this.resID = resID;
    }

    public Reservation() {
        this.date = "";
        this.pax = 0;
        this.contact = "";
        this.name = "";
        this.time = "";
        this.resID = 0;
    }

    /**
     * @return Returns unique ID of this reservation.
     */
    public int getResID() {
        return resID;
    }

    /**
     * @return Returns date String of this reservation.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return Returns number of pax for this reservation.
     */
    public int getPax() {
        return pax;
    }

    /**
     * @return Returns contact number associated with this reservation.
     */
    public String getContact() {
        return contact;
    }

    /**
     * @return Returns name of customer associated with this reservation.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns time String of this reservation.
     */
    public String getTime() {
        return time;
    }

    /**
     * Changes the date of this reservation
     * @param date This reservation's new date
     *             Should be in "YYYY-MM-DD"
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Changes the number of pax of this reservation
     * @param pax This reservation's new pax number.
     */
    public void setPax(int pax) {
        this.pax = pax;
    }

    /**
     * Changes the contact number associated with this reservation
     * @param contact This reservation's new customer contact number
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Changes the customer name assoicated with this reservation
     * @param name This reservation's new customer name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes the time of this reservation
     * @param time This reservation's new timing
     *             Should be in "HH:MM" (24 hours)
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Sets the ID of this reservation
     * @param resID This reservation's new ID.
     */
    public void setResID(int resID) {
        this.resID = resID;
    }
}
