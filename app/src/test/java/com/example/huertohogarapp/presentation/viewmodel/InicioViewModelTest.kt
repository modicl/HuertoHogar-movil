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

/**
 * Tests unitarios para InicioViewModel
 */
@ExperimentalCoroutinesApi
class InicioViewModelTest {

    private lateinit var viewModel: InicioViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = InicioViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial es correcto`() = runTest {
        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun `uiState es accesible como StateFlow`() = runTest {
        // When
        val uiState = viewModel.uiState.value

        // Then
        assertNotNull(uiState)
    }

    @Test
    fun `loadHomeData no lanza excepcion`() = runTest {
        // When & Then - No debería lanzar excepción
        assertDoesNotThrow {
            viewModel.loadHomeData()
        }
    }

    @Test
    fun `InicioUiState tiene valores por defecto correctos`() {
        // Given & When
        val uiState = InicioUiState()

        // Then
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun `InicioUiState copy funciona correctamente`() {
        // Given
        val uiState = InicioUiState()

        // When
        val uiStateCopy = uiState.copy(isLoading = true)

        // Then
        assertTrue(uiStateCopy.isLoading)
        assertNull(uiStateCopy.error)
    }

    @Test
    fun `InicioUiState con error se crea correctamente`() {
        // Given & When
        val uiState = InicioUiState(error = "Error de conexión")

        // Then
        assertEquals("Error de conexión", uiState.error)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `InicioUiState con loading true se crea correctamente`() {
        // Given & When
        val uiState = InicioUiState(isLoading = true)

        // Then
        assertTrue(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun `dos InicioUiState con mismos valores son iguales`() {
        // Given
        val uiState1 = InicioUiState(isLoading = true, error = "Error")
        val uiState2 = InicioUiState(isLoading = true, error = "Error")

        // Then
        assertEquals(uiState1, uiState2)
    }

    @Test
    fun `InicioUiState hashCode es consistente`() {
        // Given
        val uiState1 = InicioUiState(isLoading = true, error = "Error")
        val uiState2 = InicioUiState(isLoading = true, error = "Error")

        // Then
        assertEquals(uiState1.hashCode(), uiState2.hashCode())
    }

    @Test
    fun `InicioUiState toString contiene informacion relevante`() {
        // Given
        val uiState = InicioUiState(isLoading = true, error = "Error de red")

        // When
        val toString = uiState.toString()

        // Then
        assertTrue(toString.contains("isLoading=true"))
        assertTrue(toString.contains("Error de red"))
    }

    @Test
    fun `dos InicioUiState con diferentes valores son diferentes`() {
        // Given
        val uiState1 = InicioUiState(isLoading = true)
        val uiState2 = InicioUiState(isLoading = false)

        // Then
        assertNotEquals(uiState1, uiState2)
    }

    @Test
    fun `InicioUiState con error null y con error vacio son diferentes`() {
        // Given
        val uiState1 = InicioUiState(error = null)
        val uiState2 = InicioUiState(error = "")

        // Then
        assertNotEquals(uiState1, uiState2)
    }

    @Test
    fun `viewModel uiState se puede acceder multiples veces`() = runTest {
        // When
        val uiState1 = viewModel.uiState.value
        val uiState2 = viewModel.uiState.value

        // Then
        assertEquals(uiState1, uiState2)
    }

    @Test
    fun `InicioUiState copy con error nulo funciona`() {
        // Given
        val uiState = InicioUiState(error = "Error inicial")

        // When
        val uiStateCopy = uiState.copy(error = null)

        // Then
        assertNull(uiStateCopy.error)
    }
}
