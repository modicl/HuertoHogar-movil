package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.local.EstadoDataStore
import com.example.huertohogarapp.data.model.CartItem
import com.example.huertohogarapp.data.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * Repositorio para gestionar el carrito de compras
 * Arquitectura MVVM - Capa de Datos
 */
class CarritoRepository(private val dataStore: EstadoDataStore) {
    
    val carritoItems: Flow<List<CartItem>> = dataStore.carritoItems
    
    suspend fun agregarProducto(producto: Producto) {
        val currentItems = dataStore.carritoItems.first().toMutableList()
        val existingItem = currentItems.find { it.producto.idProducto == producto.idProducto }
        
        if (existingItem != null) {
            // Si ya existe, incrementar cantidad
            val index = currentItems.indexOf(existingItem)
            currentItems[index] = existingItem.copy(cantidad = existingItem.cantidad + 1)
        } else {
            // Si no existe, agregar nuevo item
            currentItems.add(CartItem(producto = producto, cantidad = 1))
        }
        
        dataStore.guardarCarrito(currentItems)
    }
    
    suspend fun quitarProducto(productoId: Int) {
        val currentItems = dataStore.carritoItems.first().toMutableList()
        val existingItem = currentItems.find { it.producto.idProducto == productoId }
        
        if (existingItem != null) {
            if (existingItem.cantidad > 1) {
                // Decrementar cantidad
                val index = currentItems.indexOf(existingItem)
                currentItems[index] = existingItem.copy(cantidad = existingItem.cantidad - 1)
            } else {
                // Eliminar del carrito
                currentItems.remove(existingItem)
            }
            dataStore.guardarCarrito(currentItems)
        }
    }
    
    suspend fun eliminarProducto(productoId: Int) {
        val currentItems = dataStore.carritoItems.first().toMutableList()
        currentItems.removeAll { it.producto.idProducto == productoId }
        dataStore.guardarCarrito(currentItems)
    }
    
    suspend fun limpiarCarrito() {
        dataStore.limpiarCarrito()
    }
    
    suspend fun obtenerTotal(): Double {
        val items = dataStore.carritoItems.first()
        return items.sumOf { it.producto.precioProducto * it.cantidad }
    }
    
    suspend fun obtenerCantidadTotal(): Int {
        val items = dataStore.carritoItems.first()
        return items.sumOf { it.cantidad }
    }
}
