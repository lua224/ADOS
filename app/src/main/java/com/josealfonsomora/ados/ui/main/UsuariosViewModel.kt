package com.josealfonsomora.ados.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josealfonsomora.ados.domain.GetUsuarioUseCase
import com.josealfonsomora.ados.domain.insertarUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuariosViewModel @Inject constructor(
    private val getUsuario: GetUsuarioUseCase,
    private val insertarUsuario: insertarUsuarioUseCase,
): ViewModel() {

    private var _state = MutableStateFlow<UsuariosState>(UsuariosState.Loading)
    val state = _state

    var usuario = ""

    init{
        getUsuarioActual(usuario)
    }

    fun getUsuarioActual(usuario: String){
        viewModelScope.launch(IO) {
            val nombre = getUsuario(usuario)
            _state.value = UsuariosState.Loaded(nombre)
        }
    }

    fun insertarNuevoUsuario(usuario: String, nombre: String){
        viewModelScope.launch(IO) {
            insertarUsuario(usuario, nombre)
            getUsuarioActual(usuario)
        }
    }
}

sealed interface UsuariosState {
    data object Loading : UsuariosState
    data class Loaded(val nombre: String) : UsuariosState
    data object Error : UsuariosState
}