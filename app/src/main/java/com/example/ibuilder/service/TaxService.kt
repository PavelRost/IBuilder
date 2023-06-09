package com.example.ibuilder.service

import com.example.ibuilder.model.Indicators

object TaxService {

    fun incrementCountUpdateTaxRate() {
        if (Indicators.availableUpdateTaxRate == 0) {
            Indicators.availableUpdateTaxRate = Indicators.availableUpdateTaxRate + 1
        }
    }

    fun decrementCountUpdateTaxRate() {
        if (Indicators.availableUpdateTaxRate != 0) {
            Indicators.availableUpdateTaxRate = Indicators.availableUpdateTaxRate - 1
        }
    }

    fun getTotalTax() = Indicators.taxRate * Indicators.totalWorkers

    fun updateSatisfaction(newTaxRate: Int) {
        if (newTaxRate > Indicators.taxRate) {
            Indicators.satisfactionCitizens =
                Indicators.satisfactionCitizens - (newTaxRate - Indicators.taxRate)
        } else {
            Indicators.satisfactionCitizens =
                Indicators.satisfactionCitizens + (Indicators.taxRate - newTaxRate)
        }
    }
}