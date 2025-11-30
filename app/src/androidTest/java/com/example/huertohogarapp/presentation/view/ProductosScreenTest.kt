package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.data.model.Producto
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests de UI para ProductosScreen
 * Estos tests verifican la renderización de la pantalla de productos
 */
@RunWith(AndroidJUnit4::class)
class ProductosScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `la pantalla de productos se renderiza correctamente`() {
        composeTestRule.setContent {
            ProductosScreen()
        }
        
        // Verificar que elementos básicos de la UI existen
        composeTestRule.waitForIdle()
        // La pantalla debería cargar sin errores
    }

    @Test
    fun `el campo de busqueda esta presente`() {
        composeTestRule.setContent {
            ProductosScreen()
        }
        
        composeTestRule.waitForIdle()
        // El campo de búsqueda debería estar visible
        composeTestRule.onNodeWithTag("SearchBar").assertExists()
    }

    @Test
    fun `se puede escribir en el campo de busqueda`() {
        composeTestRule.setContent {
            ProductosScreen()
        }
        
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("SearchBar").performTextInput("Tomate")
        composeTestRule.onNodeWithTag("SearchBar").assertTextContains("Tomate")
    }
}
