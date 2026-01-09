package com.tommytools.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> list(String q) {
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll();
        }
        return repo.findByNameContainingIgnoreCase(q.trim());
    }

    public Product get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }
}
