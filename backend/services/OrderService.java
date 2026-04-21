package backend.services;

import backend.models.CartItem;
import backend.models.Order;

import java.util.Collection;

/**
 * Handles order creation and token generation.
 */
public class OrderService {

    private int tokenCounter;

    /**
     * @param startToken the first token to issue (read from persistent storage)
     */
    public OrderService(int startToken) {
        this.tokenCounter = startToken;
    }

    /**
     * Creates a new order from the current cart contents.
     */
    public Order createOrder(Collection<CartItem> cartItems, double total, String paymentMethod) {
        return new Order(tokenCounter++, cartItems, total, paymentMethod);
    }
}

