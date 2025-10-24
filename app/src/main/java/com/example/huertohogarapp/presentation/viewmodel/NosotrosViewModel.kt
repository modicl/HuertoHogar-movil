package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla de Nosotros
 * Arquitectura MVVM
 */
class NosotrosViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(NosotrosUiState())
    val uiState: StateFlow<NosotrosUiState> = _uiState.asStateFlow()
    
    // Futuras funciones para cargar información de la empresa
    fun loadCompanyInfo() {
        // TODO: Implementar carga de información desde repositorio
    }
}

/**
 * Estado de UI para la pantalla de Nosotros
 */
data class NosotrosUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)
