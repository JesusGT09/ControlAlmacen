package com.example.controlalmacen.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.os.Bundle
import com.example.controlalmacen.R
import android.content.SharedPreferences
import android.view.*
import androidx.fragment.app.Fragment
import com.example.controlalmacen.AddProductoActivity
import com.example.controlalmacen.LoginActivity

class HomeFragment : Fragment() {
    private var tnombre: TextView? = null
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        val shared = activity!!.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val cnombre = shared.getString(nombre, "")
        val capellido = shared.getString(apellido, "")
        tnombre = root.findViewById(R.id.user_name)
        tnombre!!.setText("$cnombre $capellido")
        return root
    }

    companion object {
        const val MY_PREFS_NAME = "MySession"
        const val id = "idKey"
        const val nombre = "nombreKey"
        const val apellido = "apellidoKey"
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.ad_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}