package com.escudero.rokpeiperzizors

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    // Insertar usuario
    @Insert
    suspend fun insertUser(usuario: Usuario)

    // Buscar usuario por correo y contraseña (LOGIN)
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND password = :password")
    suspend fun login(correo: String, password: String): Usuario?

    // ✅ AGREGAR ESTE MÉTODO para verificar si el correo ya existe
    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun getUserByEmail(correo: String): Usuario?
}