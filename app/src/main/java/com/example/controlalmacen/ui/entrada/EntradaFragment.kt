package com.example.controlalmacen.ui.entrada

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import com.example.controlalmacen.model.Entrada
import androidx.recyclerview.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import android.os.Bundle
import com.example.controlalmacen.R
import android.content.SharedPreferences
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import com.android.volley.VolleyError
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import android.content.Intent
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.controlalmacen.AddEntradaActivity
import com.example.controlalmacen.adapter.EntradaAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlalmacen.LoginActivity
import java.lang.Exception
import java.util.ArrayList
import com.example.controlalmacen.GlobalInfo


class EntradaFragment : Fragment() {
    private var pDialog: ProgressDialog? = null
    private val listAnimation: MutableList<Entrada> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private val rcategoria: RelativeLayout? = null
    private val rproductos: RelativeLayout? = null
    private var imagen: ImageView? = null
    private var text1: TextView? = null
    private var text2: TextView? = null
    var crol: String? = null
    var Baseurl: String = GlobalInfo.BASE_URL

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        setHasOptionsMenu(true)
        pDialog = ProgressDialog(activity)
        pDialog!!.setCancelable(false)
        val shared = activity!!.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        crol = shared.getString(rol, "")
        imagen = root.findViewById(R.id.emptyCartImg)
        text1 = root.findViewById(R.id.emptyCartText)
        text2 = root.findViewById(R.id.addItemText)
        recyclerView = root.findViewById(R.id.favoriteResRecyclerView)
        CargarData()
        return root
    }

    private fun CargarData() {
        val url = Baseurl+"ListaEntradas.php"
        showDialog()
        val stringRequest = StringRequest(url, { response ->
            try {
                hideDialog()
                val myJsonArray = JSONArray(response)
                for (i in 0 until myJsonArray.length()) {
                    val ob = Entrada()
                    ob.id = myJsonArray.getJSONObject(i).getInt("id")
                    ob.nombre = myJsonArray.getJSONObject(i).getString("nombre")
                    ob.fecha = myJsonArray.getJSONObject(i).getString("fecha")
                    ob.foto = myJsonArray.getJSONObject(i).getString("foto")
                    ob.cantidad = myJsonArray.getJSONObject(i).getString("cantidad")
                    ob.idProducto = myJsonArray.getJSONObject(i).getString("idProducto")
                    ob.idTipo = myJsonArray.getJSONObject(i).getString("idTipo")
                    ob.tipo = myJsonArray.getJSONObject(i).getString("tipo")
                    listAnimation.add(ob)
                }
                setupRecyclerView(listAnimation)
            } catch (e: Exception) {
                e.printStackTrace()
                hideDialog()
            }
        }) { hideDialog() }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    private fun showDialog() {
        if (!pDialog!!.isShowing) pDialog!!.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_status, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(activity, AddEntradaActivity::class.java)
                intent.putExtra("id", "")
                intent.putExtra("idproducto", "")
                intent.putExtra("producto", "")
                intent.putExtra("fecha", "")
                intent.putExtra("cantidad", "")
                intent.putExtra("foto", "")
                intent.putExtra("idTipo", "")
                intent.putExtra("tipo", "")
                intent.putExtra("activity", "personal")
                startActivity(intent)
                true
            }
            R.id.action_settings -> {
                 val intent = Intent(activity, LoginActivity::class.java)
                 startActivity(intent)
                 true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecyclerView(listAnimation: List<Entrada>) {
        val myadapter = EntradaAdapter(activity!!, listAnimation)
        val numItems = myadapter.itemCount
        if (numItems == 0) {
            recyclerView!!.visibility = View.GONE
            imagen!!.visibility = View.VISIBLE
            text1!!.visibility = View.VISIBLE
            text2!!.visibility = View.VISIBLE
        } else {
            recyclerView!!.layoutManager = LinearLayoutManager(activity)
            recyclerView!!.adapter = myadapter
            recyclerView!!.visibility = View.VISIBLE
            imagen!!.visibility = View.GONE
            text1!!.visibility = View.GONE
            text2!!.visibility = View.GONE
        }
    }

    companion object {
        const val MY_PREFS_NAME = "MySession"
        const val id = "idKey"
        const val nombre = "nombreKey"
        const val apellido = "apellidoKey"
        const val rol = "rolKey"
    }
}
