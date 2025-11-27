package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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

@RunWith(AndroidJUnit4::class)
class ProductosScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: ProductosViewModel = mockk(relaxed = true)

    private val productosIniciales = listOf(
        Producto(1, "Tomate", 1.5, "", "Frutas"),
        Producto(2, "Lechuga", 1.0, "", "Verduras"),
        Producto(3, "Manzana", 2.0, "", "Frutas")
    )

    @Before
    fun setUp() {
        val uiState = MutableStateFlow(ProductosUiState(productosFiltrados = productosIniciales))
        every { mockViewModel.uiState } returns uiState
        every { mockViewModel.carritoItems } returns MutableStateFlow(emptyList())
        every { mockViewModel.obtenerCategorias() } returns listOf("Todos", "Frutas", "Verduras")
        
        composeTestRule.setContent {
            ProductosScreen(viewModel = mockViewModel)
        }
    }

    @Test
    fun `la pantalla muestra los productos iniciales`() {
        composeTestRule.onNodeWithText("Tomate").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lechuga").assertIsDisplayed()
        composeTestRule.onNodeWithText("Manzana").assertIsDisplayed()
    }

    @Test
    fun `al buscar por un producto, la lista se filtra`() {
        // When
        composeTestRule.onNodeWithTag("SearchBar").performTextInput("Tomate")

        // Then
        verify { mockViewModel.buscarProductos("Tomate") }
    }

    @Test
    fun `al hacer clic en un filtro de categoria, la lista se filtra`() {
        // When
        composeTestRule.onNodeWithText("Frutas").performClick()

        // Then
        verify { mockViewModel.filtrarPorCategoria("Frutas") }
    }

    @Test
    fun `al hacer clic en el boton Agregar, se agrega el producto al carrito`() {
        // When
        composeTestRule.onAllNodesWithText("Agregar").onFirst().performClick()

        // Then
        verify { mockViewModel.agregarAlCarrito(productosIniciales.first()) }
    }
}
