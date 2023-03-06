package model.indicatorsDB

object Resource {

    var gold: Int = 20
        set(value) {
            field += value
        }
    var food: Int = 20
        set(value) {
            field += value
        }
    var wood: Int = 10
        set(value) {
            field += value
        }
    var stone: Int = 0
        set(value) {
            field += value
        }

    val allResources = mutableMapOf(
        TypeResources.GOLD to 20,
        TypeResources.WOOD to 10,
        TypeResources.FOOD to 20,
        TypeResources.STONE to 0
    )

}