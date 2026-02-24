package com.example.miniapp.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit interface for calling the Spring Boot auth endpoints.
 */
interface ApiService {

    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>
}
