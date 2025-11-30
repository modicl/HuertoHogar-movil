package com.example.huertohogarapp.presentation.viewmodel

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para InicioUiState
 */
class InicioUiStateTest {

    @Test
    fun `InicioUiState tiene valores por defecto correctos`() {
        val state = InicioUiState()
        
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `InicioUiState se crea con valores personalizados`() {
        val state = InicioUiState(
            isLoading = true,
            error = "Error de carga"
        )
        
        assertTrue(state.isLoading)
        assertEquals("Error de carga", state.error)
    }

    @Test
    fun `InicioUiState copy funciona correctamente`() {
        val original = InicioUiState(isLoading = false)
        val copy = original.copy(isLoading = true, error = "Nuevo error")
        
        assertTrue(copy.isLoading)
        assertEquals("Nuevo error", copy.error)
        assertFalse(original.isLoading)
    }

    @Test
    fun `InicioUiState equals funciona correctamente`() {
        val state1 = InicioUiState(isLoading = true, error = "Error")
        val state2 = InicioUiState(isLoading = true, error = "Error")
        val state3 = InicioUiState(isLoading = false, error = null)
        
        assertEquals(state1, state2)
        assertNotEquals(state1, state3)
    }

    @Test
    fun `InicioUiState hashCode es consistente`() {
        val state1 = InicioUiState(isLoading = true)
        val state2 = InicioUiState(isLoading = true)
        
        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `InicioUiState toString contiene propiedades`() {
        val state = InicioUiState(isLoading = true, error = "Test error")
        val toString = state.toString()
        
        assertTrue(toString.contains("isLoading"))
        assertTrue(toString.contains("error"))
    }

    @Test
    fun `InicioUiState con error null`() {
        val state = InicioUiState(isLoading = false, error = null)
        
        assertNull(state.error)
    }

    @Test
    fun `InicioUiState isLoading cambia estado`() {
        val sinCargar = InicioUiState(isLoading = false)
        val cargando = InicioUiState(isLoading = true)
        
        assertFalse(sinCargar.isLoading)
        assertTrue(cargando.isLoading)
    }

    @Test
    fun `InicioUiState component functions funcionan`() {
        val state = InicioUiState(isLoading = true, error = "Error")
        
        val (isLoading, error) = state
        
        assertTrue(isLoading)
        assertEquals("Error", error)
    }
}
