package com.example.controlalmacen.adapter


import android.content.Context
import com.example.controlalmacen.model.Tipo
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.controlalmacen.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Intent
import android.view.View
import android.widget.Filter
import com.example.controlalmacen.EditarTipoActivity
import android.widget.Filter.FilterResults
import android.widget.Filterable
import java.util.*

/**
 * Created by Ravi Tamada on 18/05/16.
 */
class TipoAdapter(private val context: Context, private var notesList: List<Tipo>) :
    RecyclerView.Adapter<TipoAdapter.MyViewHolder>() {
    private val movieListFiltered: List<Tipo>

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
            .inflate(R.layout.tipo_list_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]
        holder.producto.text = "Id: " + note.id
        holder.precio.text = "Tipo: " + note.tipo
        holder.linear1.setOnClickListener {
            val intent = Intent(context, EditarTipoActivity::class.java)
            intent.putExtra("id", "" + note.id)
            intent.putExtra("tipo", note.tipo)
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