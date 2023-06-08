package com.example.ibuilder.service

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.building.producer.HouseWorker
import service.BuildingService
import kotlin.math.abs

object IndicatorService {

    private const val HUMAN_USE_FOOD: Int = 2

    private val buildingSer = BuildingService

    fun addResources() {
        BuildingService.getAllBuildingsBuilt()
            .filter { it.hiredWorkers > 0 || it.typeBuild == TypeBuilding.PRODUCER_WORKER }
            .forEach { it.createResources() }

        // налоги
        if (Indicators.satisfactionCitizens >= 0 && Indicators.taxRate > 0 && Indicators.totalWorkers > 0) {
            Indicators.allResources[TypeResources.GOLD] =
                Indicators.allResources[TypeResources.GOLD]!! + TaxService.getTotalTax()
        }
    }

    fun deleteResources() {
        if (Indicators.totalWorkers > 0) {

            // При низком уровне удовлетворенности жители поселения уходят
            if (Indicators.satisfactionCitizens < 0 && Indicators.freeWorkers > 0) {
                val workersForRemove = abs(Indicators.satisfactionCitizens)
                buildingSer.convertHiredInFreeWorkers(workersForRemove)
                if (workersForRemove > Indicators.freeWorkers) {
                    val currentFreeWorkers = Indicators.freeWorkers
                    Indicators.totalWorkers = Indicators.totalWorkers - currentFreeWorkers
                    Indicators.freeWorkers = Indicators.freeWorkers - currentFreeWorkers
                    BuildingService.changeCapacityHouse(currentFreeWorkers)
                } else {
                    Indicators.totalWorkers = Indicators.totalWorkers - workersForRemove
                    Indicators.freeWorkers = Indicators.freeWorkers - workersForRemove
                    BuildingService.changeCapacityHouse(workersForRemove)
                }
            }

            // При недостатке еды уходит такое количество, которое мы не способны прокормить
            if (Indicators.allResources[TypeResources.FOOD]!! < Indicators.totalWorkers * HUMAN_USE_FOOD) {
                val workersForRemove = if (Indicators.allResources[TypeResources.FOOD]!! == 0) {
                    Indicators.totalWorkers
                } else {
                    (Indicators.totalWorkers * HUMAN_USE_FOOD - Indicators.allResources[TypeResources.FOOD]!!) / HUMAN_USE_FOOD
                }
                buildingSer.convertHiredInFreeWorkers(workersForRemove)
                Indicators.totalWorkers = Indicators.totalWorkers - workersForRemove
                Indicators.freeWorkers = Indicators.freeWorkers - workersForRemove
                buildingSer.changeCapacityHouse(workersForRemove)
            }

            // Изменение количества еды с учетом оставшихся жителей
            if (Indicators.allResources[TypeResources.FOOD]!! - HUMAN_USE_FOOD * Indicators.totalWorkers <= 0) {
                Indicators.allResources[TypeResources.FOOD] = 0
            } else {
                Indicators.allResources[TypeResources.FOOD] =
                    Indicators.allResources[TypeResources.FOOD]!! - (HUMAN_USE_FOOD * Indicators.totalWorkers)
            }
        }

        val workingConsBuilding = BuildingService.getAllWorkingConsumerBuilding()
        if (workingConsBuilding.isNotEmpty()) {
            workingConsBuilding.forEach {
                val costWork = BuildingService.costWork[it.typeBuild]
                for (resource in costWork?.keys!!) {
                    when (resource) {
                        TypeResources.GOLD -> {
                            if (Indicators.allResources[TypeResources.GOLD]!! >= costWork[resource]!!) {
                                Indicators.allResources[TypeResources.GOLD] =
                                    Indicators.allResources[TypeResources.GOLD]!! - costWork[resource]!!
                            } else {
                                Indicators.allResources[TypeResources.GOLD] = 0
                                it.removeWorkers()
                            }
                        }
                        TypeResources.WOOD -> {
                            if (Indicators.allResources[TypeResources.WOOD]!! >= costWork[resource]!!) {
                                Indicators.allResources[TypeResources.WOOD] =
                                    Indicators.allResources[TypeResources.WOOD]!! - costWork[resource]!!
                            } else {
                                Indicators.allResources[TypeResources.WOOD] = 0
                                it.removeWorkers()
                            }
                        }
                        TypeResources.FOOD -> {
                            if (Indicators.allResources[TypeResources.FOOD]!! >= costWork[resource]!!) {
                                Indicators.allResources[TypeResources.FOOD] =
                                    Indicators.allResources[TypeResources.FOOD]!! - costWork[resource]!!
                            } else {
                                Indicators.allResources[TypeResources.FOOD] = 0
                                it.removeWorkers()
                            }
                        }
                        TypeResources.STONE -> {
                            if (Indicators.allResources[TypeResources.STONE]!! >= costWork[resource]!!) {
                                Indicators.allResources[TypeResources.STONE] =
                                    Indicators.allResources[TypeResources.STONE]!! - costWork[resource]!!
                            } else {
                                Indicators.allResources[TypeResources.STONE] = 0
                                it.removeWorkers()
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun checkResourceBeforeConstruction(typeBuildings: TypeBuilding): Boolean {
        val costConstruction = BuildingService.costConstruction[typeBuildings]
        for (resource in costConstruction?.keys!!) {
            when (resource) {
                TypeResources.WOOD -> if (Indicators.allResources[TypeResources.WOOD]!! >= costConstruction[resource]!!) {
                    Indicators.allResources[TypeResources.WOOD] =
                        Indicators.allResources[TypeResources.WOOD]!! - costConstruction[resource]!!
                    return true
                }
                TypeResources.FOOD -> if (Indicators.allResources[TypeResources.FOOD]!! >= costConstruction[resource]!!) {
                    Indicators.allResources[TypeResources.FOOD] =
                        Indicators.allResources[TypeResources.FOOD]!! - costConstruction[resource]!!
                    return true
                }
                TypeResources.GOLD -> if (Indicators.allResources[TypeResources.GOLD]!! >= costConstruction[resource]!!) {
                    Indicators.allResources[TypeResources.GOLD] =
                        Indicators.allResources[TypeResources.GOLD]!! - costConstruction[resource]!!
                    return true
                }
                TypeResources.STONE -> if (Indicators.allResources[TypeResources.STONE]!! >= costConstruction[resource]!!) {
                    Indicators.allResources[TypeResources.STONE] =
                        Indicators.allResources[TypeResources.STONE]!! - costConstruction[resource]!!
                    return true
                }
                else -> {}
            }
        }
        return false
    }

    fun showDisplayResources() =
        "Еда: ${Indicators.allResources[TypeResources.FOOD]}\n" +
                "Золото: ${Indicators.allResources[TypeResources.GOLD]}\n" +
                "Камень: ${Indicators.allResources[TypeResources.STONE]}\n" +
                "Древесина: ${Indicators.allResources[TypeResources.WOOD]}"

    fun showDisplayCitizens() =
        "Всего рабочих: ${Indicators.totalWorkers}\n" +
                "Своб. рабочих: ${Indicators.freeWorkers}\n" +
                "Зан. рабочих: ${Indicators.hiredWorkers}\n" +
                "Cвоб. мест: ${BuildingService.getRealCapacityHouse()}\n" +
                "Всего мест: ${
                    (BuildingService.getAllBuildingsBuilt()
                        .filterIsInstance<HouseWorker>().size * 2)
                }"

    fun showDisplayBuilt() =
        "Золотой рудник: ${
            BuildingService.getAllBuildingByType(TypeBuilding.PRODUCER_GOLD)
                ?.filter { it.constructionTime == 0 }?.size
        }\n" +
                "Камен. рудник: ${
                    BuildingService.getAllBuildingByType(TypeBuilding.PRODUCER_STONE)
                        ?.filter { it.constructionTime == 0 }?.size
                }\n" +
                "Лесопилка: ${
                    BuildingService.getAllBuildingByType(TypeBuilding.PRODUCER_WOOD)
                        ?.filter { it.constructionTime == 0 }?.size
                }\n" +
                "Ферма: ${
                    BuildingService.getAllBuildingByType(TypeBuilding.PRODUCER_FOOD)
                        ?.filter { it.constructionTime == 0 }?.size
                }\n" +
                "Дома рабочих: ${
                    BuildingService.getAllBuildingByType(TypeBuilding.PRODUCER_WORKER)
                        ?.filter { it.constructionTime == 0 }?.size
                }\n" +
                "Таверна: ${
                    BuildingService.getAllBuildingByType(TypeBuilding.CONSUMER_TAVERN)
                        ?.filter { it.constructionTime == 0 }?.size
                }\n" +
                "Цирк: ${
                    BuildingService.getAllBuildingByType(TypeBuilding.CONSUMER_CIRCUS)
                        ?.filter { it.constructionTime == 0 }?.size
                }\n" +
                "Церковь: ${
                    BuildingService.getAllBuildingByType(TypeBuilding.CONSUMER_CHURCH)
                        ?.filter { it.constructionTime == 0 }?.size
                }"
}