package com.example.club_futbol_1.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.club_futbol_1.R
import com.example.club_futbol_1.model.Noticia
import com.example.club_futbol_1.model.Producto
import com.squareup.picasso.Picasso


class TiendaAdapter(private val productos: MutableList<Producto>,val agregarAlCarrito:(producto:Producto)->Unit) :
    RecyclerView.Adapter<TiendaAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id_producto:String
        val precio: TextView
        val nombre_producto: TextView
        val descripcion_producto: TextView
        val imagen_producto: ImageView
        var url_img_producto: String
        val btn_carrito: ImageButton

        init {
            // Define click listener for the ViewHolder's View
            id_producto=""
            precio = view.findViewById(R.id.precio_producto)
            nombre_producto = view.findViewById(R.id.titulo_producto)
            descripcion_producto = view.findViewById(R.id.descripcion_producto)
            imagen_producto = view.findViewById(R.id.imagen_producto)
            url_img_producto=""
            btn_carrito = view.findViewById(R.id.btn_carrito)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tienda_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.id_producto=productos[position].id_document
        viewHolder.precio.text = productos[position].precio_producto.toString()+"$"
        viewHolder.nombre_producto.text = productos[position].nombre_producto

        Picasso.get()
            .load(productos[position].url_img_producto)
            .into(viewHolder.imagen_producto)
        viewHolder.url_img_producto = productos[position].url_img_producto
        viewHolder.btn_carrito.setOnClickListener {
            val precioSinSimboloDolar=viewHolder.precio.text.toString().dropLast(1)
            val prosucto =Producto(
                id_document=viewHolder.id_producto,
                precio_producto = precioSinSimboloDolar.toDouble(),
                url_img_producto = viewHolder.url_img_producto,
                nombre_producto = viewHolder.nombre_producto.text.toString(),
                descripcion_producto = viewHolder.descripcion_producto.text.toString()
            )
            agregarAlCarrito(prosucto)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = productos.size

}