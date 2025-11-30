package com.example.huertohogarapp.ui.theme

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import androidx.compose.ui.unit.sp

/**
 * Tests unitarios para Typography
 */
class TypeTest {

    @Test
    fun `Typography no es null`() {
        assertNotNull(Typography)
    }

    @Test
    fun `bodyLarge tiene fontSize correcto`() {
        assertEquals(16.sp, Typography.bodyLarge.fontSize)
    }

    @Test
    fun `bodyLarge tiene lineHeight correcto`() {
        assertEquals(24.sp, Typography.bodyLarge.lineHeight)
    }

    @Test
    fun `bodyLarge tiene letterSpacing correcto`() {
        assertEquals(0.5.sp, Typography.bodyLarge.letterSpacing)
    }

    @Test
    fun `Typography tiene todos los estilos definidos`() {
        assertNotNull(Typography.displayLarge)
        assertNotNull(Typography.displayMedium)
        assertNotNull(Typography.displaySmall)
        assertNotNull(Typography.headlineLarge)
        assertNotNull(Typography.headlineMedium)
        assertNotNull(Typography.headlineSmall)
        assertNotNull(Typography.titleLarge)
        assertNotNull(Typography.titleMedium)
        assertNotNull(Typography.titleSmall)
        assertNotNull(Typography.bodyLarge)
        assertNotNull(Typography.bodyMedium)
        assertNotNull(Typography.bodySmall)
        assertNotNull(Typography.labelLarge)
        assertNotNull(Typography.labelMedium)
        assertNotNull(Typography.labelSmall)
    }
}
