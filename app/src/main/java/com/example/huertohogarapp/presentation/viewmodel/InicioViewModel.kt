package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla de Inicio
 * Arquitectura MVVM
 */
class InicioViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(InicioUiState())
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()
    
    // Futuras funciones para cargar datos de productos destacados, carousel, etc.
    fun loadHomeData() {
        // TODO: Implementar carga de datos desde repositorio
    }
}

/**
 * Estado de UI para la pantalla de Inicio
 */
data class InicioUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)
