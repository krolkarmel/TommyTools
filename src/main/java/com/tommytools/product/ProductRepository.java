package com.tommytools.product;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {
    private final List<Product> data = new ArrayList<>(List.of(
            new Product(1L, "Młotek", new BigDecimal("29.99")),
            new Product(2L, "Wkrętarka", new BigDecimal("199.00")),
            new Product(3L, "Wiertło 10mm", new BigDecimal("9.50")),
            new Product(4L, "Taśma miernicza", new BigDecimal("14.99"))
    ));

    public List<Product> findAll() {
        return data;
    }

    public List<Product> findByNameContainingIgnoreCase(String q) {
        String qq = q.toLowerCase();
        return data.stream()
                .filter(p -> p.getName().toLowerCase().contains(qq))
                .collect(Collectors.toList());
    }

    public Product findById(Long id) {
        return data.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }
}
