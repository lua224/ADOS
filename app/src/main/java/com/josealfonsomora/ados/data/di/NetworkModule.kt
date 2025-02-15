package com.josealfonsomora.ados.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.josealfonsomora.ados.data.network.ChuckNorrisService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesJson() = Json {
        isLenient = true
        explicitNulls = false
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun providesChuckNorrisApi(json: Json): ChuckNorrisService {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        return retrofit.create(ChuckNorrisService::class.java)
    }
}