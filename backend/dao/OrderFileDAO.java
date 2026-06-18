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

public class OrderFileDAO {

    private static final String FILE_PATH = "orders.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
            System.err.println("Error saving order: " + e.getMessage());
        }
    }

    public int getNextToken() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) return 1;

        int maxToken = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Token: ")) {
                    try {
                        int t = Integer.parseInt(line.substring(7).trim());
                        if (t > maxToken) maxToken = t;
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read token counter: " + e.getMessage());
        }
        return maxToken + 1;
    }

    public List<OrderRecord> loadOrders() {
        List<OrderRecord> records = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) return records;

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
                    if (inOrder) {
                        records.add(new OrderRecord(token, dateTime,
                                items.toString().trim(), itemCount, total, payment, status));
                    }
                    token = 0; dateTime = ""; items = new StringBuilder();
                    itemCount = 0; total = 0; payment = ""; status = "";
                    inOrder = true; inItems = false;
                } else if (line.startsWith("Token: ")) {
                    try { token = Integer.parseInt(line.substring(7).trim()); }
                    catch (NumberFormatException ignored) {}
                    inItems = false;
                } else if (line.startsWith("Date: ")) {
                    dateTime = line.substring(6).trim();
                    inItems = false;
                } else if (line.equals("Items:")) {
                    inItems = true;
                } else if (line.startsWith("- ") && inItems) {
                    itemCount++;
                    if (items.length() > 0) items.append(", ");
                    items.append(line.substring(2).trim());
                } else if (line.startsWith("Payment: ")) {
                    payment = line.substring(9).trim();
                    inItems = false;
                } else if (line.startsWith("Total: ")) {
                    try { total = Integer.parseInt(line.substring(7).trim().replace("₹", "").replace(",", "")); }
                    catch (NumberFormatException ignored) {}
                    inItems = false;
                } else if (line.startsWith("Status: ")) {
                    status = line.substring(8).trim();
                    inItems = false;
                }
            }

            if (inOrder) {
                records.add(new OrderRecord(token, dateTime,
                        items.toString().trim(), itemCount, total, payment, status));
            }

        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
        }

        Collections.reverse(records);
        return records;
    }
}
