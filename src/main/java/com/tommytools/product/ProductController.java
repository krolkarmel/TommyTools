package com.tommytools.product;

import com.tommytools.cart.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String products(@RequestParam(required = false) String q,
                           @SessionAttribute(value = "cart", required = false) Cart cart,
                           Model model) {
        model.addAttribute("q", q);
        model.addAttribute("products", productService.list(q));

        if (cart != null) {
            model.addAttribute("cart", cart); // <- to naprawia licznik na /products
        }

        return "products";
    }
}
