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
        updateIndicatorPlayer()
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

    private fun updateIndicatorPlayer() {
        textViewCitizensTax.text = Indicators.tmpTaxRate.toString()
        textViewCitizensCountWorkers.text = Indicators.totalWorkers.toString()
        textViewCitizensTaxRate.text = Indicators.taxRate.toString()
        textViewCitizensTotalRate.text = TaxService.getTotalTax().toString()
        textViewCitizensSatisfaction.text = Indicators.satisfactionCitizens.toString()
    }

    fun incrementTaxRate(view: View) {
        Indicators.tmpTaxRate++
        displayTaxRate()
    }


    fun decrementTaxRate(view: View) {
        if (Indicators.tmpTaxRate == 0) {
            Toast.makeText(this, "Ставка не может быть отрицательной", Toast.LENGTH_SHORT).show()
            return
        }
        Indicators.tmpTaxRate--
        displayTaxRate()
    }

    private fun displayTaxRate() {
        textViewCitizensTax.text = Indicators.tmpTaxRate.toString()
    }

    fun updateTaxRate(view: View) {
        val dbService = ViewModelProvider(this)[DatabaseService::class.java]
        TaxService.updateTaxRate(this@CitizensActivity, dbService)
        updateIndicatorPlayer()
    }
}