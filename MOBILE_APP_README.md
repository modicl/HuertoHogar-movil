# HuertoHogar App - Aplicación Móvil Android

## 📱 Descripción
Aplicación móvil Android desarrollada en Kotlin que replica la funcionalidad del proyecto web HuertoHogar React. Implementa navegación inferior (Bottom Navigation) con animaciones fluidas y mantiene la misma paleta de colores del proyecto original.

## 🎨 Paleta de Colores
La aplicación utiliza los mismos colores del proyecto React:

- **Verde Principal**: `#2E8B57` - Color primario de la marca
- **Verde Oscuro**: `#246844` - Para estados activos y hover
- **Gris Claro**: `#F7F7F7` - Fondo de la aplicación
- **Gris Texto**: `#333333` - Texto principal
- **Blanco**: `#FFFFFF` - Backgrounds y texto sobre fondos oscuros
- **Verde Claro**: `#E8F5E9` - Indicadores y fondos secundarios

## 🗂️ Estructura del Proyecto

```
app/src/main/java/com/example/huertohogarapp/
├── MainActivity.kt                          # Actividad principal
├── navigation/
│   ├── Screen.kt                           # Definición de rutas
│   ├── BottomNavigation.kt                 # Barra de navegación inferior
│   └── NavGraph.kt                         # Configuración de navegación con animaciones
├── screens/
│   ├── InicioScreen.kt                     # Pantalla de inicio (HomePage)
│   ├── ProductosScreen.kt                  # Pantalla de productos
│   ├── NosotrosScreen.kt                   # Pantalla sobre nosotros
│   ├── ContactoScreen.kt                   # Pantalla de contacto
│   └── BlogScreen.kt                       # Pantalla de blog
└── ui/theme/
    ├── Color.kt                            # Definición de colores
    ├── Theme.kt                            # Tema de la aplicación
    └── Type.kt                             # Tipografía
```

## 🚀 Características Implementadas

### ✅ Navegación
- **Bottom Navigation Bar** con 5 secciones principales:
  - 🏠 Inicio
  - 🛒 Productos
  - ℹ️ Nosotros
  - 📧 Contacto
  - 📝 Blog

### ✅ Animaciones
- Transiciones suaves entre pantallas con `slideInHorizontally` y `fadeIn`
- Animaciones de 300ms para una experiencia fluida
- Indicadores visuales en la navegación inferior

### ✅ Diseño
- Material Design 3
- Paleta de colores personalizada basada en HuertoHogar
- Iconos intuitivos para cada sección
- Responsive y adaptable

## 🔧 Dependencias

```kotlin
// Navigation
implementation("androidx.navigation:navigation-compose:2.7.5")

// Icons Extended
implementation("androidx.compose.material:material-icons-extended:1.5.4")

// Material Design 3
implementation(libs.androidx.material3)
```

## 📦 Próximos Pasos

Las siguientes funcionalidades se agregarán en futuros prompts:

1. **Pantalla de Inicio**
   - Carousel de imágenes
   - Productos destacados
   - Información de la empresa

2. **Pantalla de Productos**
   - Lista de productos con imágenes
   - Filtros y búsqueda
   - Detalle de producto
   - Carrito de compras

3. **Pantalla de Nosotros**
   - Historia de la empresa
   - Misión y visión
   - Equipo

4. **Pantalla de Contacto**
   - Formulario de contacto
   - Información de ubicación
   - Mapa

5. **Pantalla de Blog**
   - Lista de artículos
   - Detalle de artículo
   - Categorías

## 🏗️ Compilación y Ejecución

### Prerrequisitos
- Android Studio Hedgehog o superior
- JDK 11 o superior
- SDK de Android 33 o superior

### Pasos para ejecutar
1. Abrir el proyecto en Android Studio
2. Sincronizar Gradle
3. Ejecutar en un emulador o dispositivo físico

```bash
# Compilar el proyecto
./gradlew build

# Instalar en dispositivo
./gradlew installDebug
```

## 📱 Requisitos del Sistema

- **minSdk**: 33 (Android 13)
- **targetSdk**: 36
- **compileSdk**: 36

## 🎯 Arquitectura

La aplicación sigue las mejores prácticas de Android:
- **Jetpack Compose** para la UI
- **Navigation Component** para navegación
- **Material Design 3** para diseño consistente
- **Kotlin** como lenguaje principal

## 📝 Notas

- El proyecto mantiene la misma estructura de navegación que el proyecto React
- Los colores y estilos son fieles al diseño original
- Las animaciones proporcionan feedback visual al usuario
- La arquitectura permite escalabilidad para futuras funcionalidades

---

**Desarrollado con ❤️ usando Kotlin y Jetpack Compose**
