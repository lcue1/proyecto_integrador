package com.example.club_futbol_1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.FragmentNoticiasEquipoBinding
import com.example.club_futbol_1.databinding.FragmentTiendaBinding
import com.example.club_futbol_1.model.Noticia
import com.example.club_futbol_1.model.Producto
import com.example.club_futbol_1.ui.adapters.NoticiasAdapter
import com.example.club_futbol_1.ui.adapters.TiendaAdapter
import com.google.firebase.firestore.FirebaseFirestore


class TiendaFragment : Fragment() {
    private var _binding:FragmentTiendaBinding?=null
    private val binding get()= _binding!!
    private val productosCarrito= mutableListOf<Producto>()
    private var nItemsCarrito=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cargarProductos()

        _binding = FragmentTiendaBinding.inflate(inflater, container, false)

        binding.btnItemsCarrito.setOnClickListener { abrirFragmentCarrito() }

        return binding.root
    }

    private fun abrirFragmentCarrito() {

    }


    private fun cargarProductos() {
        val db = FirebaseFirestore.getInstance()

        db.collection("tienda")
            .get()
            .addOnSuccessListener { result ->
                val productos= mutableListOf<Producto>()//productos para el adapter
                for (document in result) {
                    // Accede a los datos del documento
                    val producto=Producto(//creo un producto
                        id_document=document.id,
                        precio_producto = document.getDouble("precio_producto")?:0.0,
                        url_img_producto = document.getString("url_img_producto")?:"",
                        nombre_producto = document.getString("nombre_producto")?:"",
                        descripcion_producto = document.getString("descripcion_producto")?:""
                    )
                    productos.add(producto)//agrego a productos para enviar al adapter
                    //add coleccion productos
                }

                val customAdapter = TiendaAdapter(//creo adapter
                    productos = productos,//envio productos para que se muestren en el tienda item
                    agregarAlCarrito = {// agrega o elimina la lista del carrito
                        productoSeleccionado->  atregarEliminarItemsCarrrito(productoSeleccionado)   }
                )
                //vinculacion del adapter con el recyclerview
                val recyclerView: RecyclerView = binding.noticiasRecycle
                recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                recyclerView.adapter = customAdapter

            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error al obtener documentos.", exception)
            }
    }
    private fun atregarEliminarItemsCarrrito(productoSeleccionado:Producto){
        val  existeItemCarrito = productosCarrito.find { it->it.id_document==productoSeleccionado.id_document }
        if(existeItemCarrito==null){//agrega una sola vez
            productosCarrito.add(productoSeleccionado)//agrega a la mutablelist
            nItemsCarrito++//incrementa contador de tiems o productos
            binding.btnItemsCarrito.text="VVer "+nItemsCarrito.toString()//modifica boton items carrito
        }else{//elimina carrito
            productosCarrito.remove(productoSeleccionado)
            nItemsCarrito--
            binding.btnItemsCarrito.text="VVer "+nItemsCarrito.toString()

        }
        onOFBtnCarrito()//muestra o oculta botn carrito

        Log.d("productosCar",productosCarrito.toString())
    }


    private fun onOFBtnCarrito(){
        if (productosCarrito.isEmpty()) {//oculta boton carrito cuando la lista esta vacia
            binding.btnItemsCarrito.visibility = View.GONE
            return
        }
            binding.btnItemsCarrito.visibility = View.VISIBLE

    }
}