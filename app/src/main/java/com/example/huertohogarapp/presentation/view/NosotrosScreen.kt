package com.example.huertohogarapp.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogarapp.R
import com.example.huertohogarapp.presentation.viewmodel.NosotrosViewModel

/**
 * Pantalla de Nosotros (View)
 * Basada en Nosotros del proyecto React
 * Arquitectura MVVM con Material Design 3
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NosotrosScreen(
    viewModel: NosotrosViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "¿Quiénes somos?",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Sección: Sobre Nosotros con imagen
                SobreNosotrosSection(uiState.companyInfo.sobreNosotros)
                
                // Sección: Misión y Visión
                MisionVisionSection(
                    mision = uiState.companyInfo.mision,
                    vision = uiState.companyInfo.vision
                )
                
                // Espaciado final
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Sección Sobre Nosotros con imagen
 */
@Composable
private fun SobreNosotrosSection(sobreNosotros: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen del jardín
            Image(
                painter = painterResource(id = R.drawable.jardin),
                contentDescription = "Imagen Nosotros - Jardín",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            // Título
            Text(
                text = "Sobre Nosotros",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            // Texto descriptivo
            Text(
                text = sobreNosotros,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Justify,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(1.3f)
            )
        }
    }
}

/**
 * Sección Misión y Visión
 */
@Composable
private fun MisionVisionSection(mision: String, vision: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card de Misión
        InfoCard(
            titulo = "Nuestra Misión",
            contenido = mision,
            icono = Icons.Default.TrackChanges,
            iconoDescripcion = "Icono Misión",
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            titleColor = MaterialTheme.colorScheme.onPrimaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        // Card de Visión
        InfoCard(
            titulo = "Nuestra Visión",
            contenido = vision,
            icono = Icons.Default.Visibility,
            iconoDescripcion = "Icono Visión",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            titleColor = MaterialTheme.colorScheme.onSecondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

/**
 * Card reutilizable para Misión/Visión con icono
 */
@Composable
private fun InfoCard(
    titulo: String,
    contenido: String,
    icono: ImageVector,
    iconoDescripcion: String,
    backgroundColor: androidx.compose.ui.graphics.Color,
    titleColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Fila con icono y título
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = iconoDescripcion,
                    tint = titleColor,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
            }
            
            Text(
                text = contenido,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                textAlign = TextAlign.Justify,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(1.3f)
            )
        }
    }
}
