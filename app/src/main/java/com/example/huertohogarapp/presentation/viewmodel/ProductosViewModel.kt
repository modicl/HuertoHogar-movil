package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla de Productos
 * Arquitectura MVVM
 */
class ProductosViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProductosUiState())
    val uiState: StateFlow<ProductosUiState> = _uiState.asStateFlow()
    
    // Futuras funciones para cargar productos, filtrar, buscar, etc.
    fun loadProductos() {
        // TODO: Implementar carga de productos desde repositorio
    }
    
    fun searchProductos(query: String) {
        // TODO: Implementar búsqueda
    }
    
    fun filterProductos(category: String) {
        // TODO: Implementar filtrado
    }
}

/**
 * Estado de UI para la pantalla de Productos
 */
data class ProductosUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)
