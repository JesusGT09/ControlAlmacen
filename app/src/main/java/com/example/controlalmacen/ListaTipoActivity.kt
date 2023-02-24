package com.example.controlalmacen

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.controlalmacen.model.Tipo
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlalmacen.adapter.ListadoTipoAdapter
import java.lang.Exception
import java.util.ArrayList
import com.example.controlalmacen.GlobalInfo

class ListaTipoActivity : AppCompatActivity() {
    private var pDialog: ProgressDialog? = null
    private val listAnimation: MutableList<Tipo> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private val rcategoria: RelativeLayout? = null
    private val rproductos: RelativeLayout? = null
    private var imagen: ImageView? = null
    private var text1: TextView? = null
    private var text2: TextView? = null
    var id: String? = null
    var fecha: String? = null
    var idproducto: String? = null
    var producto: String? = null
    var cantidad: String? = null
    var idtipo: String? = null
    var tipo: String? = null
    var activit: String? = null
    var Baseurl: String = GlobalInfo.BASE_URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_tipo)
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
            id = param.getString("id")
            fecha = param.getString("fecha")
            idproducto = param.getString("idproducto")
            producto = param.getString("producto")
            cantidad = param.getString("cantidad")
            idtipo = param.getString("idtipo")
            tipo = param.getString("tipo")
            activit = param.getString("activity")
        }
        CargarData(id, idproducto, producto, cantidad, fecha, idtipo, tipo, activit)
    }

    private fun CargarData(
        sid: String?,
        sidproducto: String?,
        sproducto: String?,
        scantidad: String?,
        sfecha: String?,
        sidtipo: String?,
        stipo: String?,
        sactivit: String?
    ) {
        val url = Baseurl+"ListaTipo.php"
        showDialog()
        val stringRequest = StringRequest(url, { response ->
            try {
                hideDialog()
                val myJsonArray = JSONArray(response)
                for (i in 0 until myJsonArray.length()) {
                    val ob = Tipo()
                    ob.id = myJsonArray.getJSONObject(i).getInt("id")
                    ob.tipo = myJsonArray.getJSONObject(i).getString("tipo")
                    listAnimation.add(ob)
                }
                setupRecyclerView(
                    listAnimation,
                    sid,
                    sidproducto,
                    sproducto,
                    scantidad,
                    sfecha,
                    sidtipo,
                    stipo,
                    sactivit
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
        listAnimation: List<Tipo>,
        pid: String?,
        pidproducto: String?,
        pproducto: String?,
        pcantidad: String?,
        pfecha: String?,
        pidtipo: String?,
        ptipo: String?,
        pactivit: String?
    ) {
        val myadapter = ListadoTipoAdapter(
            this,
            listAnimation,
            pid!!,
            pidproducto!!,
            pproducto!!,
            pcantidad!!,
            pfecha!!,
            pidtipo!!,
            ptipo!!,
            pactivit!!
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
