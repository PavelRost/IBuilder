package com.example.ibuilder.model.indicatorsDB

object OtherIndicators {

    var currentDay: Int = 1

    var availableUpdateTaxRate: Int = 1
        set(value) {
            field += value
        }

    var taxRate: Int = 0

    var satisfactionCitizens: Int = 2
        set(value) {
            field += value
        }

    var frequencyAttackNomad: Int = 10
}