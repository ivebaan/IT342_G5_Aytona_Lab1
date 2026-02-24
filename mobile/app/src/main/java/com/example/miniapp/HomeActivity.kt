package com.example.miniapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Simple home screen shown after a successful login.
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Attach the home layout
        setContentView(R.layout.activity_home)

        // Get views using findViewById
        val tvWelcome: TextView = findViewById(R.id.tvWelcome)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        // Get the full name passed from LoginActivity (if available)
        val fullName = intent.getStringExtra("full_name") ?: "User"
        tvWelcome.text = "Welcome, $fullName!"

        // Simple logout: go back to the Login screen
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // Clear the back stack so HomeActivity is removed
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
