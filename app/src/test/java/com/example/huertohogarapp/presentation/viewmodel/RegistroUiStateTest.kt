package com.example.huertohogarapp.presentation.viewmodel

import android.net.Uri
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.mockk.mockk

/**
 * Tests unitarios para RegistroUiState
 */
class RegistroUiStateTest {

    @Test
    fun `RegistroUiState tiene valores por defecto correctos`() {
        val state = RegistroViewModel.RegistroUiState()
        
        assertEquals("", state.nombre)
        assertEquals("", state.apellido)
        assertEquals("", state.correo)
        assertEquals("", state.fechaNacimiento)
        assertNull(state.fotoPerfil)
        assertNull(state.nombreError)
        assertNull(state.apellidoError)
        assertNull(state.correoError)
        assertNull(state.fechaNacimientoError)
        assertNull(state.fotoPerfilError)
        assertFalse(state.mostrarDialogoExito)
        assertFalse(state.mostrarSelectorFoto)
        assertFalse(state.cargando)
    }

    @Test
    fun `RegistroUiState se crea con valores personalizados`() {
        val state = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            apellido = "Pérez",
            correo = "juan@test.com",
            fechaNacimiento = "01/01/1990",
            nombreError = "Error en nombre",
            apellidoError = "Error en apellido",
            correoError = "Error en correo",
            fechaNacimientoError = "Error en fecha",
            fotoPerfilError = "Error en foto",
            mostrarDialogoExito = true,
            mostrarSelectorFoto = true,
            cargando = true
        )
        
        assertEquals("Juan", state.nombre)
        assertEquals("Pérez", state.apellido)
        assertEquals("juan@test.com", state.correo)
        assertEquals("01/01/1990", state.fechaNacimiento)
        assertEquals("Error en nombre", state.nombreError)
        assertEquals("Error en apellido", state.apellidoError)
        assertEquals("Error en correo", state.correoError)
        assertEquals("Error en fecha", state.fechaNacimientoError)
        assertEquals("Error en foto", state.fotoPerfilError)
        assertTrue(state.mostrarDialogoExito)
        assertTrue(state.mostrarSelectorFoto)
        assertTrue(state.cargando)
    }

    @Test
    fun `RegistroUiState copy funciona correctamente`() {
        val original = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            correo = "juan@test.com"
        )
        
        val copy = original.copy(nombre = "Pedro")
        
        assertEquals("Pedro", copy.nombre)
        assertEquals("juan@test.com", copy.correo)
        assertNotEquals(original.nombre, copy.nombre)
    }

    @Test
    fun `RegistroUiState equals funciona correctamente`() {
        val state1 = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            apellido = "Pérez"
        )
        val state2 = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            apellido = "Pérez"
        )
        val state3 = RegistroViewModel.RegistroUiState(
            nombre = "Pedro",
            apellido = "García"
        )
        
        assertEquals(state1, state2)
        assertNotEquals(state1, state3)
    }

    @Test
    fun `RegistroUiState hashCode es consistente`() {
        val state1 = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            apellido = "Pérez"
        )
        val state2 = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            apellido = "Pérez"
        )
        
        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `RegistroUiState toString contiene propiedades`() {
        val state = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            correo = "juan@test.com"
        )
        
        val toString = state.toString()
        
        assertTrue(toString.contains("Juan"))
        assertTrue(toString.contains("juan@test.com"))
    }

    @Test
    fun `RegistroUiState con errores no afecta valores principales`() {
        val state = RegistroViewModel.RegistroUiState(
            nombre = "Juan",
            nombreError = "Nombre muy corto"
        )
        
        assertEquals("Juan", state.nombre)
        assertEquals("Nombre muy corto", state.nombreError)
    }

    @Test
    fun `RegistroUiState mostrarDialogoExito toggle`() {
        val stateInicial = RegistroViewModel.RegistroUiState(mostrarDialogoExito = false)
        val stateModificado = stateInicial.copy(mostrarDialogoExito = true)
        
        assertFalse(stateInicial.mostrarDialogoExito)
        assertTrue(stateModificado.mostrarDialogoExito)
    }

    @Test
    fun `RegistroUiState mostrarSelectorFoto toggle`() {
        val stateInicial = RegistroViewModel.RegistroUiState(mostrarSelectorFoto = false)
        val stateModificado = stateInicial.copy(mostrarSelectorFoto = true)
        
        assertFalse(stateInicial.mostrarSelectorFoto)
        assertTrue(stateModificado.mostrarSelectorFoto)
    }

    @Test
    fun `RegistroUiState cargando cambia estado`() {
        val sinCargar = RegistroViewModel.RegistroUiState(cargando = false)
        val cargando = RegistroViewModel.RegistroUiState(cargando = true)
        
        assertFalse(sinCargar.cargando)
        assertTrue(cargando.cargando)
    }

    @Test
    fun `RegistroUiState con todos los errores`() {
        val state = RegistroViewModel.RegistroUiState(
            nombreError = "Error 1",
            apellidoError = "Error 2",
            correoError = "Error 3",
            fechaNacimientoError = "Error 4",
            fotoPerfilError = "Error 5"
        )
        
        assertNotNull(state.nombreError)
        assertNotNull(state.apellidoError)
        assertNotNull(state.correoError)
        assertNotNull(state.fechaNacimientoError)
        assertNotNull(state.fotoPerfilError)
    }

    @Test
    fun `RegistroUiState sin errores`() {
        val state = RegistroViewModel.RegistroUiState()
        
        assertNull(state.nombreError)
        assertNull(state.apellidoError)
        assertNull(state.correoError)
        assertNull(state.fechaNacimientoError)
        assertNull(state.fotoPerfilError)
    }

    @Test
    fun `RegistroUiState fotoPerfil puede ser null`() {
        val sinFoto = RegistroViewModel.RegistroUiState()
        val conFoto = RegistroViewModel.RegistroUiState(fotoPerfil = mockk<Uri>())
        
        assertNull(sinFoto.fotoPerfil)
        assertNotNull(conFoto.fotoPerfil)
    }

    @Test
    fun `RegistroUiState fechaNacimiento formato`() {
        val state = RegistroViewModel.RegistroUiState(fechaNacimiento = "15/06/1995")
        
        assertEquals("15/06/1995", state.fechaNacimiento)
    }
}
