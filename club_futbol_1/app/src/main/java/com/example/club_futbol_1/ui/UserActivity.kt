package com.example.club_futbol_1.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.ActivityUserBinding
import com.example.club_futbol_1.model.Noticia
import com.example.club_futbol_1.model.Usuario
import com.example.club_futbol_1.ui.adapters.NoticiasAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.squareup.picasso.Picasso
import kotlin.math.log

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var drawerLayout: DrawerLayout
    private var usuario: Usuario?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
         usuario = intent.getParcelableExtra<Usuario>("usuario")
        usuario?.let {
         //   cargarFragmento(R.id.tiendaFragment)
            seleccionarFragmentoMenu()

        }
    }

    private fun seleccionarFragmentoMenu() {

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START) // Abre el menú
        }

        // Manejo de clics en el Navigation Drawer
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_noticias -> {
                    val bundle = Bundle().apply {
                        putParcelable("usuario", usuario)
                    }
                    cargarFragmento(R.id.noticiasEquipoFragment,bundle)
                }
                R.id.nav_perfil -> {
                    val bundle = Bundle().apply {
                        putParcelable("usuario", usuario)
                    }
                    cargarFragmento(R.id.infoUssuarioFragment,bundle)
                }
                R.id.nav_tienda -> {
                    val bundle = Bundle().apply {
                        putParcelable("usuario", usuario)
                    }
                    cargarFragmento(R.id.tiendaFragment, bundle)
                }
                R.id.menu_add_noticia -> {
                    val bundle = Bundle().apply {
                        putParcelable("usuario", usuario)
                    }
                    cargarFragmento(R.id.addNoticiaFragment,bundle)
                }
                R.id.menu_editar_noticia -> {
                    val bundle = Bundle().apply {
                        putParcelable("usuario", usuario)
                        putBoolean("esAdmin",true)
                    }
                    cargarFragmento(R.id.noticiasEquipoFragment,bundle)
                }

            }
            drawerLayout.closeDrawer(GravityCompat.START) // Cierra el menú después de seleccionar
            true
        }
    }

    private fun cargarFragmento(fragmento_a_mostrar:Int,bundle:Bundle) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Para forzar la recarga, eliminamos el fragmento del back stack antes de navegar
        navController.popBackStack(fragmento_a_mostrar, true)

        // Opciones de navegación para evitar que se guarde en el back stack y forzar recarga
        val navOptions = NavOptions.Builder()
            .setPopUpTo(fragmento_a_mostrar, true) // Elimina el fragmento si ya existe
            .setLaunchSingleTop(false) // Permite recrear el fragmento
            .build()

        navController.navigate(fragmento_a_mostrar, bundle, navOptions)

    }

}