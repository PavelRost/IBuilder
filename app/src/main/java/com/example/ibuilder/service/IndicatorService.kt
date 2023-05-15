package com.example.ibuilder.service

import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.building.producer.HouseWorker
import com.example.ibuilder.model.indicatorsDB.*
import service.BuildingService

object IndicatorService {

    private val buildingSer = BuildingService
    private val resources = Resource
    private val human = Human
    private val costConstruction = CostConstruction


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

        // Работники и еда
        if (human.totalWorkers > 0) {
            if (resources.allResources[TypeResources.FOOD]!! < human.totalWorkers * human.useFood) {
                var workersForRemove = if (resources.allResources[TypeResources.FOOD]!! == 0) {
                    human.totalWorkers
                } else {
                    (human.totalWorkers * human.useFood - resources.allResources[TypeResources.FOOD]!!) / human.useFood
                }
                while (human.freeWorkers < workersForRemove) {
                    BuildingService.getAllBuildingsBuilt()
                        .filter { it.hiredWorkers > 0 }[0].removeWorkers()
                }
                human.totalWorkers = -workersForRemove
                human.freeWorkers = -workersForRemove

                // изменяем показатель заполненности дома работниками
                (BuildingService.getAllBuildingsBuilt()
                    .filterIsInstance<HouseWorker>())
                    .filter { it.getCapacityHouse() != 2 }
                    .forEach {
                        while(it.getCapacityHouse() != 2 && workersForRemove != 0) {
                            it.addCapacity()
                            workersForRemove--
                        }
                    }
            }
            if (resources.allResources[TypeResources.FOOD]!! - human.useFood * human.totalWorkers <= 0) {
                resources.allResources[TypeResources.FOOD] = 0
                return
            }
            resources.allResources[TypeResources.FOOD] = resources.allResources[TypeResources.FOOD]!! - (human.useFood * human.totalWorkers)
        }
    }

    fun checkResourceBeforeConstruction(typeBuildings: TypeBuilding): Boolean {
        val mapCost = costConstruction.costConstruction[typeBuildings]
        for (resource in mapCost?.keys!!) {
            when(resource) {
                TypeResources.WOOD -> {
                    if (resources.allResources[TypeResources.WOOD]!! - mapCost[resource]!! >= 0) {
                        resources.allResources[TypeResources.WOOD] = resources.allResources[TypeResources.WOOD]!! - mapCost[resource]!!
                        return true
                    }
                    break
                }
                TypeResources.FOOD -> {
                    if (resources.allResources[TypeResources.FOOD]!! - mapCost[resource]!! >= 0) {
                        resources.allResources[TypeResources.FOOD] = resources.allResources[TypeResources.FOOD]!! - mapCost[resource]!!
                        return true
                    }
                    break
                }
                TypeResources.GOLD -> {
                    if (resources.allResources[TypeResources.GOLD]!! - mapCost[resource]!! >= 0) {
                        resources.allResources[TypeResources.GOLD] = resources.allResources[TypeResources.GOLD]!! - mapCost[resource]!!
                        return true
                    }
                    break
                }
                TypeResources.STONE -> {
                    if (resources.allResources[TypeResources.STONE]!! - mapCost[resource]!! >= 0) {
                        resources.allResources[TypeResources.STONE] = resources.allResources[TypeResources.STONE]!! - mapCost[resource]!!
                        return true
                    }
                    break
                }
                else -> {}
            }
        }
        return false
    }

//    fun resetValueIndicators() {
//        Human.totalWorkers = -Human.totalWorkers
//        Resource.gold = -Resource.gold
//        Resource.food = -Resource.food
//        Resource.wood = -Resource.wood
//        Resource.stone = -Resource.stone
//        OtherIndicators.currentDay = -OtherIndicators.currentDay
//    }

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
                }"




}