package com.example.club_futbol_1.ui.fragments_admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.club_futbol_1.R
import com.example.club_futbol_1.databinding.FragmentAddNoticiaBinding
import com.example.club_futbol_1.databinding.FragmentCarrritoBinding
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
        agregarListeners(arrayOf(binding.tituloAddNoticiaET,binding.descripcionNoticiaET,binding.urlNoticiaET))



        return binding.root
    }

    private fun agregarListeners(editTexts: Array<EditText>) {
        for (et in editTexts) {//evento de edit text
            et.setOnFocusChangeListener { _, foco ->
                if (!foco) {
                    if (et.text.toString().isEmpty()) {
                        et.setError("Campo requerido")
                    }
                }
            }
        }

        //evento botonagregar
        binding.addNoticiaBtn.setOnClickListener {
            val campoVacio = validarEditText(arrayOf(binding.tituloAddNoticiaET,binding.descripcionNoticiaET,binding.urlNoticiaET))
            if(campoVacio==null){//todos los edit text  llenos
                agregarNoticia()
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
                Log.d("agregar",documentoReference.toString())


            }.addOnFailureListener { error->
                Log.d("agregar",error.toString())//imprime error
            }

    }

}