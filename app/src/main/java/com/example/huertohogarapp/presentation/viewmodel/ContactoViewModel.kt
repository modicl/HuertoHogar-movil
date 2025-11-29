package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Función de extensión para validar email, independiente de Android
private fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matches(this)
}


/**
 * ViewModel para la pantalla de Contacto
 * Arquitectura MVVM
 */
class ContactoViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(ContactoUiState())
    val uiState: StateFlow<ContactoUiState> = _uiState.asStateFlow()
    
    fun onNombreChange(value: String) {
        _uiState.value = _uiState.value.copy(
            nombre = value,
            nombreError = if (value.isBlank()) "El nombre es requerido" else null
        )
    }
    
    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            emailError = when {
                value.isBlank() -> "El email es requerido"
                !value.isValidEmail() -> "El email no es válido"
                else -> null
            }
        )
    }
    
    fun onMensajeChange(value: String) {
        _uiState.value = _uiState.value.copy(
            mensaje = value,
            mensajeError = if (value.length < 10) "El mensaje debe tener al menos 10 caracteres" else null
        )
    }
    
    fun onTelefonoChange(value: String) {
        _uiState.value = _uiState.value.copy(
            telefono = value,
            telefonoError = if (value.isNotBlank() && !value.matches(Regex("^[+]?[0-9]{8,12}$"))) 
                "El teléfono no es válido" else null
        )
    }
    
    fun enviarFormulario() {
        val currentState = _uiState.value
        
        // Re-validar todos los campos antes de enviar
        onNombreChange(currentState.nombre)
        onEmailChange(currentState.email)
        onMensajeChange(currentState.mensaje)
        onTelefonoChange(currentState.telefono)
        
        val newState = _uiState.value
        if (newState.nombreError != null || newState.emailError != null || newState.mensajeError != null || newState.telefonoError != null) {
             _uiState.value = currentState.copy(
                formError = "Por favor corrija los errores en el formulario"
            )
            return
        }
        
        // TODO: Implementar lógica de envío
        _uiState.value = currentState.copy(
            isLoading = true,
            formError = null
        )
        
        // Simular envío
        viewModelScope.launch {
            delay(1500) // Simular llamada a API
            _uiState.value = ContactoUiState(
                mensajeExito = "¡Mensaje enviado con éxito!",
                isLoading = false
            )
        }
    }
    
    fun limpiarMensajeExito() {
        _uiState.value = _uiState.value.copy(mensajeExito = null)
    }
}

/**
 * Estado de UI para la pantalla de Contacto
 */
data class ContactoUiState(
    val nombre: String = "",
    val email: String = "",
    val mensaje: String = "",
    val telefono: String = "",
    val nombreError: String? = null,
    val emailError: String? = null,
    val mensajeError: String? = null,
    val telefonoError: String? = null,
    val formError: String? = null,
    val mensajeExito: String? = null,
    val isLoading: Boolean = false,
    val isSent: Boolean = false
)
