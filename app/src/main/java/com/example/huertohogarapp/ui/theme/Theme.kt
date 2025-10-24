package com.example.huertohogarapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VerdePrincipalDark,
    secondary = VerdeOscuro,
    tertiary = VerdeClaro,
    background = GrisOscuro,
    surface = GrisOscuro,
    onPrimary = Blanco,
    onSecondary = Blanco,
    onTertiary = GrisTexto,
    onBackground = Blanco,
    onSurface = Blanco
)

private val LightColorScheme = lightColorScheme(
    primary = VerdePrincipal,
    secondary = VerdeOscuro,
    tertiary = VerdeClaro,
    background = GrisClaro,
    surface = Blanco,
    onPrimary = Blanco,
    onSecondary = Blanco,
    onTertiary = GrisTexto,
    onBackground = GrisTexto,
    onSurface = GrisTexto
)

@Composable
fun HuertoHogarAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color deshabilitado para usar los colores de HuertoHogar
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}