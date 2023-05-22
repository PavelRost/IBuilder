package com.example.ibuilder.model.building

enum class TypeBuilding(val requiredEra: Int) {
    PRODUCER_FOOD(0),
    PRODUCER_GOLD(0),
    PRODUCER_WOOD(0),
    PRODUCER_STONE(0),
    PRODUCER_WORKER(0),
    CONSUMER_TAVERN(1),
    CONSUMER_CIRCUS(2),
    CONSUMER_CHURCH(3)
}