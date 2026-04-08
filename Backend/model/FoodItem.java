package model;

public class FoodItem {
    private int id;
    private String name;
    private double price;
    private String category;

    public FoodItem(int id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    public String toString() {
        return id + ". " + name + " (" + category + ") : " + price + " Rs";
    }
}
