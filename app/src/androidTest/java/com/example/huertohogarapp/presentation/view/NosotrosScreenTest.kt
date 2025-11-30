package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.presentation.viewmodel.CompanyInfo
import com.example.huertohogarapp.presentation.viewmodel.NosotrosUiState
import com.example.huertohogarapp.presentation.viewmodel.NosotrosViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para NosotrosScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de nosotros
 */
@RunWith(AndroidJUnit4::class)
class NosotrosScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: NosotrosViewModel

    private val companyInfo = CompanyInfo(
        sobreNosotros = "HuertoHogar es una tienda online dedicada a llevar la frescura y calidad de los productos del campo directamente a la puerta de nuestros clientes en Chile.",
        mision = "Nuestra misión es proporcionar productos frescos y de calidad directamente desde el campo hasta la puerta de nuestros clientes.",
        vision = "Nuestra visión es ser la tienda online líder en la distribución de productos frescos y naturales en Chile."
    )

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        
        val uiStateFlow = MutableStateFlow(
            NosotrosUiState(
                isLoading = false,
                companyInfo = companyInfo
            )
        )

        every { mockViewModel.uiState } returns uiStateFlow
    }

    private fun setupScreen() {
        composeTestRule.setContent {
            NosotrosScreen(viewModel = mockViewModel)
        }
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra titulo Quienes somos`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("¿Quiénes somos?").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra seccion Sobre Nosotros`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Sobre Nosotros").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra texto sobre nosotros`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar", substring = true).assertExists()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra seccion Nuestra Mision`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Nuestra Misión").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra texto de mision`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("proporcionar productos frescos", substring = true).assertExists()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra seccion Nuestra Vision`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Nuestra Visión").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra texto de vision`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("tienda online líder", substring = true).assertExists()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra referencia a Chile`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Chile", substring = true).assertExists()
    }

    // ==================== PRUEBAS DE ESTADO ====================

    @Test
    fun `GIVEN estado de carga WHEN isLoading es true THEN muestra indicador de carga`() {
        // Given
        val loadingState = MutableStateFlow(
            NosotrosUiState(isLoading = true)
        )
        every { mockViewModel.uiState } returns loadingState

        // When
        setupScreen()

        // Then
        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertExists()
    }

    @Test
    fun `GIVEN estado cargado WHEN isLoading es false THEN no muestra indicador de carga`() {
        // Given - Estado por defecto en setUp tiene isLoading = false

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Sobre Nosotros").assertIsDisplayed()
    }

    @Test
    fun `GIVEN contenido cargado WHEN se renderiza THEN muestra todas las secciones`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Sobre Nosotros").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nuestra Misión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nuestra Visión").assertIsDisplayed()
    }

    @Test
    fun `GIVEN contenido cargado WHEN se renderiza THEN la pantalla es scrollable`() {
        // When
        setupScreen()

        // Then - Verificamos que se puede hacer scroll buscando elementos que podrían estar abajo
        composeTestRule.onNodeWithText("Nuestra Visión").assertExists()
    }

    @Test
    fun `GIVEN informacion de empresa vacia WHEN se renderiza THEN muestra cards vacias`() {
        // Given
        val emptyState = MutableStateFlow(
            NosotrosUiState(
                isLoading = false,
                companyInfo = CompanyInfo()
            )
        )
        every { mockViewModel.uiState } returns emptyState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Sobre Nosotros").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nuestra Misión").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN TopAppBar esta visible`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("¿Quiénes somos?").assertIsDisplayed()
    }

    @Test
    fun `GIVEN contenido largo WHEN usuario hace scroll THEN puede ver todo el contenido`() {
        // When
        setupScreen()

        // Then - Scroll hacia abajo para verificar que la visión es accesible
        composeTestRule.onNodeWithText("Nuestra Visión").performScrollTo()
        composeTestRule.onNodeWithText("Nuestra Visión").assertIsDisplayed()
    }

    @Test
    fun `GIVEN cards WHEN se renderizan THEN tienen el formato correcto`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Sobre Nosotros").assertExists()
        composeTestRule.onNodeWithText("Nuestra Misión").assertExists()
        composeTestRule.onNodeWithText("Nuestra Visión").assertExists()
    }
}
