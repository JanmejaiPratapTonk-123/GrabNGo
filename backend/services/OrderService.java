package backend.services;

import backend.models.CartItem;
import backend.models.Order;

import java.util.Collection;

/**
 * Handles order creation and token generation.
 */
public class OrderService {

    private int tokenCounter = 100;

    /**
     * Creates a new order from the current cart contents.
     *
     * @param cartItems     the items being ordered
     * @param total         the order total
     * @param paymentMethod "Cash" or "UPI"
     * @return the newly created Order with a unique token
     */
    public Order createOrder(Collection<CartItem> cartItems, double total, String paymentMethod) {
        int token = tokenCounter++;
        return new Order(token, cartItems, total, paymentMethod);
    }
}
