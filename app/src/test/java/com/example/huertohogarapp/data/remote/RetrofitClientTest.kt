package com.example.huertohogarapp.data.remote

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para RetrofitClient
 */
class RetrofitClientTest {

    @Test
    fun `productoApiService no es null`() {
        val apiService = RetrofitClient.productoApiService
        assertNotNull(apiService)
    }

    @Test
    fun `productoApiService es singleton`() {
        val apiService1 = RetrofitClient.productoApiService
        val apiService2 = RetrofitClient.productoApiService
        
        assertSame(apiService1, apiService2)
    }

    @Test
    fun `productoApiService implementa ProductoApiService`() {
        val apiService = RetrofitClient.productoApiService
        assertTrue(apiService is ProductoApiService)
    }

    @Test
    fun `RetrofitClient es object singleton`() {
        val client1 = RetrofitClient
        val client2 = RetrofitClient
        
        assertSame(client1, client2)
    }
}
