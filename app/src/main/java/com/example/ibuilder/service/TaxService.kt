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


}