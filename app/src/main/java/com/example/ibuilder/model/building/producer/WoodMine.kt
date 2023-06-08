package com.example.ibuilder.model.building.producer

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding

data class WoodMine(
    override var name: String = "Лесопилка",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.PRODUCER_WOOD,
    override var constructionTime: Int = 2,
    override var profit: Int = 10,
    override var needWorkers: Int = 2,
    override val typeResources: TypeResources = TypeResources.WOOD
): AbstractBuilding() {

    override fun createResources() {
        Indicators.allResources[typeResources] = Indicators.allResources[typeResources]!! + profit
    }
}