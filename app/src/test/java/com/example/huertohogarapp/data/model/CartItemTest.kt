package com.example.huertohogarapp.data.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para el modelo CartItem
 */
class CartItemTest {

    private val categoria = Categoria(1, "Frutas", "Frutas frescas")
    private val paisOrigen = PaisOrigen(1, "Chile")
    private val producto = Producto(
        idProducto = 1,
        nombreProducto = "Tomate",
        categoria = categoria,
        descripcionProducto = "Tomate fresco",
        precioProducto = 1500.0,
        stockProducto = 100,
        paisOrigen = paisOrigen,
        imagenUrl = "url"
    )

    @Test
    fun `crear cart item con todos los campos correctamente`() {
        // Given & When
        val cartItem = CartItem(
            producto = producto,
            cantidad = 5
        )

        // Then
        assertEquals(producto, cartItem.producto)
        assertEquals(5, cartItem.cantidad)
    }

    @Test
    fun `dos cart items con mismos valores son iguales`() {
        // Given
        val cartItem1 = CartItem(producto, 5)
        val cartItem2 = CartItem(producto, 5)

        // Then
        assertEquals(cartItem1, cartItem2)
    }

    @Test
    fun `dos cart items con diferentes cantidades son diferentes`() {
        // Given
        val cartItem1 = CartItem(producto, 5)
        val cartItem2 = CartItem(producto, 10)

        // Then
        assertNotEquals(cartItem1, cartItem2)
    }

    @Test
    fun `cart item copy funciona correctamente`() {
        // Given
        val cartItem = CartItem(producto, 5)

        // When
        val cartItemCopy = cartItem.copy(cantidad = 10)

        // Then
        assertEquals(10, cartItemCopy.cantidad)
        assertEquals(cartItem.producto, cartItemCopy.producto)
    }

    @Test
    fun `cart item con cantidad uno es valido`() {
        // Given & When
        val cartItem = CartItem(producto, 1)

        // Then
        assertEquals(1, cartItem.cantidad)
    }

    @Test
    fun `cart item toString contiene informacion relevante`() {
        // Given
        val cartItem = CartItem(producto, 5)

        // When
        val toString = cartItem.toString()

        // Then
        assertTrue(toString.contains("5"))
    }

    @Test
    fun `cart item hashCode es consistente`() {
        // Given
        val cartItem1 = CartItem(producto, 5)
        val cartItem2 = CartItem(producto, 5)

        // Then
        assertEquals(cartItem1.hashCode(), cartItem2.hashCode())
    }

    @Test
    fun `calcular precio total del cart item`() {
        // Given
        val cartItem = CartItem(producto, 3)

        // When
        val total = cartItem.producto.precioProducto * cartItem.cantidad

        // Then
        assertEquals(4500.0, total)
    }

    @Test
    fun `cart item con cantidad cero es valido como data class`() {
        // Given & When
        val cartItem = CartItem(producto, 0)

        // Then
        assertEquals(0, cartItem.cantidad)
    }

    @Test
    fun `cart item con cantidad alta es valido`() {
        // Given & When
        val cartItem = CartItem(producto, 1000)

        // Then
        assertEquals(1000, cartItem.cantidad)
    }

    @Test
    fun `cart item con diferentes productos son diferentes`() {
        // Given
        val otroProducto = Producto(
            idProducto = 2,
            nombreProducto = "Manzana",
            categoria = categoria,
            descripcionProducto = "Manzana roja",
            precioProducto = 2000.0,
            stockProducto = 50,
            paisOrigen = paisOrigen,
            imagenUrl = "url2"
        )
        val cartItem1 = CartItem(producto, 5)
        val cartItem2 = CartItem(otroProducto, 5)

        // Then
        assertNotEquals(cartItem1, cartItem2)
    }

    @Test
    fun `cart item copy con producto diferente`() {
        // Given
        val otroProducto = Producto(
            idProducto = 2,
            nombreProducto = "Manzana",
            categoria = categoria,
            descripcionProducto = "Manzana roja",
            precioProducto = 2000.0,
            stockProducto = 50,
            paisOrigen = paisOrigen,
            imagenUrl = "url2"
        )
        val cartItem = CartItem(producto, 5)

        // When
        val cartItemCopy = cartItem.copy(producto = otroProducto)

        // Then
        assertEquals(otroProducto, cartItemCopy.producto)
        assertEquals(cartItem.cantidad, cartItemCopy.cantidad)
    }
}
