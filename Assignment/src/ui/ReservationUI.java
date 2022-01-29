package ui;

import entities.*;
import managers.*;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class displays all the available options for the user to select for the reservation module.
 * The appropriate function is then called via the manager based on the user's choice.
 * @author Ong Jun Siang
 */
public class ReservationUI {
    public static void ShowReservationOptions() {
        ArrayList<Reservation> reservations = Restaurant.resArrayList;
        ArrayList<Table> tables = Restaurant.tableArrayList;

        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("Choose from the following:");
        System.out.println("1 : Create Reservation");
        System.out.println("2 : Check/Remove Reservation");
        System.out.println("3 : Exit");

        choice = sc.nextInt();

        switch (choice) {
            case 1: ;
                ReservationMgr.CreateReservation(reservations, tables);
                break;
            case 2:
                int result = ReservationMgr.CheckReservation(reservations);
                if (result >= 0) {
                    System.out.println("Do you want to remove this reservation? (Y/N)");
                    String toRemove = sc.nextLine().toUpperCase();
                    while (!(toRemove.equals("Y") || toRemove.equals("N"))) {
                        System.out.println("Sorry, Invalid Input. Please enter only Y or N");
                        toRemove = sc.nextLine();
                        toRemove.toUpperCase();
                    }
                    if (toRemove.matches("Y")) {
                        ReservationMgr.RemoveReservation(reservations, tables,  result);
                    }
                } else {
                    System.out.println("Sorry, Reservation does not exist.");
                }
                System.out.println("Returning to Main Menu.");
                break;
            default:
                System.out.println("Exiting...");
                System.out.println("Returning to Main Menu.");
                break;
        }
    }


}
