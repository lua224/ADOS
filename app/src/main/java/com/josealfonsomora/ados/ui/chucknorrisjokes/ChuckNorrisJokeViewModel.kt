package com.josealfonsomora.ados.ui.chucknorrisjokes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josealfonsomora.ados.data.network.ChuckNorrisService
import com.josealfonsomora.ados.ui.chucknorrisjokes.ChuckNorrisJokeState.Error
import com.josealfonsomora.ados.ui.chucknorrisjokes.ChuckNorrisJokeState.Loaded
import com.josealfonsomora.ados.ui.chucknorrisjokes.ChuckNorrisJokeState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChuckNorrisJokeViewModel @Inject constructor(
    private val api: ChuckNorrisService
) : ViewModel() {

    private var _state = MutableStateFlow<ChuckNorrisJokeState>(Loading)
    val state = _state

    init {
        getNewJoke()
    }

    fun onCardClicked() {
        viewModelScope.launch {
            getNewJoke()
        }
    }

    private fun getNewJoke() {
        viewModelScope.launch(IO) {
            try {
                val response = api.getChuckNorrisJoke()
                _state.value = Loaded(response.joke)
            } catch (e: Exception) {
                Log.e("Retrofit", "${e.message}")
                _state.value = Error(e.message?:"")
            }
        }
    }
}

sealed interface ChuckNorrisJokeState {
    data object Loading : ChuckNorrisJokeState
    data class Loaded(val joke: String) : ChuckNorrisJokeState
    data class Error(val message: String) : ChuckNorrisJokeState
}