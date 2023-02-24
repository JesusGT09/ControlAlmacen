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
import com.example.controlalmacen.AddEntradaAdminActivity
import com.example.controlalmacen.AddSalidaAdminActivity
import android.widget.Filter.FilterResults
import android.widget.Filterable
import java.util.*

/**
 * Created by Ravi Tamada on 18/05/16.
 */
class ListadoTipoAdminAdapter(
    private val context: Context,
    private var notesList: List<Tipo>,
    sid: String,
    sidproducto: String,
    sproducto: String,
    scantidad: String,
    sfecha: String,
    sidtipo: String,
    stipo: String,
    sactivit: String
) : RecyclerView.Adapter<ListadoTipoAdminAdapter.MyViewHolder>(){
    private val movieListFiltered: List<Tipo>
    var sid: String
    var sfecha: String
    var sidproducto: String
    var sproducto: String
    var scantidad: String
    var sidtipo: String
    var stipo: String
    var sactivit: String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var producto: TextView
        var linear1: CardView

        init {
            producto = view.findViewById(R.id.orderedResName)
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
        holder.producto.text = "Tipo: " + note.tipo
        holder.linear1.setOnClickListener {
            if (sactivit == "Entrada") {
                val intent = Intent(context, AddEntradaAdminActivity::class.java)
                intent.putExtra("id", sid)
                intent.putExtra("idproducto", sidproducto)
                intent.putExtra("producto", sproducto)
                intent.putExtra("cantidad", scantidad)
                intent.putExtra("fecha", sfecha)
                intent.putExtra("idtipo", "" + note.id)
                intent.putExtra("tipo", note.tipo)
                context.startActivity(intent)
            }
            if (sactivit == "Salida") {
                val intent = Intent(context, AddSalidaAdminActivity::class.java)
                intent.putExtra("id", sid)
                intent.putExtra("idproducto", sidproducto)
                intent.putExtra("producto", sproducto)
                intent.putExtra("cantidad", scantidad)
                intent.putExtra("fecha", sfecha)
                intent.putExtra("idtipo", "" + note.id)
                intent.putExtra("tipo", note.tipo)
                context.startActivity(intent)
            }
        }
    }


    override fun getItemCount(): Int {
        return notesList.size
    }

    init {
        movieListFiltered = notesList
        this.sid = sid
        this.sidproducto = sidproducto
        this.sproducto = sproducto
        this.scantidad = scantidad
        this.sfecha = sfecha
        this.sidtipo = sidtipo
        this.stipo = stipo
        this.sactivit = sactivit
    }
}
