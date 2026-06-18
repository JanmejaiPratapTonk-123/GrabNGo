package backend.services;

import backend.models.CartItem;
import backend.models.Order;

import java.util.Collection;

public class OrderService {

    private int tokenCounter;

    public OrderService(int startToken) {
        this.tokenCounter = startToken;
    }

    public Order createOrder(Collection<CartItem> cartItems, double total, String paymentMethod) {
        return new Order(tokenCounter++, cartItems, total, paymentMethod);
    }
}

