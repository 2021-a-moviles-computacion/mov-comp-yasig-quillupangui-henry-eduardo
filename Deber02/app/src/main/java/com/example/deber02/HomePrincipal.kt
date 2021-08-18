@file:JvmName("InicioKt")

package com.example.deber02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


var recyclerInicio : RecyclerView? = null
var recyclerPlaylistAmaras: RecyclerView? = null
var recyclerRecienEscuchadas : RecyclerView? = null
var recyclerCategorias : RecyclerView? = null
var recyclerGeneros : RecyclerView? = null
var recyclerEscuchadoRecientemente : RecyclerView? = null
var recyclerListaExitos : RecyclerView? = null
/**
 * A simple [Fragment] subclass.
 * Use the [HomePrincipal.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePrincipal : Fragment() {
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomePrincipal.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
            HomePrincipal().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentHome = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerInicio = fragmentHome.findViewById(R.id.home_recycler)
        recyclerPlaylistAmaras = fragmentHome.findViewById(R.id.playlist_que_amaras_recycler)
        recyclerRecienEscuchadas = fragmentHome.findViewById(R.id.recien_escuchadas_recycler)
        recyclerCategorias = fragmentHome.findViewById(R.id.categorias_recycler)
        recyclerGeneros = fragmentHome.findViewById(R.id.generos_recycler)
        recyclerEscuchadoRecientemente = fragmentHome.findViewById(R.id.ecuchado_recientemente_recycler)
        recyclerListaExitos = fragmentHome.findViewById(R.id.lista_exitos_recycler)

        return fragmentHome
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listaItemsWelcome = arrayListOf<Canciones>()
        listaItemsWelcome.add(Canciones("hola","IMAGEN"))
        listaItemsWelcome.add(Canciones("hola","IMAGEN"))
        listaItemsWelcome.add(Canciones("hola","IMAGEN"))
        listaItemsWelcome.add(Canciones("hola","IMAGEN"))
        listaItemsWelcome.add(Canciones("hola","IMAGEN"))
        listaItemsWelcome.add(Canciones("hola","IMAGEN"))
        listaItemsWelcome.add(Canciones("DD","IMG"))
        listaItemsWelcome.add(Canciones("DD","IMG"))

        iniciarRecyclerViewWelcome(listaItemsWelcome,this, recyclerInicio!!,AdaptadorInicio(this,listaItemsWelcome, recyclerInicio!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerPlaylistAmaras!!,AdaptadorHome(this,listaItemsWelcome,recyclerPlaylistAmaras!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerRecienEscuchadas!!,AdaptadorFavoritos(this,listaItemsWelcome, recyclerRecienEscuchadas!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerCategorias!!,AdaptadorHome(this,listaItemsWelcome, recyclerCategorias!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerGeneros!!,AdaptadorInicio(this,listaItemsWelcome, recyclerGeneros!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerEscuchadoRecientemente!!,AdaptadorHome(this,listaItemsWelcome, recyclerEscuchadoRecientemente!!))
        iniciarRecyclerView(listaItemsWelcome,this, recyclerListaExitos!!,AdaptadorFavoritos(this,listaItemsWelcome,recyclerListaExitos!!))
    }

    fun iniciarRecyclerViewWelcome(lista: List<*>, actividad: HomePrincipal, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = LinearLayoutManager(actividad.context, LinearLayoutManager.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()
    }

    fun iniciarRecyclerView(lista: List<*>, actividad: HomePrincipal, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(actividad.context, LinearLayoutManager.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()
    }
}