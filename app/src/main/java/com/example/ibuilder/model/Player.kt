package com.example.ibuilder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @PrimaryKey
    val id: Int,
    val currentDay: Int,
    val availableUpdateTaxRate: Int,
    val taxRate: Int,
    val satisfactionCitizens: Int,
    val frequencyAttackNomad: Int,
    val currentEra: Int,
    val availableOperationExchange: Int,
    val totalWorkers: Int,
    val hiredWorkers: Int,
    val freeWorkers: Int,
    val resourceGold: Int,
    val resourceWood: Int,
    val resourceFood: Int,
    val resourceStone: Int
)
