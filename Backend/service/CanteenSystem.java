package service;

import model.*;
import util.MenuLoader;
import java.util.*;

public class CanteenSystem {

    private ArrayList<FoodItem> menu;
    private Queue<Order> queue;

    public CanteenSystem() {
        menu = MenuLoader.loadMenu();
        queue = new LinkedList<>();
    }

    public void showMenu() {
        for (FoodItem f : menu) {
            System.out.println(f);
        }
    }

    public FoodItem getItem(int id) {
        for (FoodItem f : menu) {
            if (f.getId() == id)
                return f;
        }
        return null;
    }

    public void placeOrder(Order order) {
        order.setStatus("Placed");
        queue.add(order);
        System.out.println("Token Generated: " + order.getToken());
    }

    public Order serveOrder() {
        if (queue.isEmpty()) {
            System.out.println("No orders.");
            return null;
        }

        Order o = queue.poll();
        o.setStatus("Completed");
        return o;
    }

    public void showQueue() {
        for (Order o : queue) {
            System.out.println("Token: " + o.getToken());
        }
    }
}
