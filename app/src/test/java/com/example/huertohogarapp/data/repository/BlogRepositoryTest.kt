package com.example.huertohogarapp.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BlogRepositoryTest {

    private lateinit var repository: BlogRepository

    @BeforeEach
    fun setUp() {
        repository = BlogRepositoryImpl()
    }

    @Test
    fun getBlogPosts () = runBlocking { //retorna todos los posts
        // When
        val result = repository.getBlogPosts().first()

        // Then
        assertEquals(4, result.size)
    }

    @Test
    fun getBlogPostById () = runBlocking { //retorna el post correcto cuando existe
        // When
        val result = repository.getBlogPostById(1).first()

        // Then
        assertEquals("Beneficios de cultivar tu propio huerto en casa", result?.titulo)
    }

    @Test
    fun getBlogPostByIdNull () = runBlocking { //retorna null cuando no existe
        // When
        val result = repository.getBlogPostById(99).first()

        // Then
        assertNull(result)
    }

    @Test
    fun getBlogPostsByCategory () = runBlocking { //retorna posts de la categoría correcta
        // When
        val result = repository.getBlogPostsByCategory("Guías").first()

        // Then
        assertEquals(1, result.size)
        assertEquals("Guía para principiantes: Cómo iniciar tu huerto urbano", result.first().titulo)
    }

    @Test
    fun getBlogPostsByCategoryEmpty () = runBlocking { //retorna una lista vacía si la categoría no existe
        // When
        val result = repository.getBlogPostsByCategory("Inexistente").first()

        // Then
        assertTrue(result.isEmpty())
    }
}
