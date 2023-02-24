package com.example.controlalmacen.adapter


import android.content.Context
import com.example.controlalmacen.model.Entrada
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
import com.example.controlalmacen.EditarEntradaActivity
import android.widget.Filter.FilterResults
import android.widget.Filterable
import android.widget.ImageView
import java.util.*
import com.example.controlalmacen.GlobalInfo



class EntradaAdapter(private val context: Context, private var notesList: List<Entrada>) :
    RecyclerView.Adapter<EntradaAdapter.MyViewHolder>(), Filterable {
    private val movieListFiltered: List<Entrada>
    var Productourl: String = GlobalInfo.PRODUCTO_URL

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var producto: TextView
        var fecha: TextView
        var cantidad: TextView
        var linear1: CardView
        var foto: ImageView

        init {
            producto = view.findViewById(R.id.orderedResName)
            cantidad = view.findViewById(R.id.orderedItemsText)
            fecha = view.findViewById(R.id.orderedResAddress)
            foto = view.findViewById(R.id.orderedResImage)
            linear1 = view.findViewById(R.id.imageContainer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.entrada_list_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]
        holder.producto.text = "Producto:" + note.nombre
        holder.fecha.text = "Fecha Entrada: " + note.fecha
        holder.cantidad.text = "Cantidad: " + note.cantidad
        Glide.with(context).load(Productourl + note.foto)
            .into(holder.foto)
        holder.linear1.setOnClickListener {
            val intent = Intent(context, EditarEntradaActivity::class.java)
            intent.putExtra("id", "" + note.id)
            intent.putExtra("idproducto", note.idProducto)
            intent.putExtra("producto", note.nombre)
            intent.putExtra("fecha", note.fecha)
            intent.putExtra("cantidad", note.cantidad)
            intent.putExtra("foto", note.foto)
            intent.putExtra("idTipo", note.idTipo)
            intent.putExtra("tipo", note.tipo)
            context.startActivity(intent)
        }
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                notesList = if (charString.isEmpty()) {
                    movieListFiltered
                } else {
                    val filteredList: MutableList<Entrada> = ArrayList()
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
                notesList = filterResults.values as ArrayList<Entrada>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    init {
        movieListFiltered = notesList
    }
}
