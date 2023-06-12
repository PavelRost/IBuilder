package com.example.ibuilder.model.building.consumer

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding

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
            Indicators.satisfactionCitizens += profit
            isProfitActivate = true
        }
    }
}