package model;

import java.util.*;

public class Order {
    private int token;
    private String name;
    private ArrayList<FoodItem> cart;
    private double total;
    private String status;
    private String paymentMode;

    public Order(int token, String name) {
        this.token = token;
        this.name = name;
        this.cart = new ArrayList<>();
        this.total = 0;
        this.status = "Cart";
    }

    public void addItem(FoodItem item) {
        cart.add(item);
        total += item.getPrice();
    }

    public void removeLastItem() {
        if (!cart.isEmpty()) {
            FoodItem item = cart.remove(cart.size() - 1);
            total -= item.getPrice();
        }
    }

    public void showCart() {
        System.out.println("\n--- CART ---");
        for (FoodItem f : cart) {
            System.out.println("- " + f.getName());
        }
        System.out.println("Total: " + total + " Rs");
    }

    public int getToken() { return token; }
    public double getTotal() { return total; }
    public ArrayList<FoodItem> getCart() { return cart; }
    public String getStatus() { return status; }

    public void setPaymentMode(String mode) {
        this.paymentMode = mode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void printBill() {
        System.out.println("\n--- BILL ---");
        System.out.println("Token: " + token);
        System.out.println("Name: " + name);

        for (FoodItem f : cart) {
            System.out.println("- " + f.getName());
        }

        System.out.println("Payment: " + paymentMode);
        System.out.println("Total: " + total + " Rs");
        System.out.println("Status: " + status);
    }
}
