package com.example.controlalmacen.ui.categoria

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.controlalmacen.AddCategoriaActivity
import com.example.controlalmacen.GlobalInfo
import com.example.controlalmacen.LoginActivity
import com.example.controlalmacen.R
import com.example.controlalmacen.adapter.CategoriaAdapter
import com.example.controlalmacen.model.Categoria
import org.json.JSONArray


class CategoriaFragment : Fragment() {
    private var pDialog: ProgressDialog? = null
    private val listAnimation: MutableList<Categoria> = ArrayList()
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
        val root = inflater.inflate(R.layout.fragment_categoria, container, false)
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
                val intent = Intent(activity, AddCategoriaActivity::class.java)
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
    private fun setupRecyclerView(listAnimation: List<Categoria>) {
        val myadapter = CategoriaAdapter(activity!!, listAnimation)
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
