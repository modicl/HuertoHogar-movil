package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.local.EstadoDataStore
import com.example.huertohogarapp.data.model.CartItem
import com.example.huertohogarapp.data.model.Categoria
import com.example.huertohogarapp.data.model.PaisOrigen
import com.example.huertohogarapp.data.model.Producto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CarritoRepositoryTest {

    private lateinit var repository: CarritoRepository
    private val dataStore: EstadoDataStore = mockk(relaxed = true)

    private val categoria = Categoria(1, "Frutas", "")
    private val pais = PaisOrigen(1, "Chile")
    private val producto1 = Producto(1, "Tomate", categoria, "", 10.0, 10, pais, "")
    private val producto2 = Producto(2, "Manzana", categoria, "", 20.0, 20, pais, "")

    @BeforeEach
    fun setUp() {
        repository = CarritoRepository(dataStore)
    }

    @Test
    fun agregarProducto() = runBlocking { // agrega nuevo item si no existe
        // Given
        coEvery { dataStore.carritoItems } returns flowOf(emptyList())

        // When
        repository.agregarProducto(producto1)

        // Then
        coVerify { dataStore.guardarCarrito(listOf(CartItem(producto1, 1))) }
    }

    @Test
    fun agregarProductoExistente () = runBlocking { //incrementa cantidad si item ya existe
        // Given
        val initialItems = listOf(CartItem(producto1, 1))
        coEvery { dataStore.carritoItems } returns flowOf(initialItems)

        // When
        repository.agregarProducto(producto1)

        // Then
        coVerify { dataStore.guardarCarrito(listOf(CartItem(producto1, 2))) }
    }

    @Test
    fun quitarProducto () = runBlocking { //disminuye cantidad si es mayor a 1
        // Given
        val initialItems = listOf(CartItem(producto1, 2))
        coEvery { dataStore.carritoItems } returns flowOf(initialItems)

        // When
        repository.quitarProducto(producto1.idProducto)

        // Then
        coVerify { dataStore.guardarCarrito(listOf(CartItem(producto1, 1))) }
    }

    @Test
    fun quitarProductoRestante () = runBlocking {//elimina item si cantidad es 1
        // Given
        val initialItems = listOf(CartItem(producto1, 1))
        coEvery { dataStore.carritoItems } returns flowOf(initialItems)

        // When
        repository.quitarProducto(producto1.idProducto)

        // Then
        coVerify { dataStore.guardarCarrito(emptyList()) }
    }

    @Test
    fun eliminarProducto () = runBlocking { //remueve el item del carrito
        // Given
        val initialItems = listOf(CartItem(producto1, 5))
        coEvery { dataStore.carritoItems } returns flowOf(initialItems)

        // When
        repository.eliminarProducto(producto1.idProducto)

        // Then
        coVerify { dataStore.guardarCarrito(emptyList()) }
    }

    @Test
    fun limpiarCarrito () = runBlocking { //llama a dataStore para limpiar
        // When
        repository.limpiarCarrito()

        // Then
        coVerify { dataStore.limpiarCarrito() }
    }

    @Test
    fun obtenerTotal () = runBlocking { //calcula la suma correctamente
        // Given
        val items = listOf(CartItem(producto1, 2), CartItem(producto2, 1)) // 2*10 + 1*20 = 40
        coEvery { dataStore.carritoItems } returns flowOf(items)

        // When
        val total = repository.obtenerTotal()

        // Then
        assertEquals(40.0, total)
    }

    @Test
    fun obtenerCantidadTotal () = runBlocking { //calcula la suma de items correctamente
        // Given
        val items = listOf(CartItem(producto1, 2), CartItem(producto2, 3))
        coEvery { dataStore.carritoItems } returns flowOf(items)

        // When
        val cantidad = repository.obtenerCantidadTotal()

        // Then
        assertEquals(5, cantidad)
    }
}
