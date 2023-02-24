package com.example.controlalmacen

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.graphics.Bitmap
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import android.content.Intent
import android.app.ProgressDialog
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.android.volley.toolbox.StringRequest
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.HashMap
import com.example.controlalmacen.GlobalInfo

class AddProductoActivity : AppCompatActivity() {
    private var producto: EditText? = null
    private var descripcion: EditText? = null
    private var stock: EditText? = null
    private var precio: EditText? = null
    private var categoria: EditText? = null
    private var idcategoria: EditText? = null
    private var submit: Button? = null
    private var pDialog: ProgressDialog? = null
    private var btnCategoria: ImageView? = null
    private var foto: ImageView? = null
    var bproducto: String? = null
    var bprecio: String? = null
    var bstock: String? = null
    var bidcategoria: String? = null
    var bcategoria: String? = null
    var bitmap: Bitmap? = null
    var PICK_IMAGE_REQUEST = 1
    var KEY_IMAGE = "foto"
    var KEY_NOMBRE = "nombre"
    var Baseurl: String = GlobalInfo.BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_producto)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        descripcion = findViewById(R.id.descripcion)
        producto = findViewById(R.id.producto)
        precio = findViewById(R.id.precio)
        stock = findViewById(R.id.stock)
        categoria = findViewById(R.id.categoria)
        idcategoria = findViewById(R.id.idCategoria)
        val param = this.intent.extras
        if (param != null) {
            bproducto = param.getString("producto").toString()
            bprecio = param.getString("precio").toString()
            bstock = param.getString("stock").toString()
            bidcategoria = param.getString("idcategoria").toString()
            bcategoria = param.getString("categoria").toString()

            //user.setText(bid):
            producto!!.setText("" + bproducto)
            precio!!.setText(bprecio)
            stock!!.setText(bstock)
            idcategoria!!.setText("" + bidcategoria)
            categoria!!.setText("" + bcategoria)
        }
        foto = findViewById(R.id.foto)
        foto!!.setOnClickListener(View.OnClickListener { showFileChooser() })
        btnCategoria = findViewById(R.id.btn_categoria)
        btnCategoria!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@AddProductoActivity, ListaCategoriaActivity::class.java)
            intent.putExtra("producto", producto!!.getText().toString())
            intent.putExtra("precio", precio!!.getText().toString())
            intent.putExtra("stock", stock!!.getText().toString())
            intent.putExtra("idcategoria", idcategoria!!.getText().toString())
            intent.putExtra("categoria", categoria!!.getText().toString())
            startActivity(intent)
        })
        submit = findViewById(R.id.submit)
        submit!!.setOnClickListener(View.OnClickListener { //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
            SaveProducto(
                producto!!.getText().toString(),
                descripcion!!.getText().toString(),
                precio!!.getText().toString(),
                stock!!.getText().toString(),
                categoria!!.getText().toString(),
                idcategoria!!.getText().toString()
            )
        })
    }

    fun getStringImagen(bmp: Bitmap?): String {
        val baos = ByteArrayOutputStream()
        bmp!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val filePath = data.data
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                //Configuración del mapa de bits en ImageView
                foto!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun SaveProducto(
        sproducto: String,
        sdescripcion: String,
        sprecio: String,
        sstock: String,
        scateegoria: String,
        sidcategoria: String
    ) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Guardando Producto ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"registrarProducto.php", Response.Listener { response ->
                hideDialog()
                producto!!.setText("")
                precio!!.setText("")
                stock!!.setText("")
                idcategoria!!.setText("")
                categoria!!.setText("")
                Toast.makeText(this@AddProductoActivity, "" + response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@AddProductoActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val simagen = getStringImagen(bitmap)
                val params: MutableMap<String, String> = HashMap()
                params["foto"] = simagen
                params["descripcion"] = sdescripcion
                params["producto"] = sproducto
                params["precio"] = sprecio
                params["stock"] = sstock
                params["categoria"] = scateegoria
                params["idcategoria"] = sidcategoria
                return params
            }
        }
        AppController.instance!!.addToRequestQueue(strReq, tag_string_req)
    }

    override fun onBackPressed() {
        val intent = Intent(this@AddProductoActivity, AdMainActivity::class.java)
        startActivity(intent)
    }

    private fun showDialog() {
        if (!pDialog!!.isShowing) pDialog!!.show()
    }

    private fun hideDialog() {
        if (pDialog!!.isShowing) pDialog!!.dismiss()
    }
}
