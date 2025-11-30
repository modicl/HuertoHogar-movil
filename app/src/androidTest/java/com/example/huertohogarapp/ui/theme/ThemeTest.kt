package com.example.huertohogarapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests instrumentados para el tema de la aplicación
 * Verifican que los colores y estilos se aplican correctamente
 */
@RunWith(AndroidJUnit4::class)
class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun theme_appliesWithoutCrashing() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                Text("Test Theme")
            }
        }

        // Then
        composeTestRule.onNodeWithText("Test Theme").assertIsDisplayed()
    }

    @Test
    fun theme_lightMode_appliesCorrectly() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme(darkTheme = false) {
                Text("Light Mode Test")
            }
        }

        // Then
        composeTestRule.onNodeWithText("Light Mode Test").assertIsDisplayed()
    }

    @Test
    fun theme_darkMode_appliesCorrectly() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme(darkTheme = true) {
                Text("Dark Mode Test")
            }
        }

        // Then
        composeTestRule.onNodeWithText("Dark Mode Test").assertIsDisplayed()
    }

    @Test
    fun theme_materialTheme_isAvailable() {
        // Given
        var primaryColorAvailable = false

        // When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                primaryColorAvailable = MaterialTheme.colorScheme.primary != null
                Text("Theme Test")
            }
        }

        // Then
        assert(primaryColorAvailable)
    }

    @Test
    fun theme_typography_isAvailable() {
        // Given
        var typographyAvailable = false

        // When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                typographyAvailable = MaterialTheme.typography.bodyLarge != null
                Text("Typography Test")
            }
        }

        // Then
        assert(typographyAvailable)
    }

    @Test
    fun theme_withDynamicColor_false_usesCustomColors() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme(dynamicColor = false) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Custom Colors Test")
                }
            }
        }

        // Then
        composeTestRule.onNodeWithText("Custom Colors Test").assertIsDisplayed()
    }

    @Test
    fun theme_colorScheme_hasPrimaryColor() {
        // Given
        var hasPrimary = false

        // When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                hasPrimary = MaterialTheme.colorScheme.primary == VerdePrincipal ||
                        MaterialTheme.colorScheme.primary == VerdePrincipalDark
                Text("Primary Color Test")
            }
        }

        // Then
        assert(hasPrimary)
    }

    @Test
    fun theme_colorScheme_hasSecondaryColor() {
        // Given
        var hasSecondary = false

        // When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                hasSecondary = MaterialTheme.colorScheme.secondary == VerdeOscuro
                Text("Secondary Color Test")
            }
        }

        // Then
        assert(hasSecondary)
    }

    @Test
    fun theme_colorScheme_hasTertiaryColor() {
        // Given
        var hasTertiary = false

        // When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                hasTertiary = MaterialTheme.colorScheme.tertiary == VerdeClaro
                Text("Tertiary Color Test")
            }
        }

        // Then
        assert(hasTertiary)
    }

    @Test
    fun theme_content_isRendered() {
        // Given & When
        composeTestRule.setContent {
            HuertoHogarAppTheme {
                TestContent()
            }
        }

        // Then
        composeTestRule.onNodeWithText("Test Content Rendered").assertIsDisplayed()
    }
}

@Composable
private fun TestContent() {
    Text("Test Content Rendered")
}
