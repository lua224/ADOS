package com.josealfonsomora.ados.domain

import com.josealfonsomora.ados.data.repository.UsuariosRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class insertarUsuarioUseCase @Inject constructor(
    private val repository: UsuariosRepository
){
    suspend operator fun invoke(nombre: String, contrasena: String){
        return withContext(IO){
            repository.insertarUsuario(nombre, contrasena)
        }
    }
}
