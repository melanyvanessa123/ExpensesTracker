package com.example.expensestracker.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.R as AndroidR
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expensestracker.databinding.ActivityAddExpenseBinding
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private val calendar = Calendar.getInstance()
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

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
                saveExpense()
            }
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
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


        if (binding.descriptionEditText.text.toString().trim().isEmpty()) {
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

    private fun saveExpense() {
        try {
            val description = binding.descriptionEditText.text.toString().trim()
            val amount = binding.amountEditText.text.toString().trim().toDouble()
            val date = binding.dateEditText.text.toString().trim()
            val category = binding.categoryAutoComplete.text.toString().trim()



            Toast.makeText(this, "Gasto guardado exitosamente", Toast.LENGTH_SHORT).show()
            finish()

        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar el gasto", Toast.LENGTH_SHORT).show()
        }
    }
}