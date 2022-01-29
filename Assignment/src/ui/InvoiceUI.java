package ui;

import entities.*;
import managers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class displays all the Invoice related prompts to the user,
 * while calling the Invoice Manager to run the appropriate functions to print the invoice and save the data.
 * @author Ong Jun Siang
 */
public class InvoiceUI {
    public static void ShowInvoiceOptions() {

        JSONArray order = (JSONArray) MenuMgr.ReadJsonArray("order.json");
        ArrayList<Table> tables = Restaurant.tableArrayList;
        ArrayList<Reservation> reservations = Restaurant.resArrayList;

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the tableID you wish to print the invoice for: ");
        int billedTableNumber = sc.nextInt();
        sc.nextLine();
        int success = InvoiceMgr.FindReservationFromTable(tables, billedTableNumber);
        if (success >= 0 && success < 999) {
            int orderIndex = InvoiceMgr.FindOrder(order, billedTableNumber);

            if (orderIndex < 0) {
                System.out.println("Sorry, no order exists for that table.");
            } else {
                // Gets Order object for the table from the Order file.
                JSONObject orderToPay = new JSONObject();
                orderToPay = (JSONObject) order.get(orderIndex);


                System.out.println("Is the customer a member? (Y/N): ");
                String member = sc.nextLine().toUpperCase();
                while (!(member.equals("Y") || member.equals("N"))) {
                    System.out.println("Sorry, Invalid Input. Please enter only Y or N");
                    member = sc.nextLine();
                    member.toUpperCase();
                }
                if (member.matches("Y")) {
                    InvoiceMgr.PrintInvoice(reservations, order, orderToPay, success, billedTableNumber, true);
                } else {
                    InvoiceMgr.PrintInvoice(reservations, order, orderToPay, success, billedTableNumber, false);
                }
                int oldResID = TableMgr.ReleaseTable(billedTableNumber, tables);
                ReservationMgr.RemoveReservationWithID(reservations, tables, oldResID);
            }

        } else if (success == 999) {
            System.out.println("Sorry, no booking exists for that table.");
        } else {
            System.out.println("Sorry, invalid tableID.");
        }
        System.out.println("Returning to Main Menu.");

    }
}

