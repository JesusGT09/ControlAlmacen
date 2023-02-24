package com.example.controlalmacen.ui.tipo

import android.annotation.SuppressLint
import android.app.ProgressDialog
import com.example.controlalmacen.model.Tipo
import androidx.recyclerview.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import android.os.Bundle
import com.example.controlalmacen.R
import android.content.Intent
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.controlalmacen.AddTipoActivity
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import com.android.volley.VolleyError
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.controlalmacen.adapter.TipoAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlalmacen.LoginActivity
import java.lang.Exception
import java.util.ArrayList
import com.example.controlalmacen.GlobalInfo


class TipoFragment : Fragment() {
    private var pDialog: ProgressDialog? = null
    private val listAnimation: MutableList<Tipo> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private val rcategoria: RelativeLayout? = null
    private val rproductos: RelativeLayout? = null
    private var imagen: ImageView? = null
    private var text1: TextView? = null
    private var text2: TextView? = null
    var Baseurl: String = GlobalInfo.BASE_URL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tipo, container, false)
        setHasOptionsMenu(true)
        pDialog = ProgressDialog(activity)
        pDialog!!.setCancelable(false)
        imagen = root.findViewById(R.id.emptyCartImg)
        text1 = root.findViewById(R.id.emptyCartText)
        text2 = root.findViewById(R.id.addItemText)
        recyclerView = root.findViewById(R.id.favoriteResRecyclerView)
        CargarData()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_status, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(activity, AddTipoActivity::class.java)
                intent.putExtra("activity", "Admin")
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

    private fun CargarData() {
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

    @SuppressLint("UseRequireInsteadOfGet")
    private fun setupRecyclerView(listAnimation: List<Tipo>) {
        val myadapter = TipoAdapter(activity!!, listAnimation)
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
}
