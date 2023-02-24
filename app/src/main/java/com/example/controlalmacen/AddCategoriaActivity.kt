package com.example.controlalmacen

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.StringRequest
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import java.util.HashMap
import com.example.controlalmacen.GlobalInfo

class AddCategoriaActivity : AppCompatActivity() {
    private var categoria: EditText? = null
    private var submit: Button? = null
    private var pDialog: ProgressDialog? = null
    var Baseurl: String = GlobalInfo.BASE_URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_categoria)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        categoria = findViewById<View>(R.id.categoria) as EditText
        submit = findViewById<View>(R.id.submit) as Button
        submit!!.setOnClickListener { SaveCategoria(categoria!!.text.toString()) }
    }

    private fun SaveCategoria(scategoria: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Guardando Categoria ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"registrarCategoria.php", Response.Listener { response ->
                hideDialog()
                categoria!!.setText("")
                Toast.makeText(this@AddCategoriaActivity, "" + response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@AddCategoriaActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["categoria"] = scategoria
                return params
            }
        }
        AppController.instance!!.addToRequestQueue(strReq, tag_string_req)
    }

    override fun onBackPressed() {
        val intent = Intent(this@AddCategoriaActivity, AdMainActivity::class.java)
        startActivity(intent)
    }

    private fun showDialog() {
        if (!pDialog!!.isShowing) pDialog!!.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
    }
}
