package com.josealfonsomora.ados.ui.main

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModel
import com.josealfonsomora.ados.data.ReadableSqlLite
import com.josealfonsomora.ados.data.WritableSqlLite
import com.josealfonsomora.ados.data.room.AdosDatabaseRoom
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite
import com.josealfonsomora.ados.domain.Autobus
import com.josealfonsomora.ados.ui.main.MainScreenState.Loaded
import com.josealfonsomora.ados.ui.main.MainScreenState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ReadableSqlLite private val sqliteReadableSqlLite: SQLiteDatabase,
    @WritableSqlLite private val sqliteWritableSqlLite: SQLiteDatabase,
    private val sqliteHelper: AdosDatabaseSqlite,
    private val roomDb: AdosDatabaseRoom
) : ViewModel() {

    private var _state = MutableStateFlow<MainScreenState>(Loading)
    val state = _state

    init {
        fetchAutobusList()
    }

    private fun fetchAutobusList() {
        val list = sqliteHelper.getAllAutobuses()
        _state.value = Loaded(list)
    }

    fun deleteAutobus(autobus: Autobus) {
        sqliteHelper.deleteAutobus(autobus.id)
        fetchAutobusList()
    }
}

sealed interface MainScreenState {
    data object Loading : MainScreenState
    data class Loaded(val list: List<Autobus>) : MainScreenState
    data object Error : MainScreenState
}