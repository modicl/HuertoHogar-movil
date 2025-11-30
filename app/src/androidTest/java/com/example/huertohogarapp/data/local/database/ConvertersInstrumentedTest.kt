package com.example.huertohogarapp.data.local.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests instrumentados para Converters de Room
 * Verifican la conversión de ByteArray a String y viceversa
 */
@RunWith(AndroidJUnit4::class)
class ConvertersInstrumentedTest {

    private lateinit var converters: Converters

    @Before
    fun setUp() {
        converters = Converters()
    }

    @Test
    fun fromByteArray_convertsCorrectly() {
        // Given
        val byteArray = "Test String".toByteArray()

        // When
        val result = converters.fromByteArray(byteArray)

        // Then
        assertEquals("Test String", result)
    }

    @Test
    fun fromByteArray_withNull_returnsNull() {
        // When
        val result = converters.fromByteArray(null)

        // Then
        assertNull(result)
    }

    @Test
    fun toByteArray_convertsCorrectly() {
        // Given
        val string = "Test String"

        // When
        val result = converters.toByteArray(string)

        // Then
        assertArrayEquals("Test String".toByteArray(), result)
    }

    @Test
    fun toByteArray_withNull_returnsNull() {
        // When
        val result = converters.toByteArray(null)

        // Then
        assertNull(result)
    }

    @Test
    fun roundTrip_preservesData() {
        // Given
        val originalString = "Contraseña Segura 123!"

        // When
        val byteArray = converters.toByteArray(originalString)
        val resultString = converters.fromByteArray(byteArray)

        // Then
        assertEquals(originalString, resultString)
    }

    @Test
    fun roundTrip_withSpecialCharacters() {
        // Given
        val originalString = "Ñoño áéíóú ¿¡"

        // When
        val byteArray = converters.toByteArray(originalString)
        val resultString = converters.fromByteArray(byteArray)

        // Then
        assertEquals(originalString, resultString)
    }

    @Test
    fun roundTrip_withEmptyString() {
        // Given
        val originalString = ""

        // When
        val byteArray = converters.toByteArray(originalString)
        val resultString = converters.fromByteArray(byteArray)

        // Then
        assertEquals(originalString, resultString)
    }

    @Test
    fun roundTrip_withLongString() {
        // Given
        val originalString = "A".repeat(1000)

        // When
        val byteArray = converters.toByteArray(originalString)
        val resultString = converters.fromByteArray(byteArray)

        // Then
        assertEquals(originalString, resultString)
    }
}
