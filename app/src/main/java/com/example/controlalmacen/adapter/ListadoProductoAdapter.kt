package com.example.controlalmacen.adapter


import android.content.Context
import com.example.controlalmacen.model.Producto
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.controlalmacen.R
import android.view.ViewGroup
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import android.content.Intent
import android.view.View
import android.widget.Filter
import com.example.controlalmacen.AddEntradaActivity
import com.example.controlalmacen.AddSalidaActivity
import android.widget.Filter.FilterResults
import android.widget.Filterable
import android.widget.ImageView
import java.util.*
import com.example.controlalmacen.GlobalInfo

/**
 * Created by Ravi Tamada on 18/05/16.
 */
class ListadoProductoAdapter(

    private val context: Context,
    private var notesList: List<Producto>,
    sid: String,
    sidproducto: String,
    sproducto: String,
    scantidad: String,
    sfecha: String,
    sidtipo: String,
    stipo: String,
    activit: String


) : RecyclerView.Adapter<ListadoProductoAdapter.MyViewHolder>(), Filterable {
    private val movieListFiltered: List<Producto>
    var sid: String
    var sfecha: String
    var sidproducto: String
    var sproducto: String
    var scantidad: String
    var sidtipo: String
    var stipo: String
    var activit: String
    var Productourl: String = GlobalInfo.PRODUCTO_URL

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var producto: TextView
        var precio: TextView
        var stock: TextView
        var categoria: TextView
        var linear1: CardView
        var foto: ImageView

        init {
            foto = view.findViewById(R.id.orderedResImage)
            producto = view.findViewById(R.id.orderedResName)
            precio = view.findViewById(R.id.orderedResAddress)
            stock = view.findViewById(R.id.orderedItemsText)
            categoria = view.findViewById(R.id.orderedTimeStamp)
            linear1 = view.findViewById(R.id.card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.producto_list_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]
        holder.producto.text = "Producto: " + note.nombre
        holder.precio.text = "Precio: " + note.precio
        holder.stock.text = "Stock: " + note.stock
        holder.categoria.text = "Categoria: " + note.fkCategoria
        Glide.with(context).load(Productourl + note.foto)
            .into(holder.foto)
        holder.linear1.setOnClickListener {
            if (activit == "Entrada") {
                val intent = Intent(context, AddEntradaActivity::class.java)
                intent.putExtra("idproducto", "" + note.id)
                intent.putExtra("producto", note.nombre)
                intent.putExtra("id", sid)
                intent.putExtra("cantidad", scantidad)
                intent.putExtra("fecha", sfecha)
                intent.putExtra("idtipo", sidtipo)
                intent.putExtra("tipo", stipo)
                context.startActivity(intent)
            }
            if (activit == "Salida") {
                val intent = Intent(context, AddSalidaActivity::class.java)
                intent.putExtra("idproducto", "" + note.id)
                intent.putExtra("producto", note.nombre)
                intent.putExtra("id", sid)
                intent.putExtra("cantidad", scantidad)
                intent.putExtra("fecha", sfecha)
                intent.putExtra("idtipo", sidtipo)
                intent.putExtra("tipo", stipo)
                context.startActivity(intent)
            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                notesList = if (charString.isEmpty()) {
                    movieListFiltered
                } else {
                    val filteredList: MutableList<Producto> = ArrayList()
                    for (movie in notesList) {
                        if (movie.nombre!!.lowercase(Locale.getDefault())
                                .contains(charString.lowercase(Locale.getDefault()))
                        ) {
                            filteredList.add(movie)
                            //   Log.e(" moview tet rre"," Cliente "+movie.getNombre()+" "+movie.getNombre().toString());
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = notesList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                notesList = filterResults.values as ArrayList<Producto>
                notifyDataSetChanged()
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
        this.activit = activit
    }
}
