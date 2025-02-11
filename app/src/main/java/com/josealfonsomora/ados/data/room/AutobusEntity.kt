package com.josealfonsomora.ados.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "autobuses")
class AutobusEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name="direccion") val direccion: String,
    val fecha: String,
    @ColumnInfo(name="inicio", defaultValue = "9:00")  val inicio: String,
    @ColumnInfo(name="fin", defaultValue = "14:00")  val fin: String
)