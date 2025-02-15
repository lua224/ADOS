package com.josealfonsomora.ados.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChuckJokeDao {

    @Query("SELECT * FROM chuck_jokes")
    fun getAll(): List<ChuckJokeEntity>

    @Insert
    fun insertAll(vararg jokes: ChuckJokeEntity)

    @Delete
    fun delete(joke: ChuckJokeEntity)
}