package com.example.huertohogarapp.data.local.database

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para UsuarioEntity
 */
class UsuarioEntityTest {

    @Test
    fun `crear UsuarioEntity con todos los campos`() {
        val imagenBytes = "imagen".toByteArray()
        val usuario = UsuarioEntity(
            id = 1,
            nombre = "Juan Pérez",
            email = "juan@test.com",
            password = "password123",
            imagenPerfil = imagenBytes
        )

        assertEquals(1, usuario.id)
        assertEquals("Juan Pérez", usuario.nombre)
        assertEquals("juan@test.com", usuario.email)
        assertEquals("password123", usuario.password)
        assertArrayEquals(imagenBytes, usuario.imagenPerfil)
    }

    @Test
    fun `crear UsuarioEntity sin imagen de perfil`() {
        val usuario = UsuarioEntity(
            id = 1,
            nombre = "Juan",
            email = "juan@test.com",
            password = "pass"
        )

        assertNull(usuario.imagenPerfil)
    }

    @Test
    fun `crear UsuarioEntity con id por defecto`() {
        val usuario = UsuarioEntity(
            nombre = "Test",
            email = "test@test.com",
            password = "test123"
        )

        assertEquals(0, usuario.id)
    }

    @Test
    fun `equals retorna true para usuarios iguales`() {
        val imagen = "foto".toByteArray()
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", imagen)
        val usuario2 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", imagen)

        assertEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna false para usuarios con diferente id`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass")
        val usuario2 = UsuarioEntity(2, "Juan", "juan@test.com", "pass")

        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna false para usuarios con diferente nombre`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass")
        val usuario2 = UsuarioEntity(1, "Pedro", "juan@test.com", "pass")

        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna false para usuarios con diferente email`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass")
        val usuario2 = UsuarioEntity(1, "Juan", "pedro@test.com", "pass")

        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna false para usuarios con diferente password`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass1")
        val usuario2 = UsuarioEntity(1, "Juan", "juan@test.com", "pass2")

        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna false cuando uno tiene imagen y otro no`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", "foto".toByteArray())
        val usuario2 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", null)

        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna false cuando otro tiene imagen y este no`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", null)
        val usuario2 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", "foto".toByteArray())

        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna false para imagenes diferentes`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", "foto1".toByteArray())
        val usuario2 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", "foto2".toByteArray())

        assertNotEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna true para ambos sin imagen`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", null)
        val usuario2 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", null)

        assertEquals(usuario1, usuario2)
    }

    @Test
    fun `equals retorna true para la misma instancia`() {
        val usuario = UsuarioEntity(1, "Juan", "juan@test.com", "pass")

        assertEquals(usuario, usuario)
    }

    @Test
    fun `equals retorna false para objeto de diferente clase`() {
        val usuario = UsuarioEntity(1, "Juan", "juan@test.com", "pass")

        assertNotEquals(usuario, "string")
    }

    @Test
    fun `hashCode es consistente para usuarios iguales`() {
        val imagen = "foto".toByteArray()
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", imagen)
        val usuario2 = UsuarioEntity(1, "Juan", "juan@test.com", "pass", imagen)

        assertEquals(usuario1.hashCode(), usuario2.hashCode())
    }

    @Test
    fun `hashCode es diferente para usuarios diferentes`() {
        val usuario1 = UsuarioEntity(1, "Juan", "juan@test.com", "pass")
        val usuario2 = UsuarioEntity(2, "Pedro", "pedro@test.com", "pass2")

        assertNotEquals(usuario1.hashCode(), usuario2.hashCode())
    }

    @Test
    fun `hashCode funciona con imagen null`() {
        val usuario = UsuarioEntity(1, "Juan", "juan@test.com", "pass", null)

        assertDoesNotThrow { usuario.hashCode() }
    }

    @Test
    fun `hashCode funciona con imagen presente`() {
        val usuario = UsuarioEntity(1, "Juan", "juan@test.com", "pass", "foto".toByteArray())

        assertDoesNotThrow { usuario.hashCode() }
    }

    @Test
    fun `copy funciona correctamente`() {
        val original = UsuarioEntity(1, "Juan", "juan@test.com", "pass")
        val copia = original.copy(nombre = "Pedro")

        assertEquals("Pedro", copia.nombre)
        assertEquals(original.id, copia.id)
        assertEquals(original.email, copia.email)
    }
}
