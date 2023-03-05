package model.building.producer

import com.example.ibuilder.model.building.AbstractBuilding
import model.building.TypeBuilding
import model.indicatorsDB.TypeResources

data class FoodMine(
    override var name: String = "Ферма с едой",
    override val serialNumber: Int,
    override val typeBuild: TypeBuilding = TypeBuilding.PRODUCER_FOOD,
    override var constructionTime: Int = 1,
    override var profit: Int = 10,
    override var needWorkers: Int = 1,
    override val typeResources: TypeResources = TypeResources.FOOD
): AbstractBuilding() {

    override fun createResources() {
        resource.food = profit
    }
}