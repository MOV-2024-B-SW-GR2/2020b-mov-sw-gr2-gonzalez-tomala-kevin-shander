package com.example.gr2sw_proyecto_2b

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val respuestaLoginUiAuth = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){
        res: FirebaseAuthUIAuthenticationResult ->
        if(res.resultCode == RESULT_OK){
            if(res.idpResponse != null){
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(res: IdpResponse){
        val uuid = FirebaseAuth.getInstance().currentUser?.uid
        val name = FirebaseAuth.getInstance().currentUser?.displayName
        val email = FirebaseAuth.getInstance().currentUser?.email

        val usuario = User(uuid!!, name!!, email!!)
        if(BaseDeDatos.tablaDelivery?.buscarUsuarioPorId(uuid) == false){
            BaseDeDatos.tablaDelivery?.crearUsuario(usuario)
        }

        irActividad(ProductoViews::class.java, usuario)

        if(res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    fun irActividad(clase: Class<*>, user: User? = null) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("user", user)

        startActivityForResult(intentExplicito, 1)
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.p_list_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            respuestaLoginUiAuth.launch(signInIntent)
            val usuario = FirebaseAuth.getInstance().currentUser
            if (usuario != null) {
                val uuid = usuario.uid
                val name = usuario.displayName
                val email = usuario.email
                val user = User(uuid, name!!, email!!)
                irActividad(ProductoViews::class.java, user)
            }
        }
    }
}