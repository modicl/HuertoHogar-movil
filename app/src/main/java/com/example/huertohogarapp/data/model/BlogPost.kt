package com.example.huertohogarapp.data.model

/**
 * Modelo de datos para artículo de Blog
 * Basado en el proyecto React
 */
data class BlogPost(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val contenido: String,
    val autor: String,
    val fecha: String,
    val imagen: String,
    val categoria: String,
    val url: String,
    val tiempoLectura: String = "5 min"
)
