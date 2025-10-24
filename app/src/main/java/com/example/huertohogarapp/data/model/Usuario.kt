package com.example.huertohogarapp.data.model

import kotlinx.serialization.Serializable

/**
 * Modelo de datos para Usuario
 * Arquitectura MVVM
 */
@Serializable
data class Usuario(
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val fechaNacimiento: String = "", // Formato: yyyy-MM-dd
    val fotoPerfil: String = "" // URI de la imagen
)
