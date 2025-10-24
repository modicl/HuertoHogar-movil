# Estructura de Archivos - Nuevas Características

## 📁 Archivos Creados/Modificados

```
HuertoHogar-movil-1/
├── app/
│   ├── build.gradle.kts                          ✏️ MODIFICADO
│   │   └── + Dependencias: Coil, CameraX, Accompanist
│   │
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml               ✏️ MODIFICADO
│           │   └── + Permisos de cámara y almacenamiento
│           │   └── + FileProvider configuración
│           │
│           ├── java/com/example/huertohogarapp/
│           │   │
│           │   ├── MainActivity.kt               ✏️ MODIFICADO
│           │   │   └── + Persistencia de última página
│           │   │   └── + Restauración de estado
│           │   │
│           │   ├── data/
│           │   │   ├── model/
│           │   │   │   ├── Usuario.kt            🆕 NUEVO
│           │   │   │   ├── Producto.kt           ✅ EXISTENTE
│           │   │   │   └── CartItem.kt           ✅ EXISTENTE
│           │   │   │
│           │   │   ├── local/
│           │   │   │   └── EstadoDataStore.kt    ✅ EXISTENTE (ya tenía carrito)
│           │   │   │
│           │   │   └── repository/
│           │   │       ├── CarritoRepository.kt  🆕 NUEVO
│           │   │       ├── ProductoRepository.kt ✅ EXISTENTE
│           │   │       └── BlogRepository.kt     ✅ EXISTENTE
│           │   │
│           │   ├── presentation/
│           │   │   ├── view/
│           │   │   │   ├── RegistroScreen.kt     🆕 NUEVO
│           │   │   │   ├── CarritoScreen.kt      🆕 NUEVO
│           │   │   │   ├── ProductosScreen.kt    ✏️ MODIFICADO
│           │   │   │   ├── InicioScreen.kt       ✏️ MODIFICADO
│           │   │   │   ├── NosotrosScreen.kt     ✅ EXISTENTE
│           │   │   │   ├── ContactoScreen.kt     ✅ EXISTENTE
│           │   │   │   └── BlogScreen.kt         ✅ EXISTENTE
│           │   │   │
│           │   │   ├── viewmodel/
│           │   │   │   ├── RegistroViewModel.kt  🆕 NUEVO
│           │   │   │   ├── CarritoViewModel.kt   🆕 NUEVO
│           │   │   │   ├── ProductosViewModel.kt ✏️ MODIFICADO
│           │   │   │   ├── InicioViewModel.kt    ✅ EXISTENTE
│           │   │   │   ├── NosotrosViewModel.kt  ✅ EXISTENTE
│           │   │   │   ├── ContactoViewModel.kt  ✅ EXISTENTE
│           │   │   │   └── BlogViewModel.kt      ✅ EXISTENTE
│           │   │   │
│           │   │   └── navigation/
│           │   │       ├── NavGraph.kt           ✏️ MODIFICADO
│           │   │       ├── Screen.kt             ✏️ MODIFICADO
│           │   │       └── BottomNavigation.kt   ✅ EXISTENTE
│           │   │
│           │   └── ui/
│           │       └── theme/
│           │           ├── Color.kt              ✅ EXISTENTE
│           │           ├── Theme.kt              ✅ EXISTENTE
│           │           └── Type.kt               ✅ EXISTENTE
│           │
│           └── res/
│               └── xml/
│                   └── file_paths.xml            🆕 NUEVO
│
├── NUEVAS_CARACTERISTICAS_IMPLEMENTADAS.md       🆕 NUEVO
└── ESTRUCTURA_ARCHIVOS.md                        🆕 NUEVO
```

---

## 📊 Estadísticas de Implementación

### Archivos por Categoría:

| Categoría | Nuevos | Modificados | Total |
|-----------|--------|-------------|-------|
| **Models** | 1 | 0 | 1 |
| **Repositories** | 1 | 0 | 1 |
| **ViewModels** | 2 | 1 | 3 |
| **Views/Screens** | 2 | 2 | 4 |
| **Navigation** | 0 | 2 | 2 |
| **Configuration** | 2 | 2 | 4 |
| **Documentation** | 2 | 0 | 2 |
| **TOTAL** | **10** | **7** | **17** |

---

## 🗂️ Descripción de Archivos Nuevos

### 1. **data/model/Usuario.kt**
```kotlin
// Modelo de datos para el registro de usuario
data class Usuario(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val fechaNacimiento: String,
    val fotoPerfil: String
)
```

### 2. **data/repository/CarritoRepository.kt**
```kotlin
// Repositorio que gestiona el carrito de compras
class CarritoRepository(private val dataStore: EstadoDataStore)
// Funciones: agregarProducto, quitarProducto, eliminarProducto, 
//            limpiarCarrito, obtenerTotal, obtenerCantidadTotal
```

### 3. **presentation/viewmodel/RegistroViewModel.kt**
```kotlin
// ViewModel para la pantalla de registro
// Gestiona estado del formulario, validaciones y lógica de negocio
data class RegistroUiState(
    val nombre, apellido, correo, fechaNacimiento, fotoPerfil,
    val errores, mostrarDialogos, cargando
)
```

### 4. **presentation/viewmodel/CarritoViewModel.kt**
```kotlin
// ViewModel para el carrito de compras
// Gestiona items del carrito, cálculos de totales y compras
data class CarritoUiState(
    val total: Double,
    val cantidadTotal: Int,
    val mostrarDialogoExito: Boolean
)
```

### 5. **presentation/view/RegistroScreen.kt**
```kotlin
// Pantalla de registro con formulario Material Design 3
// Características:
// - Campos: nombre, apellido, correo, fecha, foto
// - Captura de foto con cámara o galería
// - Validaciones en tiempo real
// - Diálogos de éxito y selección de foto
```

### 6. **presentation/view/CarritoScreen.kt**
```kotlin
// Pantalla del carrito de compras
// Características:
// - Lista de productos con cantidades
// - Controles +/- para ajustar cantidades
// - Resumen de compra con totales
// - Botones de limpiar y comprar
// - Estados vacío y con items
```

### 7. **res/xml/file_paths.xml**
```xml
<!-- Configuración de FileProvider para compartir archivos -->
<paths>
    <cache-path name="camera_images" path="/" />
</paths>
```

### 8. **NUEVAS_CARACTERISTICAS_IMPLEMENTADAS.md**
```
Documentación completa de todas las características implementadas,
flujos de usuario, dependencias agregadas y arquitectura.
```

---

## 📝 Descripción de Archivos Modificados

### 1. **app/build.gradle.kts**
- ➕ Agregadas dependencias de Coil (carga de imágenes)
- ➕ Agregadas dependencias de CameraX (captura de fotos)
- ➕ Agregada dependencia de Accompanist Permissions

### 2. **AndroidManifest.xml**
- ➕ Permisos de cámara y almacenamiento
- ➕ Configuración de FileProvider

### 3. **MainActivity.kt**
- ➕ Lógica de persistencia de última página visitada
- ➕ Restauración automática de estado al abrir la app
- ➕ Integración con EstadoDataStore

### 4. **presentation/view/ProductosScreen.kt**
- 🔄 Rediseño completo de la pantalla
- ➕ Grid de productos 2 columnas
- ➕ Sistema de filtros por categoría
- ➕ Barra de búsqueda
- ➕ Integración con carrito
- ➕ Badge de carrito en TopBar

### 5. **presentation/view/InicioScreen.kt**
- ➕ Botón de navegación a Registro
- 🔄 Mejora del diseño visual

### 6. **presentation/viewmodel/ProductosViewModel.kt**
- ➕ Lista de 8 productos con datos completos
- ➕ Lógica de filtros por categoría
- ➕ Lógica de búsqueda
- ➕ Integración con CarritoRepository
- ➕ Funciones para agregar/quitar del carrito
- ➕ Estado reactivo del carrito

### 7. **presentation/navigation/NavGraph.kt**
- ➕ Ruta y composable para RegistroScreen
- ➕ Ruta y composable para CarritoScreen
- 🔄 Actualización de rutas existentes con callbacks
- ➕ Animaciones personalizadas para nuevas pantallas

### 8. **presentation/navigation/Screen.kt**
- ➕ Screen.Registro
- ➕ Screen.Carrito
- 📋 Definición de nuevas rutas

---

## 🎯 Patrones de Arquitectura Utilizados

### MVVM (Model-View-ViewModel)
```
View (Composable)
    ↕️
ViewModel (Estado + Lógica)
    ↕️
Repository (Capa de datos)
    ↕️
DataStore/API (Persistencia)
```

### Flujo de Datos Unidireccional
```
User Action → View → ViewModel → Repository → DataStore
                ↑                                   ↓
                ← ← ← ← ← ← ← Flow/State ← ← ← ← ← ←
```

### State Management
```kotlin
// Ejemplo de patrón State
data class UiState(
    val data: List<Item>,
    val isLoading: Boolean,
    val error: String?
)

val uiState: StateFlow<UiState>
```

---

## 🔗 Dependencias entre Componentes

```
MainActivity
    └── NavGraph
        ├── InicioScreen → InicioViewModel
        ├── ProductosScreen → ProductosViewModel → CarritoRepository
        ├── CarritoScreen → CarritoViewModel → CarritoRepository
        ├── RegistroScreen → RegistroViewModel
        ├── NosotrosScreen → NosotrosViewModel
        ├── ContactoScreen → ContactoViewModel
        └── BlogScreen → BlogViewModel

CarritoRepository
    └── EstadoDataStore
        └── DataStore Preferences
            ├── carritoItems (Flow<List<CartItem>>)
            └── ultimaPagina (Flow<String>)
```

---

## 🌊 Flujo de Datos del Carrito

```
ProductosScreen
    ↓ [Usuario agrega producto]
ProductosViewModel
    ↓ [agregarAlCarrito()]
CarritoRepository
    ↓ [agregarProducto()]
EstadoDataStore
    ↓ [guardarCarrito()]
DataStore Preferences
    ↓ [JSON serializado]
Archivo local persistente
    ↓ [Al abrir app]
Flow reactivo
    ↓ [collect()]
CarritoScreen (actualización automática)
```

---

## 🎨 Componentes Material Design 3 Utilizados

### Formulario de Registro:
- `OutlinedTextField` - Campos de entrada
- `DatePicker` - Selector de fecha
- `AlertDialog` - Diálogos
- `Button` / `OutlinedButton` - Botones
- `Icon` - Iconos
- `CircularProgressIndicator` - Carga

### Pantalla de Productos:
- `LazyVerticalGrid` - Grid de productos
- `LazyRow` - Lista horizontal de filtros
- `FilterChip` - Chips de filtro
- `Card` - Tarjetas de producto
- `Badge` - Indicador de carrito
- `Snackbar` - Mensajes

### Pantalla de Carrito:
- `LazyColumn` - Lista de items
- `Badge` - Indicadores
- `Divider` - Separadores
- `Surface` - Contenedores
- `IconButton` - Botones de acción

---

**Arquitectura:** Clean Architecture + MVVM
**UI Framework:** Jetpack Compose
**Design System:** Material Design 3
**Storage:** DataStore Preferences
**State Management:** StateFlow + Coroutines
