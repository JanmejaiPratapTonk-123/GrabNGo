package backend.dao;

import backend.models.CartItem;
import backend.models.Order;
import backend.models.OrderRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * Reads all orders from orders.txt and parses them into OrderRecord objects.
     * Returns newest-first. Handles missing file and malformed entries gracefully.
     */
    public List<OrderRecord> loadOrders() {
        List<OrderRecord> records = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            return records;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int token = 0;
            String dateTime = "";
            StringBuilder items = new StringBuilder();
            int itemCount = 0;
            int total = 0;
            String payment = "";
            String status = "";
            boolean inOrder = false;
            boolean inItems = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equals("--- ORDER ---")) {
                    // Save previous order if exists
                    if (inOrder) {
                        records.add(new OrderRecord(token, dateTime,
                                items.toString().trim(), itemCount, total, payment, status));
                    }
                    // Reset for new order
                    token = 0;
                    dateTime = "";
                    items = new StringBuilder();
                    itemCount = 0;
                    total = 0;
                    payment = "";
                    status = "";
                    inOrder = true;
                    inItems = false;
                } else if (line.startsWith("Token: ")) {
                    try {
                        token = Integer.parseInt(line.substring(7).trim());
                    } catch (NumberFormatException ignored) {}
                    inItems = false;
                } else if (line.startsWith("Date: ")) {
                    dateTime = line.substring(6).trim();
                    inItems = false;
                } else if (line.equals("Items:")) {
                    inItems = true;
                } else if (line.startsWith("- ") && inItems) {
                    itemCount++;
                    if (items.length() > 0) items.append(", ");
                    // Extract just the name from "- Burger x3 (₹240)"
                    String entry = line.substring(2).trim();
                    items.append(entry);
                } else if (line.startsWith("Payment: ")) {
                    payment = line.substring(9).trim();
                    inItems = false;
                } else if (line.startsWith("Total: ")) {
                    String val = line.substring(7).trim().replace("₹", "").replace(",", "");
                    try {
                        total = Integer.parseInt(val);
                    } catch (NumberFormatException ignored) {}
                    inItems = false;
                } else if (line.startsWith("Status: ")) {
                    status = line.substring(8).trim();
                    inItems = false;
                }
            }

            // Don't forget the last order in the file
            if (inOrder) {
                records.add(new OrderRecord(token, dateTime,
                        items.toString().trim(), itemCount, total, payment, status));
            }

        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
        }

        // Newest first
        Collections.reverse(records);
        return records;
    }
}

