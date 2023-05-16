package com.example.ibuilder.service

import com.example.ibuilder.model.indicatorsDB.Human
import com.example.ibuilder.model.indicatorsDB.OtherIndicators

object TaxService {

    fun incrementCountUpdateTaxRate() {
        OtherIndicators.availableUpdateTaxRate = 1
    }

    fun decrementCountUpdateTaxRate() {
        OtherIndicators.availableUpdateTaxRate = -1
    }

    fun getTotalTax() = OtherIndicators.taxRate * Human.totalWorkers

    fun updateSatisfaction(newTaxRate: Int) {
        if (newTaxRate > OtherIndicators.taxRate) {
            OtherIndicators.satisfactionCitizens = -(newTaxRate - OtherIndicators.taxRate)
        } else {
            OtherIndicators.satisfactionCitizens = OtherIndicators.taxRate - newTaxRate
        }
    }
}