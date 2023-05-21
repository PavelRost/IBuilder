package com.example.ibuilder.service

import com.example.ibuilder.model.indicatorsDB.Era
import com.example.ibuilder.model.indicatorsDB.Resource
import com.example.ibuilder.model.indicatorsDB.TypeResources

object EraService {

    fun showCurrentEra(): String {
        return Era.currentEra.toString()
    }

    fun updateEra() {
        Era.currentEra = 1
    }

    fun isAvailableNextEra(): Boolean {
        val mapCostNextEra = Era.costNextEra[(Era.currentEra + 1).toString()]
        for (resource in mapCostNextEra?.keys!!) {
            when (resource) {
                TypeResources.WOOD -> if (Resource.allResources[TypeResources.WOOD]!! < mapCostNextEra[resource]!!) return false
                TypeResources.FOOD -> if (Resource.allResources[TypeResources.FOOD]!! < mapCostNextEra[resource]!!) return false
                TypeResources.GOLD -> if (Resource.allResources[TypeResources.GOLD]!! < mapCostNextEra[resource]!!) return false
                TypeResources.STONE -> if (Resource.allResources[TypeResources.STONE]!! < mapCostNextEra[resource]!!) return false
                else -> {}
            }
        }
        return true
    }

    fun deleteResourcesForUpdateEra() {
        val mapCostNextEra = Era.costNextEra[(Era.currentEra + 1).toString()]
        for (resource in mapCostNextEra?.keys!!) {
            when (resource) {
                TypeResources.WOOD -> Resource.allResources[TypeResources.WOOD] =
                    Resource.allResources[TypeResources.WOOD]!! - mapCostNextEra[resource]!!
                TypeResources.FOOD -> Resource.allResources[TypeResources.FOOD] =
                    Resource.allResources[TypeResources.FOOD]!! - mapCostNextEra[resource]!!
                TypeResources.GOLD -> Resource.allResources[TypeResources.GOLD] =
                    Resource.allResources[TypeResources.GOLD]!! - mapCostNextEra[resource]!!
                TypeResources.STONE -> Resource.allResources[TypeResources.STONE] =
                    Resource.allResources[TypeResources.STONE]!! - mapCostNextEra[resource]!!
                else -> {}
            }
        }
    }


}