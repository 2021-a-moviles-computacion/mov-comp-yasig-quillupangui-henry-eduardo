package com.example.deber02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorHome(private val actividad: Home, private val listaItemsWelcome: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorHome.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreItem: TextView
        val imagenItem: ImageView
        val btnItem: CardView

        init {
            nombreItem = view.findViewById(R.id.txt_tituloCancion)
            imagenItem = view.findViewById(R.id.img_cancionPlaylist)
            btnItem = view.findViewById(R.id.cardview_item_InicioPlaylist)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorHome.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_welcome, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorHome.MyViewHolder, position: Int) {
        val entrenador = listaItemsWelcome[position]
        holder.nombreItem.text = "Item $position"
    }

    override fun getItemCount(): Int {
    return listaItemsWelcome.size
    }
}