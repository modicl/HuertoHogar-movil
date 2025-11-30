package com.example.huertohogarapp.data.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para el modelo Categoria
 */
class CategoriaTest {

    @Test
    fun `crear categoria con todos los campos correctamente`() {
        // Given & When
        val categoria = Categoria(
            idCategoria = 1,
            nombreCategoria = "Frutas",
            descripcionCategoria = "Frutas frescas y de temporada"
        )

        // Then
        assertEquals(1, categoria.idCategoria)
        assertEquals("Frutas", categoria.nombreCategoria)
        assertEquals("Frutas frescas y de temporada", categoria.descripcionCategoria)
    }

    @Test
    fun `dos categorias con mismos valores son iguales`() {
        // Given
        val categoria1 = Categoria(1, "Frutas", "Descripción")
        val categoria2 = Categoria(1, "Frutas", "Descripción")

        // Then
        assertEquals(categoria1, categoria2)
    }

    @Test
    fun `dos categorias con diferentes ids son diferentes`() {
        // Given
        val categoria1 = Categoria(1, "Frutas", "Descripción")
        val categoria2 = Categoria(2, "Frutas", "Descripción")

        // Then
        assertNotEquals(categoria1, categoria2)
    }

    @Test
    fun `categoria copy funciona correctamente`() {
        // Given
        val categoria = Categoria(1, "Frutas", "Descripción")

        // When
        val categoriaCopy = categoria.copy(nombreCategoria = "Verduras")

        // Then
        assertEquals("Verduras", categoriaCopy.nombreCategoria)
        assertEquals(categoria.idCategoria, categoriaCopy.idCategoria)
    }

    @Test
    fun `categoria con descripcion vacia es valida`() {
        // Given & When
        val categoria = Categoria(1, "Frutas", "")

        // Then
        assertEquals("", categoria.descripcionCategoria)
    }

    @Test
    fun `categoria toString contiene informacion relevante`() {
        // Given
        val categoria = Categoria(1, "Frutas", "Descripción")

        // When
        val toString = categoria.toString()

        // Then
        assertTrue(toString.contains("Frutas"))
        assertTrue(toString.contains("1"))
    }

    @Test
    fun `categoria hashCode es consistente`() {
        // Given
        val categoria1 = Categoria(1, "Frutas", "Descripción")
        val categoria2 = Categoria(1, "Frutas", "Descripción")

        // Then
        assertEquals(categoria1.hashCode(), categoria2.hashCode())
    }
}
