package com.example.ibuilder.service

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources

object ExchangeResourcesService {

    val exchangeRate = mapOf(
        TypeResources.FOOD to mutableMapOf("buy" to 2, "sell" to 1),
        TypeResources.STONE to mutableMapOf("buy" to 4, "sell" to 2),
        TypeResources.WOOD to mutableMapOf("buy" to 2, "sell" to 1),
    )

    fun buyResources(typeResources: TypeResources, quantity: Int) {
        Indicators.allResources[TypeResources.GOLD] =
            Indicators.allResources[TypeResources.GOLD]!! - exchangeRate[typeResources]!!["buy"]!! * quantity
        Indicators.allResources[typeResources] = Indicators.allResources[typeResources]!! + quantity
    }

    fun sellResource(typeResources: TypeResources, quantity: Int) {
        Indicators.allResources[TypeResources.GOLD] =
            Indicators.allResources[TypeResources.GOLD]!! + exchangeRate[typeResources]!!["sell"]!! * quantity
        Indicators.allResources[typeResources] = Indicators.allResources[typeResources]!! - quantity
    }

    fun isEnoughGold(typeResources: TypeResources, quantity: Int) =
        Indicators.allResources[TypeResources.GOLD]!! - exchangeRate[typeResources]!!["buy"]!! * quantity >= 0

    fun isEnoughResourceForSell(typeResource: TypeResources, quantity: Int) =
        Indicators.allResources[typeResource]!! - quantity >= 0

    fun isAvailableExchangeOperations() = Indicators.availableOperationExchange > 0


    fun incrementCountOperations() {
        Indicators.availableOperationExchange += 1
    }

    fun decrementCountOperations() {
        Indicators.availableOperationExchange -= 1
    }
}