package com.example.huertohogarapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para País de Origen
 * Basado en la respuesta de la API
 */
data class PaisOrigen(
    @SerializedName("idPais")
    val idPais: Int,
    
    @SerializedName("nombre")
    val nombre: String
)
