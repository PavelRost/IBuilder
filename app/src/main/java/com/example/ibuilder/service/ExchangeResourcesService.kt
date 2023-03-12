package com.example.ibuilder.service

import com.example.ibuilder.model.indicatorsDB.ExchangeIndicators
import com.example.ibuilder.model.indicatorsDB.Resource
import com.example.ibuilder.model.indicatorsDB.TypeResources

object ExchangeResourcesService {

    fun buyResources(typeResources: TypeResources, quantity: Int) {
        Resource.allResources[TypeResources.GOLD] = Resource.allResources[TypeResources.GOLD]!! - ExchangeIndicators.exchangeRate[typeResources]!!["buy"]!! * quantity
        Resource.allResources[typeResources] = Resource.allResources[typeResources]!! + quantity
    }

    fun sellResource(typeResources: TypeResources, quantity: Int) {
        Resource.allResources[TypeResources.GOLD] = Resource.allResources[TypeResources.GOLD]!! + ExchangeIndicators.exchangeRate[typeResources]!!["sell"]!! * quantity
        Resource.allResources[typeResources] = Resource.allResources[typeResources]!! - quantity
    }

    fun isEnoughGold(typeResources: TypeResources, quantity: Int) =
        Resource.allResources[TypeResources.GOLD]!! - ExchangeIndicators.exchangeRate[typeResources]!!["buy"]!! * quantity >= 0

    fun isEnoughResourceForSell(typeResource: TypeResources, quantity: Int) =
        Resource.allResources[typeResource]!! - quantity >= 0

    fun isAvailableExchangeOperations() = ExchangeIndicators.availableCountOperations > 0


    fun incrementCountOperations() {
        ExchangeIndicators.availableCountOperations = 1
    }

    fun decrementCountOperations() {
        ExchangeIndicators.availableCountOperations = -1
    }
}