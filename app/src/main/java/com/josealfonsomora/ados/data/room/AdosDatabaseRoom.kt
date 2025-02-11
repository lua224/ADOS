package com.josealfonsomora.ados.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AutobusEntity::class], version = 1, exportSchema = false)
abstract class AdosDatabaseRoom : RoomDatabase() {

    abstract fun autobusDao(): AutobusDao

    companion object {
        @Volatile
        private var INSTANCE: AdosDatabaseRoom? = null

        fun getDatabase(context: Context): AdosDatabaseRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AdosDatabaseRoom::class.java,
                    "ados_room"
                ).createFromAsset("initial_database.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}