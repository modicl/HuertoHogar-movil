package com.example.huertohogarapp.data.model

import kotlinx.serialization.Serializable

/**
 * Modelo de datos para Producto
 * Basado en el proyecto React
 */
@Serializable
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val categoria: String,
    val stock: Int,
    val destacado: Boolean = false
)
