package com.example.huertohogarapp.data.remote

import com.example.huertohogarapp.data.model.RegistroRequest
import com.example.huertohogarapp.data.model.RegistroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz API para endpoints de usuarios
 * Consume la API de HuertoHogar Usuarios
 */
interface UsuarioApiService {
    
    @POST("api/v1/usuarios")
    suspend fun registrarUsuario(@Body registroRequest: RegistroRequest): Response<RegistroResponse>
}
