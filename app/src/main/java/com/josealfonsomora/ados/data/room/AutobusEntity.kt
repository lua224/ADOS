package com.josealfonsomora.ados.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.josealfonsomora.ados.domain.Autobus

@Entity(tableName = "autobuses")
class AutobusEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name="direccion") val direccion: String,
    val fecha: String,
    @ColumnInfo(name="inicio", defaultValue = "9:00")  val inicio: String,
    @ColumnInfo(name="fin", defaultValue = "14:00")  val fin: String
)

fun List<AutobusEntity>.toDomain(): List<Autobus> {
    return this.map { autobusEntity ->
        Autobus(
            id = autobusEntity.id,
            direccion = autobusEntity.direccion,
            fecha = autobusEntity.fecha,
            inicio = autobusEntity.inicio,
            fin = autobusEntity.fin
        )
    }
}

fun Autobus.toEntity(): AutobusEntity {
    return AutobusEntity(
        id = this.id,
        direccion = this.direccion,
        fecha = this.fecha,
        inicio = this.inicio,
        fin = this.fin
    )
}