package com.example.huertohogarapp.presentation.viewmodel

import com.example.huertohogarapp.data.model.CartItem
import com.example.huertohogarapp.data.model.Categoria
import com.example.huertohogarapp.data.model.PaisOrigen
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.data.repository.CarritoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class CarritoViewModelTest {

    private val carritoRepository: CarritoRepository = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `agregarProducto should call repository's agregarProducto`() = runTest {
        // Given
        val categoria = Categoria(1, "Frutas", "Descripción de frutas")
        val pais = PaisOrigen(1, "Chile")
        val producto = Producto(
            idProducto = 1,
            nombreProducto = "Tomate",
            categoria = categoria,
            descripcionProducto = "Tomate fresco",
            precioProducto = 10.0,
            stockProducto = 100,
            paisOrigen = pais,
            imagenUrl = ""
        )
        val cartItem = CartItem(producto, 1)
        coEvery { carritoRepository.carritoItems } returns flowOf(listOf(cartItem))
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle() // Permite que el StateFlow inicial se colecte

        // When
        viewModel.agregarProducto(1)
        testDispatcher.scheduler.advanceUntilIdle() // Ejecuta la coroutine del método

        // Then
        coVerify { carritoRepository.agregarProducto(producto) }
    }

    @Test
    fun `quitarProducto should call repository's quitarProducto`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.quitarProducto(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { carritoRepository.quitarProducto(1) }
    }

    @Test
    fun `eliminarProducto should call repository's eliminarProducto`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.eliminarProducto(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { carritoRepository.eliminarProducto(1) }
    }

    @Test
    fun `limpiarCarrito should call repository's limpiarCarrito`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.limpiarCarrito()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { carritoRepository.limpiarCarrito() }
    }
}
