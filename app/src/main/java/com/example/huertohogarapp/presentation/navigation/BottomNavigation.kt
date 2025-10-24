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
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination to avoid building up a large stack
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
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
    return when (screen) {
        is Screen.Inicio -> Icons.Filled.Home
        is Screen.Productos -> Icons.Filled.ShoppingCart
        is Screen.Nosotros -> Icons.Filled.Info
        is Screen.Contacto -> Icons.Filled.Email
        is Screen.Blog -> Icons.AutoMirrored.Filled.Article
    }
}
