package com.example.club_futbol_1.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.FragmentCarrritoBinding
import com.example.club_futbol_1.databinding.FragmentTiendaBinding
import com.example.club_futbol_1.model.Producto


class CarrritoFragment : Fragment() {

    private var _binding: FragmentCarrritoBinding?=null
    private val binding get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val productosCarrito = arguments?.getParcelableArrayList<Producto>("productosCarrito")

        Log.d("carritoconproductos",productosCarrito.toString())
        _binding = FragmentCarrritoBinding.inflate(inflater, container, false)

        binding.btnRegresar.setOnClickListener {
            findNavController().navigate(R.id.action_carrritoFragment_to_tiendaFragment,)
        }

        return binding.root

    }

}