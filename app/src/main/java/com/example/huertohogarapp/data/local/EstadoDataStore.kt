package com.example.huertohogarapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.huertohogarapp.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EstadoDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "estado")
        private val ULTIMA_PAGINA = stringPreferencesKey("ultima_pagina")
        private val CARRITO_ITEMS = stringPreferencesKey("carrito_items")
    }

    // Guardar la última página visitada
    suspend fun guardarUltimaPagina(ruta: String) {
        context.dataStore.edit { preferences ->
            preferences[ULTIMA_PAGINA] = ruta
        }
    }

    // Obtener la última página visitada
    val ultimaPagina: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ULTIMA_PAGINA] ?: "inicio"
    }

    // Guardar items del carrito
    suspend fun guardarCarrito(items: List<CartItem>) {
        context.dataStore.edit { preferences ->
            preferences[CARRITO_ITEMS] = Json.encodeToString(items)
        }
    }

    // Obtener items del carrito
    val carritoItems: Flow<List<CartItem>> = context.dataStore.data.map { preferences ->
        try {
            val jsonString = preferences[CARRITO_ITEMS] ?: "[]"
            Json.decodeFromString<List<CartItem>>(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Limpiar el carrito
    suspend fun limpiarCarrito() {
        context.dataStore.edit { preferences ->
            preferences[CARRITO_ITEMS] = "[]"
        }
    }
}