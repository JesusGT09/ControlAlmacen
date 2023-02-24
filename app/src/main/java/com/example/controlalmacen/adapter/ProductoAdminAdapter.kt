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
import com.example.controlalmacen.EditarProductActivity
import android.widget.Filter.FilterResults
import android.widget.Filterable
import android.widget.ImageView
import java.util.*
import com.example.controlalmacen.GlobalInfo


/**
 * Created by Ravi Tamada on 18/05/16.
 */
class ProductoAdminAdapter(private val context: Context, private var notesList: List<Producto>) :
    RecyclerView.Adapter<ProductoAdminAdapter.MyViewHolder>() {
    private val movieListFiltered: List<Producto>
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
            linear1 = view.findViewById(R.id.imageContainer)
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
        holder.categoria.text = "Categoria: " + note.categoria
        Glide.with(context).load(Productourl + note.foto)
            .into(holder.foto)
        holder.linear1.setOnClickListener {
            val intent = Intent(context, EditarProductActivity::class.java)
            intent.putExtra("id", "" + note.id)
            intent.putExtra("nombre", note.nombre)
            intent.putExtra("precio", note.precio)
            intent.putExtra("stock", note.stock)
            intent.putExtra("descripcion", note.descripcion)
            intent.putExtra("estado", note.estado)
            intent.putExtra("categoria", note.categoria)
            intent.putExtra("fkcategoria", note.fkCategoria)
            intent.putExtra("foto", note.foto)
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
