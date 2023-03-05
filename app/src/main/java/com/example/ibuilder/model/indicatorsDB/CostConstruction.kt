package com.example.ibuilder.model.indicatorsDB

import model.building.TypeBuilding
import model.indicatorsDB.TypeResources

object CostConstruction {

    val costConstruction = mutableMapOf<TypeBuilding, Map<TypeResources, Int>>()

    init {
        costConstruction[TypeBuilding.PRODUCER_GOLD] = mapOf(TypeResources.GOLD to 5)
        costConstruction[TypeBuilding.PRODUCER_WOOD] = mapOf(TypeResources.WOOD to 3)
        costConstruction[TypeBuilding.PRODUCER_STONE] = mapOf(TypeResources.GOLD to 3)
        costConstruction[TypeBuilding.PRODUCER_FOOD] = mapOf(TypeResources.WOOD to 2)
        costConstruction[TypeBuilding.PRODUCER_WORKER] = mapOf(TypeResources.WOOD to 2)
    }
}