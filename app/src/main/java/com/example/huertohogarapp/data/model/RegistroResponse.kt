package com.example.huertohogarapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de respuesta del API al registrar usuario
 */
data class RegistroResponse(
    @SerializedName("id")
    val id: Long? = null,
    
    @SerializedName("message")
    val message: String? = null,
    
    @SerializedName("success")
    val success: Boolean = false
)
