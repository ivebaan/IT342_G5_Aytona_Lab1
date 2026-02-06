package com.aytona.userauthentication.service;

import com.aytona.userauthentication.dto.UserResponse;
import com.aytona.userauthentication.entity.User;
import com.aytona.userauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserResponse getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
