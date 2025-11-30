package com.example.huertohogarapp.presentation.viewmodel

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para CarritoUiState
 */
class CarritoUiStateTest {

    @Test
    fun `estado inicial tiene valores por defecto correctos`() {
        // Given & When
        val uiState = CarritoUiState()

        // Then
        assertEquals(0.0, uiState.total)
        assertEquals(0, uiState.cantidadTotal)
        assertFalse(uiState.mostrarDialogoExito)
    }

    @Test
    fun `estado con total y cantidad se crea correctamente`() {
        // Given & When
        val uiState = CarritoUiState(
            total = 5000.0,
            cantidadTotal = 3,
            mostrarDialogoExito = false
        )

        // Then
        assertEquals(5000.0, uiState.total)
        assertEquals(3, uiState.cantidadTotal)
    }

    @Test
    fun `estado copy funciona correctamente`() {
        // Given
        val uiState = CarritoUiState(total = 1000.0, cantidadTotal = 1)

        // When
        val uiStateCopy = uiState.copy(mostrarDialogoExito = true)

        // Then
        assertTrue(uiStateCopy.mostrarDialogoExito)
        assertEquals(uiState.total, uiStateCopy.total)
        assertEquals(uiState.cantidadTotal, uiStateCopy.cantidadTotal)
    }

    @Test
    fun `estado con dialogo exito se crea correctamente`() {
        // Given & When
        val uiState = CarritoUiState(mostrarDialogoExito = true)

        // Then
        assertTrue(uiState.mostrarDialogoExito)
    }

    @Test
    fun `dos estados con mismos valores son iguales`() {
        // Given
        val uiState1 = CarritoUiState(total = 1000.0, cantidadTotal = 2)
        val uiState2 = CarritoUiState(total = 1000.0, cantidadTotal = 2)

        // Then
        assertEquals(uiState1, uiState2)
    }

    @Test
    fun `estado hashCode es consistente`() {
        // Given
        val uiState1 = CarritoUiState(total = 1000.0)
        val uiState2 = CarritoUiState(total = 1000.0)

        // Then
        assertEquals(uiState1.hashCode(), uiState2.hashCode())
    }

    @Test
    fun `estado toString contiene informacion relevante`() {
        // Given
        val uiState = CarritoUiState(total = 5000.0, cantidadTotal = 5)

        // When
        val toString = uiState.toString()

        // Then
        assertTrue(toString.contains("5000.0"))
        assertTrue(toString.contains("5"))
    }
}
