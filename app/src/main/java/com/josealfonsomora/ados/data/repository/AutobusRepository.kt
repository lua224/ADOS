package com.josealfonsomora.ados.data.repository

import android.content.Context
import com.josealfonsomora.ados.data.room.AdosDatabaseRoom
import com.josealfonsomora.ados.data.room.toEntity
import com.josealfonsomora.ados.data.room.toDomain
import com.josealfonsomora.ados.data.room.AutobusDao
import com.josealfonsomora.ados.domain.Autobus
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AutobusRepository @Inject constructor(@ApplicationContext private val context: Context){
    private val db: AdosDatabaseRoom = AdosDatabaseRoom.getDatabase(context)
    private val dao: AutobusDao = db.autobusDao()

    suspend fun getAutobuses(): List<Autobus>{
    return dao.getAll().toDomain()
    }

    suspend fun InsertAutobus(autobus: Autobus){
        dao.insert(autobus.toEntity())
    }
}


