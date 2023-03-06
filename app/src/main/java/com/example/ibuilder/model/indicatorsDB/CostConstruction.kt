package com.example.ibuilder.model.indicatorsDB

import model.building.TypeBuilding
import model.indicatorsDB.TypeResources

object CostConstruction {

    val costConstruction = mapOf (
        TypeBuilding.PRODUCER_GOLD to mapOf(TypeResources.GOLD to 5),
        TypeBuilding.PRODUCER_WOOD to mapOf(TypeResources.WOOD to 3),
        TypeBuilding.PRODUCER_STONE to mapOf(TypeResources.GOLD to 3),
        TypeBuilding.PRODUCER_FOOD to mapOf(TypeResources.WOOD to 2),
        TypeBuilding.PRODUCER_WORKER to mapOf(TypeResources.WOOD to 2)
    )
}