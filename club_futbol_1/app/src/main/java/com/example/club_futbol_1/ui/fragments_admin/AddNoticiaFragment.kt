package com.example.club_futbol_1.ui.fragments_admin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.FragmentAddNoticiaBinding
import com.example.club_futbol_1.databinding.FragmentCarrritoBinding
import com.example.club_futbol_1.model.Noticia
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore


class AddNoticiaFragment : Fragment() {
    private var _binding: FragmentAddNoticiaBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAddNoticiaBinding.inflate(inflater,container,false)
        val noticiaEditar = arguments?.getParcelable<Noticia>("noticiaEditar")

        val editTexts = arrayOf(binding.tituloAddNoticiaET,binding.descripcionNoticiaET,binding.urlNoticiaET)
        listenersEditEtexts(editTexts)
        if(noticiaEditar==null) {//Agrega la noticia
            binding.agregarEditarBtn.setOnClickListener {
                val campoVacio = validarEditText(editTexts)
                if(campoVacio==null){//todos los edit text  llenos
                    agregarNoticia()
                }

            }
        }else{//edita la noticia
            //cambio el disenio del boton y el texto
            binding.agregarEditarBtn.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.verde))
            binding.agregarEditarBtn.text = "Editar"
            binding.agregarEditarBtn.setOnClickListener {
                val campoVacio = validarEditText(editTexts)
                if(campoVacio==null){//todos los edit text  llenos
                    editarNoticia()
                }

            }
        }


        return binding.root
    }

    private fun editarNoticia() {


    }

    private fun listenersEditEtexts(editTexts:Array<EditText>) {
        for (et in editTexts) {//evento de edit text
            et.setOnFocusChangeListener { _, foco ->
                if (!foco) {
                    if (et.text.toString().isEmpty()) {
                        et.setError("Campo requerido")
                    }
                }
            }
        }
    }


    private fun validarEditText(editTexts: Array<EditText>):EditText? {//retorna null si todos los campos estan llenos
            return editTexts.find { et->
                val editTextVacio=et.text.toString().isEmpty()
                if (editTextVacio){
                    et.setError("Campo obligatorio")///indica al usuario que un campo esta vacio
                }
                editTextVacio
            }//retorna null  o un edittext
    }


    private fun agregarNoticia() {

        val db = FirebaseFirestore.getInstance()
        val nuevaNoticia = hashMapOf(
            "titulo" to binding.tituloAddNoticiaET.text.toString(),
            "descripcion" to binding.descripcionNoticiaET.text.toString(),
            "urlImg" to binding.urlNoticiaET.text.toString()
        )
        db.collection("noticias").add(nuevaNoticia)
            .addOnSuccessListener { documentoReference->

                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder
                    .setMessage("Se ha agregado una nueva noticia\n" +
                            "Desea agregar otra?")
                    .setTitle("Info")
                    .setPositiveButton("Si") { dialog, which ->
                        limpiarEditTexts(arrayOf(binding.tituloAddNoticiaET,binding.descripcionNoticiaET,binding.urlNoticiaET))
                    }
                    .setNegativeButton("No") { dialog, which ->
                        // navega a las noticias
                        val navController = findNavController()
                        navController.navigate(R.id.action_addNoticiaFragment_to_noticiasEquipoFragment)                    }

                val dialog: AlertDialog = builder.create()
                dialog.show()//mestra el dialogo

            }.addOnFailureListener { error->
            Log.d("agregar",error.toString())//imprime error
            }

    }

    private fun limpiarEditTexts(editTexts: Array<EditText>) {
        editTexts.forEach { eT->eT.text.clear() }

    }

}