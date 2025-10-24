package com.example.huertohogarapp.presentation.navigation

/**
 * Rutas de navegación de la aplicación HuertoHogar
 * Basado en las rutas del proyecto React
 * Arquitectura MVVM
 */
sealed class Screen(val route: String, val title: String) {
    object Inicio : Screen("inicio", "Inicio")
    object Productos : Screen("productos", "Productos")
    object Nosotros : Screen("nosotros", "Nosotros")
    object Contacto : Screen("contacto", "Contacto")
    object Blog : Screen("blog", "Blog")
}

/**
 * Lista de items para el Bottom Navigation
 */
val bottomNavItems = listOf(
    Screen.Inicio,
    Screen.Productos,
    Screen.Nosotros,
    Screen.Contacto,
    Screen.Blog
)
