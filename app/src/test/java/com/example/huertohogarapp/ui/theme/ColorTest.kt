package com.example.huertohogarapp.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para los colores del tema
 */
class ColorTest {

    @Test
    fun `VerdePrincipal no es null`() {
        assertNotNull(VerdePrincipal)
    }

    @Test
    fun `VerdeOscuro no es null`() {
        assertNotNull(VerdeOscuro)
    }

    @Test
    fun `GrisClaro no es null`() {
        assertNotNull(GrisClaro)
    }

    @Test
    fun `GrisTexto no es null`() {
        assertNotNull(GrisTexto)
    }

    @Test
    fun `Blanco no es null`() {
        assertNotNull(Blanco)
    }

    @Test
    fun `GrisMedio no es null`() {
        assertNotNull(GrisMedio)
    }

    @Test
    fun `VerdeClaro no es null`() {
        assertNotNull(VerdeClaro)
    }

    @Test
    fun `VerdePrincipalDark no es null`() {
        assertNotNull(VerdePrincipalDark)
    }

    @Test
    fun `GrisOscuro no es null`() {
        assertNotNull(GrisOscuro)
    }

    @Test
    fun `VerdePrincipal es diferente de VerdeOscuro`() {
        assertNotEquals(VerdePrincipal, VerdeOscuro)
    }

    @Test
    fun `VerdePrincipal es diferente de VerdePrincipalDark`() {
        assertNotEquals(VerdePrincipal, VerdePrincipalDark)
    }

    @Test
    fun `GrisClaro es diferente de GrisOscuro`() {
        assertNotEquals(GrisClaro, GrisOscuro)
    }

    @Test
    fun `todos los colores son no nulos`() {
        val colores = listOf(
            VerdePrincipal,
            VerdeOscuro,
            GrisClaro,
            GrisTexto,
            Blanco,
            GrisMedio,
            VerdeClaro,
            VerdePrincipalDark,
            GrisOscuro
        )
        
        colores.forEach { color ->
            assertNotNull(color)
        }
    }

    @Test
    fun `colores tienen alpha completo`() {
        // Los colores definidos tienen alpha 0xFF (255 = opaco)
        assertTrue(VerdePrincipal.alpha >= 0.99f)
        assertTrue(VerdeOscuro.alpha >= 0.99f)
        assertTrue(Blanco.alpha >= 0.99f)
    }

    @Test
    fun `VerdePrincipal tiene componente verde dominante`() {
        assertTrue(VerdePrincipal.green > VerdePrincipal.red)
        assertTrue(VerdePrincipal.green > VerdePrincipal.blue)
    }

    @Test
    fun `Blanco tiene todos los componentes en maximo`() {
        assertEquals(1.0f, Blanco.red, 0.01f)
        assertEquals(1.0f, Blanco.green, 0.01f)
        assertEquals(1.0f, Blanco.blue, 0.01f)
    }

    @Test
    fun `GrisClaro es mas claro que GrisMedio`() {
        assertTrue(GrisClaro.red > GrisMedio.red)
        assertTrue(GrisClaro.green > GrisMedio.green)
    }

    @Test
    fun `Colores son instancias de Color`() {
        assertTrue(VerdePrincipal is Color)
        assertTrue(VerdeOscuro is Color)
        assertTrue(GrisClaro is Color)
    }
}
