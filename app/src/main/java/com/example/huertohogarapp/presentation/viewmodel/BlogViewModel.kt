package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.model.BlogPost
import com.example.huertohogarapp.data.repository.BlogRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Blog
 * Arquitectura MVVM
 */
class BlogViewModel : ViewModel() {
    
    private val repository = BlogRepositoryImpl()
    
    private val _uiState = MutableStateFlow(BlogUiState())
    val uiState: StateFlow<BlogUiState> = _uiState.asStateFlow()
    
    init {
        loadBlogPosts()
    }
    
    /**
     * Carga los artículos del blog desde el repositorio
     */
    fun loadBlogPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getBlogPosts().collect { posts ->
                    _uiState.update { 
                        it.copy(
                            blogPosts = posts,
                            filteredPosts = posts,
                            isLoading = false
                        ) 
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar los artículos: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    /**
     * Filtra los artículos por categoría
     */
    fun filterByCategory(category: String) {
        val currentPosts = _uiState.value.blogPosts
        val filtered = if (category == "Todas") {
            currentPosts
        } else {
            currentPosts.filter { it.categoria == category }
        }
        _uiState.update { 
            it.copy(
                filteredPosts = filtered,
                selectedCategory = category
            ) 
        }
    }
    
    /**
     * Obtiene todas las categorías disponibles
     */
    fun getCategories(): List<String> {
        val categories = _uiState.value.blogPosts
            .map { it.categoria }
            .distinct()
        return listOf("Todas") + categories
    }
}

/**
 * Estado de UI para la pantalla de Blog
 */
data class BlogUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val blogPosts: List<BlogPost> = emptyList(),
    val filteredPosts: List<BlogPost> = emptyList(),
    val selectedCategory: String = "Todas"
)
