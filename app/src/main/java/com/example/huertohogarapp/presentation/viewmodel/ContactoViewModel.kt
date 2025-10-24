package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla de Contacto
 * Arquitectura MVVM
 */
class ContactoViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(ContactoUiState())
    val uiState: StateFlow<ContactoUiState> = _uiState.asStateFlow()
    
    // Futuras funciones para enviar formulario de contacto
    fun sendContactForm(name: String, email: String, message: String) {
        // TODO: Implementar envío de formulario
    }
    
    fun updateFormField(field: String, value: String) {
        // TODO: Actualizar campos del formulario
    }
}

/**
 * Estado de UI para la pantalla de Contacto
 */
data class ContactoUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSent: Boolean = false
)
