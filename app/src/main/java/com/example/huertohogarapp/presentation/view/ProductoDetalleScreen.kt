package com.example.huertohogarapp.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import coil.request.ImageRequest
import com.example.huertohogarapp.data.model.Producto
import com.example.huertohogarapp.presentation.viewmodel.ProductosViewModel

/**
 * Pantalla de detalles del producto
 * Muestra información completa del producto seleccionado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetalleScreen(
    productoId: Int,
    viewModel: ProductosViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToCarrito: () -> Unit = {}
) {
    val producto by viewModel.getProductoById(productoId).collectAsState(initial = null)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(producto) {
        if (producto == null) {
            viewModel.cargarProductos()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        producto?.nombreProducto ?: "Detalles del Producto",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (producto == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ProductoDetalleContent(
                producto = producto!!,
                viewModel = viewModel,
                modifier = Modifier.padding(paddingValues),
                snackbarHostState = snackbarHostState,
                onNavigateToCarrito = onNavigateToCarrito
            )
        }
    }
}

@Composable
private fun ProductoDetalleContent(
    producto: Producto,
    viewModel: ProductosViewModel,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onNavigateToCarrito: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Imagen del producto
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(producto.imagenUrl)
                .crossfade(true)
                .build(),
            contentDescription = producto.nombreProducto,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        // Información del producto
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Nombre del producto
            Text(
                text = producto.nombreProducto,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Categoría
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = producto.categoria.nombreCategoria,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Precio
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Precio: ",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "$${String.format("%,.0f", producto.precioProducto)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Stock
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stock disponible: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${producto.stockProducto} unidades",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = if (producto.stockProducto > 0) 
                        MaterialTheme.colorScheme.tertiary 
                    else 
                        MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // País de origen
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "País de origen: ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = producto.paisOrigen.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Descripción
            Text(
                text = "Descripción",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = producto.descripcionProducto,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón agregar al carrito
            Button(
                onClick = {
                    viewModel.agregarAlCarrito(producto)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("${producto.nombreProducto} agregado al carrito")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = producto.stockProducto > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Agregar al carrito",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (producto.stockProducto > 0) 
                        "Agregar al Carrito" 
                    else 
                        "Sin Stock",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón ir al carrito
            OutlinedButton(
                onClick = onNavigateToCarrito,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Ver Carrito",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
