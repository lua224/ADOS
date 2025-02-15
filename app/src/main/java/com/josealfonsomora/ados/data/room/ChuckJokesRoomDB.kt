package com.josealfonsomora.ados.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChuckJokeEntity::class], version = 1, exportSchema = false)
abstract class ChuckJokesRoomDB: RoomDatabase() {
    abstract fun ChuckJokeDao(): ChuckJokeDao
}