package com.example.ibuilder.model.indicatorsDB

import com.example.ibuilder.model.building.TypeBuilding

object Building {

    val costConstruction = mapOf(
        TypeBuilding.PRODUCER_GOLD to mapOf(TypeResources.GOLD to 5),
        TypeBuilding.PRODUCER_WOOD to mapOf(TypeResources.WOOD to 3),
        TypeBuilding.PRODUCER_STONE to mapOf(TypeResources.GOLD to 3),
        TypeBuilding.PRODUCER_FOOD to mapOf(TypeResources.WOOD to 2),
        TypeBuilding.PRODUCER_WORKER to mapOf(TypeResources.WOOD to 2),
        TypeBuilding.CONSUMER_TAVERN to mapOf(TypeResources.WOOD to 15),
        TypeBuilding.CONSUMER_CIRCUS to mapOf(TypeResources.STONE to 20),
        TypeBuilding.CONSUMER_CHURCH to mapOf(TypeResources.WOOD to 40)
    )

    val costWork = mapOf(
        TypeBuilding.CONSUMER_TAVERN to mapOf(TypeResources.GOLD to 1),
        TypeBuilding.CONSUMER_CIRCUS to mapOf(TypeResources.GOLD to 2),
        TypeBuilding.CONSUMER_CHURCH to mapOf(TypeResources.GOLD to 3)
    )
}