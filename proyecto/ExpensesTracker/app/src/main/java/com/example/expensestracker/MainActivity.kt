package com.example.expensestracker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.databinding.ActivityMainBinding
import com.example.expensestracker.ui.HomeActivity
import com.example.expensestracker.ui.RegisterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInput(email, password)) {
                signInWithEmail(email, password)
            }
        }

        binding.registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signInWithEmail(email: String, password: String) {
        try {
            Toast.makeText(
                this@MainActivity,
                "Welcome $email!",
                Toast.LENGTH_LONG
            ).show()


            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity,
                "Login failed: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailEditText.error = "Email is required"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Enter a valid email"
            return false
        }
        if (password.isEmpty()) {
            binding.passwordEditText.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            binding.passwordEditText.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }
}