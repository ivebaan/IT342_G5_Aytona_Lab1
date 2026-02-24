package com.example.miniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniapp.network.ApiClient
import com.example.miniapp.network.AuthResponse
import com.example.miniapp.network.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Screen for logging in an existing user.
 * Validates credentials against SharedPreferences data.
 */
class LoginActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password" // kept only for compatibility
        private const val KEY_TOKEN = "token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Attach the login layout
        setContentView(R.layout.activity_login)

        // Get references to views using findViewById
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnSignUp: Button = findViewById(R.id.btnSignUp)

        // Navigate to RegisterActivity if user taps "Create Account"
        btnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Handle login when the Login button is clicked
        btnLogin.setOnClickListener {
            val inputEmail = etEmail.text.toString().trim()
            val inputPassword = etPassword.text.toString().trim()

            // Check for empty fields
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call backend /api/auth/login to validate credentials
            val request = LoginRequest(
                email = inputEmail,
                password = inputPassword
            )

            ApiClient.apiService.login(request).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!

                        // Save user info and token from backend
                        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                        prefs.edit()
                            .putString(KEY_FULL_NAME, body.username)
                            .putString(KEY_EMAIL, body.email)
                            .putString(KEY_TOKEN, body.token)
                            // Optionally keep the last used password locally
                            .putString(KEY_PASSWORD, inputPassword)
                            .apply()

                        Toast.makeText(
                            this@LoginActivity,
                            body.message.ifEmpty { "Login Successful" },
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate to MainActivity (which hosts the fragments)
                        // and clear the back stack so user cannot go back to Login.
                        val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                            putExtra("full_name", body.username)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                    } else {
                        // Backend rejected credentials or other error
                        val errorMsg = response.errorBody()?.string()
                            ?: "Invalid Credentials"
                        Toast.makeText(
                            this@LoginActivity,
                            errorMsg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Network error: ${t.localizedMessage ?: "Please try again"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}
