package backend.controllers;

import backend.dao.OrderFileDAO;
import backend.models.CartItem;
import backend.models.MenuItem;
import backend.models.Order;
import backend.models.OrderRecord;
import backend.services.CartService;
import backend.services.HistoryService;
import backend.services.MenuService;
import backend.services.OrderService;
import backend.services.PaymentService;
import backend.services.SalesService;

import java.util.Collection;
import java.util.List;

/**
 * The single point of entry for the frontend to interact with the backend.
 * Orchestrates calls to the underlying services and DAOs.
 */
public class KioskController {

    private final MenuService menuService;
    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final SalesService salesService;
    private final HistoryService historyService;
    private final OrderFileDAO orderDAO;

    public KioskController() {
        this.menuService = new MenuService();
        this.cartService = new CartService();
        this.orderService = new OrderService();
        this.paymentService = new PaymentService();
        this.salesService = new SalesService();
        this.historyService = new HistoryService();
        this.orderDAO = new OrderFileDAO();
    }

    // --- Menu Operations ---

    public List<MenuItem> getMenu(String category) {
        return menuService.getByCategory(category);
    }

    public MenuItem getMenuItem(int id) {
        return menuService.getItemById(id);
    }

    // --- Cart Operations ---

    public void addToCart(MenuItem item) {
        cartService.addItem(item);
    }

    public void removeFromCart(String name) {
        cartService.removeItem(name);
    }

    public Collection<CartItem> getCartItems() {
        return cartService.getCartItems();
    }

    public double getCartTotal() {
        return cartService.getTotal();
    }

    public boolean isCartEmpty() {
        return cartService.isEmpty();
    }

    public void clearCart() {
        cartService.clearCart();
    }

    // --- Order & Checkout Operations ---

    /**
     * Completes the checkout process.
     * Validates payment, creates order, persists to file, tracks sales/history, and clears cart.
     *
     * @param paymentMethod "Cash" or "UPI"
     * @return The completed Order object
     */
    public Order checkout(String paymentMethod) {
        // Validate payment
        paymentService.validateMethod(paymentMethod);

        // Get cart details before clearing
        Collection<CartItem> items = cartService.getCartItems();
        double total = cartService.getTotal();

        // Create order
        Order order = orderService.createOrder(items, total, paymentMethod);
        
        // Process payment
        paymentService.processPayment(paymentMethod, total);
        order.setStatus("Completed");

        // Record metrics
        salesService.addSale(total);
        historyService.addOrder(order);

        // Save to file
        orderDAO.saveOrder(order);

        // Clear cart for next customer
        cartService.clearCart();

        return order;
    }

    // --- Admin/Reporting Operations ---

    public double getTotalSales() {
        return salesService.getTotalSales();
    }

    public List<Order> getOrderHistory() {
        return historyService.getHistory();
    }

    /**
     * Loads complete order history from the persistent orders.txt file.
     * Returns newest-first.
     */
    public List<OrderRecord> loadOrderHistory() {
        return orderDAO.loadOrders();
    }
}
