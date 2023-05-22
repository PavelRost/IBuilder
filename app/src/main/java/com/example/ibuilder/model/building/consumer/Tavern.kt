package com.example.ibuilder.model.building.consumer

import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import com.example.ibuilder.model.indicatorsDB.TypeResources

data class Tavern(
    override var name: String = "Таверна",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.CONSUMER_TAVERN,
    override var constructionTime: Int = 2,
    override var profit: Int = 3,
    override var needWorkers: Int = 2,
    override val typeResources: TypeResources = TypeResources.SATISFACTION
) : AbstractBuilding() {

    override fun createResources() {
        if (!isProfitActivate) {
            OtherIndicators.satisfactionCitizens = profit
            isProfitActivate = true
        }
    }
}