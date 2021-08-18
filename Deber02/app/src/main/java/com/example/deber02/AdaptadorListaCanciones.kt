package com.example.deber02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorListaCanciones (private val actividad: InicioDetall, private val listaItemsPlaylist: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorListaCanciones.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreCancionItem: TextView
        val artistaCancionItem:TextView
        val imagenCancionItem: ImageView
        val btnItem: LinearLayout

        init {
            nombreCancionItem = view.findViewById(R.id.txt_tituloCancion)
            artistaCancionItem = view.findViewById(R.id.txt_artistaCancionPlaylist)
            imagenCancionItem = view.findViewById(R.id.img_cancionPlaylist)
            btnItem = view.findViewById(R.id.layout_cancionPlaylist)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorListaCanciones.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_playlist,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptadorListaCanciones.MyViewHolder, position: Int) {
        val cancionPlaylist = listaItemsPlaylist[position]
    }

    override fun getItemCount(): Int {
        return listaItemsPlaylist.size
    }

}