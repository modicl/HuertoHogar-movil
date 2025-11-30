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

    @Test
    fun `getBlogPosts contiene posts con datos validos`() = runBlocking {
        val posts = repository.getBlogPosts().first()
        
        posts.forEach { post ->
            assertTrue(post.id > 0)
            assertTrue(post.titulo.isNotBlank())
            assertTrue(post.descripcion.isNotBlank())
            assertTrue(post.autor.isNotBlank())
        }
    }

    @Test
    fun `getBlogPostById retorna cada post correctamente`() = runBlocking {
        for (id in 1..4) {
            val post = repository.getBlogPostById(id).first()
            assertEquals(id, post?.id)
        }
    }

    @Test
    fun `getBlogPostsByCategory filtra por Sostenibilidad`() = runBlocking {
        val posts = repository.getBlogPostsByCategory("Sostenibilidad").first()
        assertTrue(posts.all { it.categoria == "Sostenibilidad" })
    }

    @Test
    fun `getBlogPostsByCategory filtra por Consejos`() = runBlocking {
        val posts = repository.getBlogPostsByCategory("Consejos").first()
        assertTrue(posts.all { it.categoria == "Consejos" })
    }

    @Test
    fun `getBlogPostsByCategory filtra por Tendencias`() = runBlocking {
        val posts = repository.getBlogPostsByCategory("Tendencias").first()
        assertTrue(posts.all { it.categoria == "Tendencias" })
    }

    @Test
    fun `primer post tiene autor correcto`() = runBlocking {
        val post = repository.getBlogPostById(1).first()
        assertEquals("María González", post?.autor)
    }

    @Test
    fun `segundo post tiene autor correcto`() = runBlocking {
        val post = repository.getBlogPostById(2).first()
        assertEquals("Carlos Ramírez", post?.autor)
    }

    @Test
    fun `tercer post tiene autor correcto`() = runBlocking {
        val post = repository.getBlogPostById(3).first()
        assertEquals("Ana Martínez", post?.autor)
    }

    @Test
    fun `cuarto post tiene autor correcto`() = runBlocking {
        val post = repository.getBlogPostById(4).first()
        assertEquals("Jorge López", post?.autor)
    }

    @Test
    fun `todos los posts tienen URLs validas`() = runBlocking {
        val posts = repository.getBlogPosts().first()
        posts.forEach { post ->
            assertTrue(post.url.startsWith("https://"))
        }
    }

    @Test
    fun `todos los posts tienen tiempo de lectura`() = runBlocking {
        val posts = repository.getBlogPosts().first()
        posts.forEach { post ->
            assertTrue(post.tiempoLectura.contains("min"))
        }
    }

    @Test
    fun `repository implementa BlogRepository interface`() {
        assertTrue(repository is BlogRepository)
    }
}
