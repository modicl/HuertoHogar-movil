package com.example.huertohogarapp.data.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para el modelo PaisOrigen
 */
class PaisOrigenTest {

    @Test
    fun `crear pais origen con todos los campos correctamente`() {
        // Given & When
        val paisOrigen = PaisOrigen(
            idPais = 1,
            nombre = "Chile"
        )

        // Then
        assertEquals(1, paisOrigen.idPais)
        assertEquals("Chile", paisOrigen.nombre)
    }

    @Test
    fun `dos paises origen con mismos valores son iguales`() {
        // Given
        val pais1 = PaisOrigen(1, "Chile")
        val pais2 = PaisOrigen(1, "Chile")

        // Then
        assertEquals(pais1, pais2)
    }

    @Test
    fun `dos paises origen con diferentes ids son diferentes`() {
        // Given
        val pais1 = PaisOrigen(1, "Chile")
        val pais2 = PaisOrigen(2, "Chile")

        // Then
        assertNotEquals(pais1, pais2)
    }

    @Test
    fun `pais origen copy funciona correctamente`() {
        // Given
        val pais = PaisOrigen(1, "Chile")

        // When
        val paisCopy = pais.copy(nombre = "Argentina")

        // Then
        assertEquals("Argentina", paisCopy.nombre)
        assertEquals(pais.idPais, paisCopy.idPais)
    }

    @Test
    fun `pais origen toString contiene informacion relevante`() {
        // Given
        val pais = PaisOrigen(1, "Chile")

        // When
        val toString = pais.toString()

        // Then
        assertTrue(toString.contains("Chile"))
        assertTrue(toString.contains("1"))
    }

    @Test
    fun `pais origen hashCode es consistente`() {
        // Given
        val pais1 = PaisOrigen(1, "Chile")
        val pais2 = PaisOrigen(1, "Chile")

        // Then
        assertEquals(pais1.hashCode(), pais2.hashCode())
    }

    @Test
    fun `pais origen con nombre largo es valido`() {
        // Given & When
        val pais = PaisOrigen(1, "República Democrática del Congo")

        // Then
        assertEquals("República Democrática del Congo", pais.nombre)
    }

    @Test
    fun `pais origen con nombre vacio es valido como data class`() {
        // Given & When
        val pais = PaisOrigen(1, "")

        // Then
        assertEquals("", pais.nombre)
    }

    @Test
    fun `pais origen con id cero es valido`() {
        // Given & When
        val pais = PaisOrigen(0, "Chile")

        // Then
        assertEquals(0, pais.idPais)
    }

    @Test
    fun `pais origen con id negativo es valido como data class`() {
        // Given & When
        val pais = PaisOrigen(-1, "Chile")

        // Then
        assertEquals(-1, pais.idPais)
    }

    @Test
    fun `paises con diferentes nombres son diferentes`() {
        // Given
        val pais1 = PaisOrigen(1, "Chile")
        val pais2 = PaisOrigen(1, "Peru")

        // Then
        assertNotEquals(pais1, pais2)
    }

    @Test
    fun `pais origen copy con id diferente`() {
        // Given
        val pais = PaisOrigen(1, "Chile")

        // When
        val paisCopy = pais.copy(idPais = 2)

        // Then
        assertEquals(2, paisCopy.idPais)
        assertEquals(pais.nombre, paisCopy.nombre)
    }
}
