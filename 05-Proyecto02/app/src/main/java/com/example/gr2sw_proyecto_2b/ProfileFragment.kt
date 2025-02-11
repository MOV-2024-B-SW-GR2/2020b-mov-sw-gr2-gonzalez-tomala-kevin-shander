package com.example.gr2sw_proyecto_2b

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var user: User
    private lateinit var txtEmail: TextView
    private lateinit var txtName: TextView
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Inicializar los TextViews y el Button
        txtEmail = view.findViewById(R.id.txt_email)
        txtName = view.findViewById(R.id.txt_name)
        logoutButton = view.findViewById(R.id.logoutButton)

        // Recibir el objeto User desde el Bundle
        arguments?.let {
            user = it.getParcelable("user") ?: User("", "", "")
            txtEmail.text = user.email
            txtName.text = user.displayName
        }

        // Implementar el botón de logout para Firebase Authentication
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().finish()
        }

        return view
    }

    companion object {
        // Método para crear una nueva instancia del fragmento con el User
        @JvmStatic
        fun newInstance(user: User) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putParcelable("user", user)  // Pasar el objeto User
            }
        }
    }
}
