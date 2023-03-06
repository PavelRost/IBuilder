package com.example.ibuilder.model.indicatorsDB

import model.indicatorsDB.TypeResources

object ExchangeIndicators {

    val exchangeRate = mapOf(
        TypeResources.FOOD to mutableMapOf("buy" to 2, "sell" to 1),
        TypeResources.STONE to mutableMapOf("buy" to 4, "sell" to 2),
        TypeResources.WOOD to mutableMapOf("buy" to 2, "sell" to 1),
    )



}