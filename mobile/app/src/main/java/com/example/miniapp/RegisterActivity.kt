package com.example.miniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Screen for creating a new account.
 * Uses SharedPreferences to store basic user data.
 */
class RegisterActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
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

            // All fields are valid -> save to SharedPreferences
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit()
                .putString(KEY_FULL_NAME, fullName)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .apply()

            // Show success message
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

            // Navigate to LoginActivity after successful registration
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Optional: close this screen so user can't navigate back with Back button
            finish()
        }
    }
}
