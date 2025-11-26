package com.example.huertohogarapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para Categoría
 * Basado en la respuesta de la API
 */
data class Categoria(
    @SerializedName("idCategoria")
    val idCategoria: Int,
    
    @SerializedName("nombreCategoria")
    val nombreCategoria: String,
    
    @SerializedName("descripcionCategoria")
    val descripcionCategoria: String
)
