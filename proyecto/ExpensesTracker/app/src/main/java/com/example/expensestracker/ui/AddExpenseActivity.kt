package com.example.expensestracker.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.R as AndroidR
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.databinding.ActivityAddExpenseBinding
import com.example.expensestracker.model.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private val calendar = Calendar.getInstance()
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val expensesCollection = db.collection("expenses")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val categories = arrayOf(
            "Alimentación",
            "Vestimenta",
            "Vivienda",
            "Educación",
            "Salud"
        )

        val arrayAdapter = ArrayAdapter(
            this,
            AndroidR.layout.simple_dropdown_item_1line,
            categories
        )
        binding.categoryAutoComplete.setAdapter(arrayAdapter)


        updateDateInView()
    }

    private fun setupListeners() {

        binding.dateEditText.setOnClickListener {
            showDatePicker()
        }


        binding.saveButton.setOnClickListener {
            if (validateFields()) {
                saveExpenseToFirestore()
            }
        }
    }

    private fun showDatePicker() {
        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateDateInView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
            show()
        }
    }

    private fun updateDateInView() {
        binding.dateEditText.setText(dateFormatter.format(calendar.time))
    }

    private fun validateFields(): Boolean {
        var isValid = true


        binding.descriptionInputLayout.error = null
        binding.amountInputLayout.error = null
        binding.dateInputLayout.error = null
        binding.categoryInputLayout.error = null


        val description = binding.descriptionEditText.text.toString().trim()
        if (description.isEmpty()) {
            binding.descriptionInputLayout.error = "La descripción es requerida"
            isValid = false
        }


        val amount = binding.amountEditText.text.toString().trim()
        if (amount.isEmpty()) {
            binding.amountInputLayout.error = "El monto es requerido"
            isValid = false
        } else {
            try {
                amount.toDouble()
            } catch (e: NumberFormatException) {
                binding.amountInputLayout.error = "Monto inválido"
                isValid = false
            }
        }


        if (binding.dateEditText.text.toString().trim().isEmpty()) {
            binding.dateInputLayout.error = "La fecha es requerida"
            isValid = false
        }


        if (binding.categoryAutoComplete.text.toString().trim().isEmpty()) {
            binding.categoryInputLayout.error = "La categoría es requerida"
            isValid = false
        }

        return isValid
    }

    private fun saveExpenseToFirestore() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }


        val description = binding.descriptionEditText.text.toString().trim()
        val amount = binding.amountEditText.text.toString().trim().toDouble()
        val date = binding.dateEditText.text.toString().trim()
        val category = binding.categoryAutoComplete.text.toString().trim()


        val expenseId = expensesCollection.document().id


        val expense = Expense(
            id = expenseId,
            description = description,
            amount = amount,
            date = date,
            category = category,
            userId = currentUser.uid,
            createdAt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
            timestamp = System.currentTimeMillis()
        )


        CoroutineScope(Dispatchers.IO).launch {
            try {
                expensesCollection.document(expenseId)
                    .set(expense)
                    .await()

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddExpenseActivity,
                        "Gasto guardado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AddExpenseActivity,
                        "Error al guardar: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}