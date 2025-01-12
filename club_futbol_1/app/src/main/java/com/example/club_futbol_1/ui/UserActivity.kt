package com.example.club_futbol_1.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.ActivityUserBinding
import com.example.club_futbol_1.model.Usuario
import com.squareup.picasso.Picasso

class UserActivity : AppCompatActivity() {
    //atributos
    private lateinit var binding: ActivityUserBinding
    private  var intentUsuario: Usuario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        iniciarAtributos()
        cargarInterfazDeUsuario()
    }

    private fun cargarInterfazDeUsuario() {
        if(intentUsuario!=null){
            binding.nombreUsuario.text = intentUsuario!!.nombre
            Picasso.get()
                .load(intentUsuario!!.urlImagen)
                .into(binding.imagenUsuario)

        }
    }

    private fun iniciarAtributos() {
        binding=ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentUsuario = intent.getParcelableExtra<Usuario>("usuario")
        Log.d("usuario",intentUsuario.toString())
    }
}