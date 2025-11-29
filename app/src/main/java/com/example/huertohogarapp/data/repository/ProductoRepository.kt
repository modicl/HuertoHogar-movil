package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.data.remote.ProductoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

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
class ProductoRepositoryImpl(
    private val apiService: ProductoApiService
) : ProductoRepository {

    override fun getProductos(): Flow<List<Producto>> = flow {
        emit(apiService.getProductos())
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(emptyList())
    }

    override fun getProductoById(id: Int): Flow<Producto?> = flow {
        val productos = apiService.getProductos()
        val producto = productos.find { it.idProducto == id }
        emit(producto)
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(null)
    }

    override fun searchProductos(query: String): Flow<List<Producto>> = flow {
        val productos = apiService.getProductos()
        val productosFiltrados = productos.filter {
            it.nombreProducto.contains(query, ignoreCase = true) ||
            it.descripcionProducto.contains(query, ignoreCase = true) ||
            it.categoria.nombreCategoria.contains(query, ignoreCase = true)
        }
        emit(productosFiltrados)
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(emptyList())
    }
}
