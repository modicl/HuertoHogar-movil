package com.example.huertohogarapp.presentation.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Bottom Navigation Bar para la aplicación HuertoHogar
 * Implementa navegación con iconos para las 5 secciones principales
 * Arquitectura MVVM - Componente de presentación
 */
@Composable
fun HuertoHogarBottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier.height(70.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { screen ->
            val isSelected = currentRoute == screen.route
            
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = getIconForScreen(screen),
                        contentDescription = screen.title
                    ) 
                },
                label = { 
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelSmall
                    ) 
                },
                selected = isSelected,
                onClick = {
                    // Navegar siempre, incluso si parece que estamos en la misma ruta
                    // Esto soluciona el problema del doble click
                    navController.navigate(screen.route) {
                        // Limpiar el back stack hasta Inicio
                        popUpTo(Screen.Inicio.route) {
                            inclusive = true
                        }
                        // Evitar múltiples copias del mismo destino
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.tertiary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}

/**
 * Obtiene el icono apropiado para cada pantalla
 */
private fun getIconForScreen(screen: Screen): ImageVector {
    return when (screen.route) {
        Screen.Inicio.route -> Icons.Filled.Home
        Screen.Productos.route -> Icons.Filled.ShoppingCart
        Screen.Nosotros.route -> Icons.Filled.Info
        Screen.Contacto.route -> Icons.Filled.Email
        Screen.Blog.route -> Icons.AutoMirrored.Filled.Article
        else -> Icons.Filled.Home // Default fallback
    }
}
