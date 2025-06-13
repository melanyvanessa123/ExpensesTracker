package com.example.expensestracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.balanceTextView.text = "$0.00 USD"

        binding.addExpenseButton.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        binding.viewHistoryButton.setOnClickListener {
            startActivity(Intent(this, ExpenseHistoryActivity::class.java))
        }

        binding.filterButton.setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }

        binding.categoryButton.setOnClickListener {
            startActivity(Intent(this, CategoryBreakdownActivity::class.java))
        }
    }
}
