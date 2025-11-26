package com.example.huertohogarapp.data.remote

import com.example.huertohogarapp.data.model.Producto
import retrofit2.http.GET

/**
 * Interfaz API para endpoints de productos
 * Consume la API de HuertoHogar
 */
interface ProductoApiService {
    
    @GET("api/v1/productos")
    suspend fun getProductos(): List<Producto>
}
