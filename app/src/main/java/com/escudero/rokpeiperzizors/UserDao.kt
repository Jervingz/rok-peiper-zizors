package com.escudero.rokpeiperzizors

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): Usuario?

    @Query("SELECT * FROM usuarios ORDER BY id DESC")
    fun getAllUsers(): LiveData<List<Usuario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(usuario: Usuario)

    @Update
    suspend fun updateUser(usuario: Usuario)

    @Delete
    suspend fun deleteUser(usuario: Usuario)
}