package com.example.huertohogarapp.data.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para el modelo BlogPost
 */
class BlogPostTest {

    @Test
    fun `crear blog post con todos los campos correctamente`() {
        // Given & When
        val blogPost = BlogPost(
            id = 1,
            titulo = "Título del Post",
            descripcion = "Descripción del post",
            contenido = "Contenido completo del post",
            autor = "Juan Pérez",
            fecha = "2025-01-15",
            imagen = "🌱",
            categoria = "Jardinería",
            url = "https://example.com/post",
            tiempoLectura = "5 min"
        )

        // Then
        assertEquals(1, blogPost.id)
        assertEquals("Título del Post", blogPost.titulo)
        assertEquals("Descripción del post", blogPost.descripcion)
        assertEquals("Contenido completo del post", blogPost.contenido)
        assertEquals("Juan Pérez", blogPost.autor)
        assertEquals("2025-01-15", blogPost.fecha)
        assertEquals("🌱", blogPost.imagen)
        assertEquals("Jardinería", blogPost.categoria)
        assertEquals("https://example.com/post", blogPost.url)
        assertEquals("5 min", blogPost.tiempoLectura)
    }

    @Test
    fun `blog post con tiempo lectura por defecto`() {
        // Given & When
        val blogPost = BlogPost(
            id = 1,
            titulo = "Título",
            descripcion = "Desc",
            contenido = "Contenido",
            autor = "Autor",
            fecha = "2025-01-15",
            imagen = "img",
            categoria = "Cat",
            url = "url"
        )

        // Then
        assertEquals("5 min", blogPost.tiempoLectura)
    }

    @Test
    fun `dos blog posts con mismos valores son iguales`() {
        // Given
        val post1 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "5 min")
        val post2 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "5 min")

        // Then
        assertEquals(post1, post2)
    }

    @Test
    fun `dos blog posts con diferentes ids son diferentes`() {
        // Given
        val post1 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "5 min")
        val post2 = BlogPost(2, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "5 min")

        // Then
        assertNotEquals(post1, post2)
    }

    @Test
    fun `blog post copy funciona correctamente`() {
        // Given
        val post = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "5 min")

        // When
        val postCopy = post.copy(titulo = "Nuevo Título")

        // Then
        assertEquals("Nuevo Título", postCopy.titulo)
        assertEquals(post.id, postCopy.id)
    }

    @Test
    fun `blog post toString contiene informacion relevante`() {
        // Given
        val post = BlogPost(1, "Título del Blog", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url")

        // When
        val toString = post.toString()

        // Then
        assertTrue(toString.contains("Título del Blog"))
    }

    @Test
    fun `blog post hashCode es consistente`() {
        // Given
        val post1 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url")
        val post2 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url")

        // Then
        assertEquals(post1.hashCode(), post2.hashCode())
    }

    @Test
    fun `blog post con campos vacios es valido como data class`() {
        // Given & When
        val post = BlogPost(1, "", "", "", "", "", "", "", "")

        // Then
        assertEquals("", post.titulo)
        assertEquals("", post.descripcion)
        assertEquals("5 min", post.tiempoLectura) // Valor por defecto
    }

    @Test
    fun `blog post copy modifica solo el campo especificado`() {
        // Given
        val post = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "10 min")

        // When
        val postCopy = post.copy(autor = "Nuevo Autor")

        // Then
        assertEquals("Nuevo Autor", postCopy.autor)
        assertEquals(post.id, postCopy.id)
        assertEquals(post.titulo, postCopy.titulo)
        assertEquals(post.contenido, postCopy.contenido)
        assertEquals(post.tiempoLectura, postCopy.tiempoLectura)
    }

    @Test
    fun `blog post con tiempo lectura diferente son diferentes`() {
        // Given
        val post1 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "5 min")
        val post2 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Cat", "Url", "10 min")

        // Then
        assertNotEquals(post1, post2)
    }

    @Test
    fun `blog post con categoria diferente son diferentes`() {
        // Given
        val post1 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Guías", "Url")
        val post2 = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "Consejos", "Url")

        // Then
        assertNotEquals(post1, post2)
    }

    @Test
    fun `blog post toString contiene categoria`() {
        // Given
        val post = BlogPost(1, "Título", "Desc", "Cont", "Autor", "Fecha", "Img", "MiCategoria", "Url")

        // When
        val toString = post.toString()

        // Then
        assertTrue(toString.contains("MiCategoria"))
    }
}
