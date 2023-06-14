package com.example.ibuilder.service

import android.content.Context
import android.widget.Toast
import com.example.ibuilder.model.Indicators

object TaxService {

    fun incrementCountUpdateTaxRate() {
        if (Indicators.availableUpdateTaxRate == 0) {
            Indicators.availableUpdateTaxRate += 1
        }
    }

    fun getTotalTax() = Indicators.taxRate * Indicators.totalWorkers

    fun updateTaxRate(context: Context, newTaxRate: Int, dbService: DatabaseService) {
        decrementCountUpdateTaxRate()
        updateSatisfaction(newTaxRate)
        Indicators.taxRate = newTaxRate
        Toast.makeText(context, "Ставка успешно обновлена", Toast.LENGTH_SHORT).show()
        dbService.saveAllIndicators()
    }

    private fun decrementCountUpdateTaxRate() {
        if (Indicators.availableUpdateTaxRate != 0) {
            Indicators.availableUpdateTaxRate -= 1
        }
    }

    private fun updateSatisfaction(newTaxRate: Int) {
        if (newTaxRate > Indicators.taxRate) {
            Indicators.satisfactionCitizens -= (newTaxRate - Indicators.taxRate)
        } else {
            Indicators.satisfactionCitizens += (Indicators.taxRate - newTaxRate)
        }
    }
}