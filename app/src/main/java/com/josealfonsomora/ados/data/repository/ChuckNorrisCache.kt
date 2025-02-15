package com.josealfonsomora.ados.data.repository

import com.josealfonsomora.ados.data.room.ChuckJokeDao
import com.josealfonsomora.ados.data.room.toDomain
import com.josealfonsomora.ados.data.room.toEntity
import com.josealfonsomora.ados.domain.ChuckJoke
import javax.inject.Inject

class ChuckNorrisCache @Inject constructor(
    private val dao: ChuckJokeDao
){
    fun getJoke(): ChuckJoke {
        return dao.getAll().first().toDomain()
    }
    fun saveJoke(joke: ChuckJoke){
        dao.insertAll(joke.toEntity())
    }
}
