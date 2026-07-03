package com.escudero.rokpeiperzizors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).userDao()

    val listaUsuarios: LiveData<List<Usuario>> = dao.getAllUsers()

    fun insert(usuario: Usuario) = viewModelScope.launch {
        dao.insertUser(usuario)
    }

    fun update(usuario: Usuario) = viewModelScope.launch {
        dao.updateUser(usuario)
    }

    fun delete(usuario: Usuario) = viewModelScope.launch {
        dao.deleteUser(usuario)
    }
}