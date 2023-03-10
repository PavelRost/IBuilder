package com.example.ibuilder.model.building.producer

import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.indicatorsDB.TypeResources

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
        resource.allResources[typeResources] = resource.allResources[typeResources]!! + profit
    }
}