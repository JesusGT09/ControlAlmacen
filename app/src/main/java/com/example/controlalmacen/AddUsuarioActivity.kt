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
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import java.util.HashMap
import com.example.controlalmacen.GlobalInfo

class AddUsuarioActivity : AppCompatActivity() {
    private var identificacion: EditText? = null
    private var nombre: EditText? = null
    private var apellido: EditText? = null
    private var correo: EditText? = null
    private var telefono: EditText? = null
    private var password: EditText? = null
    private var rol: Spinner? = null

    private var submit: Button? = null
    private var pDialog: ProgressDialog? = null
    var Baseurl: String = GlobalInfo.BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_usuario)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)

        identificacion = findViewById<View>(R.id.identificacion) as EditText
        nombre = findViewById<View>(R.id.nombre) as EditText
        apellido = findViewById<View>(R.id.apellido) as EditText
        correo = findViewById<View>(R.id.correo) as EditText
        telefono = findViewById<View>(R.id.telefono) as EditText
        password = findViewById<View>(R.id.password) as EditText
        rol = findViewById<View>(R.id.rol) as Spinner

        submit = findViewById<View>(R.id.submit) as Button
        submit!!.setOnClickListener { SaveUsuario(
            identificacion!!.text.toString(), nombre!!.text.toString(),
            apellido!!.text.toString(), correo!!.text.toString(),
            password!!.text.toString(), telefono!!.text.toString(),
            rol!!.selectedItem.toString()
        ) }
    }

    private fun SaveUsuario(sidentificacion: String , snombre: String, sapellido: String, scorreo: String, spassword: String, stelefono: String, srol: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Guardando Usuario ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"registrarUsuario.php", Response.Listener { response ->
                hideDialog()
                identificacion!!.setText("")
                nombre!!.setText("")
                apellido!!.setText("")
                correo!!.setText("")
                telefono!!.setText("")
                password!!.setText("")

                Toast.makeText(this@AddUsuarioActivity, "" + response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(this@AddUsuarioActivity, "Error: " + error.message, Toast.LENGTH_LONG)
                    .show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["identificacion"] = sidentificacion
                params["nombre"] = snombre
                params["apellido"] = sapellido
                params["telefono"] = stelefono
                params["correo"] = scorreo
                params["password"] = spassword
                params["rol"] = srol
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
        val intent = Intent(this@AddUsuarioActivity, AdMainActivity::class.java)
        startActivity(intent)
    }
}
