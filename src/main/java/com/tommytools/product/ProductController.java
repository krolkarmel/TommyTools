package com.tommytools.product;

import com.tommytools.cart.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@SessionAttributes("cart")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("products", productService.list(q));
        model.addAttribute("q", q == null ? "" : q);
        return "products";
    }

    @PostMapping("/{id}/add")
    public String addToCart(@PathVariable Long id,
                            @RequestParam(required = false) String q,
                            @ModelAttribute("cart") Cart cart) {
        cart.add(productService.get(id));
        return "redirect:/products" + (q != null && !q.isBlank() ? "?q=" + q : "");
    }

    @PostMapping("/cart/addOne/{id}")
    public String addOneFromCart(@PathVariable Long id, @ModelAttribute("cart") com.tommytools.cart.Cart cart) {
        cart.add(productService.get(id));
        return "redirect:/products/cart";
    }


    @GetMapping("/cart")
    public String cartView(@ModelAttribute("cart") com.tommytools.cart.Cart cart, Model model) {

        var lines = cart.getItems().entrySet().stream().map(e -> {
            var product = productService.get(e.getKey());
            int qty = e.getValue();
            var lineTotal = product.getPrice().multiply(java.math.BigDecimal.valueOf(qty));
            return new com.tommytools.cart.CartLine(product, qty, lineTotal);
        }).toList();

        var total = lines.stream()
                .map(com.tommytools.cart.CartLine::getLineTotal)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        model.addAttribute("lines", lines);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/removeOne/{id}")
    public String removeOne(@PathVariable Long id, @ModelAttribute("cart") Cart cart) {
        cart.removeOne(id);
        return "redirect:/products/cart";
    }

    @PostMapping("/cart/removeAll/{id}")
    public String removeAll(@PathVariable Long id, @ModelAttribute("cart") com.tommytools.cart.Cart cart) {
        cart.removeAll(id);
        return "redirect:/products/cart";
    }

}
