package com.example.ibuilder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ibuilder.model.Indicators
import com.example.ibuilder.service.DatabaseService
import com.example.ibuilder.service.TaxService

class CitizensActivity : AppCompatActivity() {

    private var taxRate = Indicators.taxRate
    private lateinit var textViewCitizensTax: TextView
    private lateinit var textViewCitizensCountWorkers: TextView
    private lateinit var textViewCitizensTaxRate: TextView
    private lateinit var textViewCitizensTotalRate: TextView
    private lateinit var textViewCitizensSatisfaction: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citizens)
        initViews()
        if (supportActionBar != null) supportActionBar!!.hide()
        textViewCitizensTax.text = taxRate.toString()
        textViewCitizensCountWorkers.text = Indicators.totalWorkers.toString()
        textViewCitizensTaxRate.text = Indicators.taxRate.toString()
        textViewCitizensTotalRate.text = (Indicators.taxRate * Indicators.totalWorkers).toString()
        textViewCitizensSatisfaction.text = Indicators.satisfactionCitizens.toString()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CitizensActivity::class.java)
        }
    }

    private fun initViews() {
        textViewCitizensTax = findViewById(R.id.textView_citizens_tax)
        textViewCitizensCountWorkers = findViewById(R.id.textView_citizens_count_workers)
        textViewCitizensTaxRate = findViewById(R.id.textView_citizens_tax_rate)
        textViewCitizensTotalRate = findViewById(R.id.textView_citizens_total_rate)
        textViewCitizensSatisfaction = findViewById(R.id.textView_citizens_satisfaction)
    }

    fun incrementTaxRate(view: View) {
        taxRate++
        displayTaxRate()
    }


    fun decrementTaxRate(view: View) {
        if (taxRate == 0) {
            Toast.makeText(this, "Ставка не может быть отрицательной", Toast.LENGTH_SHORT).show()
            return
        }
        taxRate--
        displayTaxRate()
    }

    private fun displayTaxRate() {
        textViewCitizensTax.text = taxRate.toString()
    }

    fun updateTaxRate(view: View) {
        if (Indicators.taxRate == taxRate) {
            Toast.makeText(this, "Сначала измените налоговую ставку", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (Indicators.availableUpdateTaxRate == 0) {
            Toast.makeText(this, "Ставку можно обновить только 1 раз за ход", Toast.LENGTH_SHORT)
                .show()
            textViewCitizensTax.text = Indicators.taxRate.toString()
            taxRate = Indicators.taxRate
            return
        }
        TaxService.decrementCountUpdateTaxRate()
        TaxService.updateSatisfaction(taxRate)
        Indicators.taxRate = taxRate
        textViewCitizensTaxRate.text = Indicators.taxRate.toString()
        textViewCitizensTotalRate.text = TaxService.getTotalTax().toString()
        textViewCitizensSatisfaction.text = Indicators.satisfactionCitizens.toString()
        ViewModelProvider(this)[DatabaseService::class.java].saveAllIndicators()
    }
}