package com.escudero.rokpeiperzizors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(
    private val onClick: (Usuario) -> Unit,
    private val onDelete: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    private var lista = listOf<Usuario>()

    fun setData(nuevaLista: List<Usuario>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val email: TextView = view.findViewById(R.id.tvCorreo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = lista[position]

        holder.nombre.text = usuario.nombre
        holder.email.text = usuario.email

        holder.itemView.setOnClickListener {
            onClick(usuario)
        }

        holder.itemView.setOnLongClickListener {
            onDelete(usuario)
            true
        }
    }
}