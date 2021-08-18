package com.example.deber02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorFavoritos (private val actividad: Home, private val listaItemsRecently: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorFavoritos.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreItem: TextView
        val imagenItem: ImageView
        val btnItem: CardView

        init {
            nombreItem = view.findViewById(R.id.txt_itemCard_recently)
            imagenItem = view.findViewById(R.id.img_item_recently)
            btnItem = view.findViewById(R.id.cardview_item_recientes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorFavoritos.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_recently_played, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenador = listaItemsRecently[position]

    }

    override fun getItemCount(): Int {
        return listaItemsRecently.size
    }


}