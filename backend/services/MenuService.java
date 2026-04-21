package backend.services;

import backend.models.MenuItem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides access to the canteen menu.
 * Centralises all menu data that was previously hardcoded in the UI.
 */
public class MenuService {

    private final List<MenuItem> menu;

    public MenuService() {
        menu = new ArrayList<>();
        menu.add(new MenuItem(1,  "Burger",   80,  "Snacks", new Color(255, 235, 180), "burger"));
        menu.add(new MenuItem(2,  "Pizza",    120, "Snacks", new Color(255, 225, 210), "pizza"));
        menu.add(new MenuItem(3,  "Sandwich", 60,  "Snacks", new Color(220, 245, 220), "sandwich"));
        menu.add(new MenuItem(4,  "Samosa",   20,  "Snacks", new Color(255, 240, 180), "samosa"));
        menu.add(new MenuItem(5,  "Fries",    50,  "Snacks", new Color(255, 245, 200), "fries"));
        menu.add(new MenuItem(6,  "Noodles",  70,  "Snacks", new Color(255, 235, 190), "noodles"));
        menu.add(new MenuItem(7,  "Chai",     15,  "Drinks", new Color(255, 230, 200), "chai"));
        menu.add(new MenuItem(8,  "Coffee",   30,  "Drinks", new Color(230, 215, 200), "coffee"));
        menu.add(new MenuItem(9,  "Juice",    40,  "Drinks", new Color(255, 240, 200), "juice"));
        menu.add(new MenuItem(10, "Lassi",    35,  "Drinks", new Color(255, 245, 230), "lassi"));
        menu.add(new MenuItem(11, "Soda",     25,  "Drinks", new Color(255, 220, 220), "soda"));
        menu.add(new MenuItem(12, "Water",    10,  "Drinks", new Color(220, 240, 255), "water"));
    }

    /**
     * Returns all menu items.
     */
    public List<MenuItem> getAllItems() {
        return Collections.unmodifiableList(menu);
    }

    /**
     * Returns menu items filtered by category.
     * If category is "All" or null, returns all items.
     */
    public List<MenuItem> getByCategory(String category) {
        if (category == null || category.equals("All")) {
            return getAllItems();
        }
        return menu.stream()
                .filter(item -> item.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * Looks up a menu item by its ID.
     */
    public MenuItem getItemById(int id) {
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
