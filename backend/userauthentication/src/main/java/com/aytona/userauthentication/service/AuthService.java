package com.aytona.userauthentication.service;

import com.aytona.userauthentication.dto.AuthResponse;
import com.aytona.userauthentication.dto.LoginRequest;
import com.aytona.userauthentication.dto.RegisterRequest;
import com.aytona.userauthentication.entity.User;
import com.aytona.userauthentication.repository.UserRepository;
import com.aytona.userauthentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public AuthResponse registerUser(RegisterRequest request) {
        // Check if email exists (as per diagram)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        userRepository.save(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        
        return new AuthResponse(token, user.getUsername(), user.getEmail(), "User registered successfully");
    }
    
    public AuthResponse authenticateUser(LoginRequest request) {
        // Find user by email (as per diagram)
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        
        return new AuthResponse(token, user.getUsername(), user.getEmail(), "Login successful");
    }
    
    public String logoutUser() {
        // In JWT stateless authentication, logout is handled client-side
        // by removing the token. This endpoint provides confirmation.
        return "Logout successful";
    }
}
