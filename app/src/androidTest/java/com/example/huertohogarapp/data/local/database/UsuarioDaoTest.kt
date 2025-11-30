package com.example.huertohogarapp.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Tests instrumentados para UsuarioDao
 * Estos tests se ejecutan en un dispositivo/emulador Android
 */
@RunWith(AndroidJUnit4::class)
class UsuarioDaoTest {

    private lateinit var usuarioDao: UsuarioDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        usuarioDao = db.usuarioDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertUsuario_and_getById() = runBlocking {
        // Given
        val usuario = UsuarioEntity(
            nombre = "Juan",
            email = "juan@test.com",
            password = "password123"
        )

        // When
        val id = usuarioDao.insertUsuario(usuario)
        val retrieved = usuarioDao.getUsuarioById(id)

        // Then
        assertNotNull(retrieved)
        assertEquals("Juan", retrieved?.nombre)
        assertEquals("juan@test.com", retrieved?.email)
    }

    @Test
    fun insertUsuario_and_getByEmail() = runBlocking {
        // Given
        val usuario = UsuarioEntity(
            nombre = "María",
            email = "maria@test.com",
            password = "password123"
        )

        // When
        usuarioDao.insertUsuario(usuario)
        val retrieved = usuarioDao.getUsuarioByEmail("maria@test.com")

        // Then
        assertNotNull(retrieved)
        assertEquals("María", retrieved?.nombre)
    }

    @Test
    fun getUsuarioByEmail_nonExistent_returnsNull() = runBlocking {
        // When
        val retrieved = usuarioDao.getUsuarioByEmail("noexiste@test.com")

        // Then
        assertNull(retrieved)
    }

    @Test
    fun getAllUsuarios_empty_returnsEmptyList() = runBlocking {
        // When
        val usuarios = usuarioDao.getAllUsuarios().first()

        // Then
        assertTrue(usuarios.isEmpty())
    }

    @Test
    fun getAllUsuarios_withData_returnsList() = runBlocking {
        // Given
        val usuario1 = UsuarioEntity(
            nombre = "Usuario1",
            email = "usuario1@test.com",
            password = "pass1"
        )
        val usuario2 = UsuarioEntity(
            nombre = "Usuario2",
            email = "usuario2@test.com",
            password = "pass2"
        )

        // When
        usuarioDao.insertUsuario(usuario1)
        usuarioDao.insertUsuario(usuario2)
        val usuarios = usuarioDao.getAllUsuarios().first()

        // Then
        assertEquals(2, usuarios.size)
    }

    @Test
    fun updateUsuario_updatesData() = runBlocking {
        // Given
        val usuario = UsuarioEntity(
            nombre = "Original",
            email = "original@test.com",
            password = "pass"
        )
        val id = usuarioDao.insertUsuario(usuario)
        
        // When
        val usuarioActualizado = usuario.copy(id = id, nombre = "Actualizado")
        usuarioDao.updateUsuario(usuarioActualizado)
        val retrieved = usuarioDao.getUsuarioById(id)

        // Then
        assertEquals("Actualizado", retrieved?.nombre)
    }

    @Test
    fun deleteUsuario_removesFromDatabase() = runBlocking {
        // Given
        val usuario = UsuarioEntity(
            nombre = "ToDelete",
            email = "delete@test.com",
            password = "pass"
        )
        val id = usuarioDao.insertUsuario(usuario)
        val usuarioConId = usuario.copy(id = id)

        // When
        usuarioDao.deleteUsuario(usuarioConId)
        val retrieved = usuarioDao.getUsuarioById(id)

        // Then
        assertNull(retrieved)
    }

    @Test
    fun deleteAllUsuarios_clearsDatabase() = runBlocking {
        // Given
        val usuario1 = UsuarioEntity(
            nombre = "User1",
            email = "user1@test.com",
            password = "pass1"
        )
        val usuario2 = UsuarioEntity(
            nombre = "User2",
            email = "user2@test.com",
            password = "pass2"
        )
        usuarioDao.insertUsuario(usuario1)
        usuarioDao.insertUsuario(usuario2)

        // When
        usuarioDao.deleteAllUsuarios()
        val usuarios = usuarioDao.getAllUsuarios().first()

        // Then
        assertTrue(usuarios.isEmpty())
    }

    @Test
    fun insertUsuario_withSameEmail_replacesExisting() = runBlocking {
        // Given
        val usuario1 = UsuarioEntity(
            nombre = "Original",
            email = "same@test.com",
            password = "pass1"
        )
        val id1 = usuarioDao.insertUsuario(usuario1)
        
        // When - Insert with same id (REPLACE strategy)
        val usuario2 = UsuarioEntity(
            id = id1,
            nombre = "Replaced",
            email = "same@test.com",
            password = "pass2"
        )
        usuarioDao.insertUsuario(usuario2)
        val retrieved = usuarioDao.getUsuarioById(id1)

        // Then
        assertEquals("Replaced", retrieved?.nombre)
    }

    @Test
    fun getUsuarioById_nonExistent_returnsNull() = runBlocking {
        // When
        val retrieved = usuarioDao.getUsuarioById(999L)

        // Then
        assertNull(retrieved)
    }

    @Test
    fun insertUsuario_withImagenPerfil() = runBlocking {
        // Given
        val imagenData = "imagen_data".toByteArray()
        val usuario = UsuarioEntity(
            nombre = "Usuario con imagen",
            email = "imagen@test.com",
            password = "pass",
            imagenPerfil = imagenData
        )

        // When
        val id = usuarioDao.insertUsuario(usuario)
        val retrieved = usuarioDao.getUsuarioById(id)

        // Then
        assertNotNull(retrieved)
        assertArrayEquals(imagenData, retrieved?.imagenPerfil)
    }

    @Test
    fun insertUsuario_withoutImagenPerfil() = runBlocking {
        // Given
        val usuario = UsuarioEntity(
            nombre = "Usuario sin imagen",
            email = "sinimagen@test.com",
            password = "pass",
            imagenPerfil = null
        )

        // When
        val id = usuarioDao.insertUsuario(usuario)
        val retrieved = usuarioDao.getUsuarioById(id)

        // Then
        assertNotNull(retrieved)
        assertNull(retrieved?.imagenPerfil)
    }
}
