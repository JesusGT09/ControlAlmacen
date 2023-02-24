package com.example.controlalmacen

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import android.content.Intent
import android.app.AlertDialog
import android.app.ProgressDialog
import android.provider.MediaStore
import android.util.Base64
import android.view.*
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

class EditarProductActivity : AppCompatActivity() {
    var bid: String? = null
    var bnombre: String? = null
    var bprecio: String? = null
    var bstock: String? = null
    var bdescripcion: String? = null
    var bestado: String? = null
    var bfkcategoria: String? = null
    var bcategoria: String? = null
    var bfoto: String? = null
    private val idsalida: EditText? = null
    private var idproducto: EditText? = null
    private var producto: EditText? = null
    private var descripcion: EditText? = null
    private var precio: EditText? = null
    private var stock: EditText? = null
    private var idcategoria: EditText? = null
    private var categoria: EditText? = null
    private var foto: ImageView? = null
    private var submit: Button? = null
    var pDialog: ProgressDialog? = null
    var bitmap: Bitmap? = null
    var PICK_IMAGE_REQUEST = 1
    var KEY_IMAGE = "foto"
    var KEY_NOMBRE = "nombre"
    var Baseurl: String = GlobalInfo.BASE_URL
    var Productourl: String = GlobalInfo.PRODUCTO_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        idproducto = findViewById<View>(R.id.idProducto) as EditText
        producto = findViewById<View>(R.id.producto) as EditText
        descripcion = findViewById<View>(R.id.descripcion) as EditText
        precio = findViewById<View>(R.id.precio) as EditText
        stock = findViewById<View>(R.id.stock) as EditText
        categoria = findViewById<View>(R.id.categoria) as EditText
        idcategoria = findViewById<View>(R.id.idCategoria) as EditText
        foto = findViewById<View>(R.id.foto) as ImageView
        val param = this.intent.extras
        if (param != null) {
            bid = param.getString("id").toString()
            bnombre = param.getString("nombre").toString()
            bprecio = param.getString("precio").toString()
            bstock = param.getString("stock").toString()
            bdescripcion = param.getString("descripcion").toString()
            bestado = param.getString("estado").toString()
            bcategoria = param.getString("categoria").toString()
            bfkcategoria = param.getString("fkcategoria").toString()
            bfoto = param.getString("foto").toString()
            idproducto!!.setText(bid)
            producto!!.setText(bnombre)
            precio!!.setText(bprecio)
            stock!!.setText(bstock)
            descripcion!!.setText(bdescripcion)
            idcategoria!!.setText(bfkcategoria)
            categoria!!.setText(bcategoria)
            Glide.with(this).load(Productourl+bfoto).into(
                foto!!
            )
        }
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
        foto = findViewById(R.id.foto)
        foto!!.setOnClickListener(View.OnClickListener { showFileChooser() })
        submit = findViewById<View>(R.id.submit) as Button
        submit!!.setOnClickListener {
            EditProducto(
                idproducto!!.text.toString(),
                "",
                producto!!.text.toString(),
                descripcion!!.text.toString(),
                precio!!.text.toString(),
                stock!!.text.toString(),
                categoria!!.text.toString(),
                idcategoria!!.text.toString()
            )
        }
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
            dialogo1.setPositiveButton("Si") { dialogo1, id -> EliminarProducto(idproducto!!.text.toString()) }
            dialogo1.setNegativeButton("No") { dialogo1, id -> dialogo1.dismiss() }
            dialogo1.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun EditProducto(
        sid: String,
        simagen: String,
        sproducto: String,
        sdescripcion: String,
        sprecio: String,
        sstock: String,
        scateegoria: String,
        sidcategoria: String
    ) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Editando Producto ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"editarProducto.php", Response.Listener { response ->
                hideDialog()
                producto!!.setText("")
                precio!!.setText("")
                stock!!.setText("")
                idcategoria!!.setText("")
                categoria!!.setText("")
                Toast.makeText(this@EditarProductActivity, "" + response, Toast.LENGTH_LONG).show()
            }, Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarProductActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val simagen = getStringImagen(bitmap)
                val params: MutableMap<String, String> = HashMap()
                params["id"] = sid
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

    private fun EliminarProducto(sidproducto: String) {
        val tag_string_req = "req_register"
        pDialog!!.setMessage("Eliminando Producto ...")
        showDialog()
        val strReq: StringRequest = object : StringRequest(
            Method.POST,
            Baseurl+"eliminarProducto.php",
            Response.Listener { hideDialog() },
            Response.ErrorListener { error ->
                hideDialog()
                Toast.makeText(
                    this@EditarProductActivity,
                    "Error: " + error.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idproducto"] = sidproducto
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
        val intent = Intent(this@EditarProductActivity, AdMainActivity::class.java)
        startActivity(intent)
    }


    override fun onKeyDown(keyCode: Int, evemt:KeyEvent): Boolean
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            val intent = Intent(this@EditarProductActivity, AdMainActivity::class.java)
            startActivity(intent)
        }
        return true;
    }
}
