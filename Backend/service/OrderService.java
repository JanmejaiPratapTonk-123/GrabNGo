package service;

import model.*;

public class OrderService {
    private int counter = 1;

    public Order createOrder(String name) {
        return new Order(counter++, name);
    }
}
