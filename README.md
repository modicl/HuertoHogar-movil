# 🌱 HuertoHogar - Aplicación Móvil Android

Aplicación móvil Android nativa para la tienda online de productos frescos del campo.

---

## 👥 Integrantes del Equipo

- **Felipe Villarroel**
- **Luciano Riveros**
- **Joaquín Reyes**
- **Cristóbal Faúndez**

---

## 📱 Funcionalidades

- **🏠 Inicio**: Pantalla de bienvenida con acceso a registro
- **🛒 Catálogo de Productos**: Listado de productos con filtros por categoría, búsqueda en tiempo real y diseño en grid
- **🛍️ Carrito de Compras**: Gestión de productos, ajuste de cantidades y resumen de compra con persistencia de datos
- **👤 Registro de Usuarios**: Formulario completo con validación, captura de foto de perfil (cámara/galería)
- **ℹ️ Nosotros**: Historia, misión y visión de la empresa
- **📧 Contacto**: Formulario de contacto e información
- **📝 Blog**: Sección de artículos y noticias

---

## 🔌 Endpoints Utilizados

### Endpoints Propios (Microservicios)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `https://hh-usuario-backend-efp2p.ondigitalocean.app/api/v1/usuarios` | Registro de usuarios |
| `GET` | `https://hh-productos-backend-xcijd.ondigitalocean.app/api/v1/productos` | Obtener lista de productos |

### Código Fuente de los Microservicios

- **Microservicio de Usuarios**: [https://github.com/modicl/usuario-backend-huertohogar](https://github.com/modicl/usuario-backend-huertohogar)
- **Microservicio de Productos**: [https://github.com/modicl/productos-backend](https://github.com/modicl/productos-backend)

---

## 🚀 Instrucciones para Ejecutar el Proyecto

### Requisitos Previos

- **Android Studio** Ladybug (2024.2.1) o superior
- **JDK 11** o superior
- **Android SDK** API 33 o superior
- Dispositivo Android o emulador con API 33+

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
   - Android Studio sincronizará automáticamente las dependencias
   - O manualmente: `File > Sync Project with Gradle Files`

4. **Ejecutar la aplicación**
   - Conecta un dispositivo Android físico o inicia un emulador
   - Click en el botón "Run" (▶️) o presiona `Shift + F10`

### Ejecutar desde Terminal

```bash
# Compilar el proyecto
./gradlew assembleDebug

# Instalar en dispositivo conectado
./gradlew installDebug
```

---

## 📦 APK Firmado y Archivo .jks

- **APK Firmado**: `app/release/app-release.apk`
- **Archivo Keystore (.jks)**: `app/huertohogar-release-key.jks`

> ⚠️ **Nota**: El archivo .jks se utiliza para firmar el APK de producción. Mantener las credenciales del keystore en un lugar seguro.

---

## 💻 Código Fuente

### Repositorios

| Componente | Repositorio |
|------------|-------------|
| **App Móvil Android** | [https://github.com/modicl/HuertoHogar-movil](https://github.com/modicl/HuertoHogar-movil) |
| **Microservicio Usuarios** | [https://github.com/modicl/usuario-backend-huertohogar](https://github.com/modicl/usuario-backend-huertohogar) |
| **Microservicio Productos** | [https://github.com/modicl/productos-backend](https://github.com/modicl/productos-backend) |

### Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Navegación**: Jetpack Navigation Compose
- **Base de Datos Local**: Room Database
- **Almacenamiento**: DataStore Preferences
- **Networking**: Retrofit + OkHttp
- **Imágenes**: Coil
- **Cámara**: CameraX

---

## 👨‍💻 Evidencia de Trabajo Colaborativo

### Commits por Integrante

Para ver los commits de cada integrante del equipo:

```bash
# Ver commits de todos los autores
git shortlog -sn --all

# Ver commits de un autor específico
git log --author="nombre" --oneline

# Ver estadísticas detalladas
git log --stat
```

### Contribuciones

Cada integrante del equipo ha contribuido en diferentes áreas del proyecto:

- **Felipe Villarroel**: Backend integration, Desarrollo de UI, testing y documentación
- **Luciano Riveros**: ViewModels y repositories
- **Joaquín Reyes**: Navegación, carrito de compras y persistencia
- **Cristóbal Faúndez**: Registro de usuarios, cámara y validaciones

---

## 📊 Estructura del Proyecto

```
app/src/main/java/com/example/huertohogarapp/
├── presentation/
│   ├── view/           # Pantallas (Composables)
│   ├── viewmodel/      # ViewModels
│   └── navigation/     # Navegación
├── data/
│   ├── model/          # Modelos de datos
│   ├── repository/     # Repositorios
│   ├── remote/         # Servicios API (Retrofit)
│   └── local/          # Room Database y DataStore
└── ui/theme/           # Tema Material 3
```

---


Este proyecto fue desarrollado como parte del curso de Desarrollo Móvil.

2025 HuertoHogar
