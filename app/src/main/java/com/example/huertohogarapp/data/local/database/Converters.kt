package com.example.huertohogarapp.data.local.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromByteArray(byteArray: ByteArray?): String? {
        return byteArray?.let { String(it) }
    }

    @TypeConverter
    fun toByteArray(string: String?): ByteArray? {
        return string?.toByteArray()
    }
}
