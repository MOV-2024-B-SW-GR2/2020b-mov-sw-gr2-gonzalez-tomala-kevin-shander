package com.example.gr2sw_proyecto_2b

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView

class HomeFragment : Fragment() {

    private val listaProduct = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartButton: ImageButton = view.findViewById(R.id.cartButton)

        val cantidad = view.findViewById<TextView>(R.id.textViewProductCount)

        // Definir los botones de agregar al carrito
        val cartButtonProduct0: ImageButton = view.findViewById(R.id.cartButtonProduct0)
        val cartButtonProduct1: ImageButton = view.findViewById(R.id.cartButtonProduct1)
        val cartButtonProduct2: ImageButton = view.findViewById(R.id.cartButtonProduct2)
        val cartButtonProduct3: ImageButton = view.findViewById(R.id.cartButtonProduct3)
        val cartButtonProduct4: ImageButton = view.findViewById(R.id.cartButtonProduct4)

        val productNameTextView0: TextView = view.findViewById(R.id.productName0)
        val productName0 = productNameTextView0.text.toString()

        val productNameTextView1: TextView = view.findViewById(R.id.productName1)
        val productName1 = productNameTextView1.text.toString()

        val productNameTextView2: TextView = view.findViewById(R.id.productName2)
        val productName2 = productNameTextView2.text.toString()

        val productNameTextView3: TextView = view.findViewById(R.id.productName3)
        val productName3 = productNameTextView3.text.toString()

        val productNameTextView4: TextView = view.findViewById(R.id.productName4)
        val productName4 = productNameTextView4.text.toString()

        val rutaImagen0 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/fullset_ramen.png").toString()
        val rutaImagen1 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/hakata_ramen.png").toString()
        val rutaImagen2 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/kurume_ramen.png").toString()
        val rutaImagen3 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/sapporo_miso_ramen.png").toString()
        val rutaImagen4 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/shrimp_fried_rice.png").toString()

        val rutaImagen5 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/mix_salad.png").toString()
        val rutaImagen6 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/veg_salad.png").toString()

        val rutaImagen7 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/berry_bonanza_waffle.png").toString()

        val rutaImagen8 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/beef_burger.png").toString()
        val rutaImagen9 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/cheese_burger.png").toString()
        val rutaImagen10 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/fried_chicken_burger.png").toString()
        val rutaImagen11 = Uri.parse("android.resource://com.example.gr2sw_proyecto_2b/drawable/grilled_beef_burger.png").toString()

        cartButton.setOnClickListener {
            val intent = Intent(activity, CartView::class.java)
            intent.putParcelableArrayListExtra("listaProduct", ArrayList(listaProduct))
            startActivity(intent)
        }

        cartButtonProduct0.setOnClickListener {
            val product = Product(
                0,
                productName0,
                12.99,
                "Ramen",
                rutaImagen3,
                20
            )
            BaseDeDatos.tablaDelivery?.insertarProducto(product)
            val cartItem = CartItem(0, 0, product.id, 1)
            BaseDeDatos.tablaDelivery?.insertarCartItem(cartItem)
            listaProduct.add(product)
            cantidad.text = listaProduct.size.toString()
            Log.d("HomeFragment", "Product added to cart!")
        }

        cartButtonProduct1.setOnClickListener {
            val product = Product(
                1,
                productName1,
                14.99,
                "Ramen",
                rutaImagen0,
                20
            )
            BaseDeDatos.tablaDelivery?.insertarProducto(product)
            val cartItem = CartItem(0, 0, product.id, 1)
            BaseDeDatos.tablaDelivery?.insertarCartItem(cartItem)
            listaProduct.add(product)
            cantidad.text = listaProduct.size.toString()
            Log.d("HomeFragment", "Product added to cart!")
        }

        cartButtonProduct2.setOnClickListener {
            val product = Product(
                2,
                productName2,
                13.99,
                "Ramen",
                rutaImagen1,
                20
            )
            BaseDeDatos.tablaDelivery?.insertarProducto(product)
            val cartItem = CartItem(0, 0, product.id, 1)
            BaseDeDatos.tablaDelivery?.insertarCartItem(cartItem)
            listaProduct.add(product)
            cantidad.text = listaProduct.size.toString()
            Log.d("HomeFragment", "Product added to cart!")
        }

        cartButtonProduct3.setOnClickListener {
            val product = Product(
                3,
                productName3,
                15.99,
                "Ramen",
                rutaImagen2,
                20
            )
            BaseDeDatos.tablaDelivery?.insertarProducto(product)
            val cartItem = CartItem(0, 0, product.id, 1)
            BaseDeDatos.tablaDelivery?.insertarCartItem(cartItem)
            listaProduct.add(product)
            cantidad.text = listaProduct.size.toString()
            Log.d("HomeFragment", "Product added to cart!")
        }

        cartButtonProduct4.setOnClickListener {
            val product = Product(
                4,
                productName4,
                10.99,
                "Fried Rice",
                rutaImagen4,
                20
            )
            BaseDeDatos.tablaDelivery?.insertarProducto(product)
            val cartItem = CartItem(0, 0, product.id, 1)
            BaseDeDatos.tablaDelivery?.insertarCartItem(cartItem)
            listaProduct.add(product)
            cantidad.text = listaProduct.size.toString()
            Log.d("HomeFragment", "Product added to cart!")
        }
    }
}

