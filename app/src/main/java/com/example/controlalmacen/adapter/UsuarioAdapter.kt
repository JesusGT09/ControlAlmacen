package com.example.controlalmacen.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.controlalmacen.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Intent
import android.view.View
import android.widget.Filter
import android.widget.Filter.FilterResults
import android.widget.Filterable
import com.example.controlalmacen.EditarUsuarioActivity
import com.example.controlalmacen.model.Usuario
import java.util.*


class UsuarioAdapter(private val context: Context, private var notesList: List<Usuario>) :
    RecyclerView.Adapter<UsuarioAdapter.MyViewHolder>() {
    private val movieListFiltered: List<Usuario>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var producto: TextView
        var precio: TextView
        var linear1: CardView

        init {
            producto = view.findViewById(R.id.orderedResName)
            precio = view.findViewById(R.id.orderedResAddress)
            linear1 = view.findViewById(R.id.card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.usuario_list_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]
        holder.producto.text = "Id: " + note.id
        holder.precio.text = "Nombre: " + note.nombre + " " + note.apellido
        holder.linear1.setOnClickListener {
            val intent = Intent(context, EditarUsuarioActivity::class.java)
            intent.putExtra("id", "" + note.id)
            intent.putExtra("identificacion", note.identificacion)
            intent.putExtra("nombre", note.nombre)
            intent.putExtra("apellido", note.apellido)
            intent.putExtra("correo", note.correo)
            intent.putExtra("password", note.password)
            intent.putExtra("telefono", note.telefono)
            intent.putExtra("rol", note.rol)
            context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int {
        return notesList.size
    }

    init {
        movieListFiltered = notesList
    }
}
