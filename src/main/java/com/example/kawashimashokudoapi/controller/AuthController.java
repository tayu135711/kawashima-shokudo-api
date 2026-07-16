package com.example.kawashimashokudoapi.controller;

import com.example.kawashimashokudoapi.dto.AuthResponse;
import com.example.kawashimashokudoapi.dto.LoginRequest;
import com.example.kawashimashokudoapi.dto.RegisterRequest;
import com.example.kawashimashokudoapi.entity.User;
import com.example.kawashimashokudoapi.repository.UserRepository;
import com.example.kawashimashokudoapi.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("このメールアドレスは既に登録されています");
        }

        var allowedRoles = java.util.Set.of("customer", "store", "courier");
        if (!allowedRoles.contains(req.getRole())) {
            return ResponseEntity.badRequest().body("指定できないroleです");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setRole(req.getRole());

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getName(), user.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("メールアドレスまたはパスワードが正しくありません");
        }

        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getName(), user.getRole()));
    }
}