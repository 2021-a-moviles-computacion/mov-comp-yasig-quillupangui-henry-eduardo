package com.example.deber02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class AdaptadorLibreria (private val actividad: Library, private val listaItemsLibrary: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorLibreria.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnItem: LinearLayout
        init {
            btnItem = view.findViewById(R.id.btn_favoritos_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorLibreria.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.artist_item, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorLibreria.MyViewHolder, position: Int) {
        val album = listaItemsLibrary[position]
    }

    override fun getItemCount(): Int {
        return listaItemsLibrary.size
    }
}