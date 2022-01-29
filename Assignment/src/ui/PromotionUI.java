package ui;

import entities.Item;
import managers.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class shows all the functions available to the user from the Promotion module,
 * and then calls the Promotion manager to perform the user-selected function.
 * @author Truong Vinh Khai
 */
public class PromotionUI {

    public static void ShowPromotionOptions() {
        Menu m = new Menu();
        JSONObject menu = (JSONObject) MenuMgr.ReadJson("menu.json");
        JSONArray promotion = (JSONArray) MenuMgr.ReadJsonArray("promotion.json");
        Scanner sc = new Scanner(System.in);

        System.out.println("List of Operation");
        System.out.println("1 : Create Promotion");
        System.out.println("2 : Update Promotion");
        System.out.println("3 : Remove Promotion");
        System.out.println("4 : Exit");

        int choice;
        System.out.println("Choose a operation : ");
        choice = sc.nextInt();
        while (choice < 1 || choice > 4) {
            System.out.println("Pls type in a valid value : ");
            choice = sc.nextInt();
        }

        while (choice != 4) {

            if (choice == 1) {
                PromotionMgr.CreatePromotion(promotion, menu);
            } else if (choice == 2) {
                PromotionMgr.UpdatePromotion(promotion, menu);
            } else if (choice == 3) {
                PromotionMgr.RemovePromotion(promotion);
            }

            System.out.println("Choose a operation : ");
            choice = sc.nextInt();
            while (choice < 1 || choice > 4) {
                System.out.println("Pls type in a valid value : ");
                choice = sc.nextInt();
            }

        }
    }
}

