package com.example.expensestracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.MainActivity
import com.example.expensestracker.databinding.ActivityRegisterBinding
import com.example.expensestracker.network.AuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInput(email, password)) {
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                AuthManager.signUp(email, password)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isBlank()) {
            binding.emailEditText.error = "Email requerido"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Email inválido"
            return false
        }
        if (password.length < 6) {
            binding.passwordEditText.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }
        return true
    }
}
