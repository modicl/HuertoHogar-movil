package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla de Blog
 * Arquitectura MVVM
 */
class BlogViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(BlogUiState())
    val uiState: StateFlow<BlogUiState> = _uiState.asStateFlow()
    
    // Futuras funciones para cargar artículos del blog
    fun loadBlogPosts() {
        // TODO: Implementar carga de artículos desde repositorio
    }
    
    fun filterByCategory(category: String) {
        // TODO: Implementar filtrado por categoría
    }
}

/**
 * Estado de UI para la pantalla de Blog
 */
data class BlogUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)
