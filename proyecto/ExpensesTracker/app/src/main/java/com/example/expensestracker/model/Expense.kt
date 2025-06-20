package com.example.expensestracker.model

data class Expense(
    val id: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val date: String = "",
    val category: String = "",
    val userId: String = "",
    val createdAt: String = "",
    val timestamp: Long = 0
)