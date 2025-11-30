package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.model.Categoria
import com.example.huertohogarapp.data.model.PaisOrigen
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.data.remote.ProductoApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductoRepositoryTest {

    private lateinit var repository: ProductoRepository
    private val apiService: ProductoApiService = mockk()

    private val categoria = Categoria(1, "Frutas", "")
    private val pais = PaisOrigen(1, "Chile")
    private val productos = listOf(
        Producto(1, "Tomate", categoria, "Tomate rojo", 10.0, 10, pais, ""),
        Producto(2, "Manzana", categoria, "Manzana verde", 20.0, 20, pais, "")
    )

    @BeforeEach
    fun setUp() {
        // CORREGIDO, Usamos el constructor para inyectar el mock
        repository = ProductoRepositoryImpl(apiService)
    }

    @Test
    fun getProductos () = runBlocking { //retorna lista de productos cuando la API tiene éxito
        // Given
        coEvery { apiService.getProductos() } returns productos

        // When
        val result = repository.getProductos().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Tomate", result[0].nombreProducto)
    }

    @Test
    fun getProductosEmpty () = runBlocking { //retorna lista vacía cuando la API falla
        // Given
        coEvery { apiService.getProductos() } throws Exception()

        // When
        val result = repository.getProductos().first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun getProductoById() = runBlocking { //retorna el producto correcto cuando existe
        // Given
        coEvery { apiService.getProductos() } returns productos

        // When
        val result = repository.getProductoById(1).first()

        // Then
        assertEquals("Tomate", result?.nombreProducto)
    }

    @Test
    fun getProductoByIdEmpty () = runBlocking { //retorna null cuando no existe
        // Given
        coEvery { apiService.getProductos() } returns productos

        // When
        val result = repository.getProductoById(3).first()

        // Then
        assertNull(result)
    }

    @Test
    fun getProductoByIdNull() = runBlocking { //retorna null cuando la API falla
        // Given
        coEvery { apiService.getProductos() } throws Exception()

        // When
        val result = repository.getProductoById(1).first()

        // Then
        assertNull(result)
    }

    @Test
    fun searchProductos() = runBlocking { //retorna productos que coinciden con la query
        // Given
        coEvery { apiService.getProductos() } returns productos

        // When
        val result = repository.searchProductos("manzana").first()

        // Then
        assertEquals(1, result.size)
        assertEquals("Manzana", result.first().nombreProducto)
    }

    @Test
    fun searchProductosEmpty() = runBlocking { //retorna una lista vacía si no hay coincidencias
        // Given
        coEvery { apiService.getProductos() } returns productos

        // When
        val result = repository.searchProductos("patata").first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun searchProductosNull() = runBlocking { //retorna una lista vacía si la API falla
        // Given
        coEvery { apiService.getProductos() } throws Exception()

        // When
        val result = repository.searchProductos("tomate").first()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchProductos busca por descripcion`() = runBlocking {
        coEvery { apiService.getProductos() } returns productos
        
        val result = repository.searchProductos("rojo").first()
        
        assertEquals(1, result.size)
        assertEquals("Tomate", result.first().nombreProducto)
    }

    @Test
    fun `searchProductos busca por categoria`() = runBlocking {
        coEvery { apiService.getProductos() } returns productos
        
        val result = repository.searchProductos("Frutas").first()
        
        assertEquals(2, result.size)
    }

    @Test
    fun `searchProductos es case insensitive`() = runBlocking {
        coEvery { apiService.getProductos() } returns productos
        
        val resultLower = repository.searchProductos("tomate").first()
        val resultUpper = repository.searchProductos("TOMATE").first()
        
        assertEquals(resultLower.size, resultUpper.size)
    }

    @Test
    fun `getProductoById encuentra segundo producto`() = runBlocking {
        coEvery { apiService.getProductos() } returns productos
        
        val result = repository.getProductoById(2).first()
        
        assertEquals("Manzana", result?.nombreProducto)
        assertEquals(20.0, result?.precioProducto)
    }

    @Test
    fun `getProductos retorna productos con datos completos`() = runBlocking {
        coEvery { apiService.getProductos() } returns productos
        
        val result = repository.getProductos().first()
        
        result.forEach { producto ->
            assertTrue(producto.idProducto > 0)
            assertTrue(producto.nombreProducto.isNotBlank())
            assertTrue(producto.precioProducto > 0)
        }
    }

    @Test
    fun `repository implementa ProductoRepository interface`() {
        assertTrue(repository is ProductoRepository)
    }
}
