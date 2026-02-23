package com.example.miniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Screen for logging in an existing user.
 * Validates credentials against SharedPreferences data.
 */
class LoginActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
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

            // Retrieve saved user data
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val savedEmail = prefs.getString(KEY_EMAIL, null)
            val savedPassword = prefs.getString(KEY_PASSWORD, null)
            val savedFullName = prefs.getString(KEY_FULL_NAME, "User")

            // If no user was registered yet
            if (savedEmail.isNullOrEmpty() || savedPassword.isNullOrEmpty()) {
                Toast.makeText(this, "No registered user found. Please register first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate credentials
            if (inputEmail == savedEmail && inputPassword == savedPassword) {
                // Successful login
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                // Navigate to HomeActivity and pass the user's name
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("full_name", savedFullName)
                }
                startActivity(intent)
                // Optional: close login so user cannot go back here with Back button
                finish()
            } else {
                // Invalid credentials
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
