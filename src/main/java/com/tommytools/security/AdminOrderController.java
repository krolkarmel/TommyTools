package com.tommytools.security;

import com.tommytools.order.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderRepository orderRepository;

    public AdminOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        ShopOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        model.addAttribute("o", order);
        model.addAttribute("statuses", OrderStatus.values());
        return "admin/order_details";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Long id,
                               @RequestParam("status") OrderStatus status,
                               RedirectAttributes ra) {
        ShopOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        order.setStatus(status);
        orderRepository.save(order);
        ra.addFlashAttribute("msg", "Zmieniono status zamówienia " + order.getOrderNo());
        return "redirect:/admin/orders/" + id;
    }
}
