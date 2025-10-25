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
 * Contiene datos de noticias relevantes sobre agricultura urbana
 */
class BlogRepositoryImpl : BlogRepository {
    
    private val blogPosts = listOf(
        BlogPost(
            id = 1,
            titulo = "Beneficios de cultivar tu propio huerto en casa",
            descripcion = "Descubre cómo un huerto casero puede mejorar tu salud, ahorrar dinero y contribuir al medio ambiente.",
            contenido = "Los huertos caseros están ganando popularidad en zonas urbanas. Cultivar tus propios alimentos no solo garantiza productos frescos y orgánicos, sino que también reduce tu huella de carbono y promueve un estilo de vida más sostenible.",
            autor = "María González",
            fecha = "15 de Octubre, 2025",
            imagen = "🌱",
            categoria = "Sostenibilidad",
            url = "https://www.ecologiaverde.com/beneficios-de-tener-un-huerto-urbano-en-casa-2848.html",
            tiempoLectura = "5 min"
        ),
        BlogPost(
            id = 2,
            titulo = "Guía para principiantes: Cómo iniciar tu huerto urbano",
            descripcion = "Todo lo que necesitas saber para comenzar tu primer huerto en casa, desde la elección del espacio hasta las primeras cosechas.",
            contenido = "Iniciar un huerto urbano es más fácil de lo que piensas. Con el espacio adecuado, las herramientas básicas y un poco de dedicación, puedes cosechar tus propias verduras y hierbas aromáticas en pocas semanas.",
            autor = "Carlos Ramírez",
            fecha = "10 de Octubre, 2025",
            imagen = "🌿",
            categoria = "Guías",
            url = "https://www.consumer.es/medio-ambiente/como-crear-un-huerto-urbano.html",
            tiempoLectura = "7 min"
        ),
        BlogPost(
            id = 3,
            titulo = "Las mejores plantas para cultivar en espacios reducidos",
            descripcion = "Conoce qué vegetales y hierbas son ideales para cultivar en balcones, terrazas o ventanas con poco espacio.",
            contenido = "No necesitas un gran jardín para cultivar tus propios alimentos. Plantas como tomates cherry, lechugas, albahaca y cilantro prosperan en macetas pequeñas y son perfectas para principiantes.",
            autor = "Ana Martínez",
            fecha = "5 de Octubre, 2025",
            imagen = "🪴",
            categoria = "Consejos",
            url = "https://www.hogarmania.com/jardineria/tecnicas/huerto/plantas-cultivar-poco-espacio.html",
            tiempoLectura = "6 min"
        ),
        BlogPost(
            id = 4,
            titulo = "Agricultura urbana: Tendencia global hacia ciudades verdes",
            descripcion = "Explora cómo la agricultura urbana está transformando las ciudades y promoviendo comunidades más sostenibles.",
            contenido = "La agricultura urbana no es solo una moda, es una necesidad. Ciudades de todo el mundo están implementando huertos comunitarios y techos verdes para mejorar la calidad del aire, fomentar la cohesión social y garantizar la seguridad alimentaria.",
            autor = "Jorge López",
            fecha = "1 de Octubre, 2025",
            imagen = "🏙️",
            categoria = "Tendencias",
            url = "https://www.fao.org/urban-agriculture/es/",
            tiempoLectura = "8 min"
        )
    )
    
    override fun getBlogPosts(): Flow<List<BlogPost>> = flow {
        emit(blogPosts)
    }
    
    override fun getBlogPostById(id: Int): Flow<BlogPost?> = flow {
        emit(blogPosts.find { it.id == id })
    }
    
    override fun getBlogPostsByCategory(category: String): Flow<List<BlogPost>> = flow {
        emit(blogPosts.filter { it.categoria == category })
    }
}
