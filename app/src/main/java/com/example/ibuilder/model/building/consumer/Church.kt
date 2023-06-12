package com.example.ibuilder.model.building.consumer

import com.example.ibuilder.model.Indicators
import com.example.ibuilder.model.TypeResources
import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding

data class Church(
    override var name: String = "Храм",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.CONSUMER_CHURCH,
    override var constructionTime: Int = 5,
    override var profit: Int = 9,
    override var needWorkers: Int = 3,
    override val typeResources: TypeResources = TypeResources.SATISFACTION
) : AbstractBuilding() {

    override fun createResources() {
        if (!isProfitActivate) {
            Indicators.satisfactionCitizens += profit
            isProfitActivate = true
        }
    }
}