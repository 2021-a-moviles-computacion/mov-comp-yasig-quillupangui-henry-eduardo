package com.example.deber02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class AdaptadorBuscador (private val actividad: Buscador, private val listaItemsSearch: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorBuscador.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnItem: ImageButton

        init {
            btnItem = view.findViewById(R.id.btn_item_buscar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorBuscador.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_search,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorBuscador.MyViewHolder, position: Int) {
        val album = listaItemsSearch[position]

    }

    override fun getItemCount(): Int {
        return listaItemsSearch.size
    }
}