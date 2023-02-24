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
import com.example.controlalmacen.EditarCategoriaActivity
import android.widget.Filter.FilterResults
import android.widget.Filterable
import java.util.*
import com.example.controlalmacen.GlobalInfo

/**
 * Created by Ravi Tamada on 18/05/16.
 */
class CategoriaAdapter(private val context: Context, private var notesList: List<Categoria>) :
    RecyclerView.Adapter<CategoriaAdapter.MyViewHolder>(), Filterable {
    private val movieListFiltered: List<Categoria>

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
            .inflate(R.layout.categoria_list_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]
        holder.producto.text = "Id: " + note.id
        holder.precio.text = "Categoria: " + note.categoria
        holder.linear1.setOnClickListener { /*
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setTitle("Mensage");
                dialogo1.setMessage("¿Deseas elimininar este registro ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();*/
            val intent = Intent(context, EditarCategoriaActivity::class.java)
            intent.putExtra("id", "" + note.id)
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
    }
}
