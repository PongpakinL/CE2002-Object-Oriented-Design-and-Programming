package entities;

import java.util.ArrayList;

/**
 * This class represents a section of the Menu, within which similar food Items are grouped together.
 * e.g Beverages, etc.
 * @author Truong Vinh Khai
 */
public class MenuSections {
    /**
     * The name of this Menu Section
     */
    public static String name;
    /**
     * ArrayList that contains the food Items that are a part of this Menu Section
     */
    public static ArrayList<Item> itemList;

    /**
     * Adds a food Item to this Menu Section's ArrayList.
     * @param addInItem food Item to be added
     */
    public static void AddInItemList(Item addInItem){
        itemList.add(addInItem);
    }

    /**
     * Changes the name of this Menu Section.
     * @param sectionName the new name of this Menu Section
     */
    public static void SetSectionName(String sectionName){
        name = sectionName;
    }

    /**
     * Remove the food Item of the given name from the ArrayList of this Menu Section
     * @param removeItem name of food Item to be removed.
     */
    public static void RemoveItem(String removeItem){
        for(int i=0;i<itemList.size();i++){
            if(itemList.get(i).name == removeItem){
                itemList.remove(i);
            }
        }
    }
}
