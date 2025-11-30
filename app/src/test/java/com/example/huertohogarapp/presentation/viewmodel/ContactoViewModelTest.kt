package com.example.huertohogarapp.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ContactoViewModelTest {

    private lateinit var viewModel: ContactoViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ContactoViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial del formulario es vacio`() {
        // Then
        val uiState = viewModel.uiState.value
        assertEquals("", uiState.nombre)
        assertEquals("", uiState.email)
        assertEquals("", uiState.mensaje)
        assertEquals("", uiState.telefono)
        assertNull(uiState.nombreError)
        assertNull(uiState.emailError)
        assertNull(uiState.mensajeError)
        assertNull(uiState.telefonoError)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `onNombreChange actualiza el nombre correctamente`() {
        // When
        viewModel.onNombreChange("Juan")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("Juan", uiState.nombre)
        assertNull(uiState.nombreError)
    }

    @Test
    fun `onNombreChange con campo vacio muestra error`() {
        // When
        viewModel.onNombreChange("")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("", uiState.nombre)
        assertEquals("El nombre es requerido", uiState.nombreError)
    }

    @Test
    fun `onEmailChange actualiza el email correctamente`() {
        // When
        viewModel.onEmailChange("test@example.com")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("test@example.com", uiState.email)
        assertNull(uiState.emailError)
    }

    @Test
    fun `onEmailChange con email vacio muestra error`() {
        // When
        viewModel.onEmailChange("")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El email es requerido", uiState.emailError)
    }

    @Test
    fun `onEmailChange con email invalido muestra error`() {
        // When
        viewModel.onEmailChange("correo-invalido")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El email no es válido", uiState.emailError)
    }

    @Test
    fun `onMensajeChange actualiza el mensaje correctamente`() {
        // When
        viewModel.onMensajeChange("Este es un mensaje de prueba")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("Este es un mensaje de prueba", uiState.mensaje)
        assertNull(uiState.mensajeError)
    }

    @Test
    fun `onMensajeChange con mensaje corto muestra error`() {
        // When
        viewModel.onMensajeChange("Hola")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El mensaje debe tener al menos 10 caracteres", uiState.mensajeError)
    }

    @Test
    fun `onTelefonoChange actualiza el telefono correctamente`() {
        // When
        viewModel.onTelefonoChange("912345678")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("912345678", uiState.telefono)
        assertNull(uiState.telefonoError)
    }

    @Test
    fun `onTelefonoChange con telefono invalido muestra error`() {
        // When
        viewModel.onTelefonoChange("123")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El teléfono no es válido", uiState.telefonoError)
    }

    @Test
    fun `onTelefonoChange permite telefono vacio sin mostrar error`() {
        // When
        viewModel.onTelefonoChange("")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("", uiState.telefono)
        assertNull(uiState.telefonoError)
    }

    @Test
    fun `enviarFormulario con campos requeridos vacios muestra error`() = runTest {
        // When
        viewModel.enviarFormulario()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("Por favor corrija los errores en el formulario", uiState.formError)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `enviarFormulario con email invalido muestra error`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("email-invalido")
        viewModel.onMensajeChange("Este es un mensaje de prueba válido")

        // When
        viewModel.enviarFormulario()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El email no es válido", uiState.emailError)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `enviarFormulario con mensaje corto muestra error`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Corto")

        // When
        viewModel.enviarFormulario()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El mensaje debe tener al menos 10 caracteres", uiState.mensajeError)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `enviarFormulario con telefono invalido muestra error`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Este es un mensaje de prueba válido")
        viewModel.onTelefonoChange("123")

        // When
        viewModel.enviarFormulario()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El teléfono no es válido", uiState.telefonoError)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `enviarFormulario con datos validos inicia carga`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Este es un mensaje de prueba válido para el formulario")

        // When
        viewModel.enviarFormulario()

        // Then - Estado inmediatamente después de llamar enviarFormulario
        val uiState = viewModel.uiState.value
        assertTrue(uiState.isLoading)
        assertNull(uiState.formError)
    }

    @Test
    fun `enviarFormulario con datos validos muestra mensaje de exito`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Este es un mensaje de prueba válido para el formulario")

        // When
        viewModel.enviarFormulario()
        testDispatcher.scheduler.advanceUntilIdle() // Avanza el tiempo simulado

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("¡Mensaje enviado con éxito!", uiState.mensajeExito)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `enviarFormulario con datos validos y telefono opcional funciona correctamente`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Este es un mensaje de prueba válido para el formulario")
        viewModel.onTelefonoChange("912345678")

        // When
        viewModel.enviarFormulario()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("¡Mensaje enviado con éxito!", uiState.mensajeExito)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `limpiarMensajeExito elimina el mensaje de exito`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Este es un mensaje de prueba válido")
        viewModel.enviarFormulario()
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.limpiarMensajeExito()

        // Then
        val uiState = viewModel.uiState.value
        assertNull(uiState.mensajeExito)
    }

    @Test
    fun `enviarFormulario limpia el formulario despues de envio exitoso`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Este es un mensaje de prueba válido")
        viewModel.onTelefonoChange("912345678")

        // When
        viewModel.enviarFormulario()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("", uiState.nombre)
        assertEquals("", uiState.email)
        assertEquals("", uiState.mensaje)
        assertEquals("", uiState.telefono)
    }

    @Test
    fun `onTelefonoChange con telefono vacio no muestra error`() {
        // When
        viewModel.onTelefonoChange("")

        // Then
        val uiState = viewModel.uiState.value
        assertNull(uiState.telefonoError)
    }

    @Test
    fun `onTelefonoChange con telefono internacional valido`() {
        // When
        viewModel.onTelefonoChange("+56912345678")

        // Then
        val uiState = viewModel.uiState.value
        assertNull(uiState.telefonoError)
    }

    @Test
    fun `onTelefonoChange con letras muestra error`() {
        // When
        viewModel.onTelefonoChange("123abc456")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El teléfono no es válido", uiState.telefonoError)
    }

    @Test
    fun `onEmailChange con email sin punto en dominio muestra error`() {
        // When
        viewModel.onEmailChange("test@examplecom")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El email no es válido", uiState.emailError)
    }

    @Test
    fun `onEmailChange con dominio muy corto muestra error`() {
        // When
        viewModel.onEmailChange("test@example.c")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El email no es válido", uiState.emailError)
    }

    @Test
    fun `onMensajeChange con exactamente 10 caracteres no muestra error`() {
        // When
        viewModel.onMensajeChange("1234567890")

        // Then
        val uiState = viewModel.uiState.value
        assertNull(uiState.mensajeError)
    }

    @Test
    fun `onMensajeChange con 9 caracteres muestra error`() {
        // When
        viewModel.onMensajeChange("123456789")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("El mensaje debe tener al menos 10 caracteres", uiState.mensajeError)
    }

    @Test
    fun `enviarFormulario con solo error en telefono muestra error de formulario`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("Este es un mensaje válido")
        viewModel.onTelefonoChange("invalido")

        // When
        viewModel.enviarFormulario()

        // Then
        val uiState = viewModel.uiState.value
        assertNotNull(uiState.telefonoError)
    }

    @Test
    fun `enviarFormulario con solo error en mensaje muestra error de formulario`() = runTest {
        // Given
        viewModel.onNombreChange("Juan Pérez")
        viewModel.onEmailChange("test@example.com")
        viewModel.onMensajeChange("corto")
        viewModel.onTelefonoChange("")

        // When
        viewModel.enviarFormulario()

        // Then
        val uiState = viewModel.uiState.value
        assertNotNull(uiState.mensajeError)
    }

    @Test
    fun `onNombreChange con espacios es valido`() {
        // When
        viewModel.onNombreChange("   ")

        // Then
        val uiState = viewModel.uiState.value
        // isBlank() retorna true para espacios en blanco
        assertEquals("El nombre es requerido", uiState.nombreError)
    }

    @Test
    fun `onEmailChange con espacios muestra error`() {
        // When
        viewModel.onEmailChange("   ")

        // Then
        val uiState = viewModel.uiState.value
        // isBlank() retorna true para espacios en blanco
        assertEquals("El email es requerido", uiState.emailError)
    }
}
