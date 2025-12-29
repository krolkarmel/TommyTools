package com.tommytools.auth;

import com.tommytools.cart.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("cart")
public class AuthController {

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session) {
        // DEMO: brak weryfikacji – zapisujemy tylko email w sesji
        session.setAttribute("currentUser", email);
        return "redirect:/products";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             HttpSession session,
                             Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Hasła nie są takie same.");
            model.addAttribute("email", email);
            return "register";
        }

        // DEMO: brak zapisu do bazy – od razu „logujemy”
        session.setAttribute("currentUser", email);
        return "redirect:/products";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/products";
    }
}
