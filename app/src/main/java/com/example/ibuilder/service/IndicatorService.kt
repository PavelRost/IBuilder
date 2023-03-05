package service

import com.example.ibuilder.model.indicatorsDB.CostConstruction
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import model.building.TypeBuilding
import model.building.producer.HouseWorker
import model.indicatorsDB.Human
import model.indicatorsDB.Resource
import model.indicatorsDB.TypeResources

object IndicatorService {

    private val buildingSer = BuildingService
    private val resources = Resource
    private val human = Human
    private val costConstruction = CostConstruction


    fun addResources() {
        buildingSer.getAllBuildingsBuilt()
            .filter { it.hiredWorkers > 0 || it.typeBuild == TypeBuilding.PRODUCER_WORKER }
            .forEach { it.createResources() }
    }

    fun deleteResources() {
        if (human.totalWorkers > 0) {
            if (resources.food < human.totalWorkers * human.useFood) {
                var workersForRemove = if (resources.food == 0) {
                    human.totalWorkers
                } else {
                    (human.totalWorkers * human.useFood - resources.food) / human.useFood
                }
                while (human.freeWorkers < workersForRemove) {
                    buildingSer.getAllBuildingsBuilt().filter { it.hiredWorkers > 0 }[0].removeWorkers()
                }
                human.totalWorkers = -workersForRemove
                human.freeWorkers = -workersForRemove

                // изменяем показатель заполненности дома работниками
                (buildingSer.getAllBuildingsBuilt()
                    .filterIsInstance<HouseWorker>())
                    .filter { it.getCapacityHouse() != 2 }
                    .forEach {
                        while(it.getCapacityHouse() != 2 && workersForRemove != 0) {
                            it.addCapacity()
                            workersForRemove--
                        }
                    }
            }
            if (resources.food - human.useFood * human.totalWorkers == 0) {
                resources.food = -resources.food
                return
            }
            resources.food = -(human.useFood * human.totalWorkers)
        }
    }

    fun checkResourceBeforeConstruction(typeBuildings: TypeBuilding): Boolean {
        val mapCost = costConstruction.costConstruction[typeBuildings]
        for (resource in mapCost?.keys!!) {
            when(resource) {
                TypeResources.WOOD -> {
                    if (resources.wood - mapCost[resource]!! >= 0) {
                        resources.wood = -mapCost[resource]!!
                        return true
                    }
                    break
                }
                TypeResources.FOOD -> {
                    if (resources.food - mapCost[resource]!! >= 0) {
                        resources.food = -mapCost[resource]!!
                        return true
                    }
                    break
                }
                TypeResources.GOLD -> {
                    if (resources.gold - mapCost[resource]!! >= 0) {
                        resources.gold = -mapCost[resource]!!
                        return true
                    }
                    break
                }
                TypeResources.STONE -> {
                    if (resources.stone - mapCost[resource]!! >= 0) {
                        resources.stone = -mapCost[resource]!!
                        return true
                    }
                    break
                }
                else -> {}
            }
        }
        return false
    }

    fun resetValueIndicators() {
        Human.totalWorkers = -Human.totalWorkers
        Resource.gold = -Resource.gold
        Resource.food = -Resource.food
        Resource.wood = -Resource.wood
        Resource.stone = -Resource.stone
        OtherIndicators.currentDay = -OtherIndicators.currentDay
    }

    fun showDisplayResources() =
        "Еда: ${Resource.food}\n" +
                "Золото: ${Resource.gold}\n" +
                "Камень: ${Resource.stone}\n" +
                "Древесина: ${Resource.wood}"

    fun showDisplayCitizens() =
        "Всего рабочих: ${Human.totalWorkers}\n" +
                "Своб. рабочих: ${Human.freeWorkers}\n" +
                "Зан. рабочих: ${Human.hiredWorkers}\n" +
                "Cвоб. мест: ${buildingSer.getRealCapacityHouse()}\n" +
                "Всего мест: ${(buildingSer.getAllBuildingsBuilt().filterIsInstance<HouseWorker>().size * 2)}"

    fun showDisplayBuilt() =
        "Золотой рудник: ${buildingSer.getAllBuildingByType(TypeBuilding.PRODUCER_GOLD)?.filter { it.constructionTime == 0 }?.size}\n" +
                "Камен. рудник: ${buildingSer.getAllBuildingByType(TypeBuilding.PRODUCER_STONE)?.filter { it.constructionTime == 0 }?.size}\n" +
                "Лесопилка: ${buildingSer.getAllBuildingByType(TypeBuilding.PRODUCER_WOOD)?.filter { it.constructionTime == 0 }?.size}\n" +
                "Ферма: ${buildingSer.getAllBuildingByType(TypeBuilding.PRODUCER_FOOD)?.filter { it.constructionTime == 0 }?.size}\n" +
                "Дома рабочих: ${buildingSer.getAllBuildingByType(TypeBuilding.PRODUCER_WORKER)?.filter { it.constructionTime == 0 }?.size}"




}