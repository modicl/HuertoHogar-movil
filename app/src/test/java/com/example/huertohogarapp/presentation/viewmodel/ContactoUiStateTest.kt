package com.example.huertohogarapp.presentation.viewmodel

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para ContactoUiState
 */
class ContactoUiStateTest {

    @Test
    fun `estado inicial tiene valores por defecto correctos`() {
        // Given & When
        val uiState = ContactoUiState()

        // Then
        assertEquals("", uiState.nombre)
        assertEquals("", uiState.email)
        assertEquals("", uiState.mensaje)
        assertEquals("", uiState.telefono)
        assertNull(uiState.nombreError)
        assertNull(uiState.emailError)
        assertNull(uiState.mensajeError)
        assertNull(uiState.telefonoError)
        assertNull(uiState.formError)
        assertNull(uiState.mensajeExito)
        assertFalse(uiState.isLoading)
        assertFalse(uiState.isSent)
    }

    @Test
    fun `estado con valores se crea correctamente`() {
        // Given & When
        val uiState = ContactoUiState(
            nombre = "Juan",
            email = "juan@example.com",
            mensaje = "Hola",
            telefono = "912345678"
        )

        // Then
        assertEquals("Juan", uiState.nombre)
        assertEquals("juan@example.com", uiState.email)
        assertEquals("Hola", uiState.mensaje)
        assertEquals("912345678", uiState.telefono)
    }

    @Test
    fun `estado copy funciona correctamente`() {
        // Given
        val uiState = ContactoUiState(nombre = "Juan")

        // When
        val uiStateCopy = uiState.copy(email = "juan@example.com")

        // Then
        assertEquals("Juan", uiStateCopy.nombre)
        assertEquals("juan@example.com", uiStateCopy.email)
    }

    @Test
    fun `estado con errores se crea correctamente`() {
        // Given & When
        val uiState = ContactoUiState(
            nombreError = "Error en nombre",
            emailError = "Error en email",
            mensajeError = "Error en mensaje",
            telefonoError = "Error en teléfono",
            formError = "Error en formulario"
        )

        // Then
        assertEquals("Error en nombre", uiState.nombreError)
        assertEquals("Error en email", uiState.emailError)
        assertEquals("Error en mensaje", uiState.mensajeError)
        assertEquals("Error en teléfono", uiState.telefonoError)
        assertEquals("Error en formulario", uiState.formError)
    }

    @Test
    fun `estado con mensaje exito se crea correctamente`() {
        // Given & When
        val uiState = ContactoUiState(mensajeExito = "Enviado correctamente")

        // Then
        assertEquals("Enviado correctamente", uiState.mensajeExito)
    }

    @Test
    fun `estado con isLoading true se crea correctamente`() {
        // Given & When
        val uiState = ContactoUiState(isLoading = true)

        // Then
        assertTrue(uiState.isLoading)
    }

    @Test
    fun `estado con isSent true se crea correctamente`() {
        // Given & When
        val uiState = ContactoUiState(isSent = true)

        // Then
        assertTrue(uiState.isSent)
    }

    @Test
    fun `dos estados con mismos valores son iguales`() {
        // Given
        val uiState1 = ContactoUiState(nombre = "Juan", email = "test@example.com")
        val uiState2 = ContactoUiState(nombre = "Juan", email = "test@example.com")

        // Then
        assertEquals(uiState1, uiState2)
    }

    @Test
    fun `estado hashCode es consistente`() {
        // Given
        val uiState1 = ContactoUiState(nombre = "Juan")
        val uiState2 = ContactoUiState(nombre = "Juan")

        // Then
        assertEquals(uiState1.hashCode(), uiState2.hashCode())
    }

    @Test
    fun `estado toString contiene informacion relevante`() {
        // Given
        val uiState = ContactoUiState(nombre = "Juan", email = "juan@example.com")

        // When
        val toString = uiState.toString()

        // Then
        assertTrue(toString.contains("Juan"))
        assertTrue(toString.contains("juan@example.com"))
    }
}
