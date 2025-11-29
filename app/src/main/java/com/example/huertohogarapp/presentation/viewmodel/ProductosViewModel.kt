package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.data.repository.CarritoRepository
import com.example.huertohogarapp.data.repository.ProductoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Productos
 * Arquitectura MVVM
 */
class ProductosViewModel(
    private val productoRepository: ProductoRepository,
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductosUiState())
    val uiState: StateFlow<ProductosUiState> = _uiState.asStateFlow()

    val carritoItems = carritoRepository.carritoItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            productoRepository.getProductos().collect { productos ->
                _uiState.value = _uiState.value.copy(
                    productos = productos,
                    productosFiltrados = productos,
                    isLoading = false
                )
            }
        }
    }

    fun getProductoById(id: Int): Flow<Producto?> {
        return productoRepository.getProductoById(id)
    }

    fun filtrarPorCategoria(categoria: String) {
        _uiState.value = _uiState.value.copy(
            categoriaSeleccionada = if (categoria == _uiState.value.categoriaSeleccionada) "Todos" else categoria
        )
        aplicarFiltros()
    }

    fun buscarProductos(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        aplicarFiltros()
    }

    private fun aplicarFiltros() {
        val state = _uiState.value
        var productosFiltrados = state.productos

        // Filtrar por categoría
        if (state.categoriaSeleccionada != "Todos") {
            productosFiltrados = productosFiltrados.filter {
                it.categoria.nombreCategoria == state.categoriaSeleccionada
            }
        }

        // Filtrar por búsqueda
        if (state.searchQuery.isNotBlank()) {
            productosFiltrados = productosFiltrados.filter {
                it.nombreProducto.contains(state.searchQuery, ignoreCase = true) ||
                it.descripcionProducto.contains(state.searchQuery, ignoreCase = true)
            }
        }

        _uiState.value = state.copy(productosFiltrados = productosFiltrados)
    }

    fun agregarAlCarrito(producto: Producto) {
        viewModelScope.launch {
            carritoRepository.agregarProducto(producto)
            _uiState.value = _uiState.value.copy(
                mensajeSnackbar = "${producto.nombreProducto} agregado al carrito"
            )
        }
    }

    fun quitarDelCarrito(productoId: Int) {
        viewModelScope.launch {
            carritoRepository.quitarProducto(productoId)
        }
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(mensajeSnackbar = null)
    }

    fun obtenerCantidadEnCarrito(productoId: Int): Int {
        return carritoItems.value.find { it.producto.idProducto == productoId }?.cantidad ?: 0
    }

    fun obtenerCategorias(): List<String> {
        return listOf("Todos") + _uiState.value.productos.map { it.categoria.nombreCategoria }.distinct().sorted()
    }
}

/**
 * Estado de UI para la pantalla de Productos
 */
data class ProductosUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = "",
    val productos: List<Producto> = emptyList(),
    val productosFiltrados: List<Producto> = emptyList(),
    val categoriaSeleccionada: String = "Todos",
    val mensajeSnackbar: String? = null
)
