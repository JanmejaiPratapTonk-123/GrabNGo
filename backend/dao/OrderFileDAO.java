package backend.dao;

import backend.models.CartItem;
import backend.models.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Handles persistence of Order objects to the file system.
 */
public class OrderFileDAO {

    private static final String FILE_PATH = "orders.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Appends an order to the orders.txt file.
     */
    public void saveOrder(Order order) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            fw.write("--- ORDER ---\n");
            fw.write("Token: " + order.getToken() + "\n");
            fw.write("Date: " + order.getTimestamp().format(FORMATTER) + "\n");
            fw.write("Items:\n");
            for (CartItem ci : order.getItems()) {
                fw.write("- " + ci.getMenuItem().getName() + " x" + ci.getQuantity() + " (₹" + (int) ci.getSubtotal() + ")\n");
            }
            fw.write("Payment: " + order.getPaymentMethod() + "\n");
            fw.write("Total: ₹" + (int) order.getTotal() + "\n");
            fw.write("Status: " + order.getStatus() + "\n\n");
        } catch (IOException e) {
            System.err.println("Error saving order details to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
