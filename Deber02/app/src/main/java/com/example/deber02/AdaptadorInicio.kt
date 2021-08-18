package com.example.deber02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView



class AdaptadorInicio (private val actividad: Home, private val listaItemsHome: List<*>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<AdaptadorInicio.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreItem: TextView
        val imagenItem: ImageView
        val btnItem: CardView

        init {
            nombreItem = view.findViewById(R.id.txt_itemCard_home)
            imagenItem = view.findViewById(R.id.img_item_home)
            btnItem = view.findViewById(R.id.cardview_item_menu_inicio)

            btnItem.setOnClickListener {
                val cambioFragment = DetallesHome()
                actividad.activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.frame_container,cambioFragment).commit()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorInicio.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_home_layout, //Definimos la vista del recycler view
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenador = listaItemsHome[position]
    }

    override fun getItemCount(): Int {
        return listaItemsHome.size
    }

}