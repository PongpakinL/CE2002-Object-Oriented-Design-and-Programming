package managers;

import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class is responsible for all the functions required by the Order module
 * @author Truong Vinh Khai
 */
public class OrderMgr {

    /**
     * Creates a new Order JSONObject from the user-entered data, and then calls on functions to add or remove items.
     * @param menu menu data from the menu JSON file
     * @param promotion promotion data from the promotions JSON file
     * @param order current list of orders from the order JSON file
     */
    public static void CreateOrder(JSONObject menu, JSONArray promotion, JSONArray order) {
        String staffName;
        int tableNumber;
        int choice;

        Order nOrder = new Order();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter staff name : ");
        staffName = sc.nextLine();
        System.out.println("Enter tableID : ");
        tableNumber = sc.nextInt();
        sc.nextLine();
        if(tableNumber > 10) {
            System.out.println("Invalid table id");
            return;
        }


        JSONObject newOrder = new JSONObject();
        JSONArray itemList = new JSONArray();

        nOrder.price = "0";
        nOrder.staffName = staffName;
        nOrder.tableNumber = tableNumber;


        newOrder.put("staffName", staffName);
        newOrder.put("price", 0);
        newOrder.put("itemList", itemList);

        order.add(newOrder);

        System.out.println("Operation List");
        System.out.println("1 : Add item");
        System.out.println("2 : Remove item");
        System.out.println("3 : Exit");


        choice = sc.nextInt();
        while(choice < 1 && choice > 3) {
            System.out.println("Please enter a valid value : ");
            choice = sc.nextInt();
        }

        if(choice == 1) {
            AddItem(order,promotion,menu,nOrder);
        }
        else if(choice == 2) {
            RemoveItem(order,nOrder);
        }


    }

    /**
     * Creates a new Item JSONObject and adds it to the respective ArrayList of the Order object that was passed in
     * when this function was called.
     * @param order current list of orders from the order JSON file
     * @param promotion promotion data from the promotions JSON file
     * @param menu menu data from the menu JSON file
     * @param nOrder Order object representing the current order
     */
    public static void AddItem(JSONArray order,JSONArray promotion, JSONObject menu, Order nOrder ) {
        String name,buffer;

        ArrayList<Item> addInItem;
        int choice;
        JSONObject currentOrder = (JSONObject) order.get(order.size()-1);
        JSONArray itemList = (JSONArray) currentOrder.get("itemList");
        Scanner sc = new Scanner(System.in);

        System.out.println("Choice");
        System.out.println("1 : Add promotion combo");
        System.out.println("2 : Add item");
        System.out.println("3 : Exit");

        System.out.println("Choose operation");
        choice = sc.nextInt();

        while(choice != 3) {

            buffer = sc.nextLine();

            if(choice == 1) {


                name = sc.nextLine();
                int existed = 0;

                JSONObject b = new JSONObject();

                for(int i=0;i< promotion.size();i++) {
                    JSONObject a = (JSONObject) promotion.get(i);
                    if( name.equals(a.get("name").toString() )) {
                        b = a;
                        existed = 1;
                    }
                }

                if(existed == 1) {


                    Float p = Float.parseFloat(currentOrder.get("price").toString());
                    Float comboPrice = Float.parseFloat(b.get("price").toString());

                    currentOrder.put("price", p + comboPrice );
                    itemList.add(b);

                    Item n = new Item();
                    n.setName(b.get("name").toString());
                    n.setDescription(b.get("description").toString());
                    n.setPrice(b.get("price").toString());

                    nOrder.NormalItems.add(n);

                }
                else {

                    System.out.println("The promotion is not existed in the Menu");
                }



            }
            else if(choice == 2) {

                name = sc.nextLine();
                int existed = 0;

                JSONArray menus = (JSONArray) menu.get("menus");
                JSONObject mainMenu = (JSONObject) menus.get(0);
                JSONArray menuSection = (JSONArray) mainMenu.get("menu_sections");

                JSONObject c = new JSONObject();

                for(int i=0;i< menuSection.size();i++) {
                    JSONObject a = (JSONObject) menuSection.get(i);
                    JSONArray List = (JSONArray) a.get("menu_items");
                    for(int j=0;j< List.size();j++) {
                        JSONObject e = (JSONObject) List.get(j);
                        if(name.equals( e.get("name").toString() )) {
                            c = e;
                            existed = 1;
                        }
                    }
                }

                if(existed == 1) {

                    Float p = Float.parseFloat(currentOrder.get("price").toString());
                    Float comboPrice = Float.parseFloat(c.get("price").toString());

                    currentOrder.put("price", p + comboPrice );
                    itemList.add(c);

                    Promotion n = new Promotion();
                    n.setPromotionName(c.get("name").toString());
                    n.setPrice(c.get("price").toString());
                    JSONArray w = (JSONArray) c.get("promotionItemList");

                    for(int e=0;e<((ArrayList) c.get("promotionItemList")).size();e++){
                        Item t = new Item();
                        JSONObject o = (JSONObject)  w.get(e);
                        t.setName(o.get("name").toString());
                        t.setPrice(o.get("price").toString());
                        t.setDescription((String) o.get("description"));
                        n.promotionItem.add(t);
                    }

                    nOrder.PromotionItems.add(n);
                }
                else {

                    System.out.println("Item is not existed in the Menu");
                }

            }

            System.out.println("Choose operation");
            System.out.println("1 : Add promotion combo");
            System.out.println("2 : Add item");
            System.out.println("3 : Exit");
            choice = sc.nextInt();
        }

        try (FileWriter file = new FileWriter("order.json")) {
            file.write(order.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Removes the item matching the user-specified name from the Order object,
     * and also updates the order JSON file that stores all the order data.
     * @param order current list of orders from the order JSON file
     * @param nOrder Order object representing order the item is to be removed from.
     */
    public static void RemoveItem(JSONArray order, Order nOrder) {

        String name;
        Scanner sc = new Scanner(System.in);

        JSONObject currentOrder = (JSONObject) order.get(order.size()-1);
        JSONArray itemList = (JSONArray) currentOrder.get("itemList");

        System.out.println("Type the name of the item to be removed : ");
        name = sc.nextLine();
        int existed = 0;

        JSONObject b = new JSONObject();
        for(int i=0;i<itemList.size();i++) {
            JSONObject a = (JSONObject) itemList.get(i);
            if(name.equals(a.get("name").toString())) {
                b = a;
                existed = 1;
            }
        }

        if(existed == 0) {
            System.out.println("Item is not existed in the Order ");
            return;
        }

        itemList.remove(b);


        for(int i=0;i<nOrder.NormalItems.size();i++){
            if(nOrder.NormalItems.get(i).getName() == name){
                nOrder.NormalItems.remove(i);
            }
        }

        for(int i=0;i<nOrder.PromotionItems.size();i++){
            if(nOrder.PromotionItems.get(i).getPromotionName() == name){
                nOrder.PromotionItems.remove(i);
            }
        }

        try (FileWriter file = new FileWriter("order.json")) {
            file.write(order.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Prints complete details of the order corresponding to the specified table number.
     * This includes table number, name of staff who created the order, list of all food items, and subtotal cost.
     * @param order current list of orders from the order JSON file
     * @param tableNumber table number of order that is to be viewed
     */
    public static void ViewOrder(JSONArray order,String tableNumber) {

        JSONObject currentOrder = new JSONObject() ;
        int existed = 0;

        for(int i=0;i<order.size();i++){
            if(((JSONObject) order.get(i)).get("tableID") == tableNumber){
                currentOrder = (JSONObject) order.get(i);
                existed =1;
            }
        }

        if(existed == 1) {

            JSONArray itemList = (JSONArray) currentOrder.get("itemList");

            System.out.printf("Created by : %s", currentOrder.get("staffName").toString());
            System.out.printf("\n", null);

            for(int i=0;i<itemList.size();i++) {
                JSONObject a = (JSONObject) itemList.get(i);
                System.out.printf("%s - %s$\n", a.get("name").toString(), a.get("price").toString());
            }

            System.out.printf("\n", null);
            System.out.printf("Total\n", null);
            System.out.printf("%s$", currentOrder.get("price").toString() );

        } else {

            System.out.println("Order is not existed ");
        }
    }


}

