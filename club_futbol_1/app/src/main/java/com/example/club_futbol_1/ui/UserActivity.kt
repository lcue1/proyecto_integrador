package com.example.club_futbol_1.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.ActivityUserBinding
import com.example.club_futbol_1.model.Noticia
import com.example.club_futbol_1.model.Usuario
import com.example.club_futbol_1.ui.adapters.NoticiasAdapter
import com.example.club_futbol_1.utils.mostrarDialogoConfirmacion
import com.google.firebase.firestore.FirebaseFirestore
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
        eventosBotones()
    }


    private fun cargarInterfazDeUsuario() {
        if(intentUsuario!=null){
            binding.nombreUsuario.text = intentUsuario!!.nombre
            Picasso.get()
                .load(intentUsuario!!.urlImagen)
                .into(binding.imagenUsuario)

            cargarNoticias()
        }
    }

    private fun cargarNoticias() {
        val db = FirebaseFirestore.getInstance()

        db.collection("noticias")
            .get()
            .addOnSuccessListener { result ->
                val noticias= mutableListOf<Noticia>()
                for (document in result) {
                    // Accede a los datos del documento
                    val titulo = document.getString("titulo")  // Campo
                    val descripcion = document.getString("descripcion")  // "
                    val utlImg = document.getString("urlImg")  // C
                    Log.d("imagen",utlImg.toString())

                    noticias.add(Noticia(
                        id = "1",
                        titulo = titulo,
                        descripcion=descripcion,
                        urlImagen = utlImg
                    ))
                }
                Log.d("noticias",noticias.toString())
                val customAdapter = NoticiasAdapter(noticias)

                val recyclerView: RecyclerView = findViewById(R.id.noticiass_recycle)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = customAdapter
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error al obtener documentos.", exception)
            }
    }

    private fun iniciarAtributos() {
        binding=ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentUsuario = intent.getParcelableExtra<Usuario>("usuario")
        Log.d("usuario",intentUsuario.toString())
    }


    //eventos
    private fun eventosBotones() {
        binding.botonSalir.setOnClickListener {
            //Abre dialog y pregunta al usuario si desea salir
            mostrarDialogoConfirmacion(this,
                "Salir","Desea salir de la app?",
                {
                    finishAffinity()
                    System.out.close()
                },
                {}
            )
        }

    }

}