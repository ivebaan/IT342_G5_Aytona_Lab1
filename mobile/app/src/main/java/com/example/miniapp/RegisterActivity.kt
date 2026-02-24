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
import com.example.miniapp.network.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Screen for creating a new account.
 * Uses SharedPreferences to store basic user data.
 */
class RegisterActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password" // kept for compatibility if needed
        private const val KEY_TOKEN = "token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Attach the register layout
        setContentView(R.layout.activity_register)

        // Get references to views using findViewById
        val etFullName: EditText = findViewById(R.id.etFullName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val etConfirmPassword: EditText = findViewById(R.id.etConfirmPassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnSignIn: Button = findViewById(R.id.btnSignIn)

        // Go to Login screen directly if user taps "Sign In"
        btnSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Handle registration when the Register button is clicked
        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // Check if any field is empty
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Basic confirmation check: passwords must match
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // All fields are valid -> call backend /api/auth/register
            val request = RegisterRequest(
                username = fullName,
                email = email,
                password = password
            )

            ApiClient.apiService.register(request).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!

                        // Save returned user data + token locally for later use
                        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                        prefs.edit()
                            .putString(KEY_FULL_NAME, body.username)
                            .putString(KEY_EMAIL, body.email)
                            .putString(KEY_TOKEN, body.token)
                            // Optionally keep plain password if you still want it locally
                            .putString(KEY_PASSWORD, password)
                            .apply()

                        Toast.makeText(
                            this@RegisterActivity,
                            body.message.ifEmpty { "Registration Successful" },
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate to LoginActivity after successful registration
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Backend returned an error (e.g., email already exists)
                        val errorMsg = response.errorBody()?.string()
                            ?: "Registration failed"
                        Toast.makeText(
                            this@RegisterActivity,
                            errorMsg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Network error: ${t.localizedMessage ?: "Please try again"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}
