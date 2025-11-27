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

@RunWith(AndroidJUnit4::class)
class CarritoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: CarritoViewModel = mockk(relaxed = true)

    private val categoriaFrutas = Categoria(1, "Frutas", "Frutas frescas")
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
        categoria = categoriaFrutas,
        descripcionProducto = "Lechuga verde",
        precioProducto = 1000.0,
        stockProducto = 50,
        paisOrigen = pais,
        imagenUrl = ""
    )

    private val carritoItemsIniciales = listOf(
        CartItem(producto1, 2),
        CartItem(producto2, 1)
    )

    @Before
    fun setUp() {
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

    @Test
    fun `la pantalla muestra el titulo Carrito de Compras`() {
        // When
        composeTestRule.setContent {
            CarritoScreen()
        }

        // Then
        composeTestRule.onNodeWithText("Carrito de Compras").assertIsDisplayed()
    }

    @Test
    fun `la pantalla con carrito vacio muestra mensaje correspondiente`() {
        // Given
        val carritoVacioFlow = MutableStateFlow(emptyList<CartItem>())
        every { mockViewModel.carritoItems } returns carritoVacioFlow

        // When
        composeTestRule.setContent {
            CarritoScreen()
        }

        // Then
        composeTestRule.onNodeWithText("Tu carrito está vacío").assertIsDisplayed()
        composeTestRule.onNodeWithText("Agrega productos desde la tienda").assertIsDisplayed()
    }

    @Test
    fun `la pantalla muestra los productos del carrito correctamente`() {
        // Given
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

        // When
        composeTestRule.setContent {
            CarritoScreen()
        }

        // Then
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga").assertIsDisplayed()
    }

    @Test
    fun `la pantalla muestra las cantidades de los productos correctamente`() {
        // Given
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

        // When
        composeTestRule.setContent {
            CarritoScreen()
        }

        // Then
        composeTestRule.onAllNodesWithText("2")[0].assertIsDisplayed() // Cantidad de Tomate
        composeTestRule.onAllNodesWithText("1")[0].assertIsDisplayed() // Cantidad de Lechuga
    }

    @Test
    fun `la pantalla muestra el total correctamente`() {
        // Given
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

        // When
        composeTestRule.setContent {
            CarritoScreen()
        }

        // Then
        composeTestRule.onNodeWithText("Subtotal (3 items)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Total").assertIsDisplayed()
    }

    @Test
    fun `al hacer clic en el boton agregar, se incrementa la cantidad del producto`() {
        // Given
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

        composeTestRule.setContent {
            CarritoScreen()
        }

        // When - Hacer clic en el primer botón de agregar (usando el icono Add)
        composeTestRule.onAllNodesWithContentDescription("Agregar")[0].performClick()

        // Then
        verify { mockViewModel.agregarProducto(1) }
    }

    @Test
    fun `al hacer clic en el boton quitar, se decrementa la cantidad del producto`() {
        // Given
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

        composeTestRule.setContent {
            CarritoScreen()
        }

        // When - Hacer clic en el primer botón de quitar (usando el icono Remove)
        composeTestRule.onAllNodesWithContentDescription("Quitar")[0].performClick()

        // Then
        verify { mockViewModel.quitarProducto(1) }
    }

    @Test
    fun `al hacer clic en el boton eliminar, se elimina el producto del carrito`() {
        // Given
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

        composeTestRule.setContent {
            CarritoScreen()
        }

        // When - Hacer clic en el primer botón de eliminar
        composeTestRule.onAllNodesWithContentDescription("Eliminar")[0].performClick()

        // Then
        verify { mockViewModel.eliminarProducto(1) }
    }

    @Test
    fun `al hacer clic en Limpiar carrito, muestra dialogo de confirmacion`() {
        // Given
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

        composeTestRule.setContent {
            CarritoScreen()
        }

        // When
        composeTestRule.onNodeWithContentDescription("Limpiar carrito").performClick()

        // Then
        composeTestRule.onNodeWithText("Limpiar carrito").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿Estás seguro de que deseas eliminar todos los productos del carrito?").assertIsDisplayed()
    }

    @Test
    fun `al confirmar limpiar carrito en el dialogo, se llama a limpiarCarrito`() {
        // Given
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

        composeTestRule.setContent {
            CarritoScreen()
        }

        // When
        composeTestRule.onNodeWithContentDescription("Limpiar carrito").performClick()
        composeTestRule.onNodeWithText("Limpiar").performClick()

        // Then
        verify { mockViewModel.limpiarCarrito() }
    }

    @Test
    fun `al hacer clic en Realizar Compra, se llama a realizarCompra`() {
        // Given
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

        composeTestRule.setContent {
            CarritoScreen()
        }

        // When
        composeTestRule.onNodeWithText("Realizar Compra").performClick()

        // Then
        verify { mockViewModel.realizarCompra() }
    }

    @Test
    fun `cuando mostrarDialogoExito es true, muestra el dialogo de compra exitosa`() {
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
            CarritoScreen()
        }

        // Then
        composeTestRule.onNodeWithText("¡Compra Exitosa!").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tu pedido ha sido procesado correctamente.").assertIsDisplayed()
    }
}
