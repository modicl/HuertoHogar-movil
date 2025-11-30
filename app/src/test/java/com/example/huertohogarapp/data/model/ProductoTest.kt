package com.example.huertohogarapp.data.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para el modelo Producto
 */
class ProductoTest {

    private val categoria = Categoria(1, "Frutas", "Frutas frescas")
    private val paisOrigen = PaisOrigen(1, "Chile")

    @Test
    fun `crear producto con todos los campos correctamente`() {
        // Given & When
        val producto = Producto(
            idProducto = 1,
            nombreProducto = "Tomate",
            categoria = categoria,
            descripcionProducto = "Tomate fresco",
            precioProducto = 1500.0,
            stockProducto = 100,
            paisOrigen = paisOrigen,
            imagenUrl = "https://example.com/tomate.jpg"
        )

        // Then
        assertEquals(1, producto.idProducto)
        assertEquals("Tomate", producto.nombreProducto)
        assertEquals(categoria, producto.categoria)
        assertEquals("Tomate fresco", producto.descripcionProducto)
        assertEquals(1500.0, producto.precioProducto)
        assertEquals(100, producto.stockProducto)
        assertEquals(paisOrigen, producto.paisOrigen)
        assertEquals("https://example.com/tomate.jpg", producto.imagenUrl)
    }

    @Test
    fun `dos productos con mismos valores son iguales`() {
        // Given
        val producto1 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")
        val producto2 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")

        // Then
        assertEquals(producto1, producto2)
    }

    @Test
    fun `dos productos con diferentes ids son diferentes`() {
        // Given
        val producto1 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")
        val producto2 = Producto(2, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")

        // Then
        assertNotEquals(producto1, producto2)
    }

    @Test
    fun `producto copy funciona correctamente`() {
        // Given
        val producto = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")

        // When
        val productoCopy = producto.copy(precioProducto = 20.0)

        // Then
        assertEquals(20.0, productoCopy.precioProducto)
        assertEquals(producto.idProducto, productoCopy.idProducto)
        assertEquals(producto.nombreProducto, productoCopy.nombreProducto)
    }

    @Test
    fun `producto con precio cero es valido`() {
        // Given & When
        val producto = Producto(1, "Tomate", categoria, "Desc", 0.0, 5, paisOrigen, "url")

        // Then
        assertEquals(0.0, producto.precioProducto)
    }

    @Test
    fun `producto con stock cero es valido`() {
        // Given & When
        val producto = Producto(1, "Tomate", categoria, "Desc", 10.0, 0, paisOrigen, "url")

        // Then
        assertEquals(0, producto.stockProducto)
    }

    @Test
    fun `producto toString contiene informacion relevante`() {
        // Given
        val producto = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")

        // When
        val toString = producto.toString()

        // Then
        assertTrue(toString.contains("Tomate"))
        assertTrue(toString.contains("1"))
    }

    @Test
    fun `producto hashCode es consistente`() {
        // Given
        val producto1 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")
        val producto2 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")

        // Then
        assertEquals(producto1.hashCode(), producto2.hashCode())
    }

    @Test
    fun `producto con precio negativo es valido como data class`() {
        // Given & When - data class no valida valores
        val producto = Producto(1, "Tomate", categoria, "Desc", -10.0, 5, paisOrigen, "url")

        // Then
        assertEquals(-10.0, producto.precioProducto)
    }

    @Test
    fun `producto con stock negativo es valido como data class`() {
        // Given & When
        val producto = Producto(1, "Tomate", categoria, "Desc", 10.0, -5, paisOrigen, "url")

        // Then
        assertEquals(-5, producto.stockProducto)
    }

    @Test
    fun `producto con nombre vacio es valido como data class`() {
        // Given & When
        val producto = Producto(1, "", categoria, "Desc", 10.0, 5, paisOrigen, "url")

        // Then
        assertEquals("", producto.nombreProducto)
    }

    @Test
    fun `producto con imagen url vacia es valido`() {
        // Given & When
        val producto = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "")

        // Then
        assertEquals("", producto.imagenUrl)
    }

    @Test
    fun `producto copy modifica solo campo especificado`() {
        // Given
        val producto = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")

        // When
        val productoCopy = producto.copy(nombreProducto = "Lechuga")

        // Then
        assertEquals("Lechuga", productoCopy.nombreProducto)
        assertEquals(producto.idProducto, productoCopy.idProducto)
        assertEquals(producto.categoria, productoCopy.categoria)
        assertEquals(producto.descripcionProducto, productoCopy.descripcionProducto)
        assertEquals(producto.precioProducto, productoCopy.precioProducto)
        assertEquals(producto.stockProducto, productoCopy.stockProducto)
        assertEquals(producto.paisOrigen, productoCopy.paisOrigen)
        assertEquals(producto.imagenUrl, productoCopy.imagenUrl)
    }

    @Test
    fun `producto con categoria diferente son diferentes`() {
        // Given
        val otraCategoria = Categoria(2, "Verduras", "Verduras frescas")
        val producto1 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")
        val producto2 = Producto(1, "Tomate", otraCategoria, "Desc", 10.0, 5, paisOrigen, "url")

        // Then
        assertNotEquals(producto1, producto2)
    }

    @Test
    fun `producto con pais diferente son diferentes`() {
        // Given
        val otroPais = PaisOrigen(2, "Argentina")
        val producto1 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, paisOrigen, "url")
        val producto2 = Producto(1, "Tomate", categoria, "Desc", 10.0, 5, otroPais, "url")

        // Then
        assertNotEquals(producto1, producto2)
    }
}
