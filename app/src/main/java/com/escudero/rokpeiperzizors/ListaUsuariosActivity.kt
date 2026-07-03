package com.escudero.rokpeiperzizors

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaUsuariosActivity : AppCompatActivity() {

    private lateinit var viewModel: UsuarioViewModel
    private lateinit var adapter: UsuarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        val recycler = findViewById<RecyclerView>(R.id.recyclerUsuarios)

        viewModel = ViewModelProvider(this)[UsuarioViewModel::class.java]

        adapter = UsuarioAdapter(
            onClick = { usuario ->
                val intent = Intent(this, RegistroActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            },
            onDelete = { usuario ->
                viewModel.delete(usuario)
                Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
            }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        viewModel.listaUsuarios.observe(this) { lista ->
            adapter.setData(lista)
        }
    }
}