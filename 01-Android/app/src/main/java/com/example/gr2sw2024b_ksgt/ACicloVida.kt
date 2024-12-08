package com.example.gr2sw2024b_ksgt

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mostrarMensaje("onCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarMensaje("onStart\n")
    }

    override fun onResume() {
        super.onResume()
        mostrarMensaje("onResume\n")
    }

    override fun onPause() {
        super.onPause()
        mostrarMensaje("onPause\n")
    }

    override fun onStop() {
        super.onStop()
        mostrarMensaje("onStop\n")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarMensaje("onDestroy\n")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run { putString("variableTextoGuardado", textoGlobal) }
        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val textoRecuperado = savedInstanceState.getString("variableTextoGuardado")
        if(textoRecuperado != null) {
            mostrarMensaje(textoRecuperado)
        }
    }

    var textoGlobal = ""
    fun mostrarMensaje(texto: String) {
        textoGlobal += texto
        var snack = Snackbar.make(findViewById(R.id.cl_ciclo_vida), textoGlobal, Snackbar.LENGTH_INDEFINITE)
        snack.show()
    }
}