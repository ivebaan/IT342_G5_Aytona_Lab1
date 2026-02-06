package com.aytona.userauthentication.controller;

import com.aytona.userauthentication.dto.UserResponse;
import com.aytona.userauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            UserResponse response = userService.getUserProfile(username);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
