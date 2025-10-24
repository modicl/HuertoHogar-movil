package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repositorio para manejar operaciones de Productos
 * Arquitectura MVVM - Capa de datos
 */
interface ProductoRepository {
    fun getProductos(): Flow<List<Producto>>
    fun getProductoById(id: Int): Flow<Producto?>
    fun getProductosDestacados(): Flow<List<Producto>>
    fun searchProductos(query: String): Flow<List<Producto>>
}

/**
 * Implementación del repositorio de productos
 * Por ahora con datos mock, en futuro se conectará a API/BD
 */
class ProductoRepositoryImpl : ProductoRepository {
    
    override fun getProductos(): Flow<List<Producto>> = flow {
        // TODO: Implementar llamada a API o base de datos
        emit(emptyList())
    }
    
    override fun getProductoById(id: Int): Flow<Producto?> = flow {
        // TODO: Implementar obtención de producto específico
        emit(null)
    }
    
    override fun getProductosDestacados(): Flow<List<Producto>> = flow {
        // TODO: Implementar obtención de productos destacados
        emit(emptyList())
    }
    
    override fun searchProductos(query: String): Flow<List<Producto>> = flow {
        // TODO: Implementar búsqueda de productos
        emit(emptyList())
    }
}
