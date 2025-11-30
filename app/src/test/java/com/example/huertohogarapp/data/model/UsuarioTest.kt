package com.example.huertohogarapp.data.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para el modelo Usuario
 */
class UsuarioTest {

    @Test
    fun `crear usuario con todos los campos correctamente`() {
        // Given & When
        val usuario = Usuario(
            nombre = "Juan",
            apellido = "Pérez",
            correo = "juan@example.com",
            fechaNacimiento = "1990-05-15",
            fotoPerfil = "uri://foto.jpg"
        )

        // Then
        assertEquals("Juan", usuario.nombre)
        assertEquals("Pérez", usuario.apellido)
        assertEquals("juan@example.com", usuario.correo)
        assertEquals("1990-05-15", usuario.fechaNacimiento)
        assertEquals("uri://foto.jpg", usuario.fotoPerfil)
    }

    @Test
    fun `crear usuario con valores por defecto`() {
        // Given & When
        val usuario = Usuario()

        // Then
        assertEquals("", usuario.nombre)
        assertEquals("", usuario.apellido)
        assertEquals("", usuario.correo)
        assertEquals("", usuario.fechaNacimiento)
        assertEquals("", usuario.fotoPerfil)
    }

    @Test
    fun `dos usuarios con mismos valores son iguales`() {
        // Given
        val usuario1 = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "foto.jpg")
        val usuario2 = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "foto.jpg")

        // Then
        assertEquals(usuario1, usuario2)
    }

    @Test
    fun `dos usuarios con diferentes correos son diferentes`() {
        // Given
        val usuario1 = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "foto.jpg")
        val usuario2 = Usuario("Juan", "Pérez", "pedro@example.com", "1990-05-15", "foto.jpg")

        // Then
        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `usuario copy funciona correctamente`() {
        // Given
        val usuario = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "foto.jpg")

        // When
        val usuarioCopy = usuario.copy(nombre = "Pedro")

        // Then
        assertEquals("Pedro", usuarioCopy.nombre)
        assertEquals(usuario.apellido, usuarioCopy.apellido)
        assertEquals(usuario.correo, usuarioCopy.correo)
    }

    @Test
    fun `usuario toString contiene informacion relevante`() {
        // Given
        val usuario = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "foto.jpg")

        // When
        val toString = usuario.toString()

        // Then
        assertTrue(toString.contains("Juan"))
    }

    @Test
    fun `usuario hashCode es consistente`() {
        // Given
        val usuario1 = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "foto.jpg")
        val usuario2 = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "foto.jpg")

        // Then
        assertEquals(usuario1.hashCode(), usuario2.hashCode())
    }

    @Test
    fun `usuario con foto perfil vacia es valido`() {
        // Given & When
        val usuario = Usuario("Juan", "Pérez", "juan@example.com", "1990-05-15", "")

        // Then
        assertEquals("", usuario.fotoPerfil)
    }
}
