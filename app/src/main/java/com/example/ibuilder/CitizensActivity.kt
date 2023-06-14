package com.example.ibuilder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ibuilder.model.Indicators
import com.example.ibuilder.service.DatabaseService
import com.example.ibuilder.service.TaxService

class CitizensActivity : AppCompatActivity() {

    private lateinit var textViewCitizensCountWorkers: TextView
    private lateinit var textViewCitizensTaxRate: TextView
    private lateinit var textViewCitizensTotalRate: TextView
    private lateinit var textViewCitizensSatisfaction: TextView
    private lateinit var editTextCitizensNewTax: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citizens)
        initViews()
        if (supportActionBar != null) supportActionBar!!.hide()
        updateIndicatorPlayer()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CitizensActivity::class.java)
        }
    }

    fun updateTaxRate(view: View) {
        if (Indicators.availableUpdateTaxRate == 0) {
            Toast.makeText(this, "Обновление доступно 1 раз за ход", Toast.LENGTH_SHORT).show()
            return
        }
        if (editTextCitizensNewTax.editableText.toString().isEmpty()) {
            Toast.makeText(this, "Укажите значение ставки", Toast.LENGTH_SHORT).show()
            return
        }
        val newTax = editTextCitizensNewTax.editableText.toString().toInt()
        if (Indicators.taxRate == newTax) {
            Toast.makeText(this, "Новая ставка не должна совпадать с текущей", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (newTax < 0) {
            Toast.makeText(this, "Ставка не может быть отрицательной", Toast.LENGTH_SHORT).show()
            return
        }
        val dbService = ViewModelProvider(this)[DatabaseService::class.java]
        TaxService.updateTaxRate(this@CitizensActivity, newTax, dbService)
        updateIndicatorPlayer()
    }

    private fun initViews() {
        editTextCitizensNewTax = findViewById(R.id.editText_citizens_new_tax)
        textViewCitizensCountWorkers = findViewById(R.id.textView_citizens_count_workers)
        textViewCitizensTaxRate = findViewById(R.id.textView_citizens_tax_rate)
        textViewCitizensTotalRate = findViewById(R.id.textView_citizens_total_rate)
        textViewCitizensSatisfaction = findViewById(R.id.textView_citizens_satisfaction)
    }

    private fun updateIndicatorPlayer() {
        textViewCitizensCountWorkers.text = Indicators.totalWorkers.toString()
        textViewCitizensTaxRate.text = Indicators.taxRate.toString()
        textViewCitizensTotalRate.text = TaxService.getTotalTax().toString()
        textViewCitizensSatisfaction.text = Indicators.satisfactionCitizens.toString()
    }
}