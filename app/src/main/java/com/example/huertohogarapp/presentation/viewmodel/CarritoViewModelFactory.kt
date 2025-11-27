package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarapp.data.repository.CarritoRepository

class CarritoViewModelFactory(
    private val carritoRepository: CarritoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarritoViewModel(carritoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
