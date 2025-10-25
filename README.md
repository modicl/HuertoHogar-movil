# 🌱 HuertoHogar - Aplicación Móvil Android

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material Design](https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=material-design&logoColor=white)

**Tienda online de productos frescos del campo directamente a tu puerta** 🥬🍎🥕

[Características](#-características) • [Tecnologías](#-tecnologías-y-herramientas) • [Arquitectura](#-arquitectura) • [Instalación](#-instalación) • [Estructura](#-estructura-del-proyecto)

</div>

---

## 📖 Descripción

**HuertoHogar** es una aplicación móvil Android nativa que permite a los usuarios explorar y comprar productos frescos y orgánicos del campo chileno. Con más de 6 años de experiencia, la empresa opera en más de 9 puntos a lo largo de Chile, conectando a las familias con productos de calidad excepcional.

La aplicación ofrece una experiencia de compra fluida y moderna, con navegación intuitiva, gestión de carrito, registro de usuarios con captura de fotos, y un diseño elegante basado en Material Design 3.

---

## ✨ Características

### 🏪 Funcionalidades Principales

- **🏠 Inicio**: Pantalla de bienvenida con acceso rápido a registro y productos destacados
- **🛒 Catálogo de Productos**: 
  - Listado completo de productos frescos
  - Sistema de filtros por categoría (Frutas, Verduras, Lácteos, Granos)
  - Búsqueda en tiempo real
  - Diseño en grid responsivo
  - Agregar productos al carrito con cantidades personalizables
  
- **🛍️ Carrito de Compras**:
  - Gestión completa de productos
  - Ajuste de cantidades (+/-)
  - Resumen de compra con totales
  - Persistencia de datos con DataStore
  - Opción de limpiar carrito
  
- **👤 Registro de Usuarios**:
  - Formulario completo con validación
  - Captura de foto con cámara o galería
  - Almacenamiento en base de datos local (Room)
  - Validación en tiempo real de campos
  
- **ℹ️ Nosotros**: 
  - Historia de la empresa
  - Misión y visión con iconos Material Design
  - Diseño elegante con Cards y elevación
  
- **📧 Contacto**: Información de contacto y formulario
  
- **📝 Blog**: Sección de artículos y noticias

### 🎨 Diseño y UX

- ✅ Material Design 3 completo
- ✅ Paleta de colores corporativa verde (#2E8B57)
- ✅ Navegación inferior (Bottom Navigation) con 5 secciones
- ✅ Animaciones fluidas entre pantallas (300ms)
- ✅ Temas claro y oscuro
- ✅ Diseño responsive y adaptativo
- ✅ Iconos Material Icons Extended

---

## 🛠️ Tecnologías y Herramientas

### Lenguajes de Programación
- **Kotlin** 1.9.10 - Lenguaje principal
- **Kotlin DSL** - Build scripts

### Frameworks y Librerías

#### Android Core
- **Jetpack Compose** - UI moderna declarativa
- **Material Design 3** - Sistema de diseño
- **Android SDK** - API 33-36

#### Arquitectura
- **MVVM** (Model-View-ViewModel) - Patrón arquitectónico
- **Jetpack Navigation** 2.7.5 - Navegación entre pantallas
- **Lifecycle & ViewModel** - Gestión de ciclo de vida
- **StateFlow & Flow** - Programación reactiva
- **Kotlin Coroutines** 1.7.3 - Programación asíncrona

#### Almacenamiento de Datos
- **Room Database** 2.6.0 - Base de datos local SQLite
- **DataStore Preferences** 1.0.0 - Almacenamiento clave-valor
- **Kotlinx Serialization** 1.6.0 - Serialización JSON

#### UI y Multimedia
- **Coil** 2.5.0 - Carga de imágenes
- **CameraX** 1.3.1 - Captura de fotos
- **Accompanist Permissions** 0.32.0 - Gestión de permisos
- **Material Icons Extended** 1.5.4 - Iconos Material

### IDEs y Herramientas de Desarrollo

- **Android Studio** Ladybug | 2024.2.1 - IDE principal
- **Visual Studio Code** - Edición de documentación y recursos React
- **Gradle** 8.9 - Sistema de compilación
- **Git** - Control de versiones

### Testing
- **JUnit** - Testing unitario
- **Espresso** - Testing de UI
- **Compose UI Test** - Testing de Composables

---

## 🏗️ Arquitectura

### Patrón MVVM (Model-View-ViewModel)

```
┌─────────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────┐         ┌──────────────────────┐        │
│  │   VIEWS      │  ◄────  │   VIEWMODELS         │        │
│  │  (Screens)   │         │  • InicioViewModel   │        │
│  │              │         │  • ProductosViewModel│        │
│  │  Composables │         │  • CarritoViewModel  │        │
│  │              │         │  • RegistroViewModel │        │
│  └──────────────┘         └──────────────────────┘        │
│                                     ▲                       │
│                                     │                       │
└─────────────────────────────────────┼───────────────────────┘
                                      │
┌─────────────────────────────────────┼───────────────────────┐
│                     DATA LAYER      │                       │
├─────────────────────────────────────┼───────────────────────┤
│                                     │                       │
│  ┌──────────────────────────────────▼─────────────────┐    │
│  │            REPOSITORIES                            │    │
│  │  • CarritoRepository                               │    │
│  │  • ProductoRepository                              │    │
│  │  • UsuarioRepository                               │    │
│  └────────────────────┬──────────────────────┬────────┘    │
│                       │                      │              │
│          ┌────────────▼─────────┐  ┌────────▼──────────┐   │
│          │   DataStore          │  │   Room Database   │   │
│          │   (Preferences)      │  │   (SQLite)        │   │
│          └──────────────────────┘  └───────────────────┘   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Capas

- **Presentation**: Views (Composables) y ViewModels
- **Data**: Repositorios, modelos y fuentes de datos
- **Navigation**: Sistema de navegación con rutas definidas

---

## 📱 Pantallas Principales

| Pantalla | Descripción | Características |
|----------|-------------|-----------------|
| 🏠 **Inicio** | Bienvenida | Botón de registro, diseño limpio |
| 🛒 **Productos** | Catálogo | Grid 2 columnas, filtros, búsqueda, badge carrito |
| 🛍️ **Carrito** | Compras | Lista items, ajuste cantidades, resumen |
| 👤 **Registro** | Usuarios | Formulario, captura foto, validación |
| ℹ️ **Nosotros** | Empresa | Misión, visión, historia con Cards |
| 📧 **Contacto** | Comunicación | Formulario contacto |
| 📝 **Blog** | Contenido | Artículos y noticias |

---

## 📂 Estructura del Proyecto

```
app/src/main/java/com/example/huertohogarapp/
│
├── 📱 presentation/              # Capa de Presentación
│   ├── view/                    # Pantallas (Composables)
│   │   ├── InicioScreen.kt
│   │   ├── ProductosScreen.kt
│   │   ├── CarritoScreen.kt
│   │   ├── RegistroScreen.kt
│   │   ├── NosotrosScreen.kt
│   │   ├── ContactoScreen.kt
│   │   └── BlogScreen.kt
│   │
│   ├── viewmodel/               # ViewModels
│   │   ├── InicioViewModel.kt
│   │   ├── ProductosViewModel.kt
│   │   ├── CarritoViewModel.kt
│   │   ├── RegistroViewModel.kt
│   │   ├── NosotrosViewModel.kt
│   │   ├── ContactoViewModel.kt
│   │   └── BlogViewModel.kt
│   │
│   └── navigation/              # Navegación
│       ├── Screen.kt            # Rutas
│       ├── BottomNavigation.kt  # Barra inferior
│       └── NavGraph.kt          # Grafo navegación
│
├── 💾 data/                     # Capa de Datos
│   ├── model/                   # Modelos de datos
│   │   ├── Producto.kt
│   │   ├── CartItem.kt
│   │   └── Usuario.kt
│   │
│   ├── repository/              # Repositorios
│   │   ├── CarritoRepository.kt
│   │   ├── ProductoRepository.kt
│   │   └── UsuarioRepository.kt
│   │
│   └── local/                   # Almacenamiento local
│       ├── EstadoDataStore.kt   # DataStore
│       └── database/            # Room Database
│           ├── AppDatabase.kt
│           └── UsuarioDao.kt
│
├── 🎨 ui/theme/                 # Tema y Estilos
│   ├── Color.kt                 # Colores corporativos
│   ├── Theme.kt                 # Tema Material 3
│   └── Type.kt                  # Tipografía
│
└── MainActivity.kt              # Actividad principal
```

---

## 🚀 Instalación

### Requisitos Previos

- **Android Studio** Ladybug o superior
- **JDK 11** o superior
- **Android SDK** API 33 o superior
- **Gradle 8.9** (incluido en el proyecto)

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/modicl/HuertoHogar-movil.git
   cd HuertoHogar-movil
   ```

2. **Abrir en Android Studio**
   - Abre Android Studio
   - Selecciona "Open an existing project"
   - Navega a la carpeta del proyecto y ábrelo

3. **Sincronizar Gradle**
   - Android Studio sincronizará automáticamente
   - O manualmente: `File > Sync Project with Gradle Files`

4. **Ejecutar la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Click en el botón "Run" (▶️) o presiona `Shift + F10`

### Configuración de Emulador

Se recomienda:
- **API Level**: 33 (Android 13) o superior
- **Dispositivo**: Pixel 6 o similar
- **RAM**: 2GB mínimo

---

## 🎨 Paleta de Colores

```kotlin
// Colores corporativos HuertoHogar
Verde Principal: #2E8B57   // Verde principal
Verde Oscuro:    #246844   // Hover/Activo
Verde Claro:     #E8F5E9   // Fondos suaves
Gris Claro:      #F7F7F7   // Fondo
Gris Texto:      #333333   // Texto principal
```

---

## 📊 Estado del Proyecto

### ✅ Completado
- [x] Arquitectura MVVM completa
- [x] Sistema de navegación con animaciones
- [x] Pantalla de Productos con filtros y búsqueda
- [x] Carrito de compras funcional con persistencia
- [x] Registro de usuarios con captura de fotos
- [x] Base de datos Room configurada
- [x] DataStore para preferencias
- [x] Pantalla Nosotros con diseño Material Design 3
- [x] Tema Material Design 3 personalizado

### 🔜 Por Implementar
- [ ] Integración con API backend
- [ ] Sistema de autenticación
- [ ] Pantalla de Contacto completa
- [ ] Pantalla de Blog con artículos
- [ ] Sistema de pagos
- [ ] Notificaciones push
- [ ] Sincronización offline

---

## 👥 Contribución

Este proyecto es parte del ecosistema HuertoHogar que incluye:
- 📱 Aplicación Móvil Android (este repositorio)
- 🌐 Aplicación Web React (en `/huertohogar-react`)

---

## 📄 Licencia

Este proyecto es privado y pertenece a HuertoHogar.

---

## 📞 Contacto

**HuertoHogar**  
- 🌐 Web: www.huertohogar.cl *(ejemplo)*
- 📧 Email: contacto@huertohogar.cl *(ejemplo)*
- 📍 Ubicaciones: Santiago, Puerto Montt, Villarica, Nacimiento, Viña del Mar, Valparaíso, Concepción

---

<div align="center">

**Hecho con ❤️ y 🥬 por el equipo de HuertoHogar**

⭐ Si te gusta este proyecto, dale una estrella!

</div>
