package com.example.huertohogarapp.data.repository

import android.content.Context
import com.example.huertohogarapp.data.local.database.AppDatabase
import com.example.huertohogarapp.data.local.database.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioRepository(context: Context) {
    private val usuarioDao = AppDatabase.getDatabase(context).usuarioDao()

    suspend fun insertarUsuario(usuario: UsuarioEntity): Long = withContext(Dispatchers.IO) {
        usuarioDao.insertUsuario(usuario)
    }
}
