
import java.util.Scanner;

import entities.Restaurant;
import managers.*;
import ui.*;

public class Main {

    /**
     * Main driver of the RRPSS app. Displays main menu and calls the appropriate UI class based on user choice.
     */
    public static void main(String[] args) {

        Restaurant.initRestaurant();
        Scanner sc = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Welcome to the Restaurant Reservation and Point of Sale System!");
            System.out.println("Please choose from one of the following by entering its number: ");
            System.out.println("1. Create/Update/Remove Menu Item.");
            System.out.println("2. Create/Update/Remove Promotion.");
            System.out.println("3. Create/View/Update Order.");
            System.out.println("4. Create/View/Remove Reservation Booking.");
            System.out.println("5. Check Table Availability.");
            System.out.println("6. Print Order Invoice.");
            System.out.println("7. Print Sale Revenue Report By Period.");
            System.out.println("8. Exit.");
            int choice = sc.nextInt();
            sc.nextLine();
            // Clear expired reservations automatically
            ReservationMgr.ClearExpiredReservations(Restaurant.resArrayList, Restaurant.tableArrayList);
            switch (choice) {
                case 1:
                    System.out.println("Create/Update/Remove Menu Item Selected.");
                    ui.MenuUI.ShowMenuOptions();
                    break;
                case 2:
                    System.out.println("Create/Update/Remove Promotion Selected.");
                    ui.PromotionUI.ShowPromotionOptions();
                    break;
                case 3:
                    System.out.println("Create/View/Update Order Selected.");
                    ui.OrderUI.ShowOrderOptions();
                    break;
                case 4:
                    System.out.println("Create/View/Remove Reservation Booking Selected");
                    ui.ReservationUI.ShowReservationOptions();
                    break;
                case 5:
                    System.out.println("Checking Tables...");
                    managers.TableMgr.ShowTableOptions();
                    break;
                case 6:
                    System.out.println("Printing Invoice...");
                    ui.InvoiceUI.ShowInvoiceOptions();
                    break;
                case 7:
                    System.out.println("Fetching Sale Revenue Report...");
                    ui.RevenueUI.RevenueOptions();
                    break;
                default:
                    System.out.println("Program Terminating.");
                    done = true;
                    break;
            }
        }
    }
}
