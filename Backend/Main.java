import service.*;
import model.*;
import java.util.*;
import java.io.*;

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);

        CanteenSystem system = new CanteenSystem();
        OrderService orderService = new OrderService();
        SalesService sales = new SalesService();
        HistoryService history = new HistoryService();

        while(true) 
        {
            System.out.println("\n1 Place Order  2 Serve  3 Queue  4 Sales  5 History  6 Exit");
            int ch = input.nextInt();
            input.nextLine();

            if(ch == 1) 
            {
                System.out.print("Enter Name: ");
                String name = input.nextLine();

                Order order = orderService.createOrder(name);

                while(true) 
                {
                    system.showMenu();
                    System.out.println("1 Add  2 Remove  3 View Cart  4 Checkout");

                    int op = input.nextInt();

                    if(op == 1) 
                    {
                        int id = input.nextInt();
                        FoodItem item = system.getItem(id);
                        if(item != null)
                            order.addItem(item);
                    }
                    else if(op == 2)
                        order.removeLastItem();
                    else if(op == 3)
                        order.showCart();
                    else 
                        break;
                }

                System.out.println("Payment: 1 Cash  2 UPI");
                int pay = input.nextInt();
                String paymentMode = PaymentService.process(pay);
                order.setPaymentMode(paymentMode);
                if(paymentMode.equals("UPI"))
                    System.out.println("Please scan the QR code to complete payment.");
                try 
                {
                    FileWriter fw = new FileWriter("orders.txt", true);
                    fw.write("--- ORDER ---\n");
                    fw.write("Token: " + order.getToken() + "\n");
                    fw.write("Name: " + name + "\n");
                    fw.write("Items:\n");
                    for (FoodItem f : order.getCart()) {
                        fw.write("- " + f.getName() + "\n");
                    }
                    fw.write("Payment: " + paymentMode + "\n");
                    fw.write("Total: " + order.getTotal() + " Rs\n");
                    fw.write("Status: " + order.getStatus() + "\n\n");
                    fw.close();
                } catch (IOException e) {
                    System.out.println("Error saving order details.");
                }

                system.placeOrder(order);

            } 
            else if (ch == 2) {
                Order o = system.serveOrder();
                if (o != null) {
                    o.printBill();
                    sales.addSale(o.getTotal());
                    history.add(o);
                }

            } else if (ch == 3) {
                system.showQueue();

            } else if (ch == 4) {
                sales.showSales();

            } else if (ch == 5) {
                history.showHistory();

            } else {
                break;
            }
        }
        sc.close();
    }
}