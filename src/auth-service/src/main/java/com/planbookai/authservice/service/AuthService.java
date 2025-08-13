package com.planbookai.authservice.service;

import com.planbookai.authservice.dto.*;
import com.planbookai.authservice.entity.User;
import com.planbookai.authservice.repository.UserRepository;
import com.planbookai.authservice.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        User u = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .roles(req.getRoles())
                .build();
        userRepository.save(u);
        String token = tokenProvider.generateToken(u.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest req) {
        User u = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = tokenProvider.generateToken(u.getUsername());
        return new AuthResponse(token);
    }
}
