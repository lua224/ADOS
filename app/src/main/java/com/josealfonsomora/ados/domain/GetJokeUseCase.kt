package com.josealfonsomora.ados.domain

import com.josealfonsomora.ados.data.repository.ChuckNorrisRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetJokeUseCase @Inject constructor(
    private val repository: ChuckNorrisRepository
) {
    suspend operator fun invoke(): ChuckJoke{
        return withContext(IO){
             repository.getJoke()
        }

    }
}

