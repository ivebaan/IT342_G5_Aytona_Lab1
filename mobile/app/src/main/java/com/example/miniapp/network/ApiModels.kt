package com.example.miniapp.network

/**
 * Data models matching the Spring Boot backend DTOs
 * used for authentication.
 */

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val username: String,
    val email: String,
    val message: String
)
