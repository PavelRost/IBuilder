package service

import com.example.ibuilder.model.building.AbstractBuilding
import model.building.TypeBuilding
import model.building.producer.*

object BuildingService {

    private val building = mutableMapOf<TypeBuilding, ArrayList<AbstractBuilding>>()
    private val indicatorService = IndicatorService

    init {
        building[TypeBuilding.PRODUCER_GOLD] = ArrayList()
        building[TypeBuilding.PRODUCER_WOOD] = ArrayList()
        building[TypeBuilding.PRODUCER_STONE] = ArrayList()
        building[TypeBuilding.PRODUCER_FOOD] = ArrayList()
        building[TypeBuilding.PRODUCER_WORKER] = ArrayList()
    }

    fun createBuilding(typeBuildings: TypeBuilding): String {
        var rsl = "Недостаточно ресурсов для постройки здания"
        if (indicatorService.checkResourceBeforeConstruction(typeBuildings)) {
            val numberBuildings = building[typeBuildings]?.size?.plus(1)
            val buildingNew: AbstractBuilding?
            when(typeBuildings) {
                TypeBuilding.PRODUCER_GOLD -> {
                    buildingNew = GoldMine(serialNumber = numberBuildings!!)
                    building[TypeBuilding.PRODUCER_GOLD]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_WOOD -> {
                    buildingNew = WoodMine(serialNumber = numberBuildings!!)
                    building[TypeBuilding.PRODUCER_WOOD]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_FOOD -> {
                    buildingNew = FoodMine(serialNumber = numberBuildings!!)
                    building[TypeBuilding.PRODUCER_FOOD]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_STONE -> {
                    buildingNew = StoneMine(serialNumber = numberBuildings!!)
                    building[TypeBuilding.PRODUCER_STONE]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_WORKER -> {
                    buildingNew = HouseWorker(serialNumber = numberBuildings!!)
                    building[TypeBuilding.PRODUCER_WORKER]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                else -> {}
            }
        }
        return rsl
    }

    fun addWorkersInBuilding(typeBuildings: TypeBuilding): String {
        val allBuildingsByType = getAllBuildingByType(typeBuildings)!!.filter { it.hiredWorkers == 0 && it.constructionTime == 0 }.toMutableList()
        if (allBuildingsByType.isEmpty()) {
            return "Постройки данного типа отсутствуют или полностью укомплектованы"
        }
        return allBuildingsByType[0].addWorkers()
    }

    fun removeWorkersInBuilding(typeBuildings: TypeBuilding): String {
        val allBuildingsByType = getAllBuildingByType(typeBuildings)!!.filter { it.hiredWorkers > 0 }.toMutableList()
        if (allBuildingsByType.isEmpty()) {
            return "Рабочие не задействованы на данных постройках"

        }
        return allBuildingsByType[0].removeWorkers()
    }

    fun getAllBuildingByType(typeBuildings: TypeBuilding): ArrayList<AbstractBuilding>? {
        return building[typeBuildings]
    }

    private fun getAllBuildingsUnderConstructionByType(typeBuildings: TypeBuilding): String {
        val rsl: StringBuilder = StringBuilder("")
        val allBuildingsByType = getAllBuildingByType(typeBuildings)!!.filter { it.constructionTime > 0 }.toMutableList()
        if (allBuildingsByType.isEmpty()) {
            return "Постройки данного типа отсутствуют, возможно, строительство было завершено"
        }
        allBuildingsByType.forEach {
            rsl.append(it.constructionStatus()).append("\n") // TODO лишний переход на новую строку
        }
        return rsl.toString()
    }

    private fun getAllBuildingsUnderConstruction(): MutableList<AbstractBuilding> {
        val rsl = mutableListOf<AbstractBuilding>()
        for (buildingsSameType in building.values) {
            for (buildings in buildingsSameType) {
                if (buildings.constructionTime > 0) {
                    rsl.add(buildings)
                }
            }
        }
        return rsl
    }

    fun getAllBuildingsBuilt(): MutableList<AbstractBuilding> {
        val rsl = mutableListOf<AbstractBuilding>()
        for (buildingsSameType in building.values) {
            for (buildings in buildingsSameType) {
                if (buildings.constructionTime == 0) {
                    rsl.add(buildings)
                }
            }
        }
        return rsl
    }

    fun getAllWorkingBuildings(): String {
        val rsl: StringBuilder = StringBuilder("Работающие производства отсутствуют")
        getAllBuildingsBuilt()
            .filter { it.hiredWorkers > 0 }
            .forEach {
                rsl.append("${it.name} №${it.serialNumber}").append("\n")
            }
        return rsl.toString()
    }

    // Получить количество свободных мест во всех домах в городе
    fun getRealCapacityHouse(): Int {
        var rsl = 0
        getAllBuildingsBuilt()
            .filterIsInstance<HouseWorker>()
            .forEach {
                rsl += it.getCapacityHouse()
            }
        return rsl
    }

    fun continueBuild(): String {
        val rsl = StringBuilder("")
        getAllBuildingsUnderConstruction().forEach { rsl.append(it.build()).append("\n") } // TODO доработать лишний пробел
        return rsl.toString()
    }

    fun checkStatusConstruction(typeBuildings: TypeBuilding): String {
        var rsl = "Постройки данного типа отсутствуют, возможно, строительство было завершено"
        when(typeBuildings) {
            TypeBuilding.PRODUCER_GOLD -> {
                rsl = getAllBuildingsUnderConstructionByType(TypeBuilding.PRODUCER_GOLD)
            }
            TypeBuilding.PRODUCER_STONE -> {
                rsl = getAllBuildingsUnderConstructionByType(TypeBuilding.PRODUCER_STONE)
            }
            TypeBuilding.PRODUCER_WOOD -> {
                rsl = getAllBuildingsUnderConstructionByType(TypeBuilding.PRODUCER_WOOD)
            }
            TypeBuilding.PRODUCER_FOOD -> {
                rsl = getAllBuildingsUnderConstructionByType(TypeBuilding.PRODUCER_FOOD)
            }
            TypeBuilding.PRODUCER_WORKER -> {
                rsl = getAllBuildingsUnderConstructionByType(TypeBuilding.PRODUCER_WORKER)
            }
            else -> {}
        }
        return rsl
    }


}