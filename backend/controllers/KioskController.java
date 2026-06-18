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
        this.orderDAO = new OrderFileDAO();
        this.orderService = new OrderService(orderDAO.getNextToken());
        this.paymentService = new PaymentService();
        this.salesService = new SalesService();
        this.historyService = new HistoryService();
    }


    public List<MenuItem> getMenu(String category) {
        return menuService.getByCategory(category);
    }

    public MenuItem getMenuItem(int id) {
        return menuService.getItemById(id);
    }


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


    public Order checkout(String paymentMethod) {
        paymentService.validateMethod(paymentMethod);

        Collection<CartItem> items = cartService.getCartItems();
        double total = cartService.getTotal();

        Order order = orderService.createOrder(items, total, paymentMethod);
        
        paymentService.processPayment(paymentMethod, total);
        order.setStatus("Completed");

        salesService.addSale(total);
        historyService.addOrder(order);

        orderDAO.saveOrder(order);

        cartService.clearCart();

        return order;
    }


    public double getTotalSales() {
        return salesService.getTotalSales();
    }

    public List<Order> getOrderHistory() {
        return historyService.getHistory();
    }

    public List<OrderRecord> loadOrderHistory() {
        return orderDAO.loadOrders();
    }
}
