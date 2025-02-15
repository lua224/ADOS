package com.josealfonsomora.ados.data.repository

import com.josealfonsomora.ados.data.network.ChuckNorrisResponse
import com.josealfonsomora.ados.data.network.ChuckNorrisService
import com.josealfonsomora.ados.domain.ChuckJoke
import javax.inject.Inject

class ChuckNorrisRepository @Inject constructor(
    private val api: ChuckNorrisService,
    private val cache: ChuckNorrisCache
) {
    suspend fun getJoke(): ChuckJoke = try {
        val response = api.getChuckNorrisJoke()
        ChuckJoke(response.id, response.joke)
    } catch(e: Exception){
                cache.getJoke()
    }
}