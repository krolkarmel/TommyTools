package com.tommytools.checkout;

import com.tommytools.cart.Cart;
import com.tommytools.cart.CartLine;
import com.tommytools.order.*;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@SessionAttributes("cart")
public class CheckoutController {

    private final OrderRepository orderRepository;

    public CheckoutController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/checkout")
    public String checkoutPage(@ModelAttribute("cart") Cart cart, Model model) {
        if (cart.getLines() == null || cart.getLines().isEmpty()) {
            return "redirect:/cart?empty";
        }
        model.addAttribute("form", new CheckoutForm());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@ModelAttribute("cart") Cart cart,
                             @Valid @ModelAttribute("form") CheckoutForm form,
                             BindingResult br,
                             RedirectAttributes ra) {

        if (cart.getLines() == null || cart.getLines().isEmpty()) {
            return "redirect:/cart?empty";
        }
        if (br.hasErrors()) {
            return "checkout";
        }

        String orderNo = "TT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        ShopOrder order = new ShopOrder();
        order.setOrderNo(orderNo);
        order.setStatus(OrderStatus.NEW);
        order.setTotalPrice(cart.getTotalPrice());

        order.setFullName(form.getFullName());
        order.setEmail(form.getEmail());
        order.setAddress(form.getAddress());
        order.setCity(form.getCity());
        order.setPostalCode(form.getPostalCode());
        order.setNotes(form.getNotes());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            order.setUsername(auth.getName());
        }

        for (CartLine line : cart.getLines()) {
            order.addItem(new OrderItem(
                    order,
                    line.getProduct().getName(),
                    line.getProduct().getPrice(),
                    line.getQuantity()
            ));
        }

        orderRepository.save(order);
        cart.clear();

        ra.addFlashAttribute("orderNo", orderNo);
        ra.addFlashAttribute("customerName", form.getFullName());
        return "redirect:/order/thanks";
    }

    @GetMapping("/order/thanks")
    public String thanks() {
        return "order_thanks";
    }
}
