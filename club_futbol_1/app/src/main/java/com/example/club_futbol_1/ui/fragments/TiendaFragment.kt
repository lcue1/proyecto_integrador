package com.example.club_futbol_1.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.FragmentNoticiasEquipoBinding
import com.example.club_futbol_1.databinding.FragmentTiendaBinding


class TiendaFragment : Fragment() {
    private var _binding:FragmentTiendaBinding?=null
    private val binding get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTiendaBinding.inflate(inflater, container, false)
        return binding.root
    }


}