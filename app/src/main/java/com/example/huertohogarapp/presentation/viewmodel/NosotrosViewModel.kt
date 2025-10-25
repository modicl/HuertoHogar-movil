package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel para la pantalla de Nosotros
 * Arquitectura MVVM
 */
class NosotrosViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(NosotrosUiState())
    val uiState: StateFlow<NosotrosUiState> = _uiState.asStateFlow()
    
    init {
        loadCompanyInfo()
    }
    
    /**
     * Carga la información de la empresa
     */
    private fun loadCompanyInfo() {
        _uiState.update { currentState ->
            currentState.copy(
                companyInfo = CompanyInfo(
                    sobreNosotros = "HuertoHogar es una tienda online dedicada a llevar la frescura y calidad de los productos del campo directamente a la puerta de nuestros clientes en Chile. Con más de 6 años de experiencia, operamos en más de 9 puntos a lo largo del país, incluyendo ciudades clave como Santiago, Puerto Montt, Villarica, Nacimiento, Viña del Mar, Valparaíso, y Concepción. Nuestra misión es conectar a las familias chilenas con el campo, promoviendo un estilo de vida saludable y sostenible.",
                    mision = "Nuestra misión es proporcionar productos frescos y de calidad directamente desde el campo hasta la puerta de nuestros clientes, garantizando la frescura y el sabor en cada entrega. Nos comprometemos a fomentar una conexión más cercana entre los consumidores y los agricultores locales, apoyando prácticas agrícolas sostenibles y promoviendo una alimentación saludable en todos los hogares chilenos.",
                    vision = "Nuestra visión es ser la tienda online líder en la distribución de productos frescos y naturales en Chile, reconocida por nuestra calidad excepcional, servicio al cliente y compromiso con la sostenibilidad. Aspiramos a expandir nuestra presencia a nivel nacional e internacional, estableciendo un nuevo estándar en la distribución de productos agrícolas directos del productor al consumidor."
                ),
                isLoading = false
            )
        }
    }
}

/**
 * Información de la empresa
 */
data class CompanyInfo(
    val sobreNosotros: String = "",
    val mision: String = "",
    val vision: String = ""
)

/**
 * Estado de UI para la pantalla de Nosotros
 */
data class NosotrosUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val companyInfo: CompanyInfo = CompanyInfo()
)
