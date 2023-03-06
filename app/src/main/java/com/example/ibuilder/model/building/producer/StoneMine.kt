package model.building.producer

import com.example.ibuilder.model.building.AbstractBuilding
import model.building.TypeBuilding
import model.indicatorsDB.TypeResources

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
        resource.allResources[typeResources] = resource.allResources[typeResources]!! + profit
    }
}