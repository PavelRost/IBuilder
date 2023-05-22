package com.example.ibuilder.model.building.consumer

import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import com.example.ibuilder.model.indicatorsDB.TypeResources

data class Circus(
    override var name: String = "Цирк",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.CONSUMER_CIRCUS,
    override var constructionTime: Int = 3,
    override var profit: Int = 7,
    override var needWorkers: Int = 5,
    override val typeResources: TypeResources = TypeResources.SATISFACTION
) : AbstractBuilding() {

    override fun createResources() {
        if (!isProfitActivate) {
            OtherIndicators.satisfactionCitizens = profit
            isProfitActivate = true
        }
    }
}