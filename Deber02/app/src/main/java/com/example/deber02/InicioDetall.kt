package com.example.deber02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var recyclerPlaylist : RecyclerView? = null
/**
 * A simple [Fragment] subclass.
 * Use the [InicioDetall.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioDetall : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentHomeDetalles = inflater.inflate(R.layout.fragment_detalles_home, container, false)
        recyclerPlaylist = fragmentHomeDetalles.findViewById(R.id.recycler_caciones_reproductor)
        // Inflate the layout for this fragment
        return fragmentHomeDetalles
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listaItemsPlaylist = arrayListOf<Canciones>()
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))
        listaItemsPlaylist.add(Canciones("hhh","IMG"))


        iniciarRecyclerView(listaItemsPlaylist,this, recyclerPlaylist!!,AdaptadorListaCanciones(this,listaItemsPlaylist, recyclerPlaylist!!))
    }

    fun iniciarRecyclerView(lista: List<*>, actividad:InicioDetall, recyclerView: RecyclerView, adaptador:RecyclerView.Adapter<*>){
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(actividad.context, LinearLayoutManager.VERTICAL, false)
        adaptador.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InicioDetall.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InicioDetall().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}