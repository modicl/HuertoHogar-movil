package com.example.huertohogarapp.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Tests unitarios para NosotrosViewModel
 */
@ExperimentalCoroutinesApi
class NosotrosViewModelTest {

    private lateinit var viewModel: NosotrosViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NosotrosViewModel()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `estado inicial carga informacion de la empresa`() = runTest {
        // Then
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)
        assertNotNull(uiState.companyInfo)
    }

    @Test
    fun `companyInfo contiene sobre nosotros`() = runTest {
        // Then
        val companyInfo = viewModel.uiState.value.companyInfo
        assertTrue(companyInfo.sobreNosotros.isNotEmpty())
        assertTrue(companyInfo.sobreNosotros.contains("HuertoHogar"))
    }

    @Test
    fun `companyInfo contiene mision`() = runTest {
        // Then
        val companyInfo = viewModel.uiState.value.companyInfo
        assertTrue(companyInfo.mision.isNotEmpty())
        assertTrue(companyInfo.mision.contains("misión"))
    }

    @Test
    fun `companyInfo contiene vision`() = runTest {
        // Then
        val companyInfo = viewModel.uiState.value.companyInfo
        assertTrue(companyInfo.vision.isNotEmpty())
        assertTrue(companyInfo.vision.contains("visión"))
    }

    @Test
    fun `uiState es accesible como StateFlow`() = runTest {
        // When
        val uiState = viewModel.uiState.value

        // Then
        assertNotNull(uiState)
    }

    @Test
    fun `NosotrosUiState tiene valores por defecto correctos`() {
        // Given & When
        val uiState = NosotrosUiState()

        // Then
        assertTrue(uiState.isLoading)
        assertNull(uiState.error)
        assertEquals(CompanyInfo(), uiState.companyInfo)
    }

    @Test
    fun `NosotrosUiState copy funciona correctamente`() {
        // Given
        val uiState = NosotrosUiState()

        // When
        val uiStateCopy = uiState.copy(isLoading = false)

        // Then
        assertFalse(uiStateCopy.isLoading)
    }

    @Test
    fun `CompanyInfo tiene valores por defecto vacios`() {
        // Given & When
        val companyInfo = CompanyInfo()

        // Then
        assertEquals("", companyInfo.sobreNosotros)
        assertEquals("", companyInfo.mision)
        assertEquals("", companyInfo.vision)
    }

    @Test
    fun `CompanyInfo copy funciona correctamente`() {
        // Given
        val companyInfo = CompanyInfo(
            sobreNosotros = "Sobre nosotros",
            mision = "Misión",
            vision = "Visión"
        )

        // When
        val companyInfoCopy = companyInfo.copy(mision = "Nueva misión")

        // Then
        assertEquals("Nueva misión", companyInfoCopy.mision)
        assertEquals(companyInfo.sobreNosotros, companyInfoCopy.sobreNosotros)
        assertEquals(companyInfo.vision, companyInfoCopy.vision)
    }

    @Test
    fun `dos CompanyInfo con mismos valores son iguales`() {
        // Given
        val info1 = CompanyInfo("Sobre", "Misión", "Visión")
        val info2 = CompanyInfo("Sobre", "Misión", "Visión")

        // Then
        assertEquals(info1, info2)
    }

    @Test
    fun `informacion de empresa contiene referencia a Chile`() = runTest {
        // Then
        val companyInfo = viewModel.uiState.value.companyInfo
        assertTrue(companyInfo.sobreNosotros.contains("Chile"))
    }

    @Test
    fun `informacion de empresa menciona años de experiencia`() = runTest {
        // Then
        val companyInfo = viewModel.uiState.value.companyInfo
        assertTrue(companyInfo.sobreNosotros.contains("años"))
    }

    @Test
    fun `CompanyInfo hashCode es consistente`() {
        // Given
        val info1 = CompanyInfo("Sobre", "Misión", "Visión")
        val info2 = CompanyInfo("Sobre", "Misión", "Visión")

        // Then
        assertEquals(info1.hashCode(), info2.hashCode())
    }

    @Test
    fun `CompanyInfo toString contiene informacion relevante`() {
        // Given
        val companyInfo = CompanyInfo(
            sobreNosotros = "Sobre nosotros",
            mision = "Misión",
            vision = "Visión"
        )

        // When
        val toString = companyInfo.toString()

        // Then
        assertTrue(toString.contains("Sobre nosotros"))
        assertTrue(toString.contains("Misión"))
        assertTrue(toString.contains("Visión"))
    }

    @Test
    fun `dos CompanyInfo con diferentes valores son diferentes`() {
        // Given
        val info1 = CompanyInfo("Sobre1", "Misión", "Visión")
        val info2 = CompanyInfo("Sobre2", "Misión", "Visión")

        // Then
        assertNotEquals(info1, info2)
    }

    @Test
    fun `NosotrosUiState hashCode es consistente`() {
        // Given
        val uiState1 = NosotrosUiState(isLoading = false, error = null)
        val uiState2 = NosotrosUiState(isLoading = false, error = null)

        // Then
        assertEquals(uiState1.hashCode(), uiState2.hashCode())
    }

    @Test
    fun `NosotrosUiState toString contiene informacion relevante`() {
        // Given
        val uiState = NosotrosUiState(isLoading = true, error = "Error test")

        // When
        val toString = uiState.toString()

        // Then
        assertTrue(toString.contains("isLoading=true"))
        assertTrue(toString.contains("Error test"))
    }

    @Test
    fun `NosotrosUiState con error se crea correctamente`() {
        // Given & When
        val uiState = NosotrosUiState(isLoading = false, error = "Error de conexión")

        // Then
        assertEquals("Error de conexión", uiState.error)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `dos NosotrosUiState con mismos valores son iguales`() {
        // Given
        val companyInfo = CompanyInfo("Sobre", "Misión", "Visión")
        val uiState1 = NosotrosUiState(isLoading = false, error = null, companyInfo = companyInfo)
        val uiState2 = NosotrosUiState(isLoading = false, error = null, companyInfo = companyInfo)

        // Then
        assertEquals(uiState1, uiState2)
    }

    @Test
    fun `CompanyInfo con valores vacios se crea correctamente`() {
        // Given & When
        val companyInfo = CompanyInfo(sobreNosotros = "", mision = "", vision = "")

        // Then
        assertEquals("", companyInfo.sobreNosotros)
        assertEquals("", companyInfo.mision)
        assertEquals("", companyInfo.vision)
    }
}
