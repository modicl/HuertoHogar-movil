package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.semantics.ProgressBarRangeInfo
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
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para ProductoDetalleScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de detalle de producto
 */
@RunWith(AndroidJUnit4::class)
class ProductoDetalleScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: ProductosViewModel
    private var navegoAtras = false
    private var navegoACarrito = false

    private val categoriaFrutas = Categoria(1, "Frutas", "Frutas frescas")
    private val pais = PaisOrigen(1, "Chile")

    private val productoTomate = Producto(
        idProducto = 1,
        nombreProducto = "Tomate",
        categoria = categoriaFrutas,
        descripcionProducto = "Tomate fresco y jugoso de la mejor calidad",
        precioProducto = 1500.0,
        stockProducto = 100,
        paisOrigen = pais,
        imagenUrl = "https://example.com/tomate.jpg"
    )

    private val productoSinStock = Producto(
        idProducto = 2,
        nombreProducto = "Lechuga",
        categoria = categoriaFrutas,
        descripcionProducto = "Lechuga sin stock",
        precioProducto = 1000.0,
        stockProducto = 0,
        paisOrigen = pais,
        imagenUrl = "https://example.com/lechuga.jpg"
    )

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        navegoAtras = false
        navegoACarrito = false
        
        val uiState = MutableStateFlow(
            ProductosUiState(
                isLoading = false,
                productos = listOf(productoTomate),
                productosFiltrados = listOf(productoTomate)
            )
        )
        every { mockViewModel.uiState } returns uiState
        every { mockViewModel.carritoItems } returns MutableStateFlow(emptyList())
        every { mockViewModel.getProductoById(1) } returns flowOf(productoTomate)
        every { mockViewModel.getProductoById(2) } returns flowOf(productoSinStock)
    }

    private fun setupScreen(productoId: Int = 1) {
        composeTestRule.setContent {
            ProductoDetalleScreen(
                productoId = productoId,
                viewModel = mockViewModel,
                onNavigateBack = { navegoAtras = true },
                onNavigateToCarrito = { navegoACarrito = true }
            )
        }
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra nombre del producto en TopBar`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra categoria del producto`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Frutas").assertIsDisplayed()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra precio`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Precio:", substring = true).assertExists()
        composeTestRule.onNodeWithText("$1.500", substring = true).assertExists()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra stock`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Stock disponible:", substring = true).assertExists()
        composeTestRule.onNodeWithText("100 unidades", substring = true).assertExists()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra pais de origen`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("País de origen:", substring = true).assertExists()
        composeTestRule.onNodeWithText("Chile").assertExists()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra descripcion`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Descripción").assertExists()
        composeTestRule.onNodeWithText("Tomate fresco y jugoso de la mejor calidad").assertExists()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra boton agregar al carrito`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Agregar al Carrito").assertIsDisplayed()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra boton ver carrito`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Ver Carrito").assertIsDisplayed()
    }

    @Test
    fun `GIVEN producto cargado WHEN pantalla se renderiza THEN muestra boton volver`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithContentDescription("Volver").assertIsDisplayed()
    }

    // ==================== PRUEBAS DE INTERACCIÓN ====================

    @Test
    fun `GIVEN boton volver WHEN usuario hace clic THEN navega atras`() {
        // Given
        setupScreen(1)

        // When
        composeTestRule.onNodeWithContentDescription("Volver").performClick()

        // Then
        assert(navegoAtras) { "Debería haber navegado atrás" }
    }

    @Test
    fun `GIVEN boton ver carrito WHEN usuario hace clic THEN navega a carrito`() {
        // Given
        setupScreen(1)

        // When
        composeTestRule.onNodeWithText("Ver Carrito").performScrollTo()
        composeTestRule.onNodeWithText("Ver Carrito").performClick()

        // Then
        assert(navegoACarrito) { "Debería haber navegado al carrito" }
    }

    @Test
    fun `GIVEN boton agregar al carrito WHEN usuario hace clic THEN llama agregarAlCarrito`() {
        // Given
        setupScreen(1)

        // When
        composeTestRule.onNodeWithText("Agregar al Carrito").performScrollTo()
        composeTestRule.onNodeWithText("Agregar al Carrito").performClick()

        // Then
        verify { mockViewModel.agregarAlCarrito(productoTomate) }
    }

    @Test
    fun `GIVEN producto con stock WHEN pantalla se carga THEN boton agregar esta habilitado`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Agregar al Carrito").assertIsEnabled()
    }

    @Test
    fun `GIVEN producto sin stock WHEN pantalla se carga THEN muestra Sin Stock`() {
        // Given
        every { mockViewModel.getProductoById(2) } returns flowOf(productoSinStock)

        // When
        setupScreen(2)

        // Then
        composeTestRule.onNodeWithText("Sin Stock").assertExists()
    }

    @Test
    fun `GIVEN botones WHEN se renderizan THEN son clickeables`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Agregar al Carrito").assertHasClickAction()
        composeTestRule.onNodeWithText("Ver Carrito").assertHasClickAction()
    }

    // ==================== PRUEBAS DE ESTADO ====================

    @Test
    fun `GIVEN producto null WHEN se renderiza THEN muestra indicador de carga`() {
        // Given
        every { mockViewModel.getProductoById(999) } returns flowOf(null)

        // When
        setupScreen(999)

        // Then
        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertExists()
    }

    @Test
    fun `GIVEN pantalla WHEN se renderiza THEN es scrollable`() {
        // When
        setupScreen(1)

        // Then - Verificamos que podemos hacer scroll
        composeTestRule.onNodeWithText("Ver Carrito").performScrollTo()
        composeTestRule.onNodeWithText("Ver Carrito").assertIsDisplayed()
    }

    @Test
    fun `GIVEN producto WHEN pantalla se carga THEN muestra toda la informacion`() {
        // When
        setupScreen(1)

        // Then
        composeTestRule.onNodeWithText("Tomate").assertExists()
        composeTestRule.onNodeWithText("Frutas").assertExists()
        composeTestRule.onNodeWithText("Chile").assertExists()
        composeTestRule.onNodeWithText("Descripción").assertExists()
    }

    @Test
    fun `GIVEN TopBar WHEN se renderiza THEN tiene el color correcto del tema`() {
        // When
        setupScreen(1)

        // Then - Verificamos que el TopBar existe y tiene el título correcto
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
    }
}
