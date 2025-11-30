package com.example.huertohogarapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.ui.theme.HuertoHogarAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests instrumentados para la aplicación principal HuertoHogar
 * Verifican que la aplicación se renderiza correctamente
 */
@RunWith(AndroidJUnit4::class)
class HuertoHogarAppTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun app_displaysHuertoHogarTitle() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
    }

    @Test
    fun app_displaysWelcomeMessage() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // Then
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertIsDisplayed()
    }

    @Test
    fun app_displaysRegisterButton() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // Then
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun app_registerButtonIsClickable() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // Then
        composeTestRule.onNodeWithText("Registrarse").assertHasClickAction()
    }

    @Test
    fun app_displaysBottomNavigation() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // Then - verify bottom navigation items exist
        composeTestRule.onNodeWithText("Inicio").assertExists()
        composeTestRule.onNodeWithText("Productos").assertExists()
        composeTestRule.onNodeWithText("Nosotros").assertExists()
        composeTestRule.onNodeWithText("Contacto").assertExists()
        composeTestRule.onNodeWithText("Blog").assertExists()
    }

    @Test
    fun app_canNavigateToProductos() {
        // Given
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // When
        composeTestRule.onNodeWithText("Productos").performClick()

        // Then - wait for navigation
        composeTestRule.waitForIdle()
        // Productos screen should have some indication (like a search or products)
    }

    @Test
    fun app_canNavigateToNosotros() {
        // Given
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // When
        composeTestRule.onNodeWithText("Nosotros").performClick()

        // Then - wait for navigation
        composeTestRule.waitForIdle()
    }

    @Test
    fun app_canNavigateToContacto() {
        // Given
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // When
        composeTestRule.onNodeWithText("Contacto").performClick()

        // Then - wait for navigation
        composeTestRule.waitForIdle()
    }

    @Test
    fun app_canNavigateToBlog() {
        // Given
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // When
        composeTestRule.onNodeWithText("Blog").performClick()

        // Then - wait for navigation
        composeTestRule.waitForIdle()
    }

    @Test
    fun app_canNavigateBackToInicio() {
        // Given
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }

        // When - navigate to Productos then back to Inicio
        composeTestRule.onNodeWithText("Productos").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Inicio").performClick()
        composeTestRule.waitForIdle()

        // Then - should show Inicio content
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
    }
}
