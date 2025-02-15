package com.josealfonsomora.ados.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.josealfonsomora.ados.domain.Autobus

@Dao
interface AutobusDao {

    @Insert
    suspend fun insert(autobus: AutobusEntity)

    @Query("SELECT * FROM autobuses")
    suspend fun getAll(): List<AutobusEntity>

    @Delete
    suspend fun deleteAutobus(autobus: AutobusEntity)
}