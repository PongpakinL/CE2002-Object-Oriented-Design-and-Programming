package ui;

import managers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is responsible for displaying all the functions available to the user from the Menu module
 * It then calls the Menu Manager to call the appropriate function based on user's choice
 * @author Truong Vinh Khai
 */
public class MenuUI {
    public static void ShowMenuOptions() {

        JSONObject menu = (JSONObject) MenuMgr.ReadJson("menu.json");
        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("Operation list");
        System.out.println("1 : Create Menu Item");
        System.out.println("2 : Update Menu Item");
        System.out.println("3 : Remove Menu Item");
        System.out.println("4 : Exit");


        choice = sc.nextInt();

        while(choice < 1 && choice > 3) {
            System.out.println("Invalid choice. Please choose again.");
            choice = sc.nextInt();
        }

        while(choice != 4) {

            if(choice == 1) {
                MenuMgr.CreateItem(menu);
            }
            else if(choice == 2) {
                MenuMgr.UpdateItem(menu);
            }
            else {
                MenuMgr.RemoveItem(menu);
            }

            System.out.println("Choose another operation");
            System.out.println("1 : Create Menu Item");
            System.out.println("2 : Update Menu Item");
            System.out.println("3 : Remove Menu Item");
            System.out.println("4 : Exit");
            choice = sc.nextInt();

            while(choice < 1 && choice > 3) {
                System.out.println("Invalid choice. Please choose again.");
                choice = sc.nextInt();
            }

        }

    }


}
