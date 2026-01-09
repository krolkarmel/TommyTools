package com.tommytools.cart;

import com.tommytools.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<CartLine> lines = new ArrayList<>();

    public List<CartLine> getLines() {
        return lines;
    }

    public void add(Product product) {
        if (product == null) return;

        for (CartLine line : lines) {
            if (line.getProduct().getId().equals(product.getId())) {
                line.setQuantity(line.getQuantity() + 1);
                return;
            }
        }
        lines.add(new CartLine(product, 1));
    }

    public void remove(Long productId) {
        if (productId == null) return;
        lines.removeIf(l -> l.getProduct() != null && productId.equals(l.getProduct().getId()));
    }

    public int getTotalItems() {
        return lines.stream().mapToInt(CartLine::getQuantity).sum();
    }

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartLine line : lines) {
            total = total.add(line.getLineTotal());
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public void clear() {
        lines.clear();
    }
}
