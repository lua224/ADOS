package com.josealfonsomora.ados.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface ChuckNorrisService {
    @GET("jokes/random")
    suspend fun getChuckNorrisJoke(): ChuckNorrisResponse
}

/**
 * {
 * "icon_url" : "https://api.chucknorris.io/img/avatar/chuck-norris.png",
 * "id" : "QGf8wj14St2f5FFdlV393g",
 * "url" : "",
 * "value" : "Chuck Norris considers Ben Hur to be a midget."
 * }
 */
@Serializable
data class ChuckNorrisResponse(
//    @SerialName("icon_url") val icon: String,
    val id: String,
//    val url: String,
    @SerialName("value") val joke: String
)


