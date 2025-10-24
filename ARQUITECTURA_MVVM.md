# 🏗️ Arquitectura MVVM - HuertoHogar App

## 📐 Estructura del Proyecto

```
app/src/main/java/com/example/huertohogarapp/
│
├── 📊 data/                          # CAPA DE DATOS
│   ├── model/                        # Modelos de datos
│   │   ├── Producto.kt              # Modelo de producto
│   │   ├── BlogPost.kt              # Modelo de artículo de blog
│   │   ├── CartItem.kt              # Modelo de item del carrito
│   │   └── ContactForm.kt           # Modelo de formulario de contacto
│   │
│   ├── repository/                   # Repositorios
│   │   ├── ProductoRepository.kt    # Repositorio de productos
│   │   └── BlogRepository.kt        # Repositorio de blog
│   │
│   └── local/                        # (Futuro) Base de datos local
│       └── dao/                      # Data Access Objects
│
├── 🎯 domain/                        # CAPA DE DOMINIO (Lógica de negocio)
│   └── usecase/                      # Casos de uso
│       └── (Futuro) GetProductosUseCase.kt
│
├── 🎨 presentation/                  # CAPA DE PRESENTACIÓN
│   ├── view/                         # Views (Composables)
│   │   ├── InicioScreen.kt          # Pantalla de inicio
│   │   ├── ProductosScreen.kt       # Pantalla de productos
│   │   ├── NosotrosScreen.kt        # Pantalla nosotros
│   │   ├── ContactoScreen.kt        # Pantalla de contacto
│   │   └── BlogScreen.kt            # Pantalla de blog
│   │
│   ├── viewmodel/                    # ViewModels
│   │   ├── InicioViewModel.kt       # ViewModel de inicio
│   │   ├── ProductosViewModel.kt    # ViewModel de productos
│   │   ├── NosotrosViewModel.kt     # ViewModel de nosotros
│   │   ├── ContactoViewModel.kt     # ViewModel de contacto
│   │   └── BlogViewModel.kt         # ViewModel de blog
│   │
│   ├── navigation/                   # Navegación
│   │   ├── Screen.kt                # Definición de rutas
│   │   ├── BottomNavigation.kt      # Componente de navegación inferior
│   │   └── NavGraph.kt              # Grafo de navegación
│   │
│   └── components/                   # Componentes reutilizables
│       └── (Futuro) ProductCard.kt, etc.
│
├── 🎭 ui/                            # TEMA Y ESTILOS
│   └── theme/
│       ├── Color.kt                 # Colores de HuertoHogar
│       ├── Theme.kt                 # Tema Material 3
│       └── Type.kt                  # Tipografía
│
└── MainActivity.kt                   # Actividad principal

```

## 🔄 Flujo de Datos MVVM

```
┌─────────────────────────────────────────────────────────────┐
│                         VIEW (UI)                           │
│  ┌─────────────────────────────────────────────────────┐   │
│  │         Composable Functions                        │   │
│  │  • InicioScreen()                                   │   │
│  │  • ProductosScreen()                                │   │
│  │  • etc...                                           │   │
│  └─────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────┲━━━━━━━━━━━━━━━━━┘
                       │                  ┃
                       │ observa          ┃ acciones del
                       │ StateFlow        ┃ usuario
                       │                  ┃
                       ▼                  ▼
┌─────────────────────────────────────────────────────────────┐
│                      VIEWMODEL                              │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  • Mantiene UI State (StateFlow)                    │   │
│  │  • Maneja lógica de presentación                    │   │
│  │  • Interactúa con Repositorios                      │   │
│  │  • Sobrevive a cambios de configuración            │   │
│  └─────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────┲━━━━━━━━━━━━━━━━━┘
                       │                  ┃
                       │ obtiene datos    ┃ devuelve datos
                       │                  ┃ (Flow)
                       ▼                  ▼
┌─────────────────────────────────────────────────────────────┐
│                     REPOSITORY                              │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  • Abstracción de fuentes de datos                  │   │
│  │  • Decide de dónde obtener datos (API, BD, Cache)  │   │
│  │  • Maneja sincronización de datos                   │   │
│  └─────────────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────┲━━━━━━━━━━━━━━━━━┘
                       │                  ┃
                       ▼                  ▼
┌─────────────────────────────────────────────────────────────┐
│                      DATA SOURCE                            │
│  ┌──────────────────────┐   ┌────────────────────────┐    │
│  │   Remote (API)       │   │   Local (Database)     │    │
│  │   • Retrofit         │   │   • Room               │    │
│  │   • REST API         │   │   • SharedPreferences  │    │
│  └──────────────────────┘   └────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

## 🎯 Responsabilidades por Capa

### 📊 DATA (Datos)
- **Models**: Estructuras de datos simples (data classes)
- **Repository**: 
  - Interface que define operaciones
  - Implementación que maneja fuentes de datos
  - Retorna Flow para datos reactivos
- **Local/Remote**: Fuentes de datos específicas

### 🎯 DOMAIN (Dominio) - Opcional/Futuro
- **Use Cases**: Lógica de negocio compleja
- Operaciones que involucran múltiples repositorios
- Transformaciones de datos complejas

### 🎨 PRESENTATION (Presentación)
- **Views**: 
  - Composables que muestran UI
  - Observan StateFlow del ViewModel
  - Envían eventos al ViewModel
  
- **ViewModels**:
  - Mantienen estado de UI (StateFlow)
  - Procesan eventos de usuario
  - Interactúan con repositorios
  - Sobreviven a rotaciones de pantalla
  
- **Navigation**: 
  - Rutas y navegación
  - Bottom Navigation Bar
  - Transiciones animadas

## 🔑 Conceptos Clave

### StateFlow
```kotlin
private val _uiState = MutableStateFlow(UiState())
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
```
- `MutableStateFlow`: Privado, solo el ViewModel puede modificarlo
- `StateFlow`: Público, las Views pueden observarlo

### Flow
```kotlin
override fun getProductos(): Flow<List<Producto>> = flow {
    emit(listaProductos)
}
```
- Stream de datos reactivo
- Soporte para operaciones asíncronas
- Integración con coroutines

### Separación de Responsabilidades
- ✅ **View**: Solo presenta datos, no contiene lógica
- ✅ **ViewModel**: Lógica de presentación, no conoce Android
- ✅ **Repository**: Maneja fuentes de datos
- ✅ **Model**: Estructuras de datos puras

## 📦 Dependencias MVVM

```kotlin
// ViewModel & Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
```

## 📝 Ejemplo de Uso

### 1. Definir el Model
```kotlin
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double
)
```

### 2. Crear el Repository
```kotlin
interface ProductoRepository {
    fun getProductos(): Flow<List<Producto>>
}

class ProductoRepositoryImpl : ProductoRepository {
    override fun getProductos(): Flow<List<Producto>> = flow {
        emit(listaDeProductos)
    }
}
```

### 3. Crear el ViewModel
```kotlin
class ProductosViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProductosUiState())
    val uiState: StateFlow<ProductosUiState> = _uiState.asStateFlow()
    
    fun loadProductos() {
        viewModelScope.launch {
            repository.getProductos().collect { productos ->
                _uiState.update { it.copy(productos = productos) }
            }
        }
    }
}
```

### 4. Crear la View
```kotlin
@Composable
fun ProductosScreen(viewModel: ProductosViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    // UI que reacciona a cambios en uiState
}
```

## ✅ Ventajas de MVVM

1. **Separación de responsabilidades**: Cada capa tiene un propósito claro
2. **Testeable**: Cada capa puede testearse independientemente
3. **Mantenible**: Cambios en una capa no afectan otras
4. **Escalable**: Fácil agregar nuevas funcionalidades
5. **Sobrevive a cambios de configuración**: ViewModels persisten
6. **Reactive**: UI se actualiza automáticamente con StateFlow

## 🚀 Estado Actual

### ✅ Implementado
- Estructura de carpetas MVVM
- ViewModels básicos para todas las pantallas
- Views (Screens) con integración de ViewModels
- Modelos de datos (Producto, BlogPost, CartItem, ContactForm)
- Repositorios con interfaces (con implementaciones mock)
- Navegación con animaciones
- Tema con colores de HuertoHogar

### 🔜 Por Implementar
- Use Cases (capa de dominio)
- Implementación real de repositorios (API/BD)
- Base de datos local (Room)
- Inyección de dependencias (Hilt/Koin)
- Contenido real de las pantallas
- Componentes reutilizables

---

**La arquitectura MVVM está lista para escalar! 🎉**
