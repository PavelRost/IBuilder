package com.example.ibuilder.service

import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.building.producer.HouseWorker
import com.example.ibuilder.model.indicatorsDB.*
import service.BuildingService
import kotlin.math.abs

object IndicatorService {

    private val buildingSer = BuildingService
    private val resources = Resource
    private val human = Human
    private val costConstruction = Building


    fun addResources() {
        BuildingService.getAllBuildingsBuilt()
            .filter { it.hiredWorkers > 0 || it.typeBuild == TypeBuilding.PRODUCER_WORKER }
            .forEach { it.createResources() }

        // налоги
        if (OtherIndicators.taxRate > 0) {
            Resource.allResources[TypeResources.GOLD] =
                Resource.allResources[TypeResources.GOLD]!! + TaxService.getTotalTax()
        }
    }

    fun deleteResources() {
        if (human.totalWorkers > 0) {
            if (OtherIndicators.satisfactionCitizens < 0 && human.freeWorkers > 0) {
                val workersForRemove = abs(OtherIndicators.satisfactionCitizens)
                buildingSer.convertHiredInFreeWorkers(workersForRemove)
                if (workersForRemove > human.freeWorkers) {
                    val currentFreeWorkers = human.freeWorkers
                    human.totalWorkers = -currentFreeWorkers
                    human.freeWorkers = -currentFreeWorkers
                    BuildingService.changeCapacityHouse(currentFreeWorkers)
                } else {
                    human.totalWorkers = -workersForRemove
                    human.freeWorkers = -workersForRemove
                    BuildingService.changeCapacityHouse(workersForRemove)
                }
            }
            if (resources.allResources[TypeResources.FOOD]!! < human.totalWorkers * human.useFood) {
                val workersForRemove = if (resources.allResources[TypeResources.FOOD]!! == 0) {
                    human.totalWorkers
                } else {
                    (human.totalWorkers * human.useFood - resources.allResources[TypeResources.FOOD]!!) / human.useFood
                }
                buildingSer.convertHiredInFreeWorkers(workersForRemove)
                human.totalWorkers = -workersForRemove
                human.freeWorkers = -workersForRemove
                buildingSer.changeCapacityHouse(workersForRemove)
            }
            if (resources.allResources[TypeResources.FOOD]!! - human.useFood * human.totalWorkers <= 0) {
                resources.allResources[TypeResources.FOOD] = 0
                return
            }
            resources.allResources[TypeResources.FOOD] =
                resources.allResources[TypeResources.FOOD]!! - (human.useFood * human.totalWorkers)
        }

        val workingConsBuilding = BuildingService.getAllWorkingConsumerBuilding()
        if (workingConsBuilding.isNotEmpty()) {
            workingConsBuilding.forEach {
                val costWork = Building.costWork[it.typeBuild]
                for (resource in costWork?.keys!!) {
                    when (resource) {
                        TypeResources.GOLD -> {
                            if (resources.allResources[TypeResources.GOLD]!! >= costWork[resource]!!) {
                                resources.allResources[TypeResources.GOLD] =
                                    resources.allResources[TypeResources.GOLD]!! - costWork[resource]!!
                            } else {
                                resources.allResources[TypeResources.GOLD] = 0
                                it.removeWorkers()
                            }
                        }
                        TypeResources.WOOD -> {
                            if (resources.allResources[TypeResources.WOOD]!! >= costWork[resource]!!) {
                                resources.allResources[TypeResources.WOOD] =
                                    resources.allResources[TypeResources.WOOD]!! - costWork[resource]!!
                            } else {
                                resources.allResources[TypeResources.WOOD] = 0
                                it.removeWorkers()
                            }
                        }
                        TypeResources.FOOD -> {
                            if (resources.allResources[TypeResources.FOOD]!! >= costWork[resource]!!) {
                                resources.allResources[TypeResources.FOOD] =
                                    resources.allResources[TypeResources.FOOD]!! - costWork[resource]!!
                            } else {
                                resources.allResources[TypeResources.FOOD] = 0
                                it.removeWorkers()
                            }
                        }
                        TypeResources.STONE -> {
                            if (resources.allResources[TypeResources.STONE]!! >= costWork[resource]!!) {
                                resources.allResources[TypeResources.STONE] =
                                    resources.allResources[TypeResources.STONE]!! - costWork[resource]!!
                            } else {
                                resources.allResources[TypeResources.STONE] = 0
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
        val mapCost = costConstruction.costConstruction[typeBuildings]
        for (resource in mapCost?.keys!!) {
            when (resource) {
                TypeResources.WOOD -> if (resources.allResources[TypeResources.WOOD]!! < mapCost[resource]!!) return false
                TypeResources.FOOD -> if (resources.allResources[TypeResources.FOOD]!! < mapCost[resource]!!) return false
                TypeResources.GOLD -> if (resources.allResources[TypeResources.GOLD]!! < mapCost[resource]!!) return false
                TypeResources.STONE -> if (resources.allResources[TypeResources.STONE]!! < mapCost[resource]!!) return false
                else -> {}
            }
        }
        return true
    }

    fun showDisplayResources() =
        "Еда: ${Resource.allResources[TypeResources.FOOD]}\n" +
                "Золото: ${Resource.allResources[TypeResources.GOLD]}\n" +
                "Камень: ${Resource.allResources[TypeResources.STONE]}\n" +
                "Древесина: ${Resource.allResources[TypeResources.WOOD]}"

    fun showDisplayCitizens() =
        "Всего рабочих: ${Human.totalWorkers}\n" +
                "Своб. рабочих: ${Human.freeWorkers}\n" +
                "Зан. рабочих: ${Human.hiredWorkers}\n" +
                "Cвоб. мест: ${BuildingService.getRealCapacityHouse()}\n" +
                "Всего мест: ${
                    (BuildingService.getAllBuildingsBuilt()
                        .filterIsInstance<HouseWorker>().size * 2)
                }"

    fun showDisplayBuiltProducer() =
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
                }"

    fun showDisplayBuiltConsumer() =
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