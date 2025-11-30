package com.example.huertohogarapp.data.local.database

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para Converters de Room
 */
class ConvertersTest {

    private lateinit var converters: Converters

    @BeforeEach
    fun setUp() {
        converters = Converters()
    }

    @Test
    fun `fromByteArray convierte ByteArray a String correctamente`() {
        val byteArray = "Hola Mundo".toByteArray()
        
        val result = converters.fromByteArray(byteArray)
        
        assertEquals("Hola Mundo", result)
    }

    @Test
    fun `fromByteArray retorna null cuando el input es null`() {
        val result = converters.fromByteArray(null)
        
        assertNull(result)
    }

    @Test
    fun `fromByteArray maneja ByteArray vacio`() {
        val byteArray = "".toByteArray()
        
        val result = converters.fromByteArray(byteArray)
        
        assertEquals("", result)
    }

    @Test
    fun `fromByteArray maneja caracteres especiales`() {
        val byteArray = "Ñoño áéíóú".toByteArray()
        
        val result = converters.fromByteArray(byteArray)
        
        assertEquals("Ñoño áéíóú", result)
    }

    @Test
    fun `toByteArray convierte String a ByteArray correctamente`() {
        val string = "Hola Mundo"
        
        val result = converters.toByteArray(string)
        
        assertArrayEquals("Hola Mundo".toByteArray(), result)
    }

    @Test
    fun `toByteArray retorna null cuando el input es null`() {
        val result = converters.toByteArray(null)
        
        assertNull(result)
    }

    @Test
    fun `toByteArray maneja String vacio`() {
        val result = converters.toByteArray("")
        
        assertArrayEquals("".toByteArray(), result)
    }

    @Test
    fun `toByteArray maneja caracteres especiales`() {
        val string = "Ñoño áéíóú"
        
        val result = converters.toByteArray(string)
        
        assertArrayEquals("Ñoño áéíóú".toByteArray(), result)
    }

    @Test
    fun `conversion ida y vuelta mantiene datos`() {
        val original = "Texto de prueba 123!@#"
        
        val byteArray = converters.toByteArray(original)
        val resultado = converters.fromByteArray(byteArray)
        
        assertEquals(original, resultado)
    }

    @Test
    fun `conversion ida y vuelta con null`() {
        val byteArray = converters.toByteArray(null)
        val resultado = converters.fromByteArray(byteArray)
        
        assertNull(resultado)
    }

    @Test
    fun `fromByteArray maneja numeros`() {
        val byteArray = "12345".toByteArray()
        
        val result = converters.fromByteArray(byteArray)
        
        assertEquals("12345", result)
    }

    @Test
    fun `toByteArray maneja numeros`() {
        val result = converters.toByteArray("12345")
        
        assertArrayEquals("12345".toByteArray(), result)
    }

    @Test
    fun `fromByteArray maneja texto largo`() {
        val textoLargo = "A".repeat(10000)
        val byteArray = textoLargo.toByteArray()
        
        val result = converters.fromByteArray(byteArray)
        
        assertEquals(textoLargo, result)
    }

    @Test
    fun `toByteArray maneja texto largo`() {
        val textoLargo = "B".repeat(10000)
        
        val result = converters.toByteArray(textoLargo)
        
        assertArrayEquals(textoLargo.toByteArray(), result)
    }
}
