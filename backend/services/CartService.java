package backend.services;

import backend.models.CartItem;
import backend.models.MenuItem;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Manages the shopping cart.
 * Pure business logic — no Swing/UI references.
 */
public class CartService {

    private final LinkedHashMap<String, CartItem> cartMap = new LinkedHashMap<>();

    /**
     * Adds one unit of the given item to the cart.
     * If the item already exists, increments its quantity.
     */
    public void addItem(MenuItem item) {
        if (cartMap.containsKey(item.getName())) {
            cartMap.get(item.getName()).increment();
        } else {
            cartMap.put(item.getName(), new CartItem(item));
        }
    }

    /**
     * Removes one unit of the named item from the cart.
     * If quantity reaches zero, removes the entry entirely.
     */
    public void removeItem(String name) {
        CartItem ci = cartMap.get(name);
        if (ci != null) {
            ci.decrement();
            if (ci.getQuantity() <= 0) {
                cartMap.remove(name);
            }
        }
    }

    /**
     * Returns all cart items.
     */
    public Collection<CartItem> getCartItems() {
        return cartMap.values();
    }

    /**
     * Calculates the current cart total.
     */
    public double getTotal() {
        return cartMap.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    /**
     * Checks if the cart is empty.
     */
    public boolean isEmpty() {
        return cartMap.isEmpty();
    }

    /**
     * Clears all items from the cart.
     */
    public void clearCart() {
        cartMap.clear();
    }
}
