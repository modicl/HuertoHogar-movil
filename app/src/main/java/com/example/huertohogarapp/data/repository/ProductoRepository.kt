package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repositorio para manejar operaciones de Productos
 * Arquitectura MVVM - Capa de datos
 */
interface ProductoRepository {
    fun getProductos(): Flow<List<Producto>>
    fun getProductoById(id: Int): Flow<Producto?>
    fun searchProductos(query: String): Flow<List<Producto>>
}

/**
 * Implementación del repositorio de productos
 * Consume la API de HuertoHogar
 */
class ProductoRepositoryImpl : ProductoRepository {
    
    private val apiService = RetrofitClient.productoApiService
    
    override fun getProductos(): Flow<List<Producto>> = flow {
        try {
            val productos = apiService.getProductos()
            emit(productos)
        } catch (e: Exception) {
            // En caso de error, emitir lista vacía
            emit(emptyList())
        }
    }
    
    override fun getProductoById(id: Int): Flow<Producto?> = flow {
        try {
            val productos = apiService.getProductos()
            val producto = productos.find { it.idProducto == id }
            emit(producto)
        } catch (e: Exception) {
            emit(null)
        }
    }
    
    override fun searchProductos(query: String): Flow<List<Producto>> = flow {
        try {
            val productos = apiService.getProductos()
            val productosFiltrados = productos.filter { 
                it.nombreProducto.contains(query, ignoreCase = true) ||
                it.descripcionProducto.contains(query, ignoreCase = true) ||
                it.categoria.nombreCategoria.contains(query, ignoreCase = true)
            }
            emit(productosFiltrados)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
