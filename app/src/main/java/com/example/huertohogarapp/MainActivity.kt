package com.example.huertohogarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.huertohogarapp.presentation.navigation.HuertoHogarBottomNavigation
import com.example.huertohogarapp.presentation.navigation.HuertoHogarNavGraph
import com.example.huertohogarapp.ui.theme.HuertoHogarAppTheme

/**
 * MainActivity - Actividad principal de la aplicación HuertoHogar
 * Implementa navegación inferior con animaciones y paleta de colores del proyecto React
 * Arquitectura MVVM
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