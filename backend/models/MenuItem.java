package backend.models;

import java.awt.Color;

/**
 * Represents a single food/drink item on the canteen menu.
 */
public class MenuItem {
    private final int id;
    private final String name;
    private final double price;
    private final String category;
    private final Color iconBg;
    private final String drawKey;

    public MenuItem(int id, String name, double price, String category, Color iconBg, String drawKey) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.iconBg = iconBg;
        this.drawKey = drawKey;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public Color getIconBg() { return iconBg; }
    public String getDrawKey() { return drawKey; }

    @Override
    public String toString() {
        return id + ". " + name + " (" + category + ") : ₹" + (int) price;
    }
}
