package backend.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Order {
    private final int token;
    private final List<CartItem> items;
    private final double total;
    private final String paymentMethod;
    private String status;
    private final LocalDateTime timestamp;

    public Order(int token, Collection<CartItem> items, double total, String paymentMethod) {
        this.token = token;
        this.items = new ArrayList<>(items);
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = "Placed";
        this.timestamp = LocalDateTime.now();
    }

    public int getToken() { return token; }
    public List<CartItem> getItems() { return Collections.unmodifiableList(items); }
    public double getTotal() { return total; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order #" + token + " | ₹" + (int) total + " | " + paymentMethod + " | " + status;
    }
}
