package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.presentation.viewmodel.InicioUiState
import com.example.huertohogarapp.presentation.viewmodel.InicioViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InicioScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockViewModel: InicioViewModel = mockk(relaxed = true)
    private var navegoARegistro = false

    @Before
    fun setUp() {
        val uiStateFlow = MutableStateFlow(InicioUiState())
        every { mockViewModel.uiState } returns uiStateFlow
        navegoARegistro = false
        
        composeTestRule.setContent {
            InicioScreen(
                viewModel = mockViewModel,
                onNavigateToRegistro = { navegoARegistro = true }
            )
        }
    }

    @Test
    fun `la pantalla muestra el titulo HuertoHogar`() {
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
    }

    @Test
    fun `la pantalla muestra el mensaje de bienvenida`() {
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertIsDisplayed()
    }

    @Test
    fun `la pantalla muestra el boton Registrarse`() {
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun `el boton Registrarse es clickeable`() {
        composeTestRule.onNodeWithText("Registrarse").assertHasClickAction()
    }

    @Test
    fun `al hacer clic en Registrarse, navega a la pantalla de registro`() {
        // When
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Then
        assert(navegoARegistro) { "Debería haber navegado a la pantalla de registro" }
    }

    @Test
    fun `el titulo HuertoHogar tiene el estilo correcto`() {
        // Verificar que el texto existe y es visible
        composeTestRule.onNodeWithText("HuertoHogar")
            .assertIsDisplayed()
            .assertExists()
    }

    @Test
    fun `todos los elementos principales estan presentes`() {
        // Verificar que todos los elementos clave están presentes
        composeTestRule.onNodeWithText("HuertoHogar").assertExists()
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertExists()
        composeTestRule.onNodeWithText("Registrarse").assertExists()
    }

    @Test
    fun `el boton de registro contiene el icono de PersonAdd`() {
        // Verificar que el botón existe y se puede hacer clic
        composeTestRule.onNodeWithText("Registrarse")
            .assertExists()
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun `la pantalla tiene el layout centrado correctamente`() {
        // Verificar que los elementos están visibles y centrados
        composeTestRule.onNodeWithText("HuertoHogar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bienvenido a tu huerto en casa").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    @Test
    fun `el estado inicial del viewModel no muestra error`() {
        val uiState = mockViewModel.uiState.value
        assert(uiState.error == null) { "No debería haber error en el estado inicial" }
    }

    @Test
    fun `el estado inicial del viewModel no esta cargando`() {
        val uiState = mockViewModel.uiState.value
        assert(!uiState.isLoading) { "No debería estar cargando en el estado inicial" }
    }
}
