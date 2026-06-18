package backend.services;

import backend.models.CartItem;
import backend.models.MenuItem;

import java.util.Collection;
import java.util.LinkedHashMap;

public class CartService {

    private final LinkedHashMap<String, CartItem> cartMap = new LinkedHashMap<>();

    public void addItem(MenuItem item) {
        if (cartMap.containsKey(item.getName())) {
            cartMap.get(item.getName()).increment();
        } else {
            cartMap.put(item.getName(), new CartItem(item));
        }
    }

    public void removeItem(String name) {
        CartItem ci = cartMap.get(name);
        if (ci != null) {
            ci.decrement();
            if (ci.getQuantity() <= 0) {
                cartMap.remove(name);
            }
        }
    }

    public Collection<CartItem> getCartItems() {
        return cartMap.values();
    }

    public double getTotal() {
        return cartMap.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public boolean isEmpty() {
        return cartMap.isEmpty();
    }

    public void clearCart() {
        cartMap.clear();
    }
}
