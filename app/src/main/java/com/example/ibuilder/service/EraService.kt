package com.example.ibuilder.service

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources

object EraService {

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

    fun showCurrentEra(): String {
        return Indicators.currentEra.toString()
    }

    fun getCurrentEra(): Int {
        return Indicators.currentEra
    }

    fun updateEra() {
        Indicators.currentEra += 1
    }

    fun isAvailableNextEra(): Boolean {
        val mapCostNextEra = costNextEra[(getCurrentEra() + 1).toString()]
        for (resource in mapCostNextEra?.keys!!) {
            when (resource) {
                TypeResources.WOOD -> if (Indicators.allResources[TypeResources.WOOD]!! < mapCostNextEra[resource]!!) return false
                TypeResources.FOOD -> if (Indicators.allResources[TypeResources.FOOD]!! < mapCostNextEra[resource]!!) return false
                TypeResources.GOLD -> if (Indicators.allResources[TypeResources.GOLD]!! < mapCostNextEra[resource]!!) return false
                TypeResources.STONE -> if (Indicators.allResources[TypeResources.STONE]!! < mapCostNextEra[resource]!!) return false
                else -> {}
            }
        }
        return true
    }

    fun deleteResourcesForUpdateEra() {
        val mapCostNextEra = costNextEra[(getCurrentEra() + 1).toString()]
        for (resource in mapCostNextEra?.keys!!) {
            when (resource) {
                TypeResources.WOOD -> Indicators.allResources[TypeResources.WOOD] =
                    Indicators.allResources[TypeResources.WOOD]!! - mapCostNextEra[resource]!!
                TypeResources.FOOD -> Indicators.allResources[TypeResources.FOOD] =
                    Indicators.allResources[TypeResources.FOOD]!! - mapCostNextEra[resource]!!
                TypeResources.GOLD -> Indicators.allResources[TypeResources.GOLD] =
                    Indicators.allResources[TypeResources.GOLD]!! - mapCostNextEra[resource]!!
                TypeResources.STONE -> Indicators.allResources[TypeResources.STONE] =
                    Indicators.allResources[TypeResources.STONE]!! - mapCostNextEra[resource]!!
                else -> {}
            }
        }
    }
}