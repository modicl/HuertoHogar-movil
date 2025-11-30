package com.example.huertohogarapp.presentation.view

import android.net.Uri
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.presentation.viewmodel.RegistroViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para RegistroScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de registro
 */
@RunWith(AndroidJUnit4::class)
class RegistroScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: RegistroViewModel
    private var navegoAtras = false
    private var registroExitosoCallback = false

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        navegoAtras = false
        registroExitosoCallback = false
        
        val uiStateFlow = MutableStateFlow(
            RegistroViewModel.RegistroUiState()
        )

        every { mockViewModel.uiState } returns uiStateFlow
    }

    private fun setupScreen() {
        composeTestRule.setContent {
            RegistroScreen(
                viewModel = mockViewModel,
                onRegistroExitoso = { registroExitosoCallback = true },
                onNavigateBack = { navegoAtras = true }
            )
        }
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra titulo Registro de Usuario`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Registro de Usuario").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo nombre`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Nombre").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo apellido`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Apellido").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo correo`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Correo Electrónico").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra campo fecha nacimiento`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Fecha de Nacimiento").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra boton registrar`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Registrar").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra boton volver`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithContentDescription("Volver").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra area de foto de perfil`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithContentDescription("Agregar foto de perfil").assertExists()
    }

    // ==================== PRUEBAS DE INTERACCIÓN ====================

    @Test
    fun `GIVEN campo nombre WHEN usuario escribe THEN llama onNombreChange`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Nombre").performTextInput("Juan")

        // Then
        verify { mockViewModel.onNombreChange("Juan") }
    }

    @Test
    fun `GIVEN campo apellido WHEN usuario escribe THEN llama onApellidoChange`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Apellido").performTextInput("Pérez")

        // Then
        verify { mockViewModel.onApellidoChange("Pérez") }
    }

    @Test
    fun `GIVEN campo correo WHEN usuario escribe THEN llama onCorreoChange`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Correo Electrónico").performTextInput("test@example.com")

        // Then
        verify { mockViewModel.onCorreoChange("test@example.com") }
    }

    @Test
    fun `GIVEN boton registrar WHEN usuario hace clic THEN llama onRegistrar`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Registrar").performClick()

        // Then
        verify { mockViewModel.onRegistrar() }
    }

    @Test
    fun `GIVEN boton volver WHEN usuario hace clic THEN navega atras`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithContentDescription("Volver").performClick()

        // Then
        assert(navegoAtras) { "Debería haber navegado atrás" }
    }

    @Test
    fun `GIVEN boton registrar WHEN se renderiza THEN es clickeable`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Registrar").assertHasClickAction()
    }

    // ==================== PRUEBAS DE ESTADO ====================

    @Test
    fun `GIVEN estado con error en nombre WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(nombreError = "El nombre es obligatorio")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("El nombre es obligatorio").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error en apellido WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(apellidoError = "El apellido es obligatorio")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("El apellido es obligatorio").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error en correo WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(correoError = "Correo inválido")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Correo inválido").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error en fecha WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(fechaNacimientoError = "La fecha de nacimiento es obligatoria")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("La fecha de nacimiento es obligatoria").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con error en foto WHEN se renderiza THEN muestra error`() {
        // Given
        val errorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(fotoPerfilError = "La foto de perfil es obligatoria")
        )
        every { mockViewModel.uiState } returns errorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("La foto de perfil es obligatoria").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado de carga WHEN cargando es true THEN boton muestra indicador`() {
        // Given
        val loadingState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(cargando = true)
        )
        every { mockViewModel.uiState } returns loadingState

        // When
        setupScreen()

        // Then
        // Cuando está cargando, el botón debería mostrar un indicador en lugar del texto
        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertExists()
    }

    @Test
    fun `GIVEN dialogo de exito WHEN mostrarDialogoExito es true THEN muestra dialogo`() {
        // Given
        val successState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(mostrarDialogoExito = true)
        )
        every { mockViewModel.uiState } returns successState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("¡Registro Exitoso!").assertIsDisplayed()
    }

    @Test
    fun `GIVEN dialogo de exito WHEN usuario hace clic en Ir al Inicio THEN llama callbacks`() {
        // Given
        val successState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(mostrarDialogoExito = true)
        )
        every { mockViewModel.uiState } returns successState
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Ir al Inicio").performClick()

        // Then
        verify { mockViewModel.ocultarDialogoExito() }
        verify { mockViewModel.limpiarFormulario() }
    }

    @Test
    fun `GIVEN dialogo selector foto WHEN mostrarSelectorFoto es true THEN muestra dialogo`() {
        // Given
        val selectorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(mostrarSelectorFoto = true)
        )
        every { mockViewModel.uiState } returns selectorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Seleccionar foto").assertIsDisplayed()
    }

    @Test
    fun `GIVEN dialogo selector foto WHEN se muestra THEN tiene opcion Camara`() {
        // Given
        val selectorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(mostrarSelectorFoto = true)
        )
        every { mockViewModel.uiState } returns selectorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Cámara").assertIsDisplayed()
    }

    @Test
    fun `GIVEN dialogo selector foto WHEN se muestra THEN tiene opcion Galeria`() {
        // Given
        val selectorState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(mostrarSelectorFoto = true)
        )
        every { mockViewModel.uiState } returns selectorState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Galería").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado con valores WHEN se renderiza THEN muestra los valores en campos`() {
        // Given
        val filledState = MutableStateFlow(
            RegistroViewModel.RegistroUiState(
                nombre = "Juan",
                apellido = "Pérez",
                correo = "juan@example.com"
            )
        )
        every { mockViewModel.uiState } returns filledState

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Juan").assertExists()
    }

    @Test
    fun `GIVEN campos WHEN se cargan THEN todos son editables`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Nombre").assertIsEnabled()
        composeTestRule.onNodeWithText("Apellido").assertIsEnabled()
        composeTestRule.onNodeWithText("Correo Electrónico").assertIsEnabled()
    }

    @Test
    fun `GIVEN pantalla WHEN se renderiza THEN es scrollable`() {
        // When
        setupScreen()

        // Then - Verificamos que podemos hacer scroll a elementos que podrían estar abajo
        composeTestRule.onNodeWithText("Registrar").performScrollTo()
        composeTestRule.onNodeWithText("Registrar").assertIsDisplayed()
    }
}
