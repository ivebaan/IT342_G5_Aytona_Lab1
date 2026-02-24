package com.example.miniapp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.miniapp.databinding.FragmentDashboardBinding

/**
 * Dashboard screen that shows the logged-in user's information
 * (full name and email) stored in SharedPreferences.
 */
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_FULL_NAME = "full_name"
        private const val KEY_EMAIL = "email"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Read user information from the same SharedPreferences
        // used in RegisterActivity and LoginActivity.
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val fullName = prefs.getString(KEY_FULL_NAME, "User") ?: "User"
        val email = prefs.getString(KEY_EMAIL, "-") ?: "-"

        // Set values to the UI elements in the dashboard fragment
        binding.tvWelcome.text = "Welcome, $fullName!"
        binding.tvUsername.text = fullName
        binding.tvEmail.text = email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}