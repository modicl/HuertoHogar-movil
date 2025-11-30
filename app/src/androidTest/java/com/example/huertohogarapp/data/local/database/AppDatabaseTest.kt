package com.example.huertohogarapp.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Tests instrumentados para AppDatabase
 * Verifican la correcta creación y configuración de la base de datos
 */
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun database_isCreatedSuccessfully() {
        // Then
        assertNotNull(db)
        assertTrue(db.isOpen)
    }

    @Test
    fun usuarioDao_isNotNull() {
        // When
        val dao = db.usuarioDao()

        // Then
        assertNotNull(dao)
    }

    @Test
    fun getDatabase_returnsSameInstance() {
        // Given
        val db1 = AppDatabase.getDatabase(context)
        val db2 = AppDatabase.getDatabase(context)

        // Then
        assertSame(db1, db2)
    }

    @Test
    fun database_canBeClosedAndReopened() {
        // Given
        val originalDb = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        
        // When
        originalDb.close()
        
        // Then
        assertFalse(originalDb.isOpen)
        
        // When - create new instance
        val newDb = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        
        // Then
        assertTrue(newDb.isOpen)
        newDb.close()
    }
}
