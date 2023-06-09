package com.example.ibuilder.model.building.producer

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding

data class HouseWorker(
    override var name: String = "Жилище работников",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.PRODUCER_WORKER,
    override var constructionTime: Int = 1,
    override var profit: Int = 1,
    override val typeResources: TypeResources = TypeResources.WORKER,
): AbstractBuilding() {

    private var capacityHouse: Int = 2

    override fun createResources() {
        if (capacityHouse > 0) {
            Indicators.totalWorkers = Indicators.totalWorkers + profit
            Indicators.freeWorkers = Indicators.freeWorkers + profit
            capacityHouse--
        }
    }

    fun getCapacityHouse(): Int {
        return capacityHouse
    }

    fun setCapacityHouse(capacity: Int) {
        capacityHouse = capacity
    }

    fun addCapacity() {
        if (capacityHouse == 2) {
            return
        }
        capacityHouse++
    }
}