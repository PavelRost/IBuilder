package com.example.ibuilder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buildingsWorking")
data class BuildingsWorking(
    @PrimaryKey
    val ID: Int,
    val PRODUCER_GOLD: Int,
    val PRODUCER_WOOD: Int,
    val PRODUCER_STONE: Int,
    val PRODUCER_FOOD: Int,
    val PRODUCER_WORKER: Int,
    val CONSUMER_TAVERN: Int,
    val CONSUMER_CIRCUS: Int,
    val CONSUMER_CHURCH: Int
)
