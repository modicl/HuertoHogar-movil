package com.example.huertohogarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.huertohogarapp.data.local.EstadoDataStore
import com.example.huertohogarapp.presentation.navigation.HuertoHogarBottomNavigation
import com.example.huertohogarapp.presentation.navigation.HuertoHogarNavGraph
import com.example.huertohogarapp.presentation.navigation.Screen
import com.example.huertohogarapp.ui.theme.HuertoHogarAppTheme
import kotlinx.coroutines.launch
import com.example.huertohogarapp.data.local.database.AppDatabase

/**
 * MainActivity - Actividad principal de la aplicación HuertoHogar
 * Implementa navegación inferior con animaciones y paleta de colores del proyecto React
 * Arquitectura MVVM con persistencia de estado
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HuertoHogarAppTheme {
                HuertoHogarApp()
            }
        }
    }
}

@Composable
fun HuertoHogarApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val dataStore = remember { EstadoDataStore(context) }
    val coroutineScope = rememberCoroutineScope()

    // Inicializar Room database (para inspección con App Inspector)
    val db = remember { AppDatabase.getDatabase(context) }
    val usuarioDao = db.usuarioDao()
    // Puedes usar usuarioDao para operaciones en la base de datos desde un ViewModel o repositorio
    
    // Restaurar última página visitada
    var isLoading by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        dataStore.ultimaPagina.collect { ultimaPagina ->
            if (isLoading) {
                isLoading = false
                // Solo navegar si la última página NO es Inicio (que ya es el startDestination)
                if (ultimaPagina != Screen.Inicio.route && ultimaPagina.isNotBlank()) {
                    // Pequeño delay para asegurar que el NavController esté listo
                    kotlinx.coroutines.delay(100)
                    navController.navigate(ultimaPagina) {
                        // Mantener Inicio en el stack para poder volver
                        popUpTo(Screen.Inicio.route) { 
                            inclusive = false 
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
    
    // Guardar última página visitada cuando cambia
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val currentRoute = backStackEntry.destination.route
            currentRoute?.let {
                // No guardar rutas temporales como Registro
                if (it != Screen.Registro.route && it != Screen.Carrito.route) {
                    coroutineScope.launch {
                        dataStore.guardarUltimaPagina(it)
                    }
                }
            }
        }
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            HuertoHogarBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            HuertoHogarNavGraph(
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HuertoHogarAppPreview() {
    HuertoHogarAppTheme {
        HuertoHogarApp()
    }
}