package com.aytona.userauthentication.controller;

import com.aytona.userauthentication.dto.AuthResponse;
import com.aytona.userauthentication.dto.LoginRequest;
import com.aytona.userauthentication.dto.RegisterRequest;
import com.aytona.userauthentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AuthController {
    
    
}
