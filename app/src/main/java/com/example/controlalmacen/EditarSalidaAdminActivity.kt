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

class EditarSalidaAdminActivity : AppCompatActivity() {
    var bid: String? = null
    var bidproducto: String? = null
    var bproducto: String? = null
    var bfecha: String? = null
    var bcantidad: String? = null
    var btipo: String? = null
    var bidtipo: String? = null
    private var idsalida: EditText? = null
    private var user: EditText? = null
    private var fecha: EditText? = null
    private var idproducto: EditText? = null
    private var producto: EditText? = null
    private var cantidad: EditText? = null
    private var idtipo: EditText? = null
    private var tipo: EditText? = null
    private var submit: Button? = null
    private var pDialog: ProgressDialog? = null
    var Baseurl: String = GlobalInfo.BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_salida)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        val channel = shared.getString(id, "")
        idsalida = findViewById<View>(R.id.idsalida) as EditText
        user = findViewById<View>(R.id.user) as EditText
        fecha = findViewById<View>(R.id.fecha) as EditText
        producto = findViewById<View>(R.id.producto) as EditText
        idproducto = findViewById<View>(R.id.idProducto) as EditText
        cantidad = findViewById<View>(R.id.cantidad) as EditText
        idtipo = findViewById<View>(R.id.idTipo) as EditText
        tipo = findViewById<View>(R.id.tipo) as EditText
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        val param = this.intent.extras
        if (param != null) {
            bid = param.getString("id").toString()
            bidproducto = param.getString("idproducto").toString()
            bproducto = param.getString("producto").toString()
            bfecha = param.getString("fecha").toString()
            bcantidad = param.getString("cantidad").toString()
            btipo = param.getString("tipo").toString()
            bidtipo = param.getString("idTipo").toString()
            idsalida!!.setText("" + bid)
            idproducto!!.setText("" + bidproducto)
            producto!!.setText(bproducto)
            fecha!!.setText(bfecha)
            cantidad!!.setText("" + bcantidad)
            idtipo!!.setText("" + bidtipo)
            tipo!!.setText(btipo)
        }
        submit = findViewById<View>(R.id.submit) as Button
        submit!!.setOnClickListener {
            EditSalida(
                idsalida!!.text.toString(),
                channel,
                idproducto!!.text.toString(),
                fecha!!.text.toString(),
                cantidad!!.text.toString(),
                idtipo!!.text.toString()
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
            dialogo1.setMessage("??Deseas elimininar este registro ?")
            dialogo1.setCancelable(false)
            dialogo1.setPositiveButton("Si") { dialogo1, id ->
                EliminarSalida(
                    idsalida!!.text.toString(),
                    idproducto!!.text.toString(),
                    cantidad!!.text.toString()
                )
            }
            dialogo1.setNegativeButton("No") { dialogo1, id -> dialogo1.dismiss() }
            dialogo1.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun EditSalida(
        sidsalida: String?,
        suser: String?,
        sidproducto: String,
        sfecha: String,
        scantidad: String,
        sidtipo: String
    ) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Editando Salida ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"editarSalida.php", Response.Listener { response ->
                hideDialog()
                Toast.makeText(this@EditarSalidaAdminActivity, "" + response, Toast.LENGTH_LONG)
                    .show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarSalidaAdminActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idsalida"] = sidsalida!!
                params["user"] = suser!!
                params["idproducto"] = sidproducto
                params["fecha"] = sfecha
                params["cantidad"] = scantidad
                params["idtipo"] = sidtipo
                return params
            }
        }
        AppController.instance!!.addToRequestQueue(strReq, tag_string_req)
    }

    private fun EliminarSalida(sidsalida: String, sidproducto: String, scantidad: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Eliminando Salida ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"eliminarSalida.php",
            Response.Listener { hideDialog() },
            Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarSalidaAdminActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idsalida"] = sidsalida
                params["idproducto"] = sidproducto
                params["cantidad"] = scantidad
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
        val intent = Intent(this@EditarSalidaAdminActivity, AdMainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val MY_PREFS_NAME = "MySession"
        const val id = "idKey"
        const val nombre = "nombreKey"
        const val apellido = "apellidoKey"
    }

    override fun onKeyDown(keyCode: Int, evemt:KeyEvent): Boolean
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            val intent = Intent(this@EditarSalidaAdminActivity, AdMainActivity::class.java)
            startActivity(intent)
        }
        return true;
    }
}
