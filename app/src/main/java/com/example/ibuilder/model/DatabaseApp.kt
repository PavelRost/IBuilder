package com.example.ibuilder.model

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Player::class, Buildings::class, BuildingsWorking::class],
    version = 4,
    exportSchema = false
)
abstract class DatabaseApp : RoomDatabase() {

    companion object {
        private var instance: DatabaseApp? = null
        private const val DB_NAME = "Ibuilder.db"

        fun getInstance(application: Application): DatabaseApp? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application,
                    DatabaseApp::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }

    abstract fun PlayerDAO(): PlayerDAO

    abstract fun BuildingsDAO(): BuildingsDAO

    abstract fun BuildingsWorkingDAO(): BuildingsWorkingDAO

}