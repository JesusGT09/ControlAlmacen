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

class AddSalidaAdminActivity : AppCompatActivity() {
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
    private val addFecha: Button? = null
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
    var bactivity: String? = null
    var Baseurl: String = GlobalInfo.BASE_URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_salida)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        val shared = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        val channel = shared.getString(id, "")
        idproducto = findViewById<View>(R.id.idProducto) as EditText
        producto = findViewById<View>(R.id.producto) as EditText
        fecha = findViewById<View>(R.id.fecha) as EditText
        cantidad = findViewById<View>(R.id.cantidad) as EditText
        idtipo = findViewById<View>(R.id.idTipo) as EditText
        tipo = findViewById<View>(R.id.tipo) as EditText
        user = findViewById(R.id.user)
        submit = findViewById(R.id.submit)
        user!!.setText("" + channel)
        val param = this.intent.extras
        if (param != null) {
            bid = param.getString("id").toString()
            bfecha = param.getString("fecha").toString()
            bidproducto = param.getString("idproducto").toString()
            bproducto = param.getString("producto").toString()
            bcantidad = param.getString("cantidad").toString()
            bidtipo = param.getString("idtipo").toString()
            btipo = param.getString("tipo").toString()
            bactivity = param.getString("activity").toString()

            //user.setText(bid):
            idproducto!!.setText("" + bidproducto)
            producto!!.setText(bproducto)
            fecha!!.setText(bfecha)
            cantidad!!.setText(bcantidad)
            idtipo!!.setText("" + bidtipo)
            tipo!!.setText(btipo)
        }
        btn_producto = findViewById<View>(R.id.btn_producto) as ImageView
        btn_tipo = findViewById<View>(R.id.btn_tipo) as ImageView
        btn_fecha = findViewById<View>(R.id.btn_fecha) as ImageView
        btn_producto!!.setOnClickListener {
            val intent = Intent(this@AddSalidaAdminActivity, ListaProductoAdminActivity::class.java)
            intent.putExtra("id", user!!.getText().toString())
            intent.putExtra("fecha", fecha!!.text.toString())
            intent.putExtra("idproducto", "" + idproducto!!.text.toString())
            intent.putExtra("producto", producto!!.text.toString())
            intent.putExtra("cantidad", cantidad!!.text.toString())
            intent.putExtra("idtipo", "" + idtipo!!.text.toString())
            intent.putExtra("tipo", tipo!!.text.toString())
            intent.putExtra("activity", "Salida")
            startActivity(intent)
        }
        btn_tipo!!.setOnClickListener {
            val intent = Intent(this@AddSalidaAdminActivity, ListaTipoAdminActivity::class.java)
            intent.putExtra("id", user!!.getText().toString())
            intent.putExtra("fecha", fecha!!.text.toString())
            intent.putExtra("idproducto", "" + idproducto!!.text.toString())
            intent.putExtra("producto", producto!!.text.toString())
            intent.putExtra("cantidad", cantidad!!.text.toString())
            intent.putExtra("idtipo", "" + idtipo!!.text.toString())
            intent.putExtra("tipo", tipo!!.text.toString())
            intent.putExtra("activity", "Salida")
            startActivity(intent)
        }
        btn_fecha!!.setOnClickListener { showDatePickerDialog() }
        submit!!.setOnClickListener(View.OnClickListener {
            SaveSalida(
                channel,
                idproducto!!.text.toString(),
                fecha!!.text.toString(),
                cantidad!!.text.toString(),
                idtipo!!.text.toString()
            )
        })
    }

    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance { datePicker, year, month, day -> // +1 because January is zero
                val selectedDate = year.toString() + "-" + (month + 1) + "-" + day
                fecha!!.setText(selectedDate)
            }
        newFragment.show(this.supportFragmentManager, "datePicker")
    }

    private fun SaveSalida(
        sid: String?,
        sidproducto: String,
        sfecha: String,
        scantidad: String,
        sidtipo: String
    ) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Guardando Salida ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"registrarSalida.php", Response.Listener { response ->
                hideDialog()
                idproducto!!.setText("")
                producto!!.setText("")
                fecha!!.setText("")
                cantidad!!.setText("")
                idtipo!!.setText("")
                tipo!!.setText("")
                Toast.makeText(this@AddSalidaAdminActivity, "" + response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@AddSalidaAdminActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["user"] = sid!!
                params["producto"] = sidproducto
                params["tipo"] = sidtipo
                params["fecha"] = sfecha
                params["cantidad"] = scantidad
                return params
            }
        }
        AppController.instance!!.addToRequestQueue(strReq, tag_string_req)
    }

    override fun onBackPressed() {
        val intent = Intent(this@AddSalidaAdminActivity, AdMainActivity::class.java)
        startActivity(intent)
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
