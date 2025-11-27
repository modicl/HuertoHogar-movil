package com.example.huertohogarapp.presentation.viewmodel

import android.app.Application
import com.example.huertohogarapp.data.model.Categoria
import com.example.huertohogarapp.data.model.PaisOrigen
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.data.repository.CarritoRepository
import com.example.huertohogarapp.data.repository.ProductoRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ProductosViewModelTest {

    private lateinit var viewModel: ProductosViewModel
    private val application: Application = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private val categoriaFrutas = Categoria(1, "Frutas", "Frutas frescas")
    private val categoriaVerduras = Categoria(2, "Verduras", "Verduras frescas")
    private val pais = PaisOrigen(1, "Chile")

    private val productosIniciales = listOf(
        Producto(
            idProducto = 1,
            nombreProducto = "Tomate",
            categoria = categoriaFrutas,
            descripcionProducto = "Tomate fresco",
            precioProducto = 1500.0,
            stockProducto = 100,
            paisOrigen = pais,
            imagenUrl = ""
        ),
        Producto(
            idProducto = 2,
            nombreProducto = "Lechuga",
            categoria = categoriaVerduras,
            descripcionProducto = "Lechuga verde",
            precioProducto = 1000.0,
            stockProducto = 50,
            paisOrigen = pais,
            imagenUrl = ""
        ),
        Producto(
            idProducto = 3,
            nombreProducto = "Manzana",
            categoria = categoriaFrutas,
            descripcionProducto = "Manzana roja",
            precioProducto = 2000.0,
            stockProducto = 80,
            paisOrigen = pais,
            imagenUrl = ""
        )
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock del ApplicationContext
        every { application.applicationContext } returns application
        
        // Mock de ProductoRepositoryImpl estático
        mockkConstructor(ProductoRepositoryImpl::class)
        every { anyConstructed<ProductoRepositoryImpl>().getProductos() } returns flowOf(productosIniciales)
        
        // Mock de CarritoRepository
        mockkConstructor(CarritoRepository::class)
        every { anyConstructed<CarritoRepository>().carritoItems } returns flowOf(emptyList())
        
        viewModel = ProductosViewModel(application)
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `al inicializar viewModel, carga los productos correctamente`() = runTest {
        // Then
        val uiState = viewModel.uiState.value
        assertEquals(3, uiState.productos.size)
        assertEquals(3, uiState.productosFiltrados.size)
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun `filtrarPorCategoria filtra correctamente productos de Frutas`() = runTest {
        // When
        viewModel.filtrarPorCategoria("Frutas")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("Frutas", uiState.categoriaSeleccionada)
        assertEquals(2, uiState.productosFiltrados.size)
        assertTrue(uiState.productosFiltrados.all { it.categoria.nombreCategoria == "Frutas" })
    }

    @Test
    fun `filtrarPorCategoria filtra correctamente productos de Verduras`() = runTest {
        // When
        viewModel.filtrarPorCategoria("Verduras")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("Verduras", uiState.categoriaSeleccionada)
        assertEquals(1, uiState.productosFiltrados.size)
        assertEquals("Lechuga", uiState.productosFiltrados.first().nombreProducto)
    }

    @Test
    fun `al seleccionar la misma categoria dos veces, vuelve a mostrar Todos`() = runTest {
        // When
        viewModel.filtrarPorCategoria("Frutas")
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.filtrarPorCategoria("Frutas")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("Todos", uiState.categoriaSeleccionada)
        assertEquals(3, uiState.productosFiltrados.size)
    }

    @Test
    fun `buscarProductos filtra productos por nombre`() = runTest {
        // When
        viewModel.buscarProductos("Tomate")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals("Tomate", uiState.searchQuery)
        assertEquals(1, uiState.productosFiltrados.size)
        assertEquals("Tomate", uiState.productosFiltrados.first().nombreProducto)
    }

    @Test
    fun `buscarProductos filtra productos por descripcion`() = runTest {
        // When
        viewModel.buscarProductos("verde")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.productosFiltrados.size)
        assertEquals("Lechuga", uiState.productosFiltrados.first().nombreProducto)
    }

    @Test
    fun `buscarProductos es case insensitive`() = runTest {
        // When
        viewModel.buscarProductos("MANZANA")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.productosFiltrados.size)
        assertEquals("Manzana", uiState.productosFiltrados.first().nombreProducto)
    }

    @Test
    fun `buscarProductos con query vacio muestra todos los productos`() = runTest {
        // When
        viewModel.buscarProductos("")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(3, uiState.productosFiltrados.size)
    }

    @Test
    fun `aplicar filtros de categoria y busqueda simultaneamente`() = runTest {
        // When
        viewModel.filtrarPorCategoria("Frutas")
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.buscarProductos("Manzana")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.productosFiltrados.size)
        assertEquals("Manzana", uiState.productosFiltrados.first().nombreProducto)
    }

    @Test
    fun `agregarAlCarrito llama al repositorio y muestra mensaje`() = runTest {
        // Given
        val producto = productosIniciales.first()
        coEvery { anyConstructed<CarritoRepository>().agregarProducto(any()) } just Runs

        // When
        viewModel.agregarAlCarrito(producto)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { anyConstructed<CarritoRepository>().agregarProducto(producto) }
        val uiState = viewModel.uiState.value
        assertEquals("${producto.nombreProducto} agregado al carrito", uiState.mensajeSnackbar)
    }

    @Test
    fun `quitarDelCarrito llama al repositorio correctamente`() = runTest {
        // Given
        coEvery { anyConstructed<CarritoRepository>().quitarProducto(any()) } just Runs

        // When
        viewModel.quitarDelCarrito(1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { anyConstructed<CarritoRepository>().quitarProducto(1) }
    }

    @Test
    fun `limpiarMensaje elimina el mensaje del snackbar`() = runTest {
        // Given
        viewModel.agregarAlCarrito(productosIniciales.first())
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.limpiarMensaje()

        // Then
        val uiState = viewModel.uiState.value
        assertNull(uiState.mensajeSnackbar)
    }

    @Test
    fun `obtenerCategorias retorna lista correcta con Todos primero`() = runTest {
        // When
        val categorias = viewModel.obtenerCategorias()

        // Then
        assertEquals("Todos", categorias.first())
        assertTrue(categorias.contains("Frutas"))
        assertTrue(categorias.contains("Verduras"))
        assertEquals(3, categorias.size) // Todos, Frutas, Verduras
    }
}
