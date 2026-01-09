package com.tommytools.cart;

import com.tommytools.product.Product;
import com.tommytools.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("cart")
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping("/cart")
    public String cartPage(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("id") Long productId,
                            @ModelAttribute("cart") Cart cart) {
        Product p = productService.get(productId);
        cart.add(p);
        return "redirect:/products";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("id") Long productId,
                                 @ModelAttribute("cart") Cart cart) {
        cart.remove(productId);
        return "redirect:/cart";
    }
}
