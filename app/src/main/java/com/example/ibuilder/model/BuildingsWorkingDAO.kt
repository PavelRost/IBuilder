package com.example.ibuilder.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BuildingsWorkingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBuildingsWorking(buildings: BuildingsWorking)

    @Query("SELECT * FROM buildingsWorking")
    fun getAllBuildingsWorking(): List<BuildingsWorking>

}