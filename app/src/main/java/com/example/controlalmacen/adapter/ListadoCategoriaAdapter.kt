package com.example.controlalmacen.adapter

import android.content.Context
import com.example.controlalmacen.model.Categoria
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.controlalmacen.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.content.Intent
import android.view.View
import android.widget.Filter
import com.example.controlalmacen.AddProductoActivity
import android.widget.Filter.FilterResults
import android.widget.Filterable
import java.util.*

class ListadoCategoriaAdapter(
    private val context: Context, private var notesList: List<Categoria>, sproducto: String,
    sprecio: String, sstock: String,
    scategoria: String, sidcategoria: String
) : RecyclerView.Adapter<ListadoCategoriaAdapter.MyViewHolder>(), Filterable {
    private val movieListFiltered: List<Categoria>
    var sproducto: String
    var sprecio: String
    var sstock: String
    var scategoria: String
    var sidcategoria: String

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
        holder.producto.text = "Categoria: " + note.categoria
        holder.linear1.setOnClickListener {
            val intent = Intent(context, AddProductoActivity::class.java)
            intent.putExtra("producto", sproducto)
            intent.putExtra("precio", sprecio)
            intent.putExtra("stock", sstock)
            intent.putExtra("idcategoria", "" + note.id)
            intent.putExtra("categoria", note.categoria)
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
                    val filteredList: MutableList<Categoria> = ArrayList()
                    for (movie in notesList) {
                        if (movie.categoria!!.lowercase(Locale.getDefault())
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
                notesList = filterResults.values as ArrayList<Categoria>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    init {
        movieListFiltered = notesList
        this.sproducto = sproducto
        this.sprecio = sprecio
        this.sstock = sstock
        this.scategoria = scategoria
        this.sidcategoria = sidcategoria
    }
}