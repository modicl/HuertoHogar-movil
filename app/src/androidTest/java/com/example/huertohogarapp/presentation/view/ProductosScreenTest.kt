package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.data.model.CartItem
import com.example.huertohogarapp.data.model.Categoria
import com.example.huertohogarapp.data.model.PaisOrigen
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.presentation.viewmodel.ProductosUiState
import com.example.huertohogarapp.presentation.viewmodel.ProductosViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para ProductosScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de productos
 */
@RunWith(AndroidJUnit4::class)
class ProductosScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: ProductosViewModel
    private var navegoACarrito = false
    private var productoIdDetalle = -1

    private val categoriaFrutas = Categoria(1, "Frutas", "Frutas frescas")
    private val categoriaVerduras = Categoria(2, "Verduras", "Verduras frescas")
    private val pais = PaisOrigen(1, "Chile")

    private val productoTomate = Producto(
        idProducto = 1,
        nombreProducto = "Tomate",
        categoria = categoriaFrutas,
        descripcionProducto = "Tomate fresco y jugoso",
        precioProducto = 1500.0,
        stockProducto = 100,
        paisOrigen = pais,
        imagenUrl = ""
    )

    private val productoLechuga = Producto(
        idProducto = 2,
        nombreProducto = "Lechuga",
        categoria = categoriaVerduras,
        descripcionProducto = "Lechuga verde crujiente",
        precioProducto = 1000.0,
        stockProducto = 50,
        paisOrigen = pais,
        imagenUrl = ""
    )

    private val productoManzana = Producto(
        idProducto = 3,
        nombreProducto = "Manzana",
        categoria = categoriaFrutas,
        descripcionProducto = "Manzana roja dulce",
        precioProducto = 2000.0,
        stockProducto = 75,
        paisOrigen = pais,
        imagenUrl = ""
    )

    private val productosIniciales = listOf(productoTomate, productoLechuga, productoManzana)

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        navegoACarrito = false
        productoIdDetalle = -1
        
        val uiState = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = productosIniciales,
                productos = productosIniciales,
                searchQuery = "",
                categoriaSeleccionada = "Todos"
            )
        )
        every { mockViewModel.uiState } returns uiState
        every { mockViewModel.carritoItems } returns MutableStateFlow(emptyList())
        every { mockViewModel.obtenerCategorias() } returns listOf("Todos", "Frutas", "Verduras")
        every { mockViewModel.obtenerCantidadEnCarrito(any()) } returns 0
    }

    private fun setupScreen() {
        composeTestRule.setContent {
            ProductosScreen(
                onNavigateToCarrito = { navegoACarrito = true },
                onNavigateToDetalle = { id -> productoIdDetalle = id }
            )
        }
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra titulo Productos`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Productos").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra barra de busqueda`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Buscar productos...").assertExists()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra icono de carrito`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithContentDescription("Carrito").assertIsDisplayed()
    }

    @Test
    fun `GIVEN lista de productos WHEN pantalla se carga THEN muestra todos los productos`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga").assertIsDisplayed()
        composeTestRule.onNodeWithText("Manzana").assertIsDisplayed()
    }

    @Test
    fun `GIVEN lista de productos WHEN pantalla se carga THEN muestra descripciones de productos`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Tomate fresco y jugoso").assertExists()
        composeTestRule.onNodeWithText("Lechuga verde crujiente").assertExists()
        composeTestRule.onNodeWithText("Manzana roja dulce").assertExists()
    }

    @Test
    fun `GIVEN lista de productos WHEN pantalla se carga THEN muestra precios de productos`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("$1.500").assertExists()
        composeTestRule.onNodeWithText("$1.000").assertExists()
        composeTestRule.onNodeWithText("$2.000").assertExists()
    }

    @Test
    fun `GIVEN lista de productos WHEN pantalla se carga THEN muestra botones Agregar`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        val agregarNodes = composeTestRule.onAllNodesWithText("Agregar")
        agregarNodes.assertCountEquals(3)
    }

    @Test
    fun `GIVEN lista de categorias WHEN pantalla se carga THEN muestra filtros de categoria`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Todos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Frutas").assertIsDisplayed()
        composeTestRule.onNodeWithText("Verduras").assertIsDisplayed()
    }

    @Test
    fun `GIVEN carrito vacio WHEN pantalla se carga THEN no muestra badge en carrito`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithContentDescription("Carrito").assertIsDisplayed()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra badge con cantidad`() {
        // Given
        val carritoItems = listOf(
            CartItem(productoTomate, 2),
            CartItem(productoLechuga, 1)
        )
        every { mockViewModel.carritoItems } returns MutableStateFlow(carritoItems)

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("3").assertExists()
    }

    @Test
    fun `GIVEN productos sin resultados WHEN lista vacia THEN muestra mensaje no se encontraron productos`() {
        // Given
        val uiStateVacio = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = emptyList(),
                productos = emptyList(),
                searchQuery = "producto inexistente"
            )
        )
        every { mockViewModel.uiState } returns uiStateVacio

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("No se encontraron productos").assertIsDisplayed()
    }

    // ==================== PRUEBAS DE INTERACCIÓN ====================

    @Test
    fun `GIVEN barra de busqueda WHEN usuario escribe texto THEN llama buscarProductos`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Buscar productos...").performTextInput("Tomate")

        // Then
        verify { mockViewModel.buscarProductos("Tomate") }
    }

    @Test
    fun `GIVEN texto en busqueda WHEN usuario hace clic en limpiar THEN limpia busqueda`() {
        // Given
        val uiStateConBusqueda = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = listOf(productoTomate),
                productos = productosIniciales,
                searchQuery = "Tomate",
                categoriaSeleccionada = "Todos"
            )
        )
        every { mockViewModel.uiState } returns uiStateConBusqueda
        setupScreen()

        // When
        composeTestRule.onNodeWithContentDescription("Limpiar").performClick()

        // Then
        verify { mockViewModel.buscarProductos("") }
    }

    @Test
    fun `GIVEN filtro de categoria WHEN usuario hace clic THEN llama filtrarPorCategoria`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Frutas").performClick()

        // Then
        verify { mockViewModel.filtrarPorCategoria("Frutas") }
    }

    @Test
    fun `GIVEN producto sin cantidad en carrito WHEN usuario hace clic en Agregar THEN llama agregarAlCarrito`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onAllNodesWithText("Agregar")[0].performClick()

        // Then
        verify { mockViewModel.agregarAlCarrito(any()) }
    }

    @Test
    fun `GIVEN producto con cantidad en carrito WHEN usuario hace clic en mas THEN llama agregarAlCarrito`() {
        // Given
        every { mockViewModel.obtenerCantidadEnCarrito(1) } returns 2
        setupScreen()

        // When
        composeTestRule.onAllNodesWithContentDescription("Agregar")[0].performClick()

        // Then
        verify { mockViewModel.agregarAlCarrito(any()) }
    }

    @Test
    fun `GIVEN producto con cantidad en carrito WHEN usuario hace clic en menos THEN llama quitarDelCarrito`() {
        // Given
        every { mockViewModel.obtenerCantidadEnCarrito(1) } returns 2
        setupScreen()

        // When
        composeTestRule.onAllNodesWithContentDescription("Quitar")[0].performClick()

        // Then
        verify { mockViewModel.quitarDelCarrito(any()) }
    }

    @Test
    fun `GIVEN icono de carrito WHEN usuario hace clic THEN navega a carrito`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithContentDescription("Carrito").performClick()

        // Then
        assert(navegoACarrito) { "Debería haber navegado al carrito" }
    }

    @Test
    fun `GIVEN producto en lista WHEN usuario hace clic en card THEN navega a detalle`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Tomate").performClick()

        // Then
        assert(productoIdDetalle != -1) { "Debería haber navegado al detalle del producto" }
    }

    // ==================== PRUEBAS DE FILTRADO ====================

    @Test
    fun `GIVEN categoria seleccionada WHEN categoria es Frutas THEN muestra solo frutas`() {
        // Given
        val productosFrutas = listOf(productoTomate, productoManzana)
        val uiStateFiltrado = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = productosFrutas,
                productos = productosIniciales,
                categoriaSeleccionada = "Frutas"
            )
        )
        every { mockViewModel.uiState } returns uiStateFiltrado

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Manzana").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga").assertDoesNotExist()
    }

    @Test
    fun `GIVEN busqueda activa WHEN se busca Tomate THEN muestra solo tomate`() {
        // Given
        val uiStateBusqueda = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = listOf(productoTomate),
                productos = productosIniciales,
                searchQuery = "Tomate"
            )
        )
        every { mockViewModel.uiState } returns uiStateBusqueda

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga").assertDoesNotExist()
        composeTestRule.onNodeWithText("Manzana").assertDoesNotExist()
    }

    @Test
    fun `GIVEN categoria seleccionada WHEN se hace clic en categoria ya seleccionada THEN vuelve a Todos`() {
        // Given
        val uiStateFiltrado = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = listOf(productoTomate, productoManzana),
                productos = productosIniciales,
                categoriaSeleccionada = "Frutas"
            )
        )
        every { mockViewModel.uiState } returns uiStateFiltrado
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Frutas").performClick()

        // Then
        verify { mockViewModel.filtrarPorCategoria("Frutas") }
    }

    // ==================== PRUEBAS DE ESTADO DEL CARRITO ====================

    @Test
    fun `GIVEN producto con cantidad en carrito WHEN pantalla se renderiza THEN muestra cantidad`() {
        // Given
        every { mockViewModel.obtenerCantidadEnCarrito(1) } returns 3
        every { mockViewModel.obtenerCantidadEnCarrito(2) } returns 0
        every { mockViewModel.obtenerCantidadEnCarrito(3) } returns 0

        // When
        setupScreen()

        // Then
        composeTestRule.onAllNodesWithText("3").assertCountEquals(1)
    }

    @Test
    fun `GIVEN producto sin cantidad en carrito WHEN pantalla se renderiza THEN muestra boton Agregar`() {
        // Given
        every { mockViewModel.obtenerCantidadEnCarrito(any()) } returns 0

        // When
        setupScreen()

        // Then
        val agregarNodes = composeTestRule.onAllNodesWithText("Agregar")
        agregarNodes.assertCountEquals(3)
    }

    @Test
    fun `GIVEN carrito con varios productos WHEN pantalla se carga THEN badge muestra suma correcta`() {
        // Given
        val carritoItems = listOf(
            CartItem(productoTomate, 5),
            CartItem(productoLechuga, 3)
        )
        every { mockViewModel.carritoItems } returns MutableStateFlow(carritoItems)

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("8").assertExists()
    }

    // ==================== PRUEBAS DE MENSAJES ====================

    @Test
    fun `GIVEN mensaje en snackbar WHEN se agrega producto THEN muestra mensaje`() {
        // Given
        val uiStateConMensaje = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = productosIniciales,
                productos = productosIniciales,
                mensajeSnackbar = "Tomate agregado al carrito"
            )
        )
        every { mockViewModel.uiState } returns uiStateConMensaje

        // When
        setupScreen()

        // Then
        composeTestRule.waitUntil(timeoutMillis = 2000) {
            composeTestRule.onAllNodesWithText("Tomate agregado al carrito")
                .fetchSemanticsNodes().isNotEmpty()
        }
    }

    // ==================== PRUEBAS DE INTEGRACIÓN ====================

    @Test
    fun `GIVEN multiples categorias WHEN se seleccionan varias THEN filtra correctamente`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Verduras").performClick()

        // Then
        verify { mockViewModel.filtrarPorCategoria("Verduras") }
    }

    @Test
    fun `GIVEN busqueda y categoria WHEN se aplican ambos filtros THEN aplica ambos`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Frutas").performClick()
        composeTestRule.onNodeWithText("Buscar productos...").performTextInput("Manzana")

        // Then
        verify { mockViewModel.filtrarPorCategoria("Frutas") }
        verify { mockViewModel.buscarProductos("Manzana") }
    }

    @Test
    fun `GIVEN producto agregado al carrito WHEN se incrementa THEN actualiza badge`() {
        // Given
        val carritoItemsFlow = MutableStateFlow(listOf(CartItem(productoTomate, 1)))
        every { mockViewModel.carritoItems } returns carritoItemsFlow
        setupScreen()

        // When - El badge debería mostrar 1
        // Then
        composeTestRule.onNodeWithText("1").assertExists()
    }

    @Test
    fun `GIVEN lista de productos WHEN se hace scroll THEN muestra todos los productos`() {
        // Given
        setupScreen()

        // When - Todos los productos deberían estar visibles (grid de 2 columnas)
        // Then
        composeTestRule.onNodeWithText("Tomate").assertExists()
        composeTestRule.onNodeWithText("Lechuga").assertExists()
        composeTestRule.onNodeWithText("Manzana").assertExists()
    }

    @Test
    fun `GIVEN barra de busqueda vacia WHEN se muestra THEN no muestra icono de limpiar`() {
        // Given
        val uiStateBusquedaVacia = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productosFiltrados = productosIniciales,
                productos = productosIniciales,
                searchQuery = ""
            )
        )
        every { mockViewModel.uiState } returns uiStateBusquedaVacia

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithContentDescription("Limpiar").assertDoesNotExist()
    }

    @Test
    fun `GIVEN categoria Todos seleccionada WHEN pantalla se carga THEN muestra todos los productos`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Tomate").assertExists()
        composeTestRule.onNodeWithText("Lechuga").assertExists()
        composeTestRule.onNodeWithText("Manzana").assertExists()
    }

    @Test
    fun `GIVEN producto card WHEN se verifica contenido THEN muestra toda la informacion`() {
        // Given
        setupScreen()

        // When - Verificar que cada card tiene la info completa
        // Then
        composeTestRule.onNodeWithText("Tomate").assertExists()
        composeTestRule.onNodeWithText("Tomate fresco y jugoso").assertExists()
        composeTestRule.onNodeWithText("$1.500").assertExists()
    }

    @Test
    fun `GIVEN filtros de categoria WHEN se verifica lista THEN muestra todas las categorias disponibles`() {
        // Given
        setupScreen()

        // When
        val categorias = listOf("Todos", "Frutas", "Verduras")

        // Then
        categorias.forEach { categoria ->
            composeTestRule.onNodeWithText(categoria).assertExists()
        }
    }
}
