# 🧪 Estrategia de Pruebas - HuertoHogar

Este documento describe la estrategia de pruebas para la aplicación **HuertoHogar**. El objetivo es garantizar la calidad, estabilidad y mantenibilidad del código a través de pruebas unitarias y de interfaz de usuario (UI).

## 🛠️ Herramientas y Dependencias

Para llevar a cabo las pruebas, se han configurado las siguientes librerías:

- **[Kotest](https://kotest.io/)**: Un potente y flexible framework de pruebas para Kotlin. Se utiliza como el corredor principal de pruebas unitarias (`JUnit5`) y para escribir aserciones más legibles.
- **[MockK](https://mockk.io/)**: Una librería de mocking para Kotlin que permite crear dobles de prueba (mocks, stubs) de manera sencilla e idiomática.
- **[JUnit 5](https://junit.org/junit5/)**: La plataforma estándar para pruebas en el ecosistema de la JVM. Se utiliza junto con Kotest.
- **[Compose UI Test](https://developer.android.com/jetpack/compose/testing)**: El framework oficial de Jetpack Compose para realizar pruebas de interfaz de usuario. Permite interactuar y verificar el comportamiento de los Composables.

## ✅ Tipos de Pruebas

### 1. Pruebas Unitarias (Lógica de Negocio)

- **Ubicación**: `app/src/test/java/`
- **Objetivo**: Verificar la lógica de negocio en los `ViewModels`, `Repositories` y otras clases de la capa de datos. Estas pruebas se ejecutan en la JVM local, por lo que son rápidas y no requieren un emulador.
- **Ejemplo**:
  - `CarritoViewModelTest.kt`: Prueba la lógica de agregar, eliminar y actualizar la cantidad de productos en el carrito.

### 2. Pruebas de UI (Frontend)

- **Ubicación**: `app/src/androidTest/java/`
- **Objetivo**: Verificar el comportamiento de la interfaz de usuario construida con Jetpack Compose. Estas pruebas se ejecutan en un emulador o dispositivo Android.
- **Ejemplo**:
  - `ProductosScreenTest.kt`: Simula la interacción del usuario con la pantalla de productos, como hacer clic en un botón para agregar un producto al carrito y verificar que la UI se actualiza correctamente.

## 🚀 Cómo Ejecutar las Pruebas

### Ejecutar Todas las Pruebas

Puedes ejecutar todas las pruebas (unitarias y de UI) utilizando la tarea de Gradle:

```bash
./gradlew check
```

### Ejecutar Pruebas Unitarias

Para ejecutar solo las pruebas unitarias:

```bash
./gradlew testDebugUnitTest
```

### Ejecutar Pruebas de UI

Para ejecutar solo las pruebas de UI (requiere un emulador o dispositivo conectado):

```bash
./gradlew connectedAndroidTest
```

## 🏗️ Proceso de Creación de Pruebas

A continuación, se documenta el proceso paso a paso para la creación de las pruebas clave del proyecto.

### Pruebas del `CarritoViewModel`

1.  **Identificación de la lógica a probar**: El `CarritoViewModel` gestiona el estado del carrito de compras. Las operaciones críticas son:
    - Agregar un nuevo producto.
    - Incrementar la cantidad de un producto existente.
    - Decrementar la cantidad.
    - Eliminar un producto.
    - Limpiar el carrito.

2.  **Creación del archivo de prueba**: Se crea `app/src/test/java/com/example/huertohogarapp/presentation/viewmodel/CarritoViewModelTest.kt`.

3.  **Mocks**: Se utiliza `MockK` para crear un mock del `CarritoRepository`, aislando al ViewModel de la capa de datos real.

4.  **Casos de prueba**:
    - `agregarProducto should call repository's agregarProducto`.
    - `quitarProducto should call repository's quitarProducto`.
    - `eliminarProducto should call repository's eliminarProducto`.
    - `limpiarCarrito should call repository's limpiarCarrito`.

---

### Pruebas de UI de `ProductosScreen`

1.  **Identificación de las interacciones a probar**: La pantalla de productos es clave para la experiencia del usuario. Las interacciones a verificar son:
    - Visualización de la lista de productos.
    - Búsqueda y filtrado en tiempo real.
    - Filtrado por categorías.
    - Adición de productos al carrito desde la `ProductoCard`.

2.  **Creación del archivo de prueba**: Se crea `app/src/androidTest/java/com/example/huertohogarapp/presentation/view/ProductosScreenTest.kt`.

3.  **ViewModel Mockeado**: Para aislar la UI, se utiliza un `ViewModel` mockeado con `MockK`. Esto permite controlar el estado (`ProductosUiState`) que la pantalla debe renderizar, independientemente de la lógica de negocio real.

4.  **Casos de prueba con `createComposeRule`**:
    - `la pantalla muestra los productos iniciales`: Verifica que la lista de productos se renderiza correctamente al iniciar la pantalla.
    - `al buscar por un producto, la lista se filtra`: Simula la escritura en la barra de búsqueda y verifica que se llama a la función de búsqueda en el ViewModel.
    - `al hacer clic en un filtro de categoria, la lista se filtra`: Simula un clic en un `FilterChip` y verifica que se llama a la función de filtrado.
    - `al hacer clic en el boton Agregar, se agrega el producto al carrito`: Simula un clic en el botón "Agregar" de un producto y verifica que la acción correspondiente se invoca en el ViewModel.
