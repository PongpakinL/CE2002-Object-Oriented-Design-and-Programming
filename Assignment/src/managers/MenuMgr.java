package managers;

import entities.Item;
import entities.MenuSections;
import entities.Restaurant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is responsible for all the functions that are required by the Menu module.
 * @author Truong Vinh Khai
 */
public class MenuMgr {

    /**
     * Reads data stored in the Menu JSON file and adds them to the restaurant entity's MainMenu ArrayList
     * @param menu JSON file containing all the latest menu data
     */
    public static void GetMenuFromJson(JSONObject menu){
        JSONArray menus = (JSONArray) menu.get("menus");
        JSONObject mMenu = (JSONObject) menus.get(0);
        JSONArray menuSection = (JSONArray) mMenu.get("menu_sections");

        for(int i=0;i<mMenu.size();i++){
            JSONObject a = (JSONObject) menuSection.get(i);
            MenuSections newMenuSections = new MenuSections();
            newMenuSections.name = a.get("section_name").toString();
            JSONArray itemList = (JSONArray) a.get("menu_items");

            for(int j=0;j< itemList.size();j++) {
                JSONObject b = (JSONObject) itemList.get(j);
                Item newItem = new Item();
                newItem.name = b.get("name").toString();
                newItem.price = b.get("price").toString();
                newItem.description = b.get("description").toString();

                newMenuSections.AddInItemList(newItem);

            }

            Restaurant.MainMenu.add(newMenuSections);
        }

    }

    /**
     * Converts a JSON file into a JSON object
     * @param fileName name of JSON file to be converted into JSONObject
     * @return JSONObject containing data from JSON file
     */
    public static JSONObject ReadJson( String fileName ) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fileName))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject menu = (JSONObject) obj;

            return menu;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converts a JSON file into a JSONArray.
     * @param fileName name of JSON file to be converted into JSONArray
     * @return JSONArray containing all the data from the JSON file.
     */
    public static JSONArray ReadJsonArray( String fileName ) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fileName))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray menu = (JSONArray) obj;

            return menu;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates a Menu Item JSONObject with the entered data, and then adds them into the MainMenu ArrayList,
     * and also updates the menu JSON file with the new item
     * @param menu JSONObject containing data extracted from the menu JSON file
     */
    public static void CreateItem(JSONObject menu) {
        String name, section, description, price;

        Scanner sc = new Scanner(System.in);

        System.out.println("Type in the item name : ");
        name = sc.nextLine();
        System.out.println("Type in the section : ");
        section = sc.nextLine();
        System.out.println("Type in the item description : ");
        description = sc.nextLine();
        System.out.println("Type in the item price : ");
        price = sc.nextLine();

        JSONArray menus = (JSONArray) menu.get("menus");
        JSONObject mainMenu = (JSONObject) menus.get(0);
        JSONArray menuSection = (JSONArray) mainMenu.get("menu_sections");

        JSONObject b = new JSONObject();


        for (int i = 0; i < menuSection.size(); i++) {
            JSONObject a = (JSONObject) menuSection.get(i);
            if (section.equals(a.get("section_name").toString())) {
                b = a;

            }
        }


        JSONArray itemList = (JSONArray) b.get("menu_items");

        Item nItem = new Item();

        nItem.setName(name);
        nItem.setPrice(price);
        nItem.setDescription(description);

        int existed = 0;
        for(int i=0;i<itemList.size();i++) {
            JSONObject a = (JSONObject)	itemList.get(i);
            if(a.get("name").toString().equals(name)) {
                existed = 1;
                break;
            }
        }

        if(existed == 0) {

            for (int j = 0; j < Restaurant.MainMenu.size(); j++) {
                MenuSections s = (MenuSections) Restaurant.MainMenu.get(j);
                if (s.name.equals(section)) {
                    Restaurant.MainMenu.get(j).AddInItemList(nItem);
                }
            }



            JSONObject newItem = new JSONObject();

            newItem.put("name", name);
            newItem.put("description", description);
            newItem.put("price", price);

            itemList.add(newItem);



            try (FileWriter file = new FileWriter("menu.json")) {
                file.write(menu.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {

            System.out.println("A item with the same name already existed in the Menu, please create another item");
        }

    }

    /**
     * Changes the attributes of the Menu Item based on user input, and updates the MainMenu ArrayList and menu JSON file as well.
     * @param menu JSONObject containing data extracted from the menu JSON file
     */
    public static void UpdateItem(JSONObject menu) {

        String name, section,changing, newValue;

        Scanner sc = new Scanner(System.in);

        System.out.println("Type in the item name : ");
        name = sc.nextLine();
        System.out.println("Type in the section : ");
        section = sc.nextLine();
        System.out.println("What attribute do you wish change? (Type in 'price', 'name' or 'description'): ");
        changing = sc.nextLine();
        System.out.println("Type in the new value : ");
        newValue = sc.nextLine();

        JSONArray menus = (JSONArray) menu.get("menus");
        JSONObject mainMenu = (JSONObject) menus.get(0);
        JSONArray menuSection = (JSONArray) mainMenu.get("menu_sections");

        JSONObject b = new JSONObject();


        for(int i=0;i< menuSection.size();i++) {
            JSONObject a = (JSONObject) menuSection.get(i);
            if(section.equals(a.get("section_name").toString())) {
                b = a;

            }
        }

        JSONObject c = new JSONObject();
        JSONArray itemList = (JSONArray) b.get("menu_items");
        int existed = 0;

        for(int i=0;i< itemList.size();i++) {
            JSONObject a = (JSONObject) itemList.get(i);
            if(name.equals(a.get("name").toString())) {
                c = a;
                existed = 1;
            }
        }

        if(existed == 1) {

            for(int j =0;j < Restaurant.MainMenu.size();j++){
                MenuSections s = (MenuSections) Restaurant.MainMenu.get(j);
                if(s.name.equals(section)){
                    for(int k=0;k<s.itemList.size();k++){
                        Item q = (Item) s.itemList.get(k);
                        if(q.name.equals(name)){
                            if(changing.equals("price"))
                                Restaurant.MainMenu.get(j).itemList.get(k).setPrice(newValue);
                            else if(changing.equals("name"))
                                Restaurant.MainMenu.get(j).itemList.get(k).setName(newValue);
                            else if(changing.equals("description"))
                                Restaurant.MainMenu.get(j).itemList.get(k).setDescription(newValue);
                        }
                    }
                }
            }

            c.put(changing, newValue);


            try (FileWriter file = new FileWriter("menu.json")) {
                file.write(menu.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {

            System.out.println("The item is not existed in Menu");

        }

    }

    /**
     * Removes the Menu Item specified by the user from both the MainMenu ArrayList and menu JSON file.
     * @param menu
     */
    public static void RemoveItem(JSONObject menu) {

        String name, section, description, price;

        Scanner sc = new Scanner(System.in);

        System.out.println("Type in the item name : ");
        name = sc.nextLine();
        System.out.println("Type in the section : ");
        section = sc.nextLine();


        JSONArray menus = (JSONArray) menu.get("menus");
        JSONObject mainMenu = (JSONObject) menus.get(0);
        JSONArray menuSection = (JSONArray) mainMenu.get("menu_sections");

        JSONObject b = new JSONObject();


        for(int i=0;i< menuSection.size();i++) {
            JSONObject a = (JSONObject) menuSection.get(i);
            if(section.equals(a.get("section_name").toString())) {
                b = a;
            }
        }

        JSONObject c = new JSONObject();
        JSONArray itemList = (JSONArray) b.get("menu_items");
        int existed = 0;

        for(int i=0;i< itemList.size();i++) {
            JSONObject a = (JSONObject) itemList.get(i);
            if(name.equals(a.get("name").toString())) {
                c = a;
                existed =1;
            }
        }

        if(existed == 1) {

            for(int j =0;j < Restaurant.MainMenu.size();j++){
                MenuSections s = (MenuSections) Restaurant.MainMenu.get(j);
                if(s.name.equals(section)){
                    Restaurant.MainMenu.get(j).RemoveItem(name);
                }
            }

            itemList.remove(c);

            try (FileWriter file = new FileWriter("menu.json")) {
                file.write(menu.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            System.out.println("The item is not existed in Menu");
        }

    }
}
