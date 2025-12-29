package com.tommytools.cart;

import com.tommytools.product.Product;

import java.math.BigDecimal;

public class CartLine {
    private final Product product;
    private final int qty;
    private final BigDecimal lineTotal;

    public CartLine(Product product, int qty, BigDecimal lineTotal) {
        this.product = product;
        this.qty = qty;
        this.lineTotal = lineTotal;
    }

    public Product getProduct() { return product; }
    public int getQty() { return qty; }
    public BigDecimal getLineTotal() { return lineTotal; }
}
