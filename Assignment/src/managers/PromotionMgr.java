package managers;

import entities.Item;
import entities.Promotion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for managing all the functions of the Promotion module
 * @author Truong Vinh Khai
 */
public class PromotionMgr {

    /**
     * Creates an empty JSON file to store the Promotion data
     * @param fileName name of JSON file
     */
    public static void CreateJsonFile(String fileName) {

        JSONArray promotion = new JSONArray();

        try (FileWriter file = new FileWriter(fileName)) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(promotion.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates a new Promotion based on input data and saves it into the Promotion JSONArray
     * @param promotion JSONArray of Promotion items
     * @param menu Menu object required for the JSONArray functions
     */
    public static void CreatePromotion(JSONArray promotion, JSONObject menu) {
        String name, startDate, endDate, price;
        JSONArray promotionItemList = new JSONArray();

        Promotion p = new Promotion();
        Scanner sc = new Scanner(System.in);
        int existed = 0;

        System.out.println("Type in the promotion name : ");
        name = sc.nextLine();

        for(int i=0; i < promotion.size();i++) {
            JSONObject a = (JSONObject) promotion.get(i);
            if(a.get("name").toString().equals(name)) {
                existed = 1;
                break;
            }

        }

        if(existed == 0) {

            System.out.println("Type in the promotion start date : ");
            startDate = sc.nextLine();
            System.out.println("Type in the promotion end date : ");
            endDate = sc.nextLine();



            System.out.println("Type in the add in item : ");
            String addInItem = "";
            addInItem = sc.nextLine();




            JSONArray menus = (JSONArray) menu.get("menus");
            JSONObject mainMenu = (JSONObject) menus.get(0);
            JSONArray menuSection = (JSONArray) mainMenu.get("menu_sections");


            while(!addInItem.equals("!")) {


                JSONObject c = new JSONObject();


                for(int i=0;i< menuSection.size();i++) {
                    JSONObject a = (JSONObject) menuSection.get(i);
                    JSONArray itemList = (JSONArray) a.get("menu_items");
                    for(int j=0;j< itemList.size();j++) {
                        JSONObject b = (JSONObject) itemList.get(j);
                        if(addInItem.equals(b.get("name").toString())) {
                            c = b;
                        }
                    }
                }

                promotionItemList.add(c);

                Item n = new Item();
                n.setDescription(c.get("description").toString());
                n.setName(c.get("name").toString());
                n.setPrice(c.get("price").toString());

                p.promotionItem.add(n);

                System.out.println("Type in the add in item : ");
                addInItem = sc.nextLine();

            }

            System.out.println("Type in the price : ");
            price = sc.nextLine();
            JSONObject newPromotion = new JSONObject();
            newPromotion.put("name", name);
            newPromotion.put("startDate", startDate);
            newPromotion.put("endDate", endDate);
            newPromotion.put("price", price);
            newPromotion.put("promotionItemList", promotionItemList);

            promotion.add(newPromotion);

            try (FileWriter file = new FileWriter("promotion.json")) {
                file.write(promotion.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {

            System.out.println("A promotion with the same name already existed");
        }

    }

    /**
     * Updates the Promotion item's name of price based on user input
     * @param promotion JSONArray of Promotion items
     * @param menu Menu object required for the JSONArray functions
     */
    public static void UpdatePromotion(JSONArray promotion, JSONObject menu) {

        String name, changing, newValue;

        Promotion p = new Promotion();
        Scanner sc = new Scanner(System.in);

        System.out.println("Type the promotion name to be updated : ");
        name = sc.nextLine();
        int existed = 0;

        JSONObject b = new JSONObject();

        for (int i=0;i<promotion.size();i++) {
            JSONObject a = (JSONObject) promotion.get(i);
            if(name.equals( a.get("name").toString() )) {
                b = a;
                existed = 1;
            }
        }

        if(existed == 1) {

            JSONArray promotionItemList = (JSONArray) b.get("promotionItemList");



            JSONArray menus = (JSONArray) menu.get("menus");
            JSONObject mainMenu = (JSONObject) menus.get(0);
            JSONArray menuSection = (JSONArray) mainMenu.get("menu_sections");

            System.out.println("Type the changing part : ");
            changing = sc.nextLine();

            String buffer;

            while(!changing.equals("!")) {


                if(changing.equals("promotionItemList")) {
                    int option;


                    System.out.println("1 : Add item ");
                    System.out.println("2 : Remove item ");
                    System.out.println("3 : Exit ");

                    option = sc.nextInt();


                    while(option != 3) {
                        buffer = sc.nextLine();
                        if(option == 1) {
                            System.out.print("Type in the add in item : ");

                            String addInItem;
                            addInItem = sc.nextLine();


                            while(!addInItem.equals("!")) {

                                JSONObject c = new JSONObject();

                                for(int i=0;i< menuSection.size();i++) {
                                    JSONObject a = (JSONObject) menuSection.get(i);
                                    JSONArray itemList = (JSONArray) a.get("menu_items");
                                    for(int j=0;j< itemList.size();j++) {
                                        JSONObject e = (JSONObject) itemList.get(j);
                                        if(addInItem.equals(e.get("name").toString())) {
                                            c = e;
                                        }
                                    }
                                }

                                Item n = new Item();
                                n.setDescription(c.get("description").toString());
                                n.setName(c.get("name").toString());
                                n.setPrice(c.get("price").toString());

                                p.promotionItem.add(n);

                                promotionItemList.add(c);
                                System.out.print("Type in the add in item : ");
                                addInItem = sc.nextLine();

                            }

                        }
                        else if(option == 2) {
                            System.out.print("Type in the remove item : ");
                            String removeItem;
                            removeItem = sc.nextLine();

                            while(!removeItem.equals("!")) {

                                JSONObject c = new JSONObject();


                                for(int i=0;i< menuSection.size();i++) {
                                    JSONObject a = (JSONObject) menuSection.get(i);
                                    JSONArray itemList = (JSONArray) a.get("menu_items");
                                    for(int j=0;j< itemList.size();j++) {
                                        JSONObject e = (JSONObject) itemList.get(j);
                                        if(removeItem.equals( e.get("name").toString() )) {
                                            c = e;
                                        }
                                    }
                                }

                                Item n = new Item();
                                n.setDescription(c.get("description").toString());
                                n.setName(c.get("name").toString());
                                n.setPrice(c.get("price").toString());

                                p.promotionItem.remove(n);

                                promotionItemList.remove(c);
                                System.out.print("Type in the remove item : ");
                                removeItem = sc.nextLine();

                            }

                        }

                        System.out.println("Choose a option : ");
                        System.out.println("1 : Add item ");
                        System.out.println("2 : Remove item ");
                        System.out.println("3 : Exit ");
                        option = sc.nextInt();

                        if(option == 3)
                            buffer = sc.nextLine();

                    }

                }
                else {
                    System.out.println("Type in the new value : ");
                    newValue = sc.nextLine();
                    b.put(changing, newValue);

                    if(changing.equals("name")) {
                        name = newValue;
                    }
                    else if(changing.equals("price")){
                        p.price = newValue;
                    }
                }

                System.out.println("Type in the part you wish to change (name or price), or ! to exit : ");
                changing = sc.nextLine();
            }


            try (FileWriter file = new FileWriter("promotion.json")) {
                file.write(promotion.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {

            System.out.println("The promotion is not existed");
        }

    }

    /**
     * Removes the Promotion with the entered name
     * @param promotion JSONArray of Promotion items
     */
    public static void RemovePromotion(JSONArray promotion) {

        Promotion p = new Promotion();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the Promotion you widh to remove: ");
        String name = sc.nextLine();
        int existed =0;

        JSONObject b = null;

        for (int i=0;i<promotion.size();i++) {
            JSONObject a = (JSONObject) promotion.get(i);
            if(name.equals(a.get("name").toString())) {
                b = new JSONObject();
                b = a;
                existed =1;
            }
        }

        if(existed == 1) {

            Item n = new Item();
            n.setDescription(b.get("description").toString());
            n.setName(b.get("name").toString());
            n.setPrice(b.get("price").toString());

            p.promotionItem.remove(n);

            promotion.remove(b);

            try (FileWriter file = new FileWriter("promotion.json")) {
                file.write(promotion.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {

            System.out.println("The promotion is not existed");
        }

    }
}
