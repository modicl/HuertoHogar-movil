package com.example.huertohogarapp.presentation.navigation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para las clases de navegación Screen
 */
class ScreenTest {

    @Test
    fun `Screen Inicio tiene ruta correcta`() {
        assertEquals("inicio", Screen.Inicio.route)
        assertEquals("Inicio", Screen.Inicio.title)
    }

    @Test
    fun `Screen Productos tiene ruta correcta`() {
        assertEquals("productos", Screen.Productos.route)
        assertEquals("Productos", Screen.Productos.title)
    }

    @Test
    fun `Screen ProductoDetalle tiene ruta correcta`() {
        assertEquals("producto/{productoId}", Screen.ProductoDetalle.route)
        assertEquals("Detalle del Producto", Screen.ProductoDetalle.title)
    }

    @Test
    fun `Screen ProductoDetalle createRoute genera ruta correcta`() {
        val ruta = Screen.ProductoDetalle.createRoute(123)
        assertEquals("producto/123", ruta)
    }

    @Test
    fun `Screen ProductoDetalle createRoute con id cero`() {
        val ruta = Screen.ProductoDetalle.createRoute(0)
        assertEquals("producto/0", ruta)
    }

    @Test
    fun `Screen Nosotros tiene ruta correcta`() {
        assertEquals("nosotros", Screen.Nosotros.route)
        assertEquals("Nosotros", Screen.Nosotros.title)
    }

    @Test
    fun `Screen Contacto tiene ruta correcta`() {
        assertEquals("contacto", Screen.Contacto.route)
        assertEquals("Contacto", Screen.Contacto.title)
    }

    @Test
    fun `Screen Blog tiene ruta correcta`() {
        assertEquals("blog", Screen.Blog.route)
        assertEquals("Blog", Screen.Blog.title)
    }

    @Test
    fun `Screen Registro tiene ruta correcta`() {
        assertEquals("registro", Screen.Registro.route)
        assertEquals("Registro", Screen.Registro.title)
    }

    @Test
    fun `Screen Carrito tiene ruta correcta`() {
        assertEquals("carrito", Screen.Carrito.route)
        assertEquals("Carrito", Screen.Carrito.title)
    }

    @Test
    fun `bottomNavItems contiene 5 elementos`() {
        assertEquals(5, bottomNavItems.size)
    }

    @Test
    fun `bottomNavItems contiene Inicio`() {
        assertTrue(bottomNavItems.contains(Screen.Inicio))
    }

    @Test
    fun `bottomNavItems contiene Productos`() {
        assertTrue(bottomNavItems.contains(Screen.Productos))
    }

    @Test
    fun `bottomNavItems contiene Nosotros`() {
        assertTrue(bottomNavItems.contains(Screen.Nosotros))
    }

    @Test
    fun `bottomNavItems contiene Contacto`() {
        assertTrue(bottomNavItems.contains(Screen.Contacto))
    }

    @Test
    fun `bottomNavItems contiene Blog`() {
        assertTrue(bottomNavItems.contains(Screen.Blog))
    }

    @Test
    fun `bottomNavItems no contiene Registro`() {
        assertFalse(bottomNavItems.contains(Screen.Registro))
    }

    @Test
    fun `bottomNavItems no contiene Carrito`() {
        assertFalse(bottomNavItems.contains(Screen.Carrito))
    }

    @Test
    fun `bottomNavItems no contiene ProductoDetalle`() {
        assertFalse(bottomNavItems.contains(Screen.ProductoDetalle))
    }

    @Test
    fun `todas las rutas son unicas`() {
        val allScreens = listOf(
            Screen.Inicio,
            Screen.Productos,
            Screen.ProductoDetalle,
            Screen.Nosotros,
            Screen.Contacto,
            Screen.Blog,
            Screen.Registro,
            Screen.Carrito
        )
        val rutas = allScreens.map { it.route }
        assertEquals(rutas.size, rutas.distinct().size)
    }

    @Test
    fun `todos los titulos son no vacios`() {
        val allScreens = listOf(
            Screen.Inicio,
            Screen.Productos,
            Screen.ProductoDetalle,
            Screen.Nosotros,
            Screen.Contacto,
            Screen.Blog,
            Screen.Registro,
            Screen.Carrito
        )
        assertTrue(allScreens.all { it.title.isNotEmpty() })
    }
}
