package com.example.ibuilder.model.building.producer

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding

data class StoneMine(
    override var name: String = "Каменный рудник",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.PRODUCER_STONE,
    override var constructionTime: Int = 4,
    override var profit: Int = 10,
    override var needWorkers: Int = 2,
    override val typeResources: TypeResources = TypeResources.STONE
): AbstractBuilding() {

    override fun createResources() {
        Indicators.allResources[typeResources] = Indicators.allResources[typeResources]!! + profit
    }
}