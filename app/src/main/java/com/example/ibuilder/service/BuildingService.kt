package service

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.building.consumer.Church
import com.example.ibuilder.model.building.consumer.Circus
import com.example.ibuilder.model.building.consumer.Tavern
import com.example.ibuilder.model.building.producer.*
import com.example.ibuilder.service.EraService
import com.example.ibuilder.service.IndicatorService

object BuildingService {

    private val indicatorService = IndicatorService

    val costConstruction = mapOf(
        TypeBuilding.PRODUCER_GOLD to mapOf(TypeResources.GOLD to 5),
        TypeBuilding.PRODUCER_WOOD to mapOf(TypeResources.WOOD to 3),
        TypeBuilding.PRODUCER_STONE to mapOf(TypeResources.GOLD to 3),
        TypeBuilding.PRODUCER_FOOD to mapOf(TypeResources.WOOD to 2),
        TypeBuilding.PRODUCER_WORKER to mapOf(TypeResources.WOOD to 2),
        TypeBuilding.CONSUMER_TAVERN to mapOf(TypeResources.WOOD to 15),
        TypeBuilding.CONSUMER_CIRCUS to mapOf(TypeResources.WOOD to 30),
        TypeBuilding.CONSUMER_CHURCH to mapOf(TypeResources.STONE to 40)
    )

    val costWork = mapOf(
        TypeBuilding.CONSUMER_TAVERN to mapOf(TypeResources.GOLD to 1),
        TypeBuilding.CONSUMER_CIRCUS to mapOf(TypeResources.GOLD to 2),
        TypeBuilding.CONSUMER_CHURCH to mapOf(TypeResources.GOLD to 3)
    )

    fun createBuilding(typeBuildings: TypeBuilding): String {
        if (typeBuildings.requiredEra > EraService.getCurrentEra()) return "Недоступно в текущей эпохе"
        var rsl = "Недостаточно ресурсов для постройки здания"
        if (indicatorService.checkResourceBeforeConstruction(typeBuildings)) {
            val numberBuildings = Indicators.building[typeBuildings]?.size?.plus(1)
            val buildingNew: AbstractBuilding?
            when (typeBuildings) {
                TypeBuilding.PRODUCER_GOLD -> {
                    buildingNew = GoldMine(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.PRODUCER_GOLD]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_WOOD -> {
                    buildingNew = WoodMine(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.PRODUCER_WOOD]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_FOOD -> {
                    buildingNew = FoodMine(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.PRODUCER_FOOD]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_STONE -> {
                    buildingNew = StoneMine(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.PRODUCER_STONE]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.PRODUCER_WORKER -> {
                    buildingNew = HouseWorker(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.PRODUCER_WORKER]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.CONSUMER_TAVERN -> {
                    buildingNew = Tavern(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.CONSUMER_TAVERN]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.CONSUMER_CIRCUS -> {
                    buildingNew = Circus(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.CONSUMER_CIRCUS]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
                TypeBuilding.CONSUMER_CHURCH -> {
                    buildingNew = Church(serialNumber = numberBuildings!!)
                    Indicators.building[TypeBuilding.CONSUMER_CHURCH]?.add(buildingNew)
                    rsl = "Строители возводят ${buildingNew.name} №${buildingNew.serialNumber}"
                }
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
        return Indicators.building[typeBuildings]
    }

    private fun getAllBuildingsUnderConstruction(): MutableList<AbstractBuilding> {
        val rsl = mutableListOf<AbstractBuilding>()
        for (buildingsSameType in Indicators.building.values) {
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
        for (buildingsSameType in Indicators.building.values) {
            for (buildings in buildingsSameType) {
                if (buildings.constructionTime == 0) {
                    rsl.add(buildings)
                }
            }
        }
        return rsl
    }

    fun getAllWorkingConsumerBuilding(): List<AbstractBuilding> {
        return getAllBuildingsBuilt().filter { it.hiredWorkers > 0 && it.typeResources == TypeResources.SATISFACTION }
    }

    fun getAllWorkingProducerBuilding(): List<AbstractBuilding> {
        return getAllBuildingsBuilt().filter { it.hiredWorkers > 0 && it.typeResources != TypeResources.SATISFACTION }
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
        getAllBuildingsUnderConstruction().forEach { rsl.append(it.build()).append("\n") }
        return rsl.toString()
    }

    fun checkStatusConstruction(typeBuildings: TypeBuilding): String {
        val rsl: StringBuilder = StringBuilder("")
        val allBuildingsByType =
            getAllBuildingByType(typeBuildings)!!.filter { it.constructionTime > 0 }.toMutableList()
        if (allBuildingsByType.isEmpty()) {
            return "Постройки данного типа отсутствуют, возможно, строительство было завершено"
        }
        allBuildingsByType.forEach {
            rsl.append(it.constructionStatus()).append("\n")
        }
        return rsl.toString()
    }

    fun changeCapacityHouse(workersForRemove: Int) {
        var workers = workersForRemove
        (BuildingService.getAllBuildingsBuilt()
            .filterIsInstance<HouseWorker>())
            .filter { it.getCapacityHouse() != 2 }
            .forEach {
                while (it.getCapacityHouse() != 2 && workers != 0) {
                    it.addCapacity()
                    workers--
                }
            }
    }

    fun convertHiredInFreeWorkers(needConvertWorkers: Int) {
        if (Indicators.freeWorkers >= needConvertWorkers) return
        val workingBuildings = getAllBuildingsBuilt().filter { it.hiredWorkers > 0 }
        if (workingBuildings.isEmpty()) return
        workingBuildings.forEach {
            if (Indicators.freeWorkers >= needConvertWorkers) return
            it.removeWorkers()
        }
    }

    fun showWorkingProducerBuilding() =
        "Золотой рудник: ${
            getAllWorkingProducerBuilding().filter { it.typeBuild == TypeBuilding.PRODUCER_GOLD }.size
        }\n" +
                "Камен. рудник: ${
                    getAllWorkingProducerBuilding().filter { it.typeBuild == TypeBuilding.PRODUCER_STONE }.size
                }\n" +
                "Лесопилка: ${
                    getAllWorkingProducerBuilding().filter { it.typeBuild == TypeBuilding.PRODUCER_WOOD }.size
                }\n" +
                "Ферма: ${
                    getAllWorkingProducerBuilding().filter { it.typeBuild == TypeBuilding.PRODUCER_FOOD }.size
                }"

    fun showWorkingConsumerBuilding() =
        "Таверна: ${
            getAllWorkingConsumerBuilding().filter { it.typeBuild == TypeBuilding.CONSUMER_TAVERN }.size
        }\n" +
                "Ярмарка: ${
                    getAllWorkingConsumerBuilding().filter { it.typeBuild == TypeBuilding.CONSUMER_CIRCUS }.size
                }\n" +
                "Храм: ${
                    getAllWorkingConsumerBuilding().filter { it.typeBuild == TypeBuilding.CONSUMER_CHURCH }.size
                }"

    fun showInformationBuilding(typeBuildings: TypeBuilding): String {
        return when (typeBuildings) {
            TypeBuilding.PRODUCER_GOLD -> GoldMine(serialNumber = 0).showInformation()
            TypeBuilding.PRODUCER_WOOD -> WoodMine(serialNumber = 0).showInformation()
            TypeBuilding.PRODUCER_FOOD -> FoodMine(serialNumber = 0).showInformation()
            TypeBuilding.PRODUCER_STONE -> StoneMine(serialNumber = 0).showInformation()
            TypeBuilding.PRODUCER_WORKER -> HouseWorker(serialNumber = 0).showInformation()
            TypeBuilding.CONSUMER_TAVERN -> Tavern(serialNumber = 0).showInformation() + "1 ${TypeResources.GOLD.name}"
            TypeBuilding.CONSUMER_CIRCUS -> Circus(serialNumber = 0).showInformation() + "2 ${TypeResources.GOLD.name}"
            TypeBuilding.CONSUMER_CHURCH -> Church(serialNumber = 0).showInformation() + "3 ${TypeResources.GOLD.name}"
        }
    }
}