package com.example.huertohogarapp.presentation.view

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.huertohogarapp.data.model.BlogPost
import com.example.huertohogarapp.presentation.viewmodel.BlogUiState
import com.example.huertohogarapp.presentation.viewmodel.BlogViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas de UI para BlogScreen
 * Arquitectura: MVVM con Compose UI Testing
 * Framework: JUnit4 + Compose Testing + MockK
 * Objetivo: Validar la visualización y comportamiento de la pantalla de blog
 */
@RunWith(AndroidJUnit4::class)
class BlogScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: BlogViewModel

    private val blogPosts = listOf(
        BlogPost(
            id = 1,
            titulo = "Beneficios del huerto urbano",
            descripcion = "Descubre los beneficios de tener un huerto en casa",
            contenido = "Contenido completo del artículo",
            autor = "María González",
            fecha = "15 de Octubre, 2025",
            imagen = "🌱",
            categoria = "Sostenibilidad",
            url = "https://example.com/post1",
            tiempoLectura = "5 min"
        ),
        BlogPost(
            id = 2,
            titulo = "Guía para principiantes",
            descripcion = "Cómo iniciar tu huerto urbano desde cero",
            contenido = "Contenido de la guía",
            autor = "Carlos Ramírez",
            fecha = "10 de Octubre, 2025",
            imagen = "🌿",
            categoria = "Guías",
            url = "https://example.com/post2",
            tiempoLectura = "7 min"
        ),
        BlogPost(
            id = 3,
            titulo = "Mejores plantas para espacios reducidos",
            descripcion = "Plantas ideales para balcones y terrazas",
            contenido = "Contenido sobre plantas",
            autor = "Ana Martínez",
            fecha = "5 de Octubre, 2025",
            imagen = "🪴",
            categoria = "Consejos",
            url = "https://example.com/post3",
            tiempoLectura = "6 min"
        )
    )

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        
        val uiStateFlow = MutableStateFlow(
            BlogUiState(
                isLoading = false,
                blogPosts = blogPosts,
                filteredPosts = blogPosts,
                selectedCategory = "Todas"
            )
        )

        every { mockViewModel.uiState } returns uiStateFlow
        every { mockViewModel.getCategories() } returns listOf("Todas", "Sostenibilidad", "Guías", "Consejos")
    }

    private fun setupScreen() {
        composeTestRule.setContent {
            BlogScreen(viewModel = mockViewModel)
        }
    }

    // ==================== PRUEBAS DE VISUALIZACIÓN ====================

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra titulo Blog`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Blog").assertIsDisplayed()
    }

    @Test
    fun `GIVEN pantalla cargada WHEN se renderiza THEN muestra descripcion del blog`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Últimas noticias sobre agricultura urbana y huertos en casa")
            .assertIsDisplayed()
    }

    @Test
    fun `GIVEN lista de posts WHEN pantalla se carga THEN muestra los titulos de posts`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Beneficios del huerto urbano").assertIsDisplayed()
        composeTestRule.onNodeWithText("Guía para principiantes").assertIsDisplayed()
    }

    @Test
    fun `GIVEN lista de posts WHEN pantalla se carga THEN muestra las descripciones de posts`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Descubre los beneficios de tener un huerto en casa").assertIsDisplayed()
    }

    @Test
    fun `GIVEN lista de posts WHEN pantalla se carga THEN muestra los autores`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("María González", substring = true).assertExists()
    }

    @Test
    fun `GIVEN lista de posts WHEN pantalla se carga THEN muestra los emojis de imagen`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("🌱").assertExists()
        composeTestRule.onNodeWithText("🌿").assertExists()
    }

    @Test
    fun `GIVEN filtros de categoria WHEN pantalla se carga THEN muestra chip Todas`() {
        // When
        setupScreen()
        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText("Todas").assertExists()
    }

    @Test
    fun `GIVEN filtros de categoria WHEN pantalla se carga THEN muestra chip Sostenibilidad`() {
        // When
        setupScreen()
        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText("Sostenibilidad").assertExists()
    }

    @Test
    fun `GIVEN filtros de categoria WHEN pantalla se carga THEN muestra chip Guías`() {
        // When
        setupScreen()
        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText("Guías").assertExists()
    }

    @Test
    fun `GIVEN filtros de categoria WHEN pantalla se carga THEN muestra chip Consejos`() {
        // When
        setupScreen()
        composeTestRule.waitForIdle()

        // Then
        composeTestRule.onNodeWithText("Consejos").assertExists()
    }

    // ==================== PRUEBAS DE INTERACCIÓN ====================

    @Test
    fun `GIVEN filtros de categoria WHEN usuario hace clic en Sostenibilidad THEN llama filterByCategory`() {
        // Given
        setupScreen()
        composeTestRule.waitForIdle()

        // When
        composeTestRule.onNodeWithText("Sostenibilidad").assertExists()
        composeTestRule.onNodeWithText("Sostenibilidad").performClick()
        composeTestRule.waitForIdle()

        // Then
        verify { mockViewModel.filterByCategory("Sostenibilidad") }
    }

    @Test
    fun `GIVEN filtros de categoria WHEN usuario hace clic en Guías THEN llama filterByCategory`() {
        // Given
        setupScreen()
        composeTestRule.waitForIdle()

        // When
        composeTestRule.onNodeWithText("Guías").assertExists()
        composeTestRule.onNodeWithText("Guías").performClick()
        composeTestRule.waitForIdle()

        // Then
        verify { mockViewModel.filterByCategory("Guías") }
    }

    @Test
    fun `GIVEN filtros de categoria WHEN usuario hace clic en Todas THEN llama filterByCategory`() {
        // Given
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Todas").performClick()

        // Then
        verify { mockViewModel.filterByCategory("Todas") }
    }

    // ==================== PRUEBAS DE ESTADO ====================

    @Test
    fun `GIVEN estado de carga WHEN isLoading es true THEN muestra indicador de carga`() {
        // Given
        val loadingState = MutableStateFlow(
            BlogUiState(isLoading = true, blogPosts = emptyList(), filteredPosts = emptyList())
        )
        every { mockViewModel.uiState } returns loadingState
        every { mockViewModel.getCategories() } returns listOf("Todas")

        // When
        setupScreen()

        // Then
        composeTestRule.onNode(hasTestTag("loading") or hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertExists()
    }

    @Test
    fun `GIVEN estado de error WHEN hay error THEN muestra mensaje de error`() {
        // Given
        val errorState = MutableStateFlow(
            BlogUiState(
                isLoading = false,
                error = "Error al cargar los artículos",
                blogPosts = emptyList(),
                filteredPosts = emptyList()
            )
        )
        every { mockViewModel.uiState } returns errorState
        every { mockViewModel.getCategories() } returns listOf("Todas")

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Error al cargar los artículos").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado de error WHEN hay error THEN muestra boton Reintentar`() {
        // Given
        val errorState = MutableStateFlow(
            BlogUiState(
                isLoading = false,
                error = "Error de conexión",
                blogPosts = emptyList(),
                filteredPosts = emptyList()
            )
        )
        every { mockViewModel.uiState } returns errorState
        every { mockViewModel.getCategories() } returns listOf("Todas")

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Reintentar").assertIsDisplayed()
    }

    @Test
    fun `GIVEN estado de error WHEN usuario hace clic en Reintentar THEN llama loadBlogPosts`() {
        // Given
        val errorState = MutableStateFlow(
            BlogUiState(
                isLoading = false,
                error = "Error",
                blogPosts = emptyList(),
                filteredPosts = emptyList()
            )
        )
        every { mockViewModel.uiState } returns errorState
        every { mockViewModel.getCategories() } returns listOf("Todas")
        setupScreen()

        // When
        composeTestRule.onNodeWithText("Reintentar").performClick()

        // Then
        verify { mockViewModel.loadBlogPosts() }
    }

    @Test
    fun `GIVEN lista vacia WHEN no hay posts THEN muestra mensaje de no disponible`() {
        // Given
        val emptyState = MutableStateFlow(
            BlogUiState(
                isLoading = false,
                blogPosts = emptyList(),
                filteredPosts = emptyList()
            )
        )
        every { mockViewModel.uiState } returns emptyState
        every { mockViewModel.getCategories() } returns listOf("Todas")

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("No hay artículos disponibles").assertIsDisplayed()
    }

    @Test
    fun `GIVEN posts filtrados WHEN se filtra por categoria THEN muestra posts correctos`() {
        // Given
        val filteredState = MutableStateFlow(
            BlogUiState(
                isLoading = false,
                blogPosts = blogPosts,
                filteredPosts = listOf(blogPosts[0]), // Solo el de Sostenibilidad
                selectedCategory = "Sostenibilidad"
            )
        )
        every { mockViewModel.uiState } returns filteredState
        every { mockViewModel.getCategories() } returns listOf("Todas", "Sostenibilidad", "Guías", "Consejos")

        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Beneficios del huerto urbano").assertIsDisplayed()
        composeTestRule.onNodeWithText("Guía para principiantes").assertDoesNotExist()
    }

    @Test
    fun `GIVEN posts WHEN pantalla se carga THEN posts son clickeables`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("Beneficios del huerto urbano").assertHasClickAction()
    }

    @Test
    fun `GIVEN posts WHEN pantalla se carga THEN muestra tiempo de lectura`() {
        // When
        setupScreen()

        // Then
        composeTestRule.onNodeWithText("5 min", substring = true).assertExists()
    }
}
