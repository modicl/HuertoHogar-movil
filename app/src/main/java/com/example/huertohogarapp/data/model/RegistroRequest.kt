package com.example.huertohogarapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para la petición de registro de usuario
 * Representa el body del POST a /api/v1/usuarios
 */
data class RegistroRequest(
    @SerializedName("nombre")
    val nombre: String,
    
    @SerializedName("sNombre")
    val segundoNombre: String,
    
    @SerializedName("aPaterno")
    val apellidoPaterno: String,
    
    @SerializedName("aMaterno")
    val apellidoMaterno: String,
    
    @SerializedName("rut")
    val rut: String,
    
    @SerializedName("dv")
    val digitoVerificador: String,
    
    @SerializedName("fechaNacimiento")
    val fechaNacimiento: String,
    
    @SerializedName("idRegion")
    val idRegion: Int,
    
    @SerializedName("direccion")
    val direccion: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("telefono")
    val telefono: String,
    
    @SerializedName("passwordHashed")
    val password: String
)
