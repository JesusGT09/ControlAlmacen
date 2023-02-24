package com.example.controlalmacen

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import android.content.Intent
import android.app.ProgressDialog
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.android.volley.toolbox.StringRequest
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import java.util.HashMap
import com.example.controlalmacen.GlobalInfo

class AddEntradaAdminActivity : AppCompatActivity() {
    private val boton: Button? = null
    private val email: EditText? = null
    private val password: EditText? = null
    private var pDialog: ProgressDialog? = null
    private var idproducto: EditText? = null
    private var producto: EditText? = null
    private var fecha: EditText? = null
    private var cantidad: EditText? = null
    private var idtipo: EditText? = null
    private var tipo: EditText? = null
    private var user: EditText? = null
    private var submit: Button? = null
    private var btn_fecha: ImageView? = null
    private var btn_producto: ImageView? = null
    private var btn_tipo: ImageView? = null
    var bid: String? = null
    var bfecha: String? = null
    var bidproducto: String? = null
    var bproducto: String? = null
    var bcantidad: String? = null
    var bidtipo: String? = null
    var btipo: String? = null
    var Baseurl: String = GlobalInfo.BASE_URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entrada)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        user = findViewById<View>(R.id.user) as EditText
        submit = findViewById<View>(R.id.submit) as Button
        idproducto = findViewById<View>(R.id.idProducto) as EditText
        producto = findViewById<View>(R.id.producto) as EditText
        fecha = findViewById<View>(R.id.fecha) as EditText
        cantidad = findViewById<View>(R.id.cantidad) as EditText
        idtipo = findViewById<View>(R.id.idTipo) as EditText
        tipo = findViewById<View>(R.id.tipo) as EditText
        val shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        val channel = shared.getString(id, "")
        user!!.setText("" + channel)
        btn_producto = findViewById<View>(R.id.btn_producto) as ImageView
        btn_tipo = findViewById<View>(R.id.btn_tipo) as ImageView
        btn_fecha = findViewById<View>(R.id.btn_fecha) as ImageView
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        val param = this.intent.extras
        if (param != null) {
            bid = param.getString("id").toString()
            bfecha = param.getString("fecha").toString()
            bidproducto = param.getString("idproducto").toString()
            bproducto = param.getString("producto").toString()
            bcantidad = param.getString("cantidad").toString()
            bidtipo = param.getString("idtipo").toString()
            btipo = param.getString("tipo").toString()

            //user.setText(bid):
            idproducto!!.setText("" + bidproducto)
            producto!!.setText(bproducto)
            fecha!!.setText(bfecha)
            cantidad!!.setText("" + bcantidad)
            idtipo!!.setText("" + bidtipo)
            tipo!!.setText(btipo)
        }
        btn_producto!!.setOnClickListener {
            val intent = Intent(this@AddEntradaAdminActivity, ListaProductoAdminActivity::class.java)
            intent.putExtra("id", user!!.text.toString())
            intent.putExtra("fecha", fecha!!.text.toString())
            intent.putExtra("idproducto", idproducto!!.text.toString())
            intent.putExtra("producto", producto!!.text.toString())
            intent.putExtra("cantidad", cantidad!!.text.toString())
            intent.putExtra("idtipo", idtipo!!.text.toString())
            intent.putExtra("tipo", tipo!!.text.toString())
            intent.putExtra("activity", "Entrada")
            startActivity(intent)
        }
        btn_tipo!!.setOnClickListener {
            val intent = Intent(this@AddEntradaAdminActivity, ListaTipoAdminActivity::class.java)
            intent.putExtra("id", user!!.text.toString())
            intent.putExtra("fecha", fecha!!.text.toString())
            intent.putExtra("idproducto", idproducto!!.text.toString())
            intent.putExtra("producto", producto!!.text.toString())
            intent.putExtra("cantidad", cantidad!!.text.toString())
            intent.putExtra("idtipo", idtipo!!.text.toString())
            intent.putExtra("tipo", tipo!!.text.toString())
            intent.putExtra("activity", "Entrada")
            startActivity(intent)
        }
        btn_fecha!!.setOnClickListener { showDatePickerDialog() }
        submit!!.setOnClickListener {
            SaveEntrada(
                channel,
                idproducto!!.text.toString(),
                fecha!!.text.toString(),
                cantidad!!.text.toString(),
                idtipo!!.text.toString()
            )
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@AddEntradaAdminActivity, AdMainActivity::class.java)
        startActivity(intent)
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance { datePicker, year, month, day -> // +1 because January is zero
                val selectedDate = year.toString() + "-" + (month + 1) + "-" + day
                fecha!!.setText(selectedDate)
            }
        newFragment.show(this.supportFragmentManager, "datePicker")
    }

    private fun SaveEntrada(
        sid: String?,
        sidproducto: String,
        sfecha: String,
        scantidad: String,
        sidtipo: String
    ) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Guardando Entrada ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"registrarEntrada.php", Response.Listener { response ->
                hideDialog()
                idproducto!!.setText("")
                producto!!.setText("")
                fecha!!.setText("")
                cantidad!!.setText("")
                idtipo!!.setText("")
                tipo!!.setText("")
                Toast.makeText(this@AddEntradaAdminActivity, "" + response, Toast.LENGTH_LONG)
                    .show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@AddEntradaAdminActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["user"] = sid!!
                params["tipo"] = sidtipo
                params["producto"] = sidproducto
                params["fecha"] = sfecha
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

    companion object {
        const val MY_PREFS_NAME = "MySession"
        const val id = "idKey"
        const val nombre = "nombreKey"
        const val apellido = "apellidoKey"
    }
}
