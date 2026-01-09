package com.tommytools.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductRepository repo;

    public AdminProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // LISTA
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", repo.findAll());
        return "admin/products";
    }

    // FORMULARZ: NOWY
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("formTitle", "Dodaj produkt");
        model.addAttribute("actionUrl", "/admin/products");
        return "admin/product_form";
    }

    // CREATE
    @PostMapping
    public String create(@ModelAttribute("product") Product product,
                         RedirectAttributes ra) {
        repo.save(product);
        ra.addFlashAttribute("msg", "Dodano produkt: " + product.getName());
        return "redirect:/admin/products";
    }

    // FORMULARZ: EDYCJA
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        model.addAttribute("product", product);
        model.addAttribute("formTitle", "Edytuj produkt");
        model.addAttribute("actionUrl", "/admin/products/" + id);
        return "admin/product_form";
    }

    // UPDATE (POST, bo HTML form)
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("product") Product form,
                         RedirectAttributes ra) {

        Product p = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        p.setName(form.getName());
        p.setPrice(form.getPrice());
        p.setImageUrl(form.getImageUrl());

        repo.save(p);
        ra.addFlashAttribute("msg", "Zapisano zmiany: " + p.getName());
        return "redirect:/admin/products";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addFlashAttribute("msg", "Usunięto produkt (id=" + id + ")");
        return "redirect:/admin/products";
    }
}
