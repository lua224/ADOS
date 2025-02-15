package com.josealfonsomora.ados.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsuariosRepository @Inject constructor(context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

suspend fun getUsuario(nombre: String): String {
    var username: String = sharedPreferences.getString(nombre, "Invitado").toString()
    return username
}

    suspend fun insertarUsuario(usuario: String, nombre: String){
        with(sharedPreferences.edit()) {
            putString(usuario, nombre)
            apply()
        }
    }
}
