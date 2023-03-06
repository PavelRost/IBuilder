package model.building.producer

import com.example.ibuilder.model.building.AbstractBuilding
import model.building.TypeBuilding
import model.indicatorsDB.TypeResources

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
        resource.allResources[typeResources] = resource.allResources[typeResources]!! + profit
    }
}