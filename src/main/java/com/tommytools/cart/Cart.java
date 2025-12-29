package com.tommytools.cart;

import com.tommytools.product.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<Long, Integer> items = new LinkedHashMap<>();

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void add(Product p) {
        items.put(p.getId(), items.getOrDefault(p.getId(), 0) + 1);
    }

    public void removeOne(Long productId) {
        Integer qty = items.get(productId);
        if (qty == null) return;
        if (qty <= 1) items.remove(productId);
        else items.put(productId, qty - 1);
    }

    public void clear() {
        items.clear();
    }

    public int getTotalItems() {
        int sum = 0;
        for (Integer qty : items.values()) {
            sum += qty;
        }
        return sum;
    }

    public void removeAll(Long productId) {
        items.remove(productId);
    }
}
