package com.example.controlalmacen

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.StringRequest
import android.widget.Toast
import android.content.Intent
import android.view.*
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import java.util.HashMap
import com.example.controlalmacen.GlobalInfo

class EditarCategoriaActivity : AppCompatActivity() {
    var bid: String? = null
    var bcategoria: String? = null
    private var idcategoria: EditText? = null
    private var categoria: EditText? = null
    private var submit: Button? = null
    var pDialog: ProgressDialog? = null
    var Baseurl: String = GlobalInfo.BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_categoria)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        idcategoria = findViewById<View>(R.id.idcategoria) as EditText
        categoria = findViewById<View>(R.id.categoria) as EditText
        val param = this.intent.extras
        if (param != null) {
            bid = param.getString("id").toString()
            bcategoria = param.getString("categoria").toString()
            idcategoria!!.setText("" + bid)
            categoria!!.setText("" + bcategoria)
        }
        submit = findViewById<View>(R.id.submit) as Button
        submit!!.setOnClickListener {
            EditCategoria(
                idcategoria!!.text.toString(),
                categoria!!.text.toString()
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_add) {
            val dialogo1 = AlertDialog.Builder(this)
            dialogo1.setTitle("Mensage")
            dialogo1.setMessage("¿Deseas elimininar este registro ?")
            dialogo1.setCancelable(false)
            dialogo1.setPositiveButton("Si") { dialogo1, id -> EliminarCategoria(idcategoria!!.text.toString()) }
            dialogo1.setNegativeButton("No") { dialogo1, id -> dialogo1.dismiss() }
            dialogo1.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun EditCategoria(sidcategoria: String, scategoria: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Editando Categoria ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"editarCategoria.php", Response.Listener { response ->
                hideDialog()
                categoria!!.setText("")
                Toast.makeText(this@EditarCategoriaActivity, "" + response, Toast.LENGTH_LONG)
                    .show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarCategoriaActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idcategoria"] = sidcategoria
                params["categoria"] = scategoria
                return params
            }
        }
        AppController.instance!!.addToRequestQueue(strReq, tag_string_req)
    }

    private fun EliminarCategoria(sidcategoria: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Eliminando Categoria ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"eliminarCategoria.php",
            Response.Listener { hideDialog() },
            Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarCategoriaActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idcategoria"] = sidcategoria
                return params
            }
        }
        AppController.instance!!.addToRequestQueue(strReq, tag_string_req)
    }

    private fun showDialog() {
        if (!pDialog!!.isShowing) pDialog!!.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
    }

    override fun onBackPressed() {
        val intent = Intent(this@EditarCategoriaActivity, AdMainActivity::class.java)
        startActivity(intent)
    }



    override fun onKeyDown(keyCode: Int, evemt:KeyEvent): Boolean
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            val intent = Intent(this@EditarCategoriaActivity, AdMainActivity::class.java)
            startActivity(intent)
        }
        return true;
    }
}
