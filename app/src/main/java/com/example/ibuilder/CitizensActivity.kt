package com.example.ibuilder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ibuilder.model.indicatorsDB.Human
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import com.example.ibuilder.service.TaxService

class CitizensActivity : AppCompatActivity() {

    private var taxRate = OtherIndicators.taxRate
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
        textViewCitizensCountWorkers.text = Human.totalWorkers.toString()
        textViewCitizensTaxRate.text = OtherIndicators.taxRate.toString()
        textViewCitizensTotalRate.text = (OtherIndicators.taxRate * Human.totalWorkers).toString()
        textViewCitizensSatisfaction.text = OtherIndicators.satisfactionCitizens.toString()
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
        if (OtherIndicators.taxRate == taxRate) {
            Toast.makeText(this, "Сначала измените налоговую ставку", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (OtherIndicators.availableUpdateTaxRate == 0) {
            Toast.makeText(this, "Ставку можно обновить только 1 раз за ход", Toast.LENGTH_SHORT)
                .show()
            textViewCitizensTax.text = OtherIndicators.taxRate.toString()
            taxRate = OtherIndicators.taxRate
            return
        }
        TaxService.decrementCountUpdateTaxRate()
        TaxService.updateSatisfaction(taxRate)
        OtherIndicators.taxRate = taxRate
        textViewCitizensTaxRate.text = OtherIndicators.taxRate.toString()
        textViewCitizensTotalRate.text = TaxService.getTotalTax().toString()
        textViewCitizensSatisfaction.text = OtherIndicators.satisfactionCitizens.toString()
    }
}