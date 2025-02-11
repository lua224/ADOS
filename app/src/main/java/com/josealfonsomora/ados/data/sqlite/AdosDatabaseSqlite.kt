package com.josealfonsomora.ados.data.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite.AdosDatabaseContract.BusEntry.COLUMN_DIRECCION
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite.AdosDatabaseContract.BusEntry.COLUMN_FECHA
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite.AdosDatabaseContract.BusEntry.COLUMN_FIN
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite.AdosDatabaseContract.BusEntry.COLUMN_ID
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite.AdosDatabaseContract.BusEntry.COLUMN_INICIO
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite.AdosDatabaseContract.BusEntry.TABLE_NAME
import com.josealfonsomora.ados.domain.Autobus
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class AdosDatabaseSqlite(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    object AdosDatabaseContract {
        object BusEntry : BaseColumns {
            const val TABLE_NAME = "autobuses"
            const val COLUMN_ID = "id"
            const val COLUMN_DIRECCION = "direccion"
            const val COLUMN_FECHA = "fecha"
            const val COLUMN_INICIO = "inicio"
            const val COLUMN_FIN = "fin"
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("""
            CREATE TABLE $TABLE_NAME ( 
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_DIRECCION TEXT,
            $COLUMN_FECHA TEXT,
            $COLUMN_INICIO TEXT,
            $COLUMN_FIN TEXT
            )""")
        db?.execSQL(createTable)

        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insertar un autobús
    fun insertAutobus(direccion: String, fecha: String, inicio: String, fin: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DIRECCION, direccion)
            put(COLUMN_FECHA, fecha)
            put(COLUMN_INICIO, inicio)
            put(COLUMN_FIN, fin)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    // Obtener todos los autobuses
    fun getAllAutobuses(): List<Autobus> {
        val db = this.readableDatabase

        // Definir las columnas que queremos recuperar (projection)
        val projection = arrayOf(
            COLUMN_ID, COLUMN_DIRECCION, COLUMN_FECHA, COLUMN_INICIO, COLUMN_FIN
        )

        // Realizar la consulta con projection
        val cursor = db.query(
            TABLE_NAME,      // Nombre de la tabla
            projection,      // Columnas a recuperar
            null,           // Clausula WHERE (null para todas las filas)
            null,           // Argumentos de la clausula WHERE
            null,           // GROUP BY
            null,           // HAVING
            null            // ORDER BY
        )

        val autobuses = mutableListOf<Autobus>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val direccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION))
            val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA))
            val inicio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INICIO))
            val fin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIN))

            autobuses.add(Autobus(id, direccion, fecha, inicio, fin))
        }
        cursor.close()
        return autobuses.toList()
    }

    // Actualizar un autobús
    fun updateAutobus(id: Int, direccion: String, fecha: String, inicio: String, fin: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DIRECCION, direccion)
            put(COLUMN_FECHA, fecha)
            put(COLUMN_INICIO, inicio)
            put(COLUMN_FIN, fin)
        }
        val result = db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    // Eliminar un autobús
    fun deleteAutobus(id: Int): Int {
        val db = this.writableDatabase
        val selection = "$COLUMN_ID = ?"
        val result = db.delete(TABLE_NAME, selection, arrayOf(id.toString()))
        db.close()
        return result
    }

    private fun insertInitialData(db: SQLiteDatabase?) {
        val direccionesGalicia = listOf(
            "Rúa do Franco, Santiago de Compostela",
            "Praza do Obradoiro, Santiago de Compostela",
            "Rúa Real, Pontevedra",
            "Praza da Leña, Vigo",
            "Rúa da Raiña, Lugo",
            "Praza Maior, Ourense",
            "Rúa da Torre, A Coruña",
            "Praza do Toural, Pontevedra",
            "Rúa do Sol, Vigo",
            "Praza de María Pita, A Coruña"
        )

        val fechaActual = Calendar.getInstance().let { calendar ->
            "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                calendar.get(
                    Calendar.YEAR
                )
            }"
        }

        for (i in 1..10) { // Insertar 10 autobuses iniciales
            val direccion = direccionesGalicia[Random.nextInt(direccionesGalicia.size)]
            val inicio = generarHoraAleatoria(Random, 9, 16) // Hora de inicio entre 9:00 y 16:00
            val fin = generarHoraAleatoria(
                Random,
                inicio.split(":")[0].toInt() + 1,
                21
            ) // Hora de fin entre inicio + 1 y 21:00

            val contentValues = ContentValues().apply {
                put(COLUMN_DIRECCION, direccion)
                put(COLUMN_FECHA, fechaActual)
                put(COLUMN_INICIO, inicio)
                put(COLUMN_FIN, fin)
            }
            db?.insert(TABLE_NAME, null, contentValues)
        }
    }

    private fun generarHoraAleatoria(random: Random, horaInicio: Int, horaFin: Int): String {
        val hora = random.nextInt(horaFin - horaInicio) + horaInicio
        val minuto = random.nextInt(60)
        return String.format(
            locale = Locale.getDefault(),
            format = "%02d:%02d",
            hora,
            minuto,
        )
    }

    companion object {
        private const val DATABASE_NAME = "ados_sqlite.db"
        private const val DATABASE_VERSION = 1
    }
}
