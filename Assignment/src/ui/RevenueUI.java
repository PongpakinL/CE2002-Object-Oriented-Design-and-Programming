package ui;

import java.text.ParseException;
import java.util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import managers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * This class is responsible for displaying all the options available to the user from the Revenue Report module
 * It then calls the Revenue Manager to perfrom the appropriate function based on the user's choice.
 * @author Lum Pongpakin
 */
public class RevenueUI {

    public static void RevenueOptions() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Which period you want to view?");
        System.out.println("[1] - View Sales Revenue By Day");
        System.out.println("[2] - View Sales Revenue By Month");
        System.out.println("[3] - View All Sales Revenue");
        System.out.println("[4] - Exit");

        int choice;
        System.out.println("Choose an operation : ");
        choice = sc.nextInt();
        while(choice < 1 || choice > 4) {
            System.out.println("Please type a valid input : ");
            choice = sc.nextInt();
        }

        while(choice != 4) {
            if(choice == 1) {
                RevenueMgr.viewDay();
            }
            else if(choice == 2){
                RevenueMgr.viewMonth();
            }
            else if(choice == 3){
                RevenueMgr.viewAll();
            }

            System.out.println("Choose an operation : ");
            System.out.println("[1] - View Sales Revenue By Day");
            System.out.println("[2] - View Sales Revenue By Month");
            System.out.println("[3] - View All Sales Revenue");
            System.out.println("[4] - Exit");
            choice = sc.nextInt();
            while(choice < 1 || choice > 4) {
                System.out.println("Please type a valid input : ");
                choice = sc.nextInt();
            }
        }
    }

}
