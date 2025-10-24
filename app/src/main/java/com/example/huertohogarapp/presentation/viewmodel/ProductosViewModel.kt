package com.example.huertohogarapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.local.EstadoDataStore
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.data.repository.CarritoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Productos
 * Arquitectura MVVM
 */
class ProductosViewModel(application: Application) : AndroidViewModel(application) {
    
    private val carritoRepository = CarritoRepository(EstadoDataStore(application))
    
    // Lista de 8 productos de HuertoHogar
    private val todosLosProductos = listOf(
        Producto(
            id = 1,
            nombre = "Kit de Inicio",
            descripcion = "Kit completo para comenzar tu huerto en casa",
            precio = 29990.0,
            imagen = "kit_inicio",
            categoria = "Kits",
            stock = 15,
            destacado = true
        ),
        Producto(
            id = 2,
            nombre = "Semillas de Tomate",
            descripcion = "Semillas orgánicas de tomate cherry",
            precio = 3990.0,
            imagen = "semillas_tomate",
            categoria = "Semillas",
            stock = 50,
            destacado = true
        ),
        Producto(
            id = 3,
            nombre = "Tierra Orgánica",
            descripcion = "Sustrato orgánico premium 5kg",
            precio = 8990.0,
            imagen = "tierra",
            categoria = "Sustratos",
            stock = 30,
            destacado = false
        ),
        Producto(
            id = 4,
            nombre = "Macetas Biodegradables",
            descripcion = "Set de 10 macetas biodegradables",
            precio = 5990.0,
            imagen = "macetas",
            categoria = "Macetas",
            stock = 25,
            destacado = false
        ),
        Producto(
            id = 5,
            nombre = "Fertilizante Natural",
            descripcion = "Abono orgánico para todo tipo de plantas",
            precio = 6990.0,
            imagen = "fertilizante",
            categoria = "Fertilizantes",
            stock = 40,
            destacado = true
        ),
        Producto(
            id = 6,
            nombre = "Semillas de Lechuga",
            descripcion = "Semillas orgánicas de lechuga variada",
            precio = 2990.0,
            imagen = "semillas_lechuga",
            categoria = "Semillas",
            stock = 60,
            destacado = false
        ),
        Producto(
            id = 7,
            nombre = "Kit de Herramientas",
            descripcion = "Set básico de herramientas de jardinería",
            precio = 15990.0,
            imagen = "herramientas",
            categoria = "Herramientas",
            stock = 20,
            destacado = false
        ),
        Producto(
            id = 8,
            nombre = "Sistema de Riego",
            descripcion = "Sistema de riego por goteo automático",
            precio = 24990.0,
            imagen = "riego",
            categoria = "Riego",
            stock = 12,
            destacado = true
        )
    )
    
    private val _uiState = MutableStateFlow(ProductosUiState())
    val uiState: StateFlow<ProductosUiState> = _uiState.asStateFlow()
    
    val carritoItems = carritoRepository.carritoItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    init {
        cargarProductos()
    }
    
    private fun cargarProductos() {
        _uiState.value = _uiState.value.copy(
            productos = todosLosProductos,
            productosFiltrados = todosLosProductos,
            isLoading = false
        )
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
                it.categoria == state.categoriaSeleccionada
            }
        }
        
        // Filtrar por búsqueda
        if (state.searchQuery.isNotBlank()) {
            productosFiltrados = productosFiltrados.filter {
                it.nombre.contains(state.searchQuery, ignoreCase = true) ||
                it.descripcion.contains(state.searchQuery, ignoreCase = true)
            }
        }
        
        _uiState.value = state.copy(productosFiltrados = productosFiltrados)
    }
    
    fun agregarAlCarrito(producto: Producto) {
        viewModelScope.launch {
            carritoRepository.agregarProducto(producto)
            _uiState.value = _uiState.value.copy(
                mensajeSnackbar = "${producto.nombre} agregado al carrito"
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
        return carritoItems.value.find { it.producto.id == productoId }?.cantidad ?: 0
    }
    
    fun obtenerCategorias(): List<String> {
        return listOf("Todos") + todosLosProductos.map { it.categoria }.distinct().sorted()
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

