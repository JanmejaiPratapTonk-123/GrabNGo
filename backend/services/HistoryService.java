package backend.services;

import backend.models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Maintains a log of all completed orders.
 */
public class HistoryService {

    private final List<Order> history = new ArrayList<>();

    /**
     * Adds a completed order to history.
     */
    public void addOrder(Order order) {
        history.add(order);
    }

    /**
     * Returns an unmodifiable view of the order history.
     */
    public List<Order> getHistory() {
        return Collections.unmodifiableList(history);
    }
}
