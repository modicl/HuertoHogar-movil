package com.example.huertohogarapp.data.model

/**
 * Modelo de datos para información de contacto
 */
data class ContactForm(
    val nombre: String,
    val email: String,
    val mensaje: String,
    val telefono: String? = null
)
