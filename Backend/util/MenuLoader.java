package util;

import model.FoodItem;
import java.util.*;

public class MenuLoader {

    public static ArrayList<FoodItem> loadMenu() {
        ArrayList<FoodItem> menu = new ArrayList<>();

        menu.add(new FoodItem(1, "Burger", 50, "Snacks"));
        menu.add(new FoodItem(2, "Pizza", 100, "Snacks"));
        menu.add(new FoodItem(3, "Sandwich", 40, "Snacks"));
        menu.add(new FoodItem(4, "Cold Drink", 30, "Drinks"));
        menu.add(new FoodItem(5, "Maggi", 35, "Snacks"));
        menu.add(new FoodItem(6, "Coffee", 25, "Drinks"));

        return menu;
    }
}
