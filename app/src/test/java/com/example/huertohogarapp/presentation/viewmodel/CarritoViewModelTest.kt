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

    @Test
    fun `realizarCompra should show success dialog`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.realizarCompra()

        // Then
        assert(viewModel.uiState.value.mostrarDialogoExito)
    }

    @Test
    fun `ocultarDialogoExito should hide success dialog`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.realizarCompra()

        // When
        viewModel.ocultarDialogoExito()

        // Then
        assert(!viewModel.uiState.value.mostrarDialogoExito)
    }

    @Test
    fun `confirmarCompra should clear cart and hide dialog`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.realizarCompra()

        // When
        viewModel.confirmarCompra()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { carritoRepository.limpiarCarrito() }
        assert(!viewModel.uiState.value.mostrarDialogoExito)
    }

    @Test
    fun `uiState should update total when items change`() = runTest {
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
        val cartItem = CartItem(producto, 2)
        coEvery { carritoRepository.carritoItems } returns flowOf(listOf(cartItem))
        
        // When
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assert(viewModel.uiState.value.total == 20.0)
        assert(viewModel.uiState.value.cantidadTotal == 2)
    }

    @Test
    fun `agregarProducto with non-existing product should not call repository`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.agregarProducto(999) // ID que no existe
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 0) { carritoRepository.agregarProducto(any()) }
    }

    @Test
    fun `carritoItems should be exposed correctly`() = runTest {
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

        // When
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assert(viewModel.carritoItems.value.size == 1)
    }

    @Test
    fun `uiState should have zero total with empty cart`() = runTest {
        // Given
        coEvery { carritoRepository.carritoItems } returns flowOf(emptyList())

        // When
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assert(viewModel.uiState.value.total == 0.0)
        assert(viewModel.uiState.value.cantidadTotal == 0)
    }

    @Test
    fun `uiState should calculate total correctly with multiple items`() = runTest {
        // Given
        val categoria = Categoria(1, "Frutas", "Descripción de frutas")
        val pais = PaisOrigen(1, "Chile")
        val producto1 = Producto(
            idProducto = 1,
            nombreProducto = "Tomate",
            categoria = categoria,
            descripcionProducto = "Tomate fresco",
            precioProducto = 10.0,
            stockProducto = 100,
            paisOrigen = pais,
            imagenUrl = ""
        )
        val producto2 = Producto(
            idProducto = 2,
            nombreProducto = "Lechuga",
            categoria = categoria,
            descripcionProducto = "Lechuga verde",
            precioProducto = 5.0,
            stockProducto = 50,
            paisOrigen = pais,
            imagenUrl = ""
        )
        val cartItems = listOf(
            CartItem(producto1, 2), // 10 * 2 = 20
            CartItem(producto2, 3)  // 5 * 3 = 15
        )
        coEvery { carritoRepository.carritoItems } returns flowOf(cartItems)

        // When
        val viewModel = CarritoViewModel(carritoRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assert(viewModel.uiState.value.total == 35.0) // 20 + 15 = 35
        assert(viewModel.uiState.value.cantidadTotal == 5) // 2 + 3 = 5
    }
}
