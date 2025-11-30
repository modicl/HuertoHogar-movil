package com.example.huertohogarapp.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para BlogViewModel
 */
@ExperimentalCoroutinesApi
class BlogViewModelTest {

    private lateinit var viewModel: BlogViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = BlogViewModel()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial carga posts del blog`() = runTest {
        // Given - ViewModel inicializado en setUp
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(4, uiState.blogPosts.size)
        assertEquals(4, uiState.filteredPosts.size)
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun `getCategories retorna lista de categorias incluyendo Todas`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        val categorias = viewModel.getCategories()

        // Then
        assertTrue(categorias.contains("Todas"))
        assertTrue(categorias.size >= 2)
    }

    @Test
    fun `filterByCategory con Todas muestra todos los posts`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.filterByCategory("Todas")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(4, uiState.filteredPosts.size)
        assertEquals("Todas", uiState.selectedCategory)
    }

    @Test
    fun `filterByCategory con categoria especifica filtra correctamente`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.filterByCategory("Guías")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.filteredPosts.size)
        assertEquals("Guías", uiState.selectedCategory)
        assertTrue(uiState.filteredPosts.all { it.categoria == "Guías" })
    }

    @Test
    fun `filterByCategory con categoria Sostenibilidad filtra correctamente`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.filterByCategory("Sostenibilidad")

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.filteredPosts.size)
        assertEquals("Sostenibilidad", uiState.selectedCategory)
    }

    @Test
    fun `filterByCategory con categoria inexistente retorna lista vacia`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.filterByCategory("CategoriaInexistente")

        // Then
        val uiState = viewModel.uiState.value
        assertTrue(uiState.filteredPosts.isEmpty())
        assertEquals("CategoriaInexistente", uiState.selectedCategory)
    }

    @Test
    fun `loadBlogPosts carga posts correctamente`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.loadBlogPosts()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(4, uiState.blogPosts.size)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `posts del blog contienen informacion correcta`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        val primerPost = uiState.blogPosts.first()
        
        assertEquals(1, primerPost.id)
        assertTrue(primerPost.titulo.isNotEmpty())
        assertTrue(primerPost.descripcion.isNotEmpty())
        assertTrue(primerPost.contenido.isNotEmpty())
        assertTrue(primerPost.autor.isNotEmpty())
    }

    @Test
    fun `cambiar filtro multiples veces funciona correctamente`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.filterByCategory("Guías")
        val estadoGuias = viewModel.uiState.value.filteredPosts.size
        
        viewModel.filterByCategory("Todas")
        val estadoTodas = viewModel.uiState.value.filteredPosts.size
        
        viewModel.filterByCategory("Consejos")
        val estadoConsejos = viewModel.uiState.value.filteredPosts.size

        // Then
        assertEquals(1, estadoGuias)
        assertEquals(4, estadoTodas)
        assertEquals(1, estadoConsejos)
    }

    @Test
    fun `selectedCategory se actualiza correctamente`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals("Todas", viewModel.uiState.value.selectedCategory)

        // When
        viewModel.filterByCategory("Tendencias")

        // Then
        assertEquals("Tendencias", viewModel.uiState.value.selectedCategory)
    }

    @Test
    fun `getCategories incluye Todas como primera opcion`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        val categorias = viewModel.getCategories()

        // Then
        assertEquals("Todas", categorias.first())
    }

    @Test
    fun `getCategories retorna categorias unicas`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        val categorias = viewModel.getCategories()

        // Then
        val sinTodas = categorias.drop(1)
        assertEquals(sinTodas.size, sinTodas.distinct().size)
    }

    @Test
    fun `filterByCategory mantiene blogPosts sin modificar`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()
        val cantidadOriginal = viewModel.uiState.value.blogPosts.size

        // When
        viewModel.filterByCategory("Guías")

        // Then
        assertEquals(cantidadOriginal, viewModel.uiState.value.blogPosts.size)
    }

    @Test
    fun `filteredPosts se inicializa con todos los posts`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(uiState.blogPosts.size, uiState.filteredPosts.size)
    }

    @Test
    fun `uiState error es null al inicio`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `posts tienen categorias validas`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val categorias = viewModel.getCategories().drop(1) // Sin "Todas"
        val uiState = viewModel.uiState.value
        uiState.blogPosts.forEach { post ->
            assertTrue(categorias.contains(post.categoria))
        }
    }
}
