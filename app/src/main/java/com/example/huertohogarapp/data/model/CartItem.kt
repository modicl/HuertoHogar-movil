package com.example.huertohogarapp.data.model

import kotlinx.serialization.Serializable

/**
 * Modelo de datos para item del carrito
 */
@Serializable
data class CartItem(
    val producto: Producto,
    val cantidad: Int
)
