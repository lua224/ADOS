package com.josealfonsomora.ados.domain

data class Autobus(
    val id: Int = 0,
    val direccion: String = "",
    val fecha: String = "",
    val inicio: String = "09:00",
    val fin: String = "14:00"
)