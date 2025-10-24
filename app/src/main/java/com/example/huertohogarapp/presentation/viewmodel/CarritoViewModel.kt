package com.example.huertohogarapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.local.EstadoDataStore
import com.example.huertohogarapp.data.model.CartItem
import com.example.huertohogarapp.data.repository.CarritoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Carrito
 * Arquitectura MVVM
 */
class CarritoViewModel(application: Application) : AndroidViewModel(application) {
    
    private val carritoRepository = CarritoRepository(EstadoDataStore(application))
    
    val carritoItems = carritoRepository.carritoItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState: StateFlow<CarritoUiState> = _uiState.asStateFlow()
    
    init {
        observarCarrito()
    }
    
    private fun observarCarrito() {
        viewModelScope.launch {
            carritoItems.collect { items ->
                val total = items.sumOf { it.producto.precio * it.cantidad }
                val cantidadTotal = items.sumOf { it.cantidad }
                _uiState.value = _uiState.value.copy(
                    total = total,
                    cantidadTotal = cantidadTotal
                )
            }
        }
    }
    
    fun agregarProducto(productoId: Int) {
        viewModelScope.launch {
            val item = carritoItems.value.find { it.producto.id == productoId }
            item?.let {
                carritoRepository.agregarProducto(it.producto)
            }
        }
    }
    
    fun quitarProducto(productoId: Int) {
        viewModelScope.launch {
            carritoRepository.quitarProducto(productoId)
        }
    }
    
    fun eliminarProducto(productoId: Int) {
        viewModelScope.launch {
            carritoRepository.eliminarProducto(productoId)
        }
    }
    
    fun limpiarCarrito() {
        viewModelScope.launch {
            carritoRepository.limpiarCarrito()
        }
    }
    
    fun realizarCompra() {
        _uiState.value = _uiState.value.copy(
            mostrarDialogoExito = true
        )
    }
    
    fun ocultarDialogoExito() {
        _uiState.value = _uiState.value.copy(mostrarDialogoExito = false)
    }
    
    fun confirmarCompra() {
        viewModelScope.launch {
            limpiarCarrito()
            _uiState.value = _uiState.value.copy(mostrarDialogoExito = false)
        }
    }
}

data class CarritoUiState(
    val total: Double = 0.0,
    val cantidadTotal: Int = 0,
    val mostrarDialogoExito: Boolean = false
)
