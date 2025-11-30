package com.example.huertohogarapp.presentation.viewmodel

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para ProductosUiState
 */
class ProductosUiStateTest {

    @Test
    fun `estado inicial tiene valores por defecto correctos`() {
        // Given & When
        val uiState = ProductosUiState()

        // Then
        assertTrue(uiState.isLoading)
        assertNull(uiState.error)
        assertEquals("", uiState.searchQuery)
        assertTrue(uiState.productos.isEmpty())
        assertTrue(uiState.productosFiltrados.isEmpty())
        assertEquals("Todos", uiState.categoriaSeleccionada)
        assertNull(uiState.mensajeSnackbar)
    }

    @Test
    fun `estado copy funciona correctamente`() {
        // Given
        val uiState = ProductosUiState()

        // When
        val uiStateCopy = uiState.copy(isLoading = false, searchQuery = "tomate")

        // Then
        assertFalse(uiStateCopy.isLoading)
        assertEquals("tomate", uiStateCopy.searchQuery)
    }

    @Test
    fun `estado con error se crea correctamente`() {
        // Given & When
        val uiState = ProductosUiState(error = "Error de conexión")

        // Then
        assertEquals("Error de conexión", uiState.error)
    }

    @Test
    fun `estado con mensaje snackbar se crea correctamente`() {
        // Given & When
        val uiState = ProductosUiState(mensajeSnackbar = "Producto agregado")

        // Then
        assertEquals("Producto agregado", uiState.mensajeSnackbar)
    }

    @Test
    fun `dos estados con mismos valores son iguales`() {
        // Given
        val uiState1 = ProductosUiState(isLoading = false, searchQuery = "test")
        val uiState2 = ProductosUiState(isLoading = false, searchQuery = "test")

        // Then
        assertEquals(uiState1, uiState2)
    }

    @Test
    fun `estado hashCode es consistente`() {
        // Given
        val uiState1 = ProductosUiState(isLoading = false)
        val uiState2 = ProductosUiState(isLoading = false)

        // Then
        assertEquals(uiState1.hashCode(), uiState2.hashCode())
    }
}
