package com.example.huertohogarapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarapp.data.repository.CarritoRepository
import com.example.huertohogarapp.data.repository.ProductoRepository
import com.example.huertohogarapp.data.repository.ProductoRepositoryImpl

class ProductosViewModelFactory(
    private val productoRepository: ProductoRepository,
    private val carritoRepository: CarritoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductosViewModel(productoRepository, carritoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
