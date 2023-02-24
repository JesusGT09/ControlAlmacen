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
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import java.util.HashMap
import com.example.controlalmacen.GlobalInfo

class EditarUsuarioActivity : AppCompatActivity() {
    var bid: String? = null
    var bidentificacion: String? = null
    var bnombre: String? = null
    var bapellido: String? = null
    var bcorreo: String? = null
    var btelefono: String? = null
    var bpassword: String? = null
    var brol: String? = null
    private var idUsuario: EditText? = null
    private var identificacion: EditText? = null
    private var nombre: EditText? = null
    private var apellido: EditText? = null
    private var correo: EditText? = null
    private var telefono: EditText? = null
    private var password: EditText? = null
    private var rol: Spinner? = null
    private var submit: Button? = null
    var pDialog: ProgressDialog? = null
    var Baseurl: String = GlobalInfo.BASE_URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_usuario)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)

        idUsuario = findViewById<View>(R.id.idusuario) as EditText
        identificacion = findViewById<View>(R.id.identificacion) as EditText
        nombre = findViewById<View>(R.id.nombre) as EditText
        apellido = findViewById<View>(R.id.apellido) as EditText
        correo = findViewById<View>(R.id.correo) as EditText
        telefono = findViewById<View>(R.id.telefono) as EditText
        password = findViewById<View>(R.id.password) as EditText
        rol = findViewById<View>(R.id.rol) as Spinner

        val param = this.intent.extras
        if (param != null) {
            bid = param.getString("id").toString()
            bidentificacion = param.getString("identificacion").toString()
            bnombre = param.getString("nombre").toString()
            bapellido = param.getString("apellido").toString()
            bcorreo = param.getString("correo").toString()
            bpassword = param.getString("password").toString()
            btelefono = param.getString("telefono").toString()
            brol = param.getString("rol").toString()

            idUsuario!!.setText("" + bid)
            identificacion!!.setText(bidentificacion)
            nombre!!.setText(bnombre)
            apellido!!.setText(bapellido)
            correo!!.setText(bcorreo)
            telefono!!.setText(btelefono)
            password!!.setText(bpassword)
           // rol!!.setText(brol)
        }
        submit = findViewById<View>(R.id.submit) as Button
        submit!!.setOnClickListener { EditUsuario(
            idUsuario!!.text.toString(),
            identificacion!!.text.toString(), nombre!!.text.toString(),
            apellido!!.text.toString(), correo!!.text.toString(),
            password!!.text.toString(), telefono!!.text.toString(),
            rol!!.selectedItem.toString()   ) }
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
            dialogo1.setPositiveButton("Si") { dialogo1, id -> EliminarUsuario(idUsuario!!.text.toString()) }
            dialogo1.setNegativeButton("No") { dialogo1, id -> dialogo1.dismiss() }
            dialogo1.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun EditUsuario(sid: String, sidentificacion: String , snombre: String, sapellido: String, scorreo: String, spassword: String, stelefono: String, srol: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Editando Usuario ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"editarUsuario.php", Response.Listener { response ->
                hideDialog()
                Toast.makeText(this@EditarUsuarioActivity, "" + response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarUsuarioActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["id"] = sid
                params["identificacion"] = sidentificacion
                params["nombre"] = snombre
                params["apellido"] = sapellido
                params["correo"] = scorreo
                params["password"] = spassword
                params["telefono"] = stelefono
                params["rol"] = srol
                return params
            }
        }
        AppController.instance!!.addToRequestQueue(strReq, tag_string_req)
    }

    private fun EliminarUsuario(sidusuario: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Eliminando Usuario ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"eliminarUsuario.php",
            Response.Listener { hideDialog() },
            Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarUsuarioActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idusuario"] = sidusuario
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
        val intent = Intent(this@EditarUsuarioActivity, AdMainActivity::class.java)
        startActivity(intent)
    }

    override fun onKeyDown(keyCode: Int, evemt:KeyEvent): Boolean
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            val intent = Intent(this@EditarUsuarioActivity, AdMainActivity::class.java)
            startActivity(intent)
        }
        return true;
    }
}
