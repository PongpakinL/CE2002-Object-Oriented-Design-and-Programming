package ui;

import managers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class shows all the options available to the user from the Order module,
 * and then calls the Order manager to perform the user-selected function.
 * @author Truong Vinh Khai
 */
public class OrderUI {
    public static void ShowOrderOptions() {
        JSONObject menu = (JSONObject) MenuMgr.ReadJson("menu.json");
        JSONArray promotion = (JSONArray) MenuMgr.ReadJsonArray("promotion.json");
        JSONArray order = (JSONArray) MenuMgr.ReadJsonArray("order.json");

        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("Choose from the following:");
        System.out.println("1 : Create Order");
        System.out.println("2 : View Order");


        choice = sc.nextInt();
        if(choice == 1) {
            OrderMgr.CreateOrder(menu,promotion,order);
        }
        else if(choice == 2) {
            System.out.println("Type in the table number for the order you wish to see: ");
            String tableToView = sc.nextLine();
            OrderMgr.ViewOrder(order, tableToView);
        }

    }

}
