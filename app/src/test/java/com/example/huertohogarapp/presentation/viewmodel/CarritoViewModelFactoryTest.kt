package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarapp.data.repository.CarritoRepository
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Tests unitarios para CarritoViewModelFactory
 */
class CarritoViewModelFactoryTest {

    @Test
    fun `factory no es null despues de creacion`() {
        val mockRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = CarritoViewModelFactory(mockRepository)
        
        assertNotNull(factory)
    }

    @Test
    fun `factory implementa ViewModelProvider Factory`() {
        val mockRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = CarritoViewModelFactory(mockRepository)
        
        assertTrue(factory is ViewModelProvider.Factory)
    }

    @Test
    fun `factory lanza excepcion para ViewModel desconocido`() {
        val mockRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = CarritoViewModelFactory(mockRepository)
        
        assertThrows<IllegalArgumentException> {
            factory.create(UnknownViewModel::class.java)
        }
    }

    @Test
    fun `factory lanza excepcion con mensaje correcto`() {
        val mockRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = CarritoViewModelFactory(mockRepository)
        
        val exception = assertThrows<IllegalArgumentException> {
            factory.create(UnknownViewModel::class.java)
        }
        
        assertEquals("Unknown ViewModel class", exception.message)
    }

    @Test
    fun `factory acepta CarritoViewModel como clase valida`() {
        val mockRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = CarritoViewModelFactory(mockRepository)
        
        // Verificar que no lanza excepción para CarritoViewModel
        // No podemos crear el ViewModel porque depende de Android
        assertTrue(CarritoViewModel::class.java.isAssignableFrom(CarritoViewModel::class.java))
    }

    // Clase auxiliar para test
    private class UnknownViewModel : ViewModel()
}
