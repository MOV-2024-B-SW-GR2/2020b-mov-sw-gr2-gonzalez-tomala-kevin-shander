package com.example.gr2sw_proyecto_2b

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.gr2sw_proyecto_2b.databinding.ActivityProductoViewsBinding

class ProductoViews : AppCompatActivity() {

    private lateinit var binding: ActivityProductoViewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductoViewsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.getRoot())

        val cart = Cart(0, 0, 0.0, 0.0, true)
        BaseDeDatos.tablaDelivery?.insertarCart(cart)

        // Deshabilitar el botón de regresar
        onBackPressedDispatcher.addCallback(this) {
            // No hacer nada para deshabilitar el botón de regreso
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.p_list_view)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        replaceFragment(HomeFragment())

        val user = intent.getParcelableExtra<User>("user")

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.item_user -> {
                    if (user != null) {
                        val profileFragment = ProfileFragment.newInstance(user)
                        replaceFragment(profileFragment)
                    }
                    true
                }

                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
