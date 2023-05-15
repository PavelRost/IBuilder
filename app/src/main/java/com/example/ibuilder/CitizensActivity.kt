package com.example.ibuilder

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citizens)
        findViewById<TextView>(R.id.textView_citizens_tax).text = taxRate.toString()
        findViewById<TextView>(R.id.textView_citizens_count_workers).text =
            Human.totalWorkers.toString()
        findViewById<TextView>(R.id.textView_citizens_tax_rate).text =
            OtherIndicators.taxRate.toString()
        findViewById<TextView>(R.id.textView_citizens_total_rate).text =
            (OtherIndicators.taxRate * Human.totalWorkers).toString()
        findViewById<TextView>(R.id.textView_citizens_satisfaction).text =
            OtherIndicators.satisfactionCitizens.toString()
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
        findViewById<TextView>(R.id.textView_citizens_tax).text = taxRate.toString()
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
            findViewById<TextView>(R.id.textView_citizens_tax).text =
                OtherIndicators.taxRate.toString()
            //taxRate = OtherIndicators.taxRate
            return
        }
        TaxService.decrementCountUpdateTaxRate()
        OtherIndicators.taxRate = taxRate
        findViewById<TextView>(R.id.textView_citizens_tax_rate).text =
            OtherIndicators.taxRate.toString()
        findViewById<TextView>(R.id.textView_citizens_total_rate).text =
            TaxService.getTotalTax().toString()
    }


}