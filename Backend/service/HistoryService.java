package service;

import model.Order;
import java.util.*;

public class HistoryService {
    private ArrayList<Order> history = new ArrayList<>();

    public void add(Order order) {
        history.add(order);
    }

    public void showHistory() {
        System.out.println("\n--- ORDER HISTORY ---");
        for (Order o : history) {
            System.out.println("Token: " + o.getToken() + " Completed");
        }
    }
}
