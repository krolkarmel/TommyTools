package com.tommytools;

import com.tommytools.product.Product;
import com.tommytools.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Product("Młotek", new BigDecimal("29.99")));
                repo.save(new Product("Wkrętarka", new BigDecimal("199.00")));
                repo.save(new Product("Wiertło 10mm", new BigDecimal("9.50")));
                repo.save(new Product("Taśma miernicza", new BigDecimal("14.99")));
            }
        };
    }
}
