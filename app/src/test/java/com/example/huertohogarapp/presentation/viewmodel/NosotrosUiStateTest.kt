package com.example.huertohogarapp.presentation.viewmodel

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para NosotrosUiState y CompanyInfo
 */
class NosotrosUiStateTest {

    @Test
    fun `NosotrosUiState tiene valores por defecto correctos`() {
        val state = NosotrosUiState()
        
        assertTrue(state.isLoading)
        assertNull(state.error)
        assertNotNull(state.companyInfo)
    }

    @Test
    fun `NosotrosUiState copy funciona correctamente`() {
        val original = NosotrosUiState(isLoading = true)
        val copy = original.copy(isLoading = false, error = "Error")
        
        assertFalse(copy.isLoading)
        assertEquals("Error", copy.error)
    }

    @Test
    fun `NosotrosUiState equals funciona correctamente`() {
        val state1 = NosotrosUiState(isLoading = false)
        val state2 = NosotrosUiState(isLoading = false)
        val state3 = NosotrosUiState(isLoading = true)
        
        assertEquals(state1, state2)
        assertNotEquals(state1, state3)
    }

    @Test
    fun `NosotrosUiState hashCode es consistente`() {
        val state1 = NosotrosUiState(isLoading = false)
        val state2 = NosotrosUiState(isLoading = false)
        
        assertEquals(state1.hashCode(), state2.hashCode())
    }

    @Test
    fun `CompanyInfo tiene valores por defecto vacios`() {
        val info = CompanyInfo()
        
        assertEquals("", info.sobreNosotros)
        assertEquals("", info.mision)
        assertEquals("", info.vision)
    }

    @Test
    fun `CompanyInfo se crea con valores personalizados`() {
        val info = CompanyInfo(
            sobreNosotros = "Sobre nosotros texto",
            mision = "Nuestra misión",
            vision = "Nuestra visión"
        )
        
        assertEquals("Sobre nosotros texto", info.sobreNosotros)
        assertEquals("Nuestra misión", info.mision)
        assertEquals("Nuestra visión", info.vision)
    }

    @Test
    fun `CompanyInfo copy funciona correctamente`() {
        val original = CompanyInfo(sobreNosotros = "Original")
        val copy = original.copy(mision = "Nueva misión")
        
        assertEquals("Original", copy.sobreNosotros)
        assertEquals("Nueva misión", copy.mision)
    }

    @Test
    fun `CompanyInfo equals funciona correctamente`() {
        val info1 = CompanyInfo(sobreNosotros = "Texto")
        val info2 = CompanyInfo(sobreNosotros = "Texto")
        val info3 = CompanyInfo(sobreNosotros = "Diferente")
        
        assertEquals(info1, info2)
        assertNotEquals(info1, info3)
    }

    @Test
    fun `CompanyInfo hashCode es consistente`() {
        val info1 = CompanyInfo(sobreNosotros = "Texto")
        val info2 = CompanyInfo(sobreNosotros = "Texto")
        
        assertEquals(info1.hashCode(), info2.hashCode())
    }

    @Test
    fun `CompanyInfo toString contiene propiedades`() {
        val info = CompanyInfo(
            sobreNosotros = "Sobre",
            mision = "Misión",
            vision = "Visión"
        )
        
        val toString = info.toString()
        
        assertTrue(toString.contains("Sobre"))
        assertTrue(toString.contains("Misión"))
        assertTrue(toString.contains("Visión"))
    }

    @Test
    fun `NosotrosUiState con companyInfo personalizado`() {
        val companyInfo = CompanyInfo(
            sobreNosotros = "Info",
            mision = "Misión",
            vision = "Visión"
        )
        val state = NosotrosUiState(
            isLoading = false,
            companyInfo = companyInfo
        )
        
        assertFalse(state.isLoading)
        assertEquals("Info", state.companyInfo.sobreNosotros)
        assertEquals("Misión", state.companyInfo.mision)
        assertEquals("Visión", state.companyInfo.vision)
    }

    @Test
    fun `NosotrosUiState toString contiene propiedades`() {
        val state = NosotrosUiState(isLoading = false, error = "Error test")
        val toString = state.toString()
        
        assertTrue(toString.contains("isLoading"))
        assertTrue(toString.contains("error"))
    }
}
