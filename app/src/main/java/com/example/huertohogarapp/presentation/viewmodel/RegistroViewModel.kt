package com.example.huertohogarapp.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.model.RegistroRequest
import com.example.huertohogarapp.data.remote.UsuarioRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Registro
 * Arquitectura MVVM - Capa de Presentación (ViewModel)
 */
class RegistroViewModel(
    application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "RegistroViewModel"
        
        // Lista de regiones de Chile (1-13)
        val REGIONES_CHILE = listOf(
            1 to "Tarapacá",
            2 to "Antofagasta",
            3 to "Atacama",
            4 to "Coquimbo",
            5 to "Valparaíso",
            6 to "O'Higgins",
            7 to "Maule",
            8 to "Biobío",
            9 to "La Araucanía",
            10 to "Los Lagos",
            11 to "Aysén",
            12 to "Magallanes",
            13 to "Metropolitana"
        )
    }

    data class RegistroUiState(
        // Campos para el API
        val nombre: String = "",
        val segundoNombre: String = "",
        val apellidoPaterno: String = "",
        val apellidoMaterno: String = "",
        val rut: String = "",
        val digitoVerificador: String = "",
        val fechaNacimiento: String = "",
        val idRegion: Int = 13, // Default: Metropolitana
        val direccion: String = "",
        val correo: String = "",
        val telefono: String = "",
        val password: String = "",
        val confirmarPassword: String = "",
        
        // Campo local (no se envía al API)
        val fotoPerfil: Uri? = null,
        
        // Errores de validación
        val nombreError: String? = null,
        val segundoNombreError: String? = null,
        val apellidoPaternoError: String? = null,
        val apellidoMaternoError: String? = null,
        val rutError: String? = null,
        val dvError: String? = null,
        val fechaNacimientoError: String? = null,
        val regionError: String? = null,
        val direccionError: String? = null,
        val correoError: String? = null,
        val telefonoError: String? = null,
        val passwordError: String? = null,
        val confirmarPasswordError: String? = null,
        val fotoPerfilError: String? = null,
        
        // Estados de UI
        val mostrarDialogoExito: Boolean = false,
        val mostrarDialogoError: Boolean = false,
        val mensajeError: String = "",
        val mostrarSelectorFoto: Boolean = false,
        val mostrarSelectorRegion: Boolean = false,
        val cargando: Boolean = false,
        val passwordVisible: Boolean = false,
        val confirmarPasswordVisible: Boolean = false
    ) {
        // Alias para compatibilidad con el código existente
        val apellido: String get() = apellidoPaterno
        val apellidoError: String? get() = apellidoPaternoError
    }

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    // Funciones para actualizar campos
    fun onNombreChange(nombre: String) {
        _uiState.value = _uiState.value.copy(nombre = nombre, nombreError = null)
    }

    fun onSegundoNombreChange(segundoNombre: String) {
        _uiState.value = _uiState.value.copy(segundoNombre = segundoNombre, segundoNombreError = null)
    }

    fun onApellidoPaternoChange(apellido: String) {
        _uiState.value = _uiState.value.copy(apellidoPaterno = apellido, apellidoPaternoError = null)
    }

    fun onApellidoMaternoChange(apellido: String) {
        _uiState.value = _uiState.value.copy(apellidoMaterno = apellido, apellidoMaternoError = null)
    }

    // Mantener compatibilidad con el código existente de RegistroScreen
    fun onApellidoChange(apellido: String) {
        onApellidoPaternoChange(apellido)
    }

    fun onRutChange(rut: String) {
        // Solo permitir números
        val rutFiltrado = rut.filter { it.isDigit() }
        _uiState.value = _uiState.value.copy(rut = rutFiltrado, rutError = null)
    }

    fun onDigitoVerificadorChange(dv: String) {
        // Solo permitir un dígito o 'K'
        val dvFiltrado = dv.uppercase().take(1).filter { it.isDigit() || it == 'K' }
        _uiState.value = _uiState.value.copy(digitoVerificador = dvFiltrado, dvError = null)
    }

    fun onFechaNacimientoChange(fecha: String) {
        _uiState.value = _uiState.value.copy(fechaNacimiento = fecha, fechaNacimientoError = null)
    }

    fun onRegionChange(idRegion: Int) {
        _uiState.value = _uiState.value.copy(idRegion = idRegion, regionError = null, mostrarSelectorRegion = false)
    }

    fun onDireccionChange(direccion: String) {
        _uiState.value = _uiState.value.copy(direccion = direccion, direccionError = null)
    }

    fun onCorreoChange(correo: String) {
        _uiState.value = _uiState.value.copy(correo = correo, correoError = null)
    }

    fun onTelefonoChange(telefono: String) {
        _uiState.value = _uiState.value.copy(telefono = telefono, telefonoError = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = null)
    }

    fun onConfirmarPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(confirmarPassword = password, confirmarPasswordError = null)
    }

    fun onFotoPerfilChange(uri: Uri?) {
        _uiState.value = _uiState.value.copy(fotoPerfil = uri, fotoPerfilError = null)
    }

    // Funciones de UI
    fun mostrarSelectorFoto() {
        _uiState.value = _uiState.value.copy(mostrarSelectorFoto = true)
    }

    fun ocultarSelectorFoto() {
        _uiState.value = _uiState.value.copy(mostrarSelectorFoto = false)
    }

    fun mostrarSelectorRegion() {
        _uiState.value = _uiState.value.copy(mostrarSelectorRegion = true)
    }

    fun ocultarSelectorRegion() {
        _uiState.value = _uiState.value.copy(mostrarSelectorRegion = false)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(passwordVisible = !_uiState.value.passwordVisible)
    }

    fun toggleConfirmarPasswordVisibility() {
        _uiState.value = _uiState.value.copy(confirmarPasswordVisible = !_uiState.value.confirmarPasswordVisible)
    }

    fun ocultarDialogoExito() {
        _uiState.value = _uiState.value.copy(mostrarDialogoExito = false)
    }

    fun ocultarDialogoError() {
        _uiState.value = _uiState.value.copy(mostrarDialogoError = false)
    }

    fun onRegistrar() {
        if (validarFormulario()) {
            _uiState.value = _uiState.value.copy(cargando = true)
            viewModelScope.launch {
                try {
                    val state = _uiState.value
                    val request = RegistroRequest(
                        nombre = state.nombre,
                        segundoNombre = state.segundoNombre,
                        apellidoPaterno = state.apellidoPaterno,
                        apellidoMaterno = state.apellidoMaterno,
                        rut = state.rut,
                        digitoVerificador = state.digitoVerificador,
                        fechaNacimiento = state.fechaNacimiento,
                        idRegion = state.idRegion,
                        direccion = state.direccion,
                        email = state.correo,
                        telefono = state.telefono,
                        password = state.password
                    )
                    
                    Log.d(TAG, "Enviando registro: $request")
                    
                    val response = UsuarioRetrofitClient.usuarioApiService.registrarUsuario(request)
                    
                    if (response.isSuccessful) {
                        Log.d(TAG, "Registro exitoso: ${response.body()}")
                        _uiState.value = _uiState.value.copy(
                            cargando = false,
                            mostrarDialogoExito = true
                        )
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                        Log.e(TAG, "Error en registro: ${response.code()} - $errorBody")
                        _uiState.value = _uiState.value.copy(
                            cargando = false,
                            mostrarDialogoError = true,
                            mensajeError = "Error al registrar: ${response.code()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Excepción en registro", e)
                    _uiState.value = _uiState.value.copy(
                        cargando = false,
                        mostrarDialogoError = true,
                        mensajeError = "Error de conexión: ${e.message}"
                    )
                }
            }
        }
    }

    private fun validarFormulario(): Boolean {
        var esValido = true
        val state = _uiState.value

        // Validar nombre
        if (state.nombre.isBlank()) {
            _uiState.value = _uiState.value.copy(nombreError = "El nombre es obligatorio")
            esValido = false
        } else if (state.nombre.length < 2) {
            _uiState.value = _uiState.value.copy(nombreError = "El nombre debe tener al menos 2 caracteres")
            esValido = false
        }

        // Validar segundo nombre (opcional, pero si tiene contenido debe ser válido)
        if (state.segundoNombre.isNotBlank() && state.segundoNombre.length < 2) {
            _uiState.value = _uiState.value.copy(segundoNombreError = "El segundo nombre debe tener al menos 2 caracteres")
            esValido = false
        }

        // Validar apellido paterno
        if (state.apellidoPaterno.isBlank()) {
            _uiState.value = _uiState.value.copy(apellidoPaternoError = "El apellido paterno es obligatorio")
            esValido = false
        } else if (state.apellidoPaterno.length < 2) {
            _uiState.value = _uiState.value.copy(apellidoPaternoError = "El apellido debe tener al menos 2 caracteres")
            esValido = false
        }

        // Validar apellido materno
        if (state.apellidoMaterno.isBlank()) {
            _uiState.value = _uiState.value.copy(apellidoMaternoError = "El apellido materno es obligatorio")
            esValido = false
        } else if (state.apellidoMaterno.length < 2) {
            _uiState.value = _uiState.value.copy(apellidoMaternoError = "El apellido debe tener al menos 2 caracteres")
            esValido = false
        }

        // Validar RUT
        if (state.rut.isBlank()) {
            _uiState.value = _uiState.value.copy(rutError = "El RUT es obligatorio")
            esValido = false
        } else if (state.rut.length < 7 || state.rut.length > 8) {
            _uiState.value = _uiState.value.copy(rutError = "El RUT debe tener entre 7 y 8 dígitos")
            esValido = false
        }

        // Validar dígito verificador
        if (state.digitoVerificador.isBlank()) {
            _uiState.value = _uiState.value.copy(dvError = "Requerido")
            esValido = false
        }

        // Validar fecha de nacimiento
        if (state.fechaNacimiento.isBlank()) {
            _uiState.value = _uiState.value.copy(fechaNacimientoError = "La fecha de nacimiento es obligatoria")
            esValido = false
        }

        // Validar región (1-13)
        if (state.idRegion < 1 || state.idRegion > 13) {
            _uiState.value = _uiState.value.copy(regionError = "Selecciona una región válida")
            esValido = false
        }

        // Validar dirección
        if (state.direccion.isBlank()) {
            _uiState.value = _uiState.value.copy(direccionError = "La dirección es obligatoria")
            esValido = false
        } else if (state.direccion.length < 10) {
            _uiState.value = _uiState.value.copy(direccionError = "Ingresa una dirección más detallada")
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

        // Validar teléfono
        if (state.telefono.isBlank()) {
            _uiState.value = _uiState.value.copy(telefonoError = "El teléfono es obligatorio")
            esValido = false
        } else if (state.telefono.length < 9) {
            _uiState.value = _uiState.value.copy(telefonoError = "Ingresa un teléfono válido")
            esValido = false
        }

        // Validar contraseña
        // Requisitos: máximo 10 caracteres, 1 mayúscula, 1 carácter especial
        if (state.password.isBlank()) {
            _uiState.value = _uiState.value.copy(passwordError = "La contraseña es obligatoria")
            esValido = false
        } else {
            val passwordValidation = validarPassword(state.password)
            if (passwordValidation != null) {
                _uiState.value = _uiState.value.copy(passwordError = passwordValidation)
                esValido = false
            }
        }

        // Validar confirmación de contraseña
        if (state.confirmarPassword.isBlank()) {
            _uiState.value = _uiState.value.copy(confirmarPasswordError = "Confirma tu contraseña")
            esValido = false
        } else if (state.password != state.confirmarPassword) {
            _uiState.value = _uiState.value.copy(confirmarPasswordError = "Las contraseñas no coinciden")
            esValido = false
        }

        return esValido
    }

    private fun validarPassword(password: String): String? {
        if (password.length > 10) {
            return "Máximo 10 caracteres"
        }
        if (password.length < 6) {
            return "Mínimo 6 caracteres"
        }
        if (!password.any { it.isUpperCase() }) {
            return "Debe contener al menos una mayúscula"
        }
        val caracteresEspeciales = "!@#\$%^&*()_+-=[]{}|;':\",./<>?"
        if (!password.any { it in caracteresEspeciales }) {
            return "Debe contener al menos un carácter especial (!@#\$%...)"
        }
        return null
    }

    fun limpiarFormulario() {
        _uiState.value = RegistroUiState()
    }
}
