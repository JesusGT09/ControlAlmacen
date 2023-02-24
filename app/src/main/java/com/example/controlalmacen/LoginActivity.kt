package com.example.controlalmacen

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.Response
import java.lang.Exception
import java.util.HashMap
import com.example.controlalmacen.GlobalInfo

class LoginActivity : AppCompatActivity() {
    private var boton: Button? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var pDialog: ProgressDialog? = null
    var editor: SharedPreferences.Editor? = null
    var openeditor: SharedPreferences? = null
    var Baseurl: String = GlobalInfo.BASE_URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        boton = findViewById(R.id.submit)
        boton!!.setOnClickListener(View.OnClickListener {
            LoginUser(
                email!!.getText().toString(),
                password!!.getText().toString()
            )
        })
    }

    private fun LoginUser(suser: String, scontrasena: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Validando usuario ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"login.php", Response.Listener { response ->
                hideDialog()
                //	Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_LONG).show();
                try {
                    val respObj = JSONArray(response)
                    for (i in 0 until respObj.length()) {
                        val c = respObj.getJSONObject(i)
                        val resultado = c.getString("resultado")
                        val sid = c.getString("id")
                        val sidentificacion = c.getString("identificacion")
                        val snombre = c.getString("nombre")
                        val sapellido = c.getString("apellido")
                        val scorreo = c.getString("correo")
                        val spassword = c.getString("password")
                        val stelefono = c.getString("telefonod")
                        val srol = c.getString("rol")
                        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
                        editor!!.putString(id, sid)
                        editor!!.putString(nombre, snombre)
                        editor!!.putString(apellido, sapellido)
                        editor!!.putString(rol, srol)
                        editor!!.commit()
                        if (srol == "admin") {
                            val intent = Intent(this@LoginActivity, AdMainActivity::class.java)
                            startActivity(intent)
                        }
                        if (srol == "personal") {
                            val intent = Intent(this@LoginActivity, PersoMainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@LoginActivity, "" + e.message, Toast.LENGTH_LONG).show()
                }
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(this@LoginActivity, "Error: " + error.message, Toast.LENGTH_LONG)
                    .show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["correo"] = suser
                params["password"] = scontrasena
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
        const val id = "idKey"
        const val nombre = "nombreKey"
        const val apellido = "apellidoKey"
        const val rol = "rolKey"
        const val MY_PREFS_NAME = "MySession"
    }

    override fun onBackPressed() {}
}
