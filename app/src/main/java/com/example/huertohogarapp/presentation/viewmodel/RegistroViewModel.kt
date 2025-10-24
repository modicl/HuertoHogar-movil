package com.example.huertohogarapp.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla de Registro
 * Arquitectura MVVM - Capa de Presentación (ViewModel)
 */
class RegistroViewModel : ViewModel() {

    data class RegistroUiState(
        val nombre: String = "",
        val apellido: String = "",
        val correo: String = "",
        val fechaNacimiento: String = "",
        val fotoPerfil: Uri? = null,
        val nombreError: String? = null,
        val apellidoError: String? = null,
        val correoError: String? = null,
        val fechaNacimientoError: String? = null,
        val fotoPerfilError: String? = null,
        val mostrarDialogoExito: Boolean = false,
        val mostrarSelectorFoto: Boolean = false,
        val cargando: Boolean = false
    )

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onNombreChange(nombre: String) {
        _uiState.value = _uiState.value.copy(
            nombre = nombre,
            nombreError = null
        )
    }

    fun onApellidoChange(apellido: String) {
        _uiState.value = _uiState.value.copy(
            apellido = apellido,
            apellidoError = null
        )
    }

    fun onCorreoChange(correo: String) {
        _uiState.value = _uiState.value.copy(
            correo = correo,
            correoError = null
        )
    }

    fun onFechaNacimientoChange(fecha: String) {
        _uiState.value = _uiState.value.copy(
            fechaNacimiento = fecha,
            fechaNacimientoError = null
        )
    }

    fun onFotoPerfilChange(uri: Uri?) {
        _uiState.value = _uiState.value.copy(
            fotoPerfil = uri,
            fotoPerfilError = null
        )
    }

    fun mostrarSelectorFoto() {
        _uiState.value = _uiState.value.copy(mostrarSelectorFoto = true)
    }

    fun ocultarSelectorFoto() {
        _uiState.value = _uiState.value.copy(mostrarSelectorFoto = false)
    }

    fun onRegistrar() {
        if (validarFormulario()) {
            _uiState.value = _uiState.value.copy(cargando = true)
            
            // Simulación de registro
            // Aquí se podría guardar en DataStore o enviar a un servidor
            
            _uiState.value = _uiState.value.copy(
                cargando = false,
                mostrarDialogoExito = true
            )
        }
    }

    fun ocultarDialogoExito() {
        _uiState.value = _uiState.value.copy(mostrarDialogoExito = false)
    }

    private fun validarFormulario(): Boolean {
        var esValido = true
        val state = _uiState.value

        // Validar nombre
        if (state.nombre.isBlank()) {
            _uiState.value = state.copy(nombreError = "El nombre es obligatorio")
            esValido = false
        } else if (state.nombre.length < 2) {
            _uiState.value = state.copy(nombreError = "El nombre debe tener al menos 2 caracteres")
            esValido = false
        }

        // Validar apellido
        if (state.apellido.isBlank()) {
            _uiState.value = _uiState.value.copy(apellidoError = "El apellido es obligatorio")
            esValido = false
        } else if (state.apellido.length < 2) {
            _uiState.value = _uiState.value.copy(apellidoError = "El apellido debe tener al menos 2 caracteres")
            esValido = false
        }

        // Validar correo
        if (state.correo.isBlank()) {
            _uiState.value = _uiState.value.copy(correoError = "El correo es obligatorio")
            esValido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.correo).matches()) {
            _uiState.value = _uiState.value.copy(correoError = "Correo inválido")
            esValido = false
        }

        // Validar fecha de nacimiento
        if (state.fechaNacimiento.isBlank()) {
            _uiState.value = _uiState.value.copy(fechaNacimientoError = "La fecha de nacimiento es obligatoria")
            esValido = false
        }

        // Validar foto de perfil
        if (state.fotoPerfil == null) {
            _uiState.value = _uiState.value.copy(fotoPerfilError = "La foto de perfil es obligatoria")
            esValido = false
        }

        return esValido
    }

    fun limpiarFormulario() {
        _uiState.value = RegistroUiState()
    }
}
