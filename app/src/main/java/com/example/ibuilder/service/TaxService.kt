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

    private fun decrementCountUpdateTaxRate() {
        if (Indicators.availableUpdateTaxRate != 0) {
            Indicators.availableUpdateTaxRate -= 1
        }
    }

    fun getTotalTax() = Indicators.taxRate * Indicators.totalWorkers

    private fun updateSatisfaction(newTaxRate: Int) {
        if (newTaxRate > Indicators.taxRate) {
            Indicators.satisfactionCitizens -= (newTaxRate - Indicators.taxRate)
        } else {
            Indicators.satisfactionCitizens += (Indicators.taxRate - newTaxRate)
        }
    }

    fun updateTaxRate(context: Context, dbService: DatabaseService) {
        if (Indicators.taxRate == Indicators.tmpTaxRate) {
            Toast.makeText(context, "Сначала измените налоговую ставку", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (Indicators.availableUpdateTaxRate == 0) {
            Toast.makeText(context, "Ставку можно обновить только 1 раз за ход", Toast.LENGTH_SHORT)
                .show()
            Indicators.tmpTaxRate = Indicators.taxRate
            return
        }
        decrementCountUpdateTaxRate()
        updateSatisfaction(Indicators.tmpTaxRate)
        Indicators.taxRate = Indicators.tmpTaxRate
        Toast.makeText(context, "Ставка успешно обновлена", Toast.LENGTH_SHORT).show()
        dbService.saveAllIndicators()
    }
}