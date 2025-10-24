package com.example.huertohogarapp.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val imagenPerfil: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UsuarioEntity

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (imagenPerfil != null) {
            if (other.imagenPerfil == null) return false
            if (!imagenPerfil.contentEquals(other.imagenPerfil)) return false
        } else if (other.imagenPerfil != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nombre.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (imagenPerfil?.contentHashCode() ?: 0)
        return result
    }
}
