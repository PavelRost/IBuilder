package com.example.ibuilder.model

import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding

object Indicators {

    var idPlayer: Int = 0

    var totalWorkers: Int = 0
    var hiredWorkers: Int = 0
    var freeWorkers: Int = 0

    var currentDay: Int = 1
    var availableUpdateTaxRate: Int = 1
    var taxRate: Int = 0
    var tmpTaxRate: Int = 0
    var satisfactionCitizens: Int = 2
    var frequencyAttackNomad: Int = 10
    var currentEra: Int = 0
    var availableOperationExchange: Int = 1

    val allResources = mutableMapOf(
        TypeResources.GOLD to 20,
        TypeResources.WOOD to 10,
        TypeResources.FOOD to 20,
        TypeResources.STONE to 0
    )

    val building = mutableMapOf<TypeBuilding, ArrayList<AbstractBuilding>>()

    init {
        building[TypeBuilding.PRODUCER_GOLD] = ArrayList()
        building[TypeBuilding.PRODUCER_WOOD] = ArrayList()
        building[TypeBuilding.PRODUCER_STONE] = ArrayList()
        building[TypeBuilding.PRODUCER_FOOD] = ArrayList()
        building[TypeBuilding.PRODUCER_WORKER] = ArrayList()
        building[TypeBuilding.CONSUMER_TAVERN] = ArrayList()
        building[TypeBuilding.CONSUMER_CIRCUS] = ArrayList()
        building[TypeBuilding.CONSUMER_CHURCH] = ArrayList()
    }


}