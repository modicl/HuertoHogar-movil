package com.example.huertohogarapp.presentation.view

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.huertohogarapp.presentation.viewmodel.RegistroViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.File

/**
 * Pantalla de Registro de Usuario (View)
 * Formulario con Material Design 3
 * Arquitectura MVVM
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel = viewModel(),
    onRegistroExitoso: () -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Launcher para seleccionar imagen de galería
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onFotoPerfilChange(it) }
        viewModel.ocultarSelectorFoto()
    }

    // Crear archivo temporal para la cámara
    val photoFile = remember {
        File(context.cacheDir, "temp_photo_${System.currentTimeMillis()}.jpg")
    }
    
    val photoUri = remember(photoFile) {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            photoFile
        )
    }

    // Launcher para tomar foto con cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.onFotoPerfilChange(photoUri)
        }
        viewModel.ocultarSelectorFoto()
    }

    // Permisos de cámara y almacenamiento
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    )

    // Diálogo de éxito
    if (uiState.mostrarDialogoExito) {
        AlertDialog(
            onDismissRequest = { },
            icon = {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    "¡Registro Exitoso!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("Tu cuenta ha sido creada correctamente.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.ocultarDialogoExito()
                        viewModel.limpiarFormulario()
                        onRegistroExitoso()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Ir al Inicio")
                }
            }
        )
    }

    // Diálogo de error
    if (uiState.mostrarDialogoError) {
        AlertDialog(
            onDismissRequest = { viewModel.ocultarDialogoError() },
            icon = {
                Icon(
                    Icons.Filled.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    "Error en el Registro",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(uiState.mensajeError)
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.ocultarDialogoError() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

    // Diálogo selector de foto
    if (uiState.mostrarSelectorFoto) {
        AlertDialog(
            onDismissRequest = { viewModel.ocultarSelectorFoto() },
            title = { Text("Seleccionar foto") },
            text = { Text("¿Cómo deseas agregar tu foto de perfil?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (permissionsState.allPermissionsGranted) {
                            cameraLauncher.launch(photoUri)
                        } else {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    }
                ) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cámara")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    }
                ) {
                    Icon(Icons.Filled.PhotoLibrary, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Galería")
                }
            }
        )
    }

    // Diálogo selector de región
    if (uiState.mostrarSelectorRegion) {
        AlertDialog(
            onDismissRequest = { viewModel.ocultarSelectorRegion() },
            title = { Text("Seleccionar Región") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    RegistroViewModel.REGIONES_CHILE.forEach { (id, nombre) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onRegionChange(id) }
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = uiState.idRegion == id,
                                onClick = { viewModel.onRegionChange(id) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = nombre,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.ocultarSelectorRegion() }) {
                    Text("Cerrar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Registro de Usuario",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Foto de perfil (solo local, no se envía al API)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.fotoPerfil != null) {
                    Image(
                        painter = rememberAsyncImagePainter(uiState.fotoPerfil),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Botón para agregar foto (opcional)
            OutlinedButton(
                onClick = { viewModel.mostrarSelectorFoto() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.AddAPhoto, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (uiState.fotoPerfil == null) "Agregar Foto (Opcional)" else "Cambiar Foto")
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Sección: Datos Personales
            Text(
                text = "Datos Personales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre *") },
                leadingIcon = {
                    Icon(Icons.Filled.Person, contentDescription = null)
                },
                isError = uiState.nombreError != null,
                supportingText = {
                    if (uiState.nombreError != null) {
                        Text(uiState.nombreError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Segundo Nombre
            OutlinedTextField(
                value = uiState.segundoNombre,
                onValueChange = { viewModel.onSegundoNombreChange(it) },
                label = { Text("Segundo Nombre") },
                leadingIcon = {
                    Icon(Icons.Filled.Person, contentDescription = null)
                },
                isError = uiState.segundoNombreError != null,
                supportingText = {
                    if (uiState.segundoNombreError != null) {
                        Text(uiState.segundoNombreError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Apellido Paterno
            OutlinedTextField(
                value = uiState.apellidoPaterno,
                onValueChange = { viewModel.onApellidoPaternoChange(it) },
                label = { Text("Apellido Paterno *") },
                leadingIcon = {
                    Icon(Icons.Filled.Person, contentDescription = null)
                },
                isError = uiState.apellidoPaternoError != null,
                supportingText = {
                    if (uiState.apellidoPaternoError != null) {
                        Text(uiState.apellidoPaternoError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo Apellido Materno
            OutlinedTextField(
                value = uiState.apellidoMaterno,
                onValueChange = { viewModel.onApellidoMaternoChange(it) },
                label = { Text("Apellido Materno *") },
                leadingIcon = {
                    Icon(Icons.Filled.Person, contentDescription = null)
                },
                isError = uiState.apellidoMaternoError != null,
                supportingText = {
                    if (uiState.apellidoMaternoError != null) {
                        Text(uiState.apellidoMaternoError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // RUT (en una fila: RUT + DV)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.rut,
                    onValueChange = { viewModel.onRutChange(it) },
                    label = { Text("RUT *") },
                    leadingIcon = {
                        Icon(Icons.Filled.Badge, contentDescription = null)
                    },
                    isError = uiState.rutError != null,
                    supportingText = {
                        if (uiState.rutError != null) {
                            Text(uiState.rutError!!)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text("12345678") }
                )

                OutlinedTextField(
                    value = uiState.digitoVerificador,
                    onValueChange = { viewModel.onDigitoVerificadorChange(it) },
                    label = { Text("DV *") },
                    isError = uiState.dvError != null,
                    supportingText = {
                        if (uiState.dvError != null) {
                            Text(uiState.dvError!!)
                        }
                    },
                    modifier = Modifier.width(80.dp),
                    singleLine = true,
                    placeholder = { Text("K") }
                )
            }

            // Campo Fecha de Nacimiento
            var showDatePicker by remember { mutableStateOf(false) }
            
            OutlinedTextField(
                value = uiState.fechaNacimiento,
                onValueChange = { },
                label = { Text("Fecha de Nacimiento *") },
                leadingIcon = {
                    Icon(Icons.Filled.CalendarToday, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Filled.EditCalendar, contentDescription = "Seleccionar fecha")
                    }
                },
                isError = uiState.fechaNacimientoError != null,
                supportingText = {
                    if (uiState.fechaNacimientoError != null) {
                        Text(uiState.fechaNacimientoError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                placeholder = { Text("YYYY-MM-DD") }
            )

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val date = java.text.SimpleDateFormat(
                                        "yyyy-MM-dd",
                                        java.util.Locale.getDefault()
                                    ).format(java.util.Date(millis))
                                    viewModel.onFechaNacimientoChange(date)
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Sección: Ubicación
            Text(
                text = "Ubicación",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Región
            val regionNombre = RegistroViewModel.REGIONES_CHILE.find { it.first == uiState.idRegion }?.second ?: ""
            OutlinedTextField(
                value = regionNombre,
                onValueChange = { },
                label = { Text("Región *") },
                leadingIcon = {
                    Icon(Icons.Filled.LocationOn, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.mostrarSelectorRegion() }) {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Seleccionar región")
                    }
                },
                isError = uiState.regionError != null,
                supportingText = {
                    if (uiState.regionError != null) {
                        Text(uiState.regionError!!)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.mostrarSelectorRegion() },
                readOnly = true
            )

            // Campo Dirección
            OutlinedTextField(
                value = uiState.direccion,
                onValueChange = { viewModel.onDireccionChange(it) },
                label = { Text("Dirección *") },
                leadingIcon = {
                    Icon(Icons.Filled.Home, contentDescription = null)
                },
                isError = uiState.direccionError != null,
                supportingText = {
                    if (uiState.direccionError != null) {
                        Text(uiState.direccionError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 2,
                placeholder = { Text("Av. Libertador 123, Santiago") }
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Sección: Contacto
            Text(
                text = "Contacto",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Correo
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { viewModel.onCorreoChange(it) },
                label = { Text("Correo Electrónico *") },
                leadingIcon = {
                    Icon(Icons.Filled.Email, contentDescription = null)
                },
                isError = uiState.correoError != null,
                supportingText = {
                    if (uiState.correoError != null) {
                        Text(uiState.correoError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                placeholder = { Text("correo@ejemplo.com") }
            )

            // Campo Teléfono
            OutlinedTextField(
                value = uiState.telefono,
                onValueChange = { viewModel.onTelefonoChange(it) },
                label = { Text("Teléfono *") },
                leadingIcon = {
                    Icon(Icons.Filled.Phone, contentDescription = null)
                },
                isError = uiState.telefonoError != null,
                supportingText = {
                    if (uiState.telefonoError != null) {
                        Text(uiState.telefonoError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                placeholder = { Text("+56987654321") }
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Sección: Seguridad
            Text(
                text = "Seguridad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "La contraseña debe tener entre 6-10 caracteres, una mayúscula y un carácter especial.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )

            // Campo Contraseña
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña *") },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                        Icon(
                            if (uiState.passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (uiState.passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                visualTransformation = if (uiState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = uiState.passwordError != null,
                supportingText = {
                    if (uiState.passwordError != null) {
                        Text(uiState.passwordError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Campo Confirmar Contraseña
            OutlinedTextField(
                value = uiState.confirmarPassword,
                onValueChange = { viewModel.onConfirmarPasswordChange(it) },
                label = { Text("Confirmar Contraseña *") },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.toggleConfirmarPasswordVisibility() }) {
                        Icon(
                            if (uiState.confirmarPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (uiState.confirmarPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                visualTransformation = if (uiState.confirmarPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                isError = uiState.confirmarPasswordError != null,
                supportingText = {
                    if (uiState.confirmarPasswordError != null) {
                        Text(uiState.confirmarPasswordError!!)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de enviar
            Button(
                onClick = { viewModel.onRegistrar() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.cargando,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (uiState.cargando) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(Icons.Filled.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Registrarse",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
