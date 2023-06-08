package com.example.ibuilder.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(player: Player)

    @Query("SELECT * FROM players")
    fun getAll(): List<Player>

}