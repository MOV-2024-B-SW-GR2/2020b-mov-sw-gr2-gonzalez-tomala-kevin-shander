package com.example.gr2sw_proyecto_2b

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CartView : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var listaProduct = mutableListOf<Product>()

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.lvl_cart_item)
        listaProduct = intent.getParcelableArrayListExtra("listaProduct") ?: mutableListOf()

        val txtDelivery = findViewById<TextView>(R.id.txt_delivery)
        val txtTotalOrder = findViewById<TextView>(R.id.txt_order_total)
        val txtTotal = findViewById<TextView>(R.id.txt_total_cart)

        txtDelivery.setText("$5.99")

        val totalPrice = listaProduct.sumByDouble { it.price }
        txtTotalOrder.setText(String.format("$%.2f", totalPrice))

        val totalPriceCart = totalPrice + 5.99
        txtTotal.setText(String.format("$%.2f", totalPriceCart))

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaProduct.map { it.name + " - $" + it.price })
        listView.adapter = adapter

        registerForContextMenu(listView)

        adapter.clear()
        adapter.addAll(listaProduct.map { it.name + " - " + it.price })
        adapter.notifyDataSetChanged()
    }
}