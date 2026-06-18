package backend.services;

import backend.models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryService {

    private final List<Order> history = new ArrayList<>();

    public void addOrder(Order order) {
        history.add(order);
    }

    public List<Order> getHistory() {
        return Collections.unmodifiableList(history);
    }
}
