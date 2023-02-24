package com.example.controlalmacen

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.controlalmacen.model.Categoria
import androidx.recyclerview.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import android.os.Bundle
import android.view.View
import com.example.controlalmacen.R
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import com.android.volley.VolleyError
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.controlalmacen.adapter.ListadoCategoriaAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import java.lang.Exception
import java.util.ArrayList
import com.example.controlalmacen.GlobalInfo

class ListaCategoriaActivity : AppCompatActivity() {
    private var pDialog: ProgressDialog? = null
    private val listAnimation: MutableList<Categoria> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private val rcategoria: RelativeLayout? = null
    private val rproductos: RelativeLayout? = null
    private var imagen: ImageView? = null
    private var text1: TextView? = null
    private var text2: TextView? = null
    var producto: String? = null
    var precio: String? = null
    var stock: String? = null
    var idcategoria: String? = null
    var categoria: String? = null
    var Baseurl: String = GlobalInfo.BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_categoria)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        imagen = findViewById(R.id.emptyCartImg)
        text1 = findViewById(R.id.emptyCartText)
        text2 = findViewById(R.id.addItemText)
        recyclerView = findViewById(R.id.favoriteResRecyclerView)
        val param = this.intent.extras
        if (param != null) {
            producto = param.getString("producto")
            precio = param.getString("precio")
            stock = param.getString("stock")
            idcategoria = param.getString("idcategoria")
            categoria = param.getString("categoria")
        }
        CargarData(producto, precio, stock, idcategoria, categoria)
    }

    private fun CargarData(
        sproducto: String?,
        sprecio: String?,
        sstock: String?,
        sidcategoria: String?,
        scategoria: String?
    ) {
        val url = Baseurl+"ListaCategoria.php"
        showDialog()
        val stringRequest = StringRequest(url, { response ->
            try {
                hideDialog()
                val myJsonArray = JSONArray(response)
                for (i in 0 until myJsonArray.length()) {
                    val ob = Categoria()
                    ob.id = myJsonArray.getJSONObject(i).getInt("id")
                    ob.categoria = myJsonArray.getJSONObject(i).getString("categoria")
                    listAnimation.add(ob)
                }
                setupRecyclerView(
                    listAnimation,
                    sproducto,
                    sprecio,
                    sstock,
                    sidcategoria,
                    scategoria
                )
            } catch (e: Exception) {
                e.printStackTrace()
                hideDialog()
            }
        }) { hideDialog() }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun showDialog() {
        if (!pDialog!!.isShowing) pDialog!!.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
    }

    private fun setupRecyclerView(
        listAnimation: List<Categoria>,
        pproducto: String?,
        pprecio: String?,
        pstock: String?,
        pcategoria: String?,
        pidcategoria: String?
    ) {
        val myadapter = ListadoCategoriaAdapter(
            this,
            listAnimation,
            pproducto!!,
            pprecio!!,
            pstock!!,
            pcategoria!!,
            pidcategoria!!
        )
        val numItems = myadapter.itemCount
        if (numItems == 0) {
            recyclerView!!.visibility = View.GONE
            imagen!!.visibility = View.VISIBLE
            text1!!.visibility = View.VISIBLE
            text2!!.visibility = View.VISIBLE
        } else {
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            recyclerView!!.adapter = myadapter
            recyclerView!!.visibility = View.VISIBLE
            imagen!!.visibility = View.GONE
            text1!!.visibility = View.GONE
            text2!!.visibility = View.GONE
        }
    }
}
