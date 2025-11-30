package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.data.model.CartItem
import com.example.huertohogarapp.data.model.Categoria
import com.example.huertohogarapp.data.model.PaisOrigen
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.presentation.viewmodel.CarritoUiState
import com.example.huertohogarapp.presentation.viewmodel.CarritoViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para CarritoScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de carrito
 */
@RunWith(AndroidJUnit4::class)
class CarritoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: CarritoViewModel
    private var navegoAtras = false
    private var compraExitosaCallback = false

    private val categoriaFrutas = Categoria(1, "Frutas", "Frutas frescas")
    private val categoriaVerduras = Categoria(2, "Verduras", "Verduras frescas")
    private val pais = PaisOrigen(1, "Chile")

    private val producto1 = Producto(
        idProducto = 1,
        nombreProducto = "Tomate",
        categoria = categoriaFrutas,
        descripcionProducto = "Tomate fresco",
        precioProducto = 1500.0,
        stockProducto = 100,
        paisOrigen = pais,
        imagenUrl = ""
    )

    private val producto2 = Producto(
        idProducto = 2,
        nombreProducto = "Lechuga",
        categoria = categoriaVerduras,
        descripcionProducto = "Lechuga verde",
        precioProducto = 1000.0,
        stockProducto = 50,
        paisOrigen = pais,
        imagenUrl = ""
    )

    private val producto3 = Producto(
        idProducto = 3,
        nombreProducto = "Manzana",
        categoria = categoriaFrutas,
        descripcionProducto = "Manzana roja",
        precioProducto = 2000.0,
        stockProducto = 75,
        paisOrigen = pais,
        imagenUrl = ""
    )

    private val carritoItemsIniciales = listOf(
        CartItem(producto1, 2),
        CartItem(producto2, 1)
    )

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        navegoAtras = false
        compraExitosaCallback = false
        
        val carritoItemsFlow = MutableStateFlow(carritoItemsIniciales)
        val uiStateFlow = MutableStateFlow(
            CarritoUiState(
                cantidadTotal = 3,
                total = 4000.0,
                mostrarDialogoExito = false
            )
        )

        every { mockViewModel.carritoItems } returns carritoItemsFlow
        every { mockViewModel.uiState } returns uiStateFlow
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================
    
    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra titulo Carrito de Compras`() {
        // Given - Setup ya realizado en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Carrito de Compras").assertIsDisplayed()
    }

    @Test
    fun `GIVEN carrito vacio WHEN pantalla se carga THEN muestra mensaje de carrito vacio`() {
        // Given
        val carritoVacioFlow = MutableStateFlow(emptyList<CartItem>())
        val uiStateVacio = MutableStateFlow(
            CarritoUiState(
                cantidadTotal = 0,
                total = 0.0,
                mostrarDialogoExito = false
            )
        )
        every { mockViewModel.carritoItems } returns carritoVacioFlow
        every { mockViewModel.uiState } returns uiStateVacio

        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Tu carrito está vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("Agrega productos desde la tienda").assertIsDisplayed()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra los productos correctamente`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tomate fresco").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga verde").assertIsDisplayed()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra las cantidades correctamente`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onAllNodesWithText("2").assertCountEquals(1)
        composeTestRule.onAllNodesWithText("1").assertCountEquals(1)
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra precios correctamente`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("$1.500").assertExists()
        composeTestRule.onNodeWithText("$1.000").assertExists()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra subtotal correctamente`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Subtotal (3 items)").assertIsDisplayed()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra total correctamente`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Total").assertIsDisplayed()
        composeTestRule.onNodeWithText("$4.000").assertExists()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra boton Realizar Compra`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Realizar Compra").assertIsDisplayed()
        composeTestRule.onNodeWithText("Realizar Compra").assertHasClickAction()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra icono de navegacion atras`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Volver").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Volver").assertHasClickAction()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra icono de limpiar carrito`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Limpiar carrito").assertIsDisplayed()
    }

    @Test
    fun `GIVEN carrito vacio WHEN pantalla se carga THEN NO muestra icono de limpiar carrito`() {
        // Given
        val carritoVacioFlow = MutableStateFlow(emptyList<CartItem>())
        val uiStateVacio = MutableStateFlow(CarritoUiState())
        every { mockViewModel.carritoItems } returns carritoVacioFlow
        every { mockViewModel.uiState } returns uiStateVacio

        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Limpiar carrito").assertDoesNotExist()
    }

    @Test
    fun `GIVEN carrito vacio WHEN pantalla se carga THEN muestra icono de carrito grande`() {
        // Given
        val carritoVacioFlow = MutableStateFlow(emptyList<CartItem>())
        val uiStateVacio = MutableStateFlow(CarritoUiState())
        every { mockViewModel.carritoItems } returns carritoVacioFlow
        every { mockViewModel.uiState } returns uiStateVacio

        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Tu carrito está vacío").assertIsDisplayed()
    }

    // ==================== PRUEBAS DE INTERACCIÓN ====================

    @Test
    fun `GIVEN carrito con productos WHEN usuario hace clic en boton Agregar THEN llama agregarProducto`() {
        // Given - Setup en setUp()
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // When - Hacer clic en el primer botón de agregar
        composeTestRule.onAllNodesWithContentDescription("Agregar")[0].performClick()

        // Then
        verify { mockViewModel.agregarProducto(1) }
    }

    @Test
    fun `GIVEN carrito con productos WHEN usuario hace clic en boton Quitar THEN llama quitarProducto`() {
        // Given - Setup en setUp()
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // When - Hacer clic en el primer botón de quitar
        composeTestRule.onAllNodesWithContentDescription("Quitar")[0].performClick()

        // Then
        verify { mockViewModel.quitarProducto(1) }
    }

    @Test
    fun `GIVEN carrito con productos WHEN usuario hace clic en boton Eliminar THEN llama eliminarProducto`() {
        // Given - Setup en setUp()
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // When - Hacer clic en el primer botón de eliminar
        composeTestRule.onAllNodesWithContentDescription("Eliminar")[0].performClick()

        // Then
        verify { mockViewModel.eliminarProducto(1) }
    }

    @Test
    fun `GIVEN carrito con productos WHEN usuario hace clic en Realizar Compra THEN llama realizarCompra`() {
        // Given - Setup en setUp()
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // When
        composeTestRule.onNodeWithText("Realizar Compra").performClick()

        // Then
        verify { mockViewModel.realizarCompra() }
    }

    @Test
    fun `GIVEN pantalla cargada WHEN usuario hace clic en flecha atras THEN ejecuta callback onNavigateBack`() {
        // Given
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("Volver").performClick()

        // Then
        assert(navegoAtras) { "Debería haber ejecutado navegación hacia atrás" }
    }

    @Test
    fun `GIVEN carrito con productos WHEN usuario hace clic en Limpiar carrito THEN muestra dialogo de confirmacion`() {
        // Given - Setup en setUp()
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // When
        composeTestRule.onNodeWithContentDescription("Limpiar carrito").performClick()

        // Then
        composeTestRule.onNodeWithText("Limpiar carrito").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿Estás seguro de que deseas eliminar todos los productos del carrito?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Limpiar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancelar").assertIsDisplayed()
    }

    @Test
    fun `GIVEN dialogo de limpiar visible WHEN usuario confirma THEN llama limpiarCarrito y cierra dialogo`() {
        // Given
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }
        composeTestRule.onNodeWithContentDescription("Limpiar carrito").performClick()

        // When
        composeTestRule.onNodeWithText("Limpiar").performClick()

        // Then
        verify { mockViewModel.limpiarCarrito() }
    }

    @Test
    fun `GIVEN dialogo de limpiar visible WHEN usuario cancela THEN cierra dialogo sin limpiar`() {
        // Given
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }
        composeTestRule.onNodeWithContentDescription("Limpiar carrito").performClick()

        // When
        composeTestRule.onNodeWithText("Cancelar").performClick()

        // Then
        composeTestRule.waitUntil(timeoutMillis = 1000) {
            composeTestRule.onAllNodesWithText("Limpiar carrito").fetchSemanticsNodes().isEmpty()
        }
    }

    // ==================== PRUEBAS DE ESTADO ====================

    @Test
    fun `GIVEN mostrarDialogoExito es true WHEN pantalla se renderiza THEN muestra dialogo de compra exitosa`() {
        // Given
        val carritoItemsFlow = MutableStateFlow(carritoItemsIniciales)
        val uiStateFlow = MutableStateFlow(
            CarritoUiState(
                cantidadTotal = 3,
                total = 4000.0,
                mostrarDialogoExito = true
            )
        )
        every { mockViewModel.carritoItems } returns carritoItemsFlow
        every { mockViewModel.uiState } returns uiStateFlow

        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("¡Compra Exitosa!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tu pedido ha sido procesado correctamente.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Total: $4.000").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ir al Inicio").assertIsDisplayed()
    }

    @Test
    fun `GIVEN dialogo de exito visible WHEN usuario hace clic en Ir al Inicio THEN llama confirmarCompra y ejecuta callback`() {
        // Given
        val carritoItemsFlow = MutableStateFlow(carritoItemsIniciales)
        val uiStateFlow = MutableStateFlow(
            CarritoUiState(
                cantidadTotal = 3,
                total = 4000.0,
                mostrarDialogoExito = true
            )
        )
        every { mockViewModel.carritoItems } returns carritoItemsFlow
        every { mockViewModel.uiState } returns uiStateFlow

        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // When
        composeTestRule.onNodeWithText("Ir al Inicio").performClick()

        // Then
        verify { mockViewModel.confirmarCompra() }
        assert(compraExitosaCallback) { "Debería haber ejecutado callback de compra exitosa" }
    }

    @Test
    fun `GIVEN carrito con un producto WHEN pantalla se carga THEN muestra cantidadTotal correcta`() {
        // Given
        val carritoUnItem = listOf(CartItem(producto1, 5))
        val carritoItemsFlow = MutableStateFlow(carritoUnItem)
        val uiStateFlow = MutableStateFlow(
            CarritoUiState(
                cantidadTotal = 5,
                total = 7500.0,
                mostrarDialogoExito = false
            )
        )
        every { mockViewModel.carritoItems } returns carritoItemsFlow
        every { mockViewModel.uiState } returns uiStateFlow

        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Subtotal (5 items)").assertIsDisplayed()
        composeTestRule.onNodeWithText("$7.500").assertExists()
    }

    @Test
    fun `GIVEN carrito con tres productos WHEN pantalla se carga THEN muestra todos los productos`() {
        // Given
        val carritoTresItems = listOf(
            CartItem(producto1, 1),
            CartItem(producto2, 1),
            CartItem(producto3, 1)
        )
        val carritoItemsFlow = MutableStateFlow(carritoTresItems)
        val uiStateFlow = MutableStateFlow(
            CarritoUiState(
                cantidadTotal = 3,
                total = 4500.0,
                mostrarDialogoExito = false
            )
        )
        every { mockViewModel.carritoItems } returns carritoItemsFlow
        every { mockViewModel.uiState } returns uiStateFlow

        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Tomate").assertExists()
        composeTestRule.onNodeWithText("Lechuga").assertExists()
        composeTestRule.onNodeWithText("Manzana").assertExists()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN botones de accion son clickeables`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onAllNodesWithContentDescription("Agregar")[0].assertHasClickAction()
        composeTestRule.onAllNodesWithContentDescription("Quitar")[0].assertHasClickAction()
        composeTestRule.onAllNodesWithContentDescription("Eliminar")[0].assertHasClickAction()
    }

    @Test
    fun `GIVEN carrito con productos WHEN pantalla se carga THEN muestra descripcion de productos`() {
        // Given - Setup en setUp()
        
        // When
        composeTestRule.setContent {
            CarritoScreen(
                onNavigateBack = { navegoAtras = true },
                onCompraExitosa = { compraExitosaCallback = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Tomate fresco").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga verde").assertIsDisplayed()
    }
}
