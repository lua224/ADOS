package com.josealfonsomora.ados.domain

import com.josealfonsomora.ados.data.repository.AutobusRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertAutobusUseCase @Inject constructor(
    private val repository: AutobusRepository
){
    suspend operator fun invoke(autobus: Autobus){
        withContext(IO){
            repository.InsertAutobus(autobus)
        }
    }

}
