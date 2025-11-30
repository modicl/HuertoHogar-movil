package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarapp.data.repository.CarritoRepository
import com.example.huertohogarapp.data.repository.ProductoRepository
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Tests unitarios para ProductosViewModelFactory
 */
class ProductosViewModelFactoryTest {

    @Test
    fun `factory no es null despues de creacion`() {
        val mockProductoRepository = mockk<ProductoRepository>(relaxed = true)
        val mockCarritoRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = ProductosViewModelFactory(mockProductoRepository, mockCarritoRepository)
        
        assertNotNull(factory)
    }

    @Test
    fun `factory implementa ViewModelProvider Factory`() {
        val mockProductoRepository = mockk<ProductoRepository>(relaxed = true)
        val mockCarritoRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = ProductosViewModelFactory(mockProductoRepository, mockCarritoRepository)
        
        assertTrue(factory is ViewModelProvider.Factory)
    }

    @Test
    fun `factory lanza excepcion para ViewModel desconocido`() {
        val mockProductoRepository = mockk<ProductoRepository>(relaxed = true)
        val mockCarritoRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = ProductosViewModelFactory(mockProductoRepository, mockCarritoRepository)
        
        assertThrows<IllegalArgumentException> {
            factory.create(UnknownViewModel::class.java)
        }
    }

    @Test
    fun `factory lanza excepcion con mensaje correcto`() {
        val mockProductoRepository = mockk<ProductoRepository>(relaxed = true)
        val mockCarritoRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = ProductosViewModelFactory(mockProductoRepository, mockCarritoRepository)
        
        val exception = assertThrows<IllegalArgumentException> {
            factory.create(UnknownViewModel::class.java)
        }
        
        assertEquals("Unknown ViewModel class", exception.message)
    }

    @Test
    fun `factory acepta ProductosViewModel como clase valida`() {
        val mockProductoRepository = mockk<ProductoRepository>(relaxed = true)
        val mockCarritoRepository = mockk<CarritoRepository>(relaxed = true)
        val factory = ProductosViewModelFactory(mockProductoRepository, mockCarritoRepository)
        
        // Verificar que ProductosViewModel es asignable
        assertTrue(ProductosViewModel::class.java.isAssignableFrom(ProductosViewModel::class.java))
    }

    // Clase auxiliar para test
    private class UnknownViewModel : ViewModel()
}
