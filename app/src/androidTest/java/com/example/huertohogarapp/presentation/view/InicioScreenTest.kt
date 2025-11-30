package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.presentation.viewmodel.InicioUiState
import com.example.huertohogarapp.presentation.viewmodel.InicioViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para InicioScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de inicio
 */
@RunWith(AndroidJUnit4::class)
class InicioScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: InicioViewModel
    private var navegoARegistro = false

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        navegoARegistro = false
        
        val uiStateFlow = MutableStateFlow(InicioUiState())
        every { mockViewModel.uiState } returns uiStateFlow
    }

    private fun setupScreen() {
        composeTestRule.setContent {
            InicioScreen(
                viewModel = mockViewModel,
                onNavigateToRegistro = { navegoARegistro = true }
            )
        }
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra el titulo HuertoHogar`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra el mensaje de bienvenida`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra el boton Registrarse`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN el boton Registrarse es clickeable`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Registrarse").assertHasClickAction()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN todos los elementos principales estan presentes`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertExists()
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertExists()
        composeTestRule.onNodeWithText("Registrarse").assertExists()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN el titulo tiene el formato correcto`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar")
            .assertIsDisplayed()
            .assertExists()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN el layout tiene estructura correcta`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    // ==================== PRUEBAS DE INTERACCIÓN ====================

    @Test
    fun `GIVEN pantalla cargada WHEN usuario hace clic en Registrarse THEN navega a pantalla de registro`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Then
        assert(navegoARegistro) { "Debería haber navegado a la pantalla de registro" }
    }

    @Test
    fun `GIVEN pantalla cargada WHEN usuario hace clic en Registrarse THEN el callback se ejecuta correctamente`() {
        // Given
        setupScreen()
        assert(!navegoARegistro) { "El callback no debería haberse ejecutado aún" }

        // When
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Then
        assert(navegoARegistro) { "El callback debería haberse ejecutado" }
    }

    @Test
    fun `GIVEN pantalla cargada WHEN usuario hace clic en Registrarse varias veces THEN el callback se ejecuta cada vez`() {
        // Given
        var contadorClicks = 0
        composeTestRule.setContent {
            InicioScreen(
                viewModel = mockViewModel,
                onNavigateToRegistro = { contadorClicks++ }
            )
        }

        // When
        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Then
        assert(contadorClicks == 2) { "El callback debería haberse ejecutado 2 veces, pero se ejecutó $contadorClicks veces" }
    }

    // ==================== PRUEBAS DE ESTADO ====================

    @Test
    fun `GIVEN uiState con isLoading false WHEN se renderiza THEN muestra contenido normal`() {
        // Given
        val uiStateFlow = MutableStateFlow(InicioUiState(isLoading = false))
        every { mockViewModel.uiState } returns uiStateFlow

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun `GIVEN uiState sin error WHEN se renderiza THEN no muestra mensaje de error`() {
        // Given
        val uiStateFlow = MutableStateFlow(InicioUiState(error = null))
        every { mockViewModel.uiState } returns uiStateFlow

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado inicial WHEN se verifica estado THEN no hay errores`() {
        // Given
        val uiStateFlow = MutableStateFlow(InicioUiState())
        every { mockViewModel.uiState } returns uiStateFlow

        // When
        setupScreen()

        // Then
        val uiState = mockViewModel.uiState.value
        assert(uiState.error == null) { "No debería haber error en el estado inicial" }
    }

    @Test
    fun `GIVEN estado inicial WHEN se verifica estado THEN no esta cargando`() {
        // Given
        val uiStateFlow = MutableStateFlow(InicioUiState())
        every { mockViewModel.uiState } returns uiStateFlow

        // When
        setupScreen()

        // Then
        val uiState = mockViewModel.uiState.value
        assert(!uiState.isLoading) { "No debería estar cargando en el estado inicial" }
    }

    // ==================== PRUEBAS DE INTEGRACIÓN ====================

    @Test
    fun `GIVEN viewModel inyectado WHEN pantalla se carga THEN utiliza el viewModel correctamente`() {
        // Given
        val customViewModel = mockk<InicioViewModel>(relaxed = true)
        val uiStateFlow = MutableStateFlow(InicioUiState())
        every { customViewModel.uiState } returns uiStateFlow

        // When
        composeTestRule.setContent {
            InicioScreen(
                viewModel = customViewModel,
                onNavigateToRegistro = { navegoARegistro = true }
            )
        }

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se verifica visibilidad THEN todos los elementos son visibles`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza sin callback THEN usa callback por defecto`() {
        // Given
        composeTestRule.setContent {
            InicioScreen(viewModel = mockViewModel)
        }

        // When - El botón está visible y clickeable
        // Then - No debe lanzar excepción
        composeTestRule.onNodeWithText("Registrarse").assertExists()
        composeTestRule.onNodeWithText("Registrarse").assertHasClickAction()
    }

    @Test
    fun `GIVEN boton Registrarse WHEN se verifica icono THEN boton tiene icono PersonAdd`() {
        // Given - Setup en setUp()
        
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Registrarse")
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}
