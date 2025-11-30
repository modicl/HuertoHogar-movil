package com.example.huertohogarapp.data.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para el modelo ContactForm
 */
class ContactFormTest {

    @Test
    fun `crear contact form con todos los campos correctamente`() {
        // Given & When
        val contactForm = ContactForm(
            nombre = "Juan Pérez",
            email = "juan@example.com",
            mensaje = "Este es un mensaje de prueba",
            telefono = "+56912345678"
        )

        // Then
        assertEquals("Juan Pérez", contactForm.nombre)
        assertEquals("juan@example.com", contactForm.email)
        assertEquals("Este es un mensaje de prueba", contactForm.mensaje)
        assertEquals("+56912345678", contactForm.telefono)
    }

    @Test
    fun `crear contact form sin telefono`() {
        // Given & When
        val contactForm = ContactForm(
            nombre = "Juan Pérez",
            email = "juan@example.com",
            mensaje = "Este es un mensaje de prueba"
        )

        // Then
        assertNull(contactForm.telefono)
    }

    @Test
    fun `dos contact forms con mismos valores son iguales`() {
        // Given
        val form1 = ContactForm("Juan", "juan@example.com", "Mensaje", "+56912345678")
        val form2 = ContactForm("Juan", "juan@example.com", "Mensaje", "+56912345678")

        // Then
        assertEquals(form1, form2)
    }

    @Test
    fun `dos contact forms con diferentes emails son diferentes`() {
        // Given
        val form1 = ContactForm("Juan", "juan@example.com", "Mensaje", "+56912345678")
        val form2 = ContactForm("Juan", "pedro@example.com", "Mensaje", "+56912345678")

        // Then
        assertNotEquals(form1, form2)
    }

    @Test
    fun `contact form copy funciona correctamente`() {
        // Given
        val form = ContactForm("Juan", "juan@example.com", "Mensaje", "+56912345678")

        // When
        val formCopy = form.copy(nombre = "Pedro")

        // Then
        assertEquals("Pedro", formCopy.nombre)
        assertEquals(form.email, formCopy.email)
        assertEquals(form.mensaje, formCopy.mensaje)
    }

    @Test
    fun `contact form toString contiene informacion relevante`() {
        // Given
        val form = ContactForm("Juan Pérez", "juan@example.com", "Mensaje de prueba")

        // When
        val toString = form.toString()

        // Then
        assertTrue(toString.contains("Juan Pérez"))
    }

    @Test
    fun `contact form hashCode es consistente`() {
        // Given
        val form1 = ContactForm("Juan", "juan@example.com", "Mensaje")
        val form2 = ContactForm("Juan", "juan@example.com", "Mensaje")

        // Then
        assertEquals(form1.hashCode(), form2.hashCode())
    }

    @Test
    fun `contact form con telefono null es valido`() {
        // Given & When
        val form = ContactForm("Juan", "juan@example.com", "Mensaje", null)

        // Then
        assertNull(form.telefono)
    }

    @Test
    fun `contact form con mensaje largo es valido`() {
        // Given
        val mensajeLargo = "Este es un mensaje muy largo " + "a".repeat(500)
        
        // When
        val form = ContactForm("Juan", "juan@example.com", mensajeLargo)

        // Then
        assertTrue(form.mensaje.length > 500)
    }

    @Test
    fun `contact form con campos vacios es valido como data class`() {
        // Given & When
        val form = ContactForm("", "", "")

        // Then
        assertEquals("", form.nombre)
        assertEquals("", form.email)
        assertEquals("", form.mensaje)
    }

    @Test
    fun `contact form copy con telefono`() {
        // Given
        val form = ContactForm("Juan", "juan@example.com", "Mensaje", null)

        // When
        val formCopy = form.copy(telefono = "+56912345678")

        // Then
        assertEquals("+56912345678", formCopy.telefono)
    }

    @Test
    fun `contact form toString contiene email`() {
        // Given
        val form = ContactForm("Juan", "test@test.com", "Mensaje")

        // When
        val toString = form.toString()

        // Then
        assertTrue(toString.contains("test@test.com"))
    }

    @Test
    fun `dos contact forms con telefono null y vacio son diferentes`() {
        // Given
        val form1 = ContactForm("Juan", "juan@example.com", "Mensaje", null)
        val form2 = ContactForm("Juan", "juan@example.com", "Mensaje", "")

        // Then
        assertNotEquals(form1, form2)
    }
}
