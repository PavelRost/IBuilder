package com.example.ibuilder.model.building.producer

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding

data class GoldMine(
    override var name: String = "Золотой рудник",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.PRODUCER_GOLD,
    override var constructionTime: Int = 4,
    override var profit: Int = 10,
    override var needWorkers: Int = 2,
    override val typeResources: TypeResources = TypeResources.GOLD
): AbstractBuilding() {

    override fun createResources() {
        Indicators.allResources[typeResources] = Indicators.allResources[typeResources]!! + profit
    }
}