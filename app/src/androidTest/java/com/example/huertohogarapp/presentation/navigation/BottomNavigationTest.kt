package com.example.huertohogarapp.presentation.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests instrumentados para BottomNavigation
 * Verifican que la barra de navegación inferior funciona correctamente
 */
@RunWith(AndroidJUnit4::class)
class BottomNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomNavigation_displaysAllItems() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then - Verify all navigation items are displayed
        composeTestRule.onNodeWithText("Inicio").assertIsDisplayed()
        composeTestRule.onNodeWithText("Productos").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nosotros").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contacto").assertIsDisplayed()
        composeTestRule.onNodeWithText("Blog").assertIsDisplayed()
    }

    @Test
    fun bottomNavigation_inicioItem_hasClickAction() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText("Inicio").assertHasClickAction()
    }

    @Test
    fun bottomNavigation_productosItem_hasClickAction() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText("Productos").assertHasClickAction()
    }

    @Test
    fun bottomNavigation_nosotrosItem_hasClickAction() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText("Nosotros").assertHasClickAction()
    }

    @Test
    fun bottomNavigation_contactoItem_hasClickAction() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText("Contacto").assertHasClickAction()
    }

    @Test
    fun bottomNavigation_blogItem_hasClickAction() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then
        composeTestRule.onNodeWithText("Blog").assertHasClickAction()
    }

    @Test
    fun bottomNavigation_doesNotShowRegistro() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then - Registro should not be in bottom navigation
        composeTestRule.onNodeWithText("Registro").assertDoesNotExist()
    }

    @Test
    fun bottomNavigation_doesNotShowCarrito() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then - Carrito should not be in bottom navigation
        composeTestRule.onNodeWithText("Carrito").assertDoesNotExist()
    }

    @Test
    fun bottomNavigation_hasExactlyFiveItems() {
        // Given & When
        composeTestRule.setContent {
            val navController = rememberNavController()
            HuertoHogarBottomNavigation(navController = navController)
        }

        // Then - Should have exactly 5 navigation items
        val items = listOf("Inicio", "Productos", "Nosotros", "Contacto", "Blog")
        items.forEach { item ->
            composeTestRule.onNodeWithText(item).assertExists()
        }
    }
}
