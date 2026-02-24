package com.example.miniapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

/**
 * Landing page shown when the app starts.
 * From here the user can begin the registration/login flow.
 */
class LandingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Attach the landing page layout to this Activity
        setContentView(R.layout.activity_landing)

        // Get reference to the "Get Started" button using findViewById
        val btnGetStarted: Button = findViewById(R.id.btnGetStarted)

        // Navigate to the Register screen when the button is clicked
        btnGetStarted.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}