package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.model.BlogPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repositorio para manejar operaciones de Blog
 * Arquitectura MVVM - Capa de datos
 */
interface BlogRepository {
    fun getBlogPosts(): Flow<List<BlogPost>>
    fun getBlogPostById(id: Int): Flow<BlogPost?>
    fun getBlogPostsByCategory(category: String): Flow<List<BlogPost>>
}

/**
 * Implementación del repositorio de blog
 * Por ahora con datos mock, en futuro se conectará a API/BD
 */
class BlogRepositoryImpl : BlogRepository {
    
    override fun getBlogPosts(): Flow<List<BlogPost>> = flow {
        // TODO: Implementar llamada a API o base de datos
        emit(emptyList())
    }
    
    override fun getBlogPostById(id: Int): Flow<BlogPost?> = flow {
        // TODO: Implementar obtención de post específico
        emit(null)
    }
    
    override fun getBlogPostsByCategory(category: String): Flow<List<BlogPost>> = flow {
        // TODO: Implementar filtrado por categoría
        emit(emptyList())
    }
}
