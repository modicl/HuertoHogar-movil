package com.example.huertohogarapp.presentation.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests instrumentados para la navegación de la aplicación
 * Verifican que las rutas y navegación funcionan correctamente
 */
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navGraph_startsAtInicioScreen() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarNavGraph(navController = navController)
        }

        // Then - Inicio screen should be displayed
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
    }

    @Test
    fun navGraph_inicioScreen_showsRegisterButton() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarNavGraph(navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun navGraph_inicioScreen_showsWelcomeMessage() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarNavGraph(navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertIsDisplayed()
    }

    @Test
    fun screen_inicio_hasCorrectRoute() {
        // Then
        assert(Screen.Inicio.route == "inicio")
    }

    @Test
    fun screen_productos_hasCorrectRoute() {
        // Then
        assert(Screen.Productos.route == "productos")
    }

    @Test
    fun screen_productoDetalle_hasCorrectRoute() {
        // Then
        assert(Screen.ProductoDetalle.route == "producto/{productoId}")
    }

    @Test
    fun screen_productoDetalle_createRoute_generatesCorrectPath() {
        // When
        val route = Screen.ProductoDetalle.createRoute(123)

        // Then
        assert(route == "producto/123")
    }

    @Test
    fun screen_nosotros_hasCorrectRoute() {
        // Then
        assert(Screen.Nosotros.route == "nosotros")
    }

    @Test
    fun screen_contacto_hasCorrectRoute() {
        // Then
        assert(Screen.Contacto.route == "contacto")
    }

    @Test
    fun screen_blog_hasCorrectRoute() {
        // Then
        assert(Screen.Blog.route == "blog")
    }

    @Test
    fun screen_registro_hasCorrectRoute() {
        // Then
        assert(Screen.Registro.route == "registro")
    }

    @Test
    fun screen_carrito_hasCorrectRoute() {
        // Then
        assert(Screen.Carrito.route == "carrito")
    }

    @Test
    fun bottomNavItems_containsCorrectItems() {
        // Then
        assert(bottomNavItems.size == 5)
        assert(bottomNavItems.contains(Screen.Inicio))
        assert(bottomNavItems.contains(Screen.Productos))
        assert(bottomNavItems.contains(Screen.Nosotros))
        assert(bottomNavItems.contains(Screen.Contacto))
        assert(bottomNavItems.contains(Screen.Blog))
    }

    @Test
    fun bottomNavItems_doesNotContainRegistro() {
        // Then
        assert(!bottomNavItems.contains(Screen.Registro))
    }

    @Test
    fun bottomNavItems_doesNotContainCarrito() {
        // Then
        assert(!bottomNavItems.contains(Screen.Carrito))
    }

    @Test
    fun screen_titles_areNotEmpty() {
        // Then
        assert(Screen.Inicio.title.isNotEmpty())
        assert(Screen.Productos.title.isNotEmpty())
        assert(Screen.ProductoDetalle.title.isNotEmpty())
        assert(Screen.Nosotros.title.isNotEmpty())
        assert(Screen.Contacto.title.isNotEmpty())
        assert(Screen.Blog.title.isNotEmpty())
        assert(Screen.Registro.title.isNotEmpty())
        assert(Screen.Carrito.title.isNotEmpty())
    }
}
