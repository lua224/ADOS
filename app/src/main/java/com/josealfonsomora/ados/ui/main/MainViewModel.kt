package com.josealfonsomora.ados.ui.main

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josealfonsomora.ados.data.di.ReadableSqlLite
import com.josealfonsomora.ados.data.di.WritableSqlLite
import com.josealfonsomora.ados.data.repository.AutobusRepository
import com.josealfonsomora.ados.data.room.AdosDatabaseRoom
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite
import com.josealfonsomora.ados.domain.Autobus
import com.josealfonsomora.ados.domain.GetAutobusUseCase
import com.josealfonsomora.ados.domain.GetUsuarioUseCase
import com.josealfonsomora.ados.domain.InsertAutobusUseCase
import com.josealfonsomora.ados.domain.insertarUsuarioUseCase
import com.josealfonsomora.ados.ui.chucknorrisjokes.ChuckNorrisJokeState
import com.josealfonsomora.ados.ui.main.MainScreenState.Loaded
import com.josealfonsomora.ados.ui.main.MainScreenState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ReadableSqlLite private val sqliteReadableSqlLite: SQLiteDatabase,
    @WritableSqlLite private val sqliteWritableSqlLite: SQLiteDatabase,
    private val sqliteHelper: AdosDatabaseSqlite,
    private val roomDb: AdosDatabaseRoom,

    private val getAutobus: GetAutobusUseCase,
    private val insertAutobus: InsertAutobusUseCase

    ) : ViewModel() {

    private var _state = MutableStateFlow<MainScreenState>(Loading)
    val state = _state

    init {
        viewModelScope.launch {
            _state.value = MainScreenState.Loaded(getAutobus())
        }
    }

    fun insertAutobuses(autobus: Autobus){
        viewModelScope.launch(IO) {
            insertAutobus(autobus)
        }
    }

    private fun getAutobuses(){
        viewModelScope.launch(IO) {
            _state.value = MainScreenState.Loaded(getAutobus())
        }
    }

    private fun fetchAutobusList() {
        viewModelScope.launch(IO) {
            val list = sqliteHelper.getAllAutobuses()
            _state.value = Loaded(list)
        }
    }

    fun deleteAutobus(autobus: Autobus) {
        viewModelScope.launch(IO) {
            sqliteHelper.deleteAutobus(autobus.id)
            fetchAutobusList()
        }
    }
}

sealed interface MainScreenState {
    data object Loading : MainScreenState
    data class Loaded(val list: List<Autobus>) : MainScreenState
    data object Error : MainScreenState
}