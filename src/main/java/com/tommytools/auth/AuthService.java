package com.tommytools.auth;

import com.tommytools.security.AppUser;
import com.tommytools.security.Role;
import com.tommytools.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String password) {
        String u = username == null ? "" : username.trim();

        if (u.isEmpty()) {
            throw new IllegalArgumentException("Login nie może być pusty");
        }
        if (password == null || password.trim().length() < 4) {
            throw new IllegalArgumentException("Hasło musi mieć min. 4 znaki");
        }

        if (userRepository.existsByUsername(u)) {
            throw new IllegalArgumentException("Taki użytkownik już istnieje");
        }

        AppUser user = new AppUser(u, passwordEncoder.encode(password), Role.USER);
        userRepository.save(user);
    }
}
