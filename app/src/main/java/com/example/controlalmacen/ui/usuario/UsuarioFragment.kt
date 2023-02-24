package com.example.controlalmacen.ui.usuario

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.recyclerview.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import android.os.Bundle
import com.example.controlalmacen.R
import android.content.Intent
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.controlalmacen.AddUsuarioActivity
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import com.android.volley.VolleyError
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.controlalmacen.adapter.UsuarioAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlalmacen.LoginActivity
import com.example.controlalmacen.model.Usuario
import java.lang.Exception
import java.util.ArrayList
import com.example.controlalmacen.GlobalInfo


class UsuarioFragment : Fragment() {
    private var pDialog: ProgressDialog? = null
    private val listAnimation: MutableList<Usuario> = ArrayList()
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
        val root = inflater.inflate(R.layout.fragment_usuarios, container, false)
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
                val intent = Intent(activity, AddUsuarioActivity::class.java)
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
        val url = Baseurl+"ListaUsuarios.php"
        showDialog()
        val stringRequest = StringRequest(url, { response ->
            try {
                hideDialog()
                val myJsonArray = JSONArray(response)
                for (i in 0 until myJsonArray.length()) {
                    val ob = Usuario()
                    ob.id = myJsonArray.getJSONObject(i).getInt("id")
                    ob.identificacion = myJsonArray.getJSONObject(i).getString("identificacion")
                    ob.nombre = myJsonArray.getJSONObject(i).getString("nombre")
                    ob.apellido = myJsonArray.getJSONObject(i).getString("apellido")
                    ob.correo = myJsonArray.getJSONObject(i).getString("correo")
                    ob.password = myJsonArray.getJSONObject(i).getString("password")
                    ob.telefono = myJsonArray.getJSONObject(i).getString("telefono")
                    ob.rol = myJsonArray.getJSONObject(i).getString("rol")
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
    private fun setupRecyclerView(listAnimation: List<Usuario>) {
        val myadapter = UsuarioAdapter(activity!!, listAnimation)
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
