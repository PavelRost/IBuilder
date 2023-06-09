package com.example.ibuilder.service

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ibuilder.model.*
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.building.consumer.Church
import com.example.ibuilder.model.building.consumer.Circus
import com.example.ibuilder.model.building.consumer.Tavern
import com.example.ibuilder.model.building.producer.*
import kotlin.concurrent.thread

class DatabaseService(
    application: Application,
) : AndroidViewModel(application) {

    private var databaseApp: DatabaseApp = DatabaseApp.getInstance(application)!!

    fun initAllIndicators() {
        var players: List<Player>
        var buildings: List<Buildings>
        var buildingsWorking: List<BuildingsWorking>
        thread {
            players = databaseApp.PlayerDAO().getAll()
            if (players.isNotEmpty()) {
                Indicators.idPlayer = players[0].id
                Indicators.currentDay = players[0].currentDay
                Indicators.availableUpdateTaxRate = players[0].availableUpdateTaxRate
                Indicators.taxRate = players[0].taxRate
                Indicators.tmpTaxRate = players[0].taxRate
                Indicators.satisfactionCitizens = players[0].satisfactionCitizens
                Indicators.frequencyAttackNomad = players[0].frequencyAttackNomad
                Indicators.currentEra = players[0].currentEra
                Indicators.availableOperationExchange = players[0].availableOperationExchange
                Indicators.allResources[TypeResources.GOLD] = players[0].resourceGold
                Indicators.allResources[TypeResources.WOOD] = players[0].resourceWood
                Indicators.allResources[TypeResources.FOOD] = players[0].resourceFood
                Indicators.allResources[TypeResources.STONE] = players[0].resourceStone
                Indicators.totalWorkers = players[0].totalWorkers
                Indicators.hiredWorkers = players[0].hiredWorkers
                Indicators.freeWorkers = players[0].freeWorkers
            }

            buildings = databaseApp.BuildingsDAO().getAllBuildings()
            if (buildings.isNotEmpty()) {
                if (buildings[0].PRODUCER_GOLD > 0) {
                    repeat(buildings[0].PRODUCER_GOLD) {
                        val buildingNew = GoldMine(serialNumber = it)
                        buildingNew.constructionTime = 0
                        Indicators.building[TypeBuilding.PRODUCER_GOLD]?.add(buildingNew)
                    }
                }
                if (buildings[0].PRODUCER_WOOD > 0) {
                    repeat(buildings[0].PRODUCER_WOOD) {
                        val buildingNew = WoodMine(serialNumber = it)
                        buildingNew.constructionTime = 0
                        Indicators.building[TypeBuilding.PRODUCER_WOOD]?.add(buildingNew)
                    }
                }
                if (buildings[0].PRODUCER_FOOD > 0) {
                    repeat(buildings[0].PRODUCER_FOOD) {
                        val buildingNew = FoodMine(serialNumber = it)
                        buildingNew.constructionTime = 0
                        Indicators.building[TypeBuilding.PRODUCER_FOOD]?.add(buildingNew)
                    }
                }
                if (buildings[0].PRODUCER_STONE > 0) {
                    repeat(buildings[0].PRODUCER_STONE) {
                        val buildingNew = StoneMine(serialNumber = it)
                        buildingNew.constructionTime = 0
                        Indicators.building[TypeBuilding.PRODUCER_STONE]?.add(buildingNew)
                    }
                }
                if (buildings[0].PRODUCER_WORKER > 0) {
                    repeat(buildings[0].PRODUCER_WORKER) {
                        val buildingNew = HouseWorker(serialNumber = it)
                        buildingNew.constructionTime = 0
                        buildingNew.setCapacityHouseZero()
                        Indicators.building[TypeBuilding.PRODUCER_WORKER]?.add(buildingNew)
                    }
                }
                if (buildings[0].CONSUMER_TAVERN > 0) {
                    repeat(buildings[0].CONSUMER_TAVERN) {
                        val buildingNew = Tavern(serialNumber = it)
                        buildingNew.constructionTime = 0
                        Indicators.building[TypeBuilding.CONSUMER_TAVERN]?.add(buildingNew)
                    }
                }
                if (buildings[0].CONSUMER_CIRCUS > 0) {
                    repeat(buildings[0].CONSUMER_CIRCUS) {
                        val buildingNew = Circus(serialNumber = it)
                        buildingNew.constructionTime = 0
                        Indicators.building[TypeBuilding.CONSUMER_CIRCUS]?.add(buildingNew)
                    }
                }
                if (buildings[0].CONSUMER_CHURCH > 0) {
                    repeat(buildings[0].CONSUMER_CHURCH) {
                        val buildingNew = Church(serialNumber = it)
                        buildingNew.constructionTime = 0
                        Indicators.building[TypeBuilding.CONSUMER_CHURCH]?.add(buildingNew)
                    }
                }
            }

            buildingsWorking = databaseApp.BuildingsWorkingDAO().getAllBuildingsWorking()
            if (buildingsWorking.isNotEmpty()) {
                if (buildingsWorking[0].PRODUCER_GOLD > 0) {
                    repeat(buildingsWorking[0].PRODUCER_GOLD) {
                        Indicators.building[TypeBuilding.PRODUCER_GOLD]?.forEach {
                            it.hiredWorkers = it.needWorkers
                        }
                    }
                }
                if (buildingsWorking[0].PRODUCER_WOOD > 0) {
                    repeat(buildingsWorking[0].PRODUCER_WOOD) {
                        Indicators.building[TypeBuilding.PRODUCER_WOOD]?.forEach {
                            it.hiredWorkers = it.needWorkers
                        }
                    }
                }
                if (buildingsWorking[0].PRODUCER_FOOD > 0) {
                    repeat(buildingsWorking[0].PRODUCER_FOOD) {
                        Indicators.building[TypeBuilding.PRODUCER_FOOD]?.forEach {
                            it.hiredWorkers = it.needWorkers
                        }
                    }
                }
                if (buildingsWorking[0].PRODUCER_STONE > 0) {
                    repeat(buildingsWorking[0].PRODUCER_STONE) {
                        Indicators.building[TypeBuilding.PRODUCER_STONE]?.forEach {
                            it.hiredWorkers = it.needWorkers
                        }
                    }
                }
                if (buildingsWorking[0].CONSUMER_TAVERN > 0) {
                    repeat(buildingsWorking[0].CONSUMER_TAVERN) {
                        Indicators.building[TypeBuilding.CONSUMER_TAVERN]?.forEach {
                            it.hiredWorkers = it.needWorkers
                            it.isProfitActivate = true
                        }
                    }
                }
                if (buildingsWorking[0].CONSUMER_CIRCUS > 0) {
                    repeat(buildingsWorking[0].CONSUMER_CIRCUS) {
                        Indicators.building[TypeBuilding.CONSUMER_CIRCUS]?.forEach {
                            it.hiredWorkers = it.needWorkers
                            it.isProfitActivate = true
                        }
                    }
                }
                if (buildingsWorking[0].CONSUMER_CHURCH > 0) {
                    repeat(buildingsWorking[0].CONSUMER_CHURCH) {
                        Indicators.building[TypeBuilding.CONSUMER_CHURCH]?.forEach {
                            it.hiredWorkers = it.needWorkers
                            it.isProfitActivate = true
                        }
                    }
                }
            }
        }
    }

    fun saveAllIndicators() {
        val player = Player(
            id = Indicators.idPlayer,
            currentDay = Indicators.currentDay,
            availableUpdateTaxRate = Indicators.availableUpdateTaxRate,
            taxRate = Indicators.taxRate,
            satisfactionCitizens = Indicators.satisfactionCitizens,
            frequencyAttackNomad = Indicators.frequencyAttackNomad,
            totalWorkers = Indicators.totalWorkers,
            hiredWorkers = Indicators.hiredWorkers,
            freeWorkers = Indicators.freeWorkers,
            resourceGold = Indicators.allResources[TypeResources.GOLD]!!,
            resourceWood = Indicators.allResources[TypeResources.WOOD]!!,
            resourceFood = Indicators.allResources[TypeResources.FOOD]!!,
            resourceStone = Indicators.allResources[TypeResources.STONE]!!,
            currentEra = Indicators.currentEra,
            availableOperationExchange = Indicators.availableOperationExchange
        )
        val buildings = Buildings(
            ID = Indicators.idPlayer,
            PRODUCER_GOLD = Indicators.building[TypeBuilding.PRODUCER_GOLD]?.size!!,
            PRODUCER_FOOD = Indicators.building[TypeBuilding.PRODUCER_FOOD]?.size!!,
            PRODUCER_STONE = Indicators.building[TypeBuilding.PRODUCER_STONE]?.size!!,
            PRODUCER_WOOD = Indicators.building[TypeBuilding.PRODUCER_WOOD]?.size!!,
            PRODUCER_WORKER = Indicators.building[TypeBuilding.PRODUCER_WORKER]?.size!!,
            CONSUMER_TAVERN = Indicators.building[TypeBuilding.CONSUMER_TAVERN]?.size!!,
            CONSUMER_CIRCUS = Indicators.building[TypeBuilding.CONSUMER_CIRCUS]?.size!!,
            CONSUMER_CHURCH = Indicators.building[TypeBuilding.CONSUMER_CHURCH]?.size!!
        )
        val buildingsWorking = BuildingsWorking(
            ID = Indicators.idPlayer,
            PRODUCER_GOLD = Indicators.building[TypeBuilding.PRODUCER_GOLD]?.filter { it.hiredWorkers > 0 }!!.size,
            PRODUCER_FOOD = Indicators.building[TypeBuilding.PRODUCER_FOOD]?.filter { it.hiredWorkers > 0 }!!.size,
            PRODUCER_STONE = Indicators.building[TypeBuilding.PRODUCER_STONE]?.filter { it.hiredWorkers > 0 }!!.size,
            PRODUCER_WOOD = Indicators.building[TypeBuilding.PRODUCER_WOOD]?.filter { it.hiredWorkers > 0 }!!.size,
            PRODUCER_WORKER = Indicators.building[TypeBuilding.PRODUCER_WORKER]?.filter { it.hiredWorkers > 0 }!!.size,
            CONSUMER_TAVERN = Indicators.building[TypeBuilding.CONSUMER_TAVERN]?.filter { it.hiredWorkers > 0 }!!.size,
            CONSUMER_CIRCUS = Indicators.building[TypeBuilding.CONSUMER_CIRCUS]?.filter { it.hiredWorkers > 0 }!!.size,
            CONSUMER_CHURCH = Indicators.building[TypeBuilding.CONSUMER_CHURCH]?.filter { it.hiredWorkers > 0 }!!.size
        )
        thread {
            databaseApp.PlayerDAO().add(player)
            databaseApp.BuildingsDAO().addBuildings(buildings)
            databaseApp.BuildingsWorkingDAO().addBuildingsWorking(buildingsWorking)
        }
    }
}