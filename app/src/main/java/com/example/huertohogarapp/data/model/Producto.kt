package com.example.huertohogarapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para Producto
 * Basado en la respuesta de la API
 */
data class Producto(
    @SerializedName("idProducto")
    val idProducto: Int,
    
    @SerializedName("nombreProducto")
    val nombreProducto: String,
    
    @SerializedName("categoria")
    val categoria: Categoria,
    
    @SerializedName("descripcionProducto")
    val descripcionProducto: String,
    
    @SerializedName("precioProducto")
    val precioProducto: Double,
    
    @SerializedName("stockProducto")
    val stockProducto: Int,
    
    @SerializedName("paisOrigen")
    val paisOrigen: PaisOrigen,
    
    @SerializedName("imagenUrl")
    val imagenUrl: String
)
