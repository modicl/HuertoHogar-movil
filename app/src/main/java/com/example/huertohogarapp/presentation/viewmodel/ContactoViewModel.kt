package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches() -> 
                    "El email no es válido"
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
        
        // Validar campos requeridos
        if (currentState.nombre.isBlank() || 
            currentState.email.isBlank() || 
            currentState.mensaje.isBlank()) {
            _uiState.value = currentState.copy(
                formError = "Por favor complete todos los campos requeridos"
            )
            return
        }
        
        // Validar formato de email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            _uiState.value = currentState.copy(
                emailError = "El email no es válido"
            )
            return
        }
        
        // Validar longitud del mensaje
        if (currentState.mensaje.length < 10) {
            _uiState.value = currentState.copy(
                mensajeError = "El mensaje debe tener al menos 10 caracteres"
            )
            return
        }
        
        // Validar teléfono si se proporcionó
        if (currentState.telefono.isNotBlank() && 
            !currentState.telefono.matches(Regex("^[+]?[0-9]{8,12}$"))) {
            _uiState.value = currentState.copy(
                telefonoError = "El teléfono no es válido"
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
