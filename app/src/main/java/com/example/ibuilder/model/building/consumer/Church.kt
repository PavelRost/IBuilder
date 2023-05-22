package com.example.ibuilder.model.building.consumer

import com.example.ibuilder.model.building.AbstractBuilding
import com.example.ibuilder.model.building.TypeBuilding
import com.example.ibuilder.model.indicatorsDB.OtherIndicators
import com.example.ibuilder.model.indicatorsDB.TypeResources

data class Church(
    override var name: String = "Церковь",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.CONSUMER_CHURCH,
    override var constructionTime: Int = 5,
    override var profit: Int = 9,
    override var needWorkers: Int = 3,
    override val typeResources: TypeResources = TypeResources.SATISFACTION
) : AbstractBuilding() {

    override fun createResources() {
        if (!isProfitActivate) {
            OtherIndicators.satisfactionCitizens = profit
            isProfitActivate = true
        }
    }
}