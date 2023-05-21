package com.example.ibuilder.model.indicatorsDB

object Era {

    var currentEra: Int = 0
        set(value) {
            field += value
        }

    val costNextEra = mapOf(
        "1" to mapOf(TypeResources.FOOD to 50, TypeResources.WOOD to 50),
        "2" to mapOf(
            TypeResources.FOOD to 100,
            TypeResources.GOLD to 100,
            TypeResources.WOOD to 100
        ),
        "3" to mapOf(
            TypeResources.FOOD to 150,
            TypeResources.GOLD to 150,
            TypeResources.WOOD to 150,
            TypeResources.STONE to 150
        )
    )
}