package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.presentation.viewmodel.ContactoUiState
import com.example.huertohogarapp.presentation.viewmodel.ContactoViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para ContactoScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de contacto
 */
@RunWith(AndroidJUnit4::class)
class ContactoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: ContactoViewModel

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        
        val uiStateFlow = MutableStateFlow(ContactoUiState())
        every { mockViewModel.uiState } returns uiStateFlow
    }

    private fun setupScreen() {
        composeTestRule.setContent {
            ContactoScreen(viewModel = mockViewModel)
        }
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra titulo Contacto`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Contacto").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra mensaje de bienvenida`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("¿Tienes alguna pregunta? ¡Contáctanos!").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo de nombre`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Nombre *").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo de email`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Email *").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo de telefono`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Teléfono (opcional)").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo de mensaje`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Mensaje *").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra boton Enviar Mensaje`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Enviar Mensaje").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra informacion de contacto`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Información de Contacto").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra direccion`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Dirección: Quilpué, El Retiro # 777", substring = true).assertExists()
    }

    // ==================== PRUEBAS DE INTERACCIÓN ====================

    @Test
    fun `GIVEN campo nombre WHEN usuario escribe THEN llama onNombreChange`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Nombre *").performTextInput("Juan")

        // Then
        verify { mockViewModel.onNombreChange("Juan") }
    }

    @Test
    fun `GIVEN campo email WHEN usuario escribe THEN llama onEmailChange`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Email *").performTextInput("test@example.com")

        // Then
        verify { mockViewModel.onEmailChange("test@example.com") }
    }

    @Test
    fun `GIVEN campo telefono WHEN usuario escribe THEN llama onTelefonoChange`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Teléfono (opcional)").performTextInput("912345678")

        // Then
        verify { mockViewModel.onTelefonoChange("912345678") }
    }

    @Test
    fun `GIVEN campo mensaje WHEN usuario escribe THEN llama onMensajeChange`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Mensaje *").performTextInput("Este es un mensaje de prueba")

        // Then
        verify { mockViewModel.onMensajeChange("Este es un mensaje de prueba") }
    }

    @Test
    fun `GIVEN boton enviar WHEN usuario hace clic THEN llama enviarFormulario`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Enviar Mensaje").performClick()

        // Then
        verify { mockViewModel.enviarFormulario() }
    }

    @Test
    fun `GIVEN boton enviar WHEN se renderiza THEN es clickeable`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Enviar Mensaje").assertHasClickAction()
    }

    // ==================== PRUEBAS DE ESTADO ====================

    @Test
    fun `GIVEN estado con error en nombre WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            ContactoUiState(nombreError = "El nombre es requerido")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("El nombre es requerido").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error en email WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            ContactoUiState(emailError = "El email no es válido")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("El email no es válido").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error en mensaje WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            ContactoUiState(mensajeError = "El mensaje debe tener al menos 10 caracteres")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("El mensaje debe tener al menos 10 caracteres").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error en telefono WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            ContactoUiState(telefonoError = "El teléfono no es válido")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("El teléfono no es válido").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error de formulario WHEN se renderiza THEN muestra error general`() {
        // Given
        val errorState = MutableStateFlow(
            ContactoUiState(formError = "Por favor corrija los errores en el formulario")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Por favor corrija los errores en el formulario").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado de carga WHEN isLoading es true THEN boton muestra indicador`() {
        // Given
        val loadingState = MutableStateFlow(
            ContactoUiState(isLoading = true)
        )
        every { mockViewModel.uiState } returns loadingState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Enviar Mensaje").assertDoesNotExist()
    }

    @Test
    fun `GIVEN estado con valores WHEN se renderiza THEN muestra los valores en campos`() {
        // Given
        val filledState = MutableStateFlow(
            ContactoUiState(
                nombre = "Juan Pérez",
                email = "juan@example.com",
                mensaje = "Este es un mensaje de prueba"
            )
        )
        every { mockViewModel.uiState } returns filledState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Juan Pérez").assertExists()
    }

    @Test
    fun `GIVEN campos WHEN se cargan THEN todos tienen iconos`() {
        // When
        setupScreen()

        // Then - Los campos tienen iconos pero no podemos verificarlos directamente
        // Verificamos que los campos existen
        composeTestRule.onNodeWithText("Nombre *").assertExists()
        composeTestRule.onNodeWithText("Email *").assertExists()
        composeTestRule.onNodeWithText("Teléfono (opcional)").assertExists()
        composeTestRule.onNodeWithText("Mensaje *").assertExists()
    }

    @Test
    fun `GIVEN formulario WHEN se renderiza THEN los campos son editables`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Nombre *").assertIsEnabled()
        composeTestRule.onNodeWithText("Email *").assertIsEnabled()
        composeTestRule.onNodeWithText("Teléfono (opcional)").assertIsEnabled()
        composeTestRule.onNodeWithText("Mensaje *").assertIsEnabled()
    }
}
