package com.example.ibuilder.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BuildingsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBuildings(buildings: Buildings)

    @Query("SELECT * FROM buildings")
    fun getAllBuildings(): List<Buildings>

}