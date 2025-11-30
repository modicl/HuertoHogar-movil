package com.example.huertohogarapp.presentation.viewmodel

import com.example.huertohogarapp.data.model.BlogPost
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para BlogUiState
 */
class BlogUiStateTest {

    private val samplePosts = listOf(
        BlogPost(1, "Título 1", "Desc 1", "Cont 1", "Autor 1", "Fecha", "Img", "Cat1", "Url1"),
        BlogPost(2, "Título 2", "Desc 2", "Cont 2", "Autor 2", "Fecha", "Img", "Cat2", "Url2")
    )

    @Test
    fun `estado inicial tiene valores por defecto correctos`() {
        // Given & When
        val uiState = BlogUiState()

        // Then
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
        assertTrue(uiState.blogPosts.isEmpty())
        assertTrue(uiState.filteredPosts.isEmpty())
        assertEquals("Todas", uiState.selectedCategory)
    }

    @Test
    fun `estado con posts se crea correctamente`() {
        // Given & When
        val uiState = BlogUiState(blogPosts = samplePosts, filteredPosts = samplePosts)

        // Then
        assertEquals(2, uiState.blogPosts.size)
        assertEquals(2, uiState.filteredPosts.size)
    }

    @Test
    fun `estado copy funciona correctamente`() {
        // Given
        val uiState = BlogUiState(isLoading = true)

        // When
        val uiStateCopy = uiState.copy(isLoading = false, selectedCategory = "Guías")

        // Then
        assertFalse(uiStateCopy.isLoading)
        assertEquals("Guías", uiStateCopy.selectedCategory)
    }

    @Test
    fun `estado con error se crea correctamente`() {
        // Given & When
        val uiState = BlogUiState(error = "Error al cargar")

        // Then
        assertEquals("Error al cargar", uiState.error)
    }

    @Test
    fun `estado con isLoading true se crea correctamente`() {
        // Given & When
        val uiState = BlogUiState(isLoading = true)

        // Then
        assertTrue(uiState.isLoading)
    }

    @Test
    fun `dos estados con mismos valores son iguales`() {
        // Given
        val uiState1 = BlogUiState(isLoading = false, selectedCategory = "Todas")
        val uiState2 = BlogUiState(isLoading = false, selectedCategory = "Todas")

        // Then
        assertEquals(uiState1, uiState2)
    }

    @Test
    fun `estado hashCode es consistente`() {
        // Given
        val uiState1 = BlogUiState(selectedCategory = "Guías")
        val uiState2 = BlogUiState(selectedCategory = "Guías")

        // Then
        assertEquals(uiState1.hashCode(), uiState2.hashCode())
    }

    @Test
    fun `estado toString contiene informacion relevante`() {
        // Given
        val uiState = BlogUiState(selectedCategory = "Sostenibilidad")

        // When
        val toString = uiState.toString()

        // Then
        assertTrue(toString.contains("Sostenibilidad"))
    }

    @Test
    fun `estado con posts filtrados diferentes a posts completos`() {
        // Given & When
        val uiState = BlogUiState(
            blogPosts = samplePosts,
            filteredPosts = listOf(samplePosts[0])
        )

        // Then
        assertEquals(2, uiState.blogPosts.size)
        assertEquals(1, uiState.filteredPosts.size)
    }
}
