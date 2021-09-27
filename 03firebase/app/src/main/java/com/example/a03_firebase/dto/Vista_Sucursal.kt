package com.example.a03_firebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Vista_Sucursal : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var tituloSucursalSeleccionada = ""
    var idSucursalSeleccionada = ""
    var nombreEmpresa = ""
    var idEmpresa = ""
    var latitud = 0.0
    var longitud =0.0
    lateinit var sucursales :ArrayList<Sucursal>
    lateinit var adaptador: ArrayAdapter<Sucursal>
    lateinit var listSucursalesView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_sucursales)
        idEmpresa = intent.getStringExtra("idEmpresa").toString()
        listSucursalesView = findViewById(R.id.listview_Sucursales)
        val botonVerUbicacion = findViewById<Button>(R.id.btn_ver_ubicacion)
        //--------------------------------------------------------------------
        val arregloSucursales = mutableListOf<Sucursal>()
        val db = Firebase.firestore
        val referencia = db.collection("sucursal")
        referencia.whereEqualTo("empresa", idEmpresa).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val sucursal = document.toObject(Sucursal::class.java)
                sucursal.id = document.id
                arregloSucursales.add(sucursal)
                adaptador.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.i("firebase-firestore", "Error leyendo coleccion")
        }
        sucursales= arregloSucursales as ArrayList<Sucursal>
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1,sucursales)

        listSucursalesView.adapter = adaptador
        val txtEmpresa = findViewById<TextView>(R.id.txt_NombreSucursalE)
        nombreEmpresa = intent.getStringExtra("nombreEmpresa").toString()

        txtEmpresa.text = nombreEmpresa
        val botonCrearSucursal = findViewById<Button>(R.id.btn_crear_Sucursal)
        botonCrearSucursal.setOnClickListener {
            abrirActividadConParametros(nombreEmpresa, idEmpresa, "", CrearSucursal::class.java)
        }
        registerForContextMenu(listSucursalesView)
        val infoSucursal = findViewById<TextView>(R.id.txt_infoSucursal)
        listSucursalesView.setOnItemClickListener { _, _, position, _ ->
            val sucurSelect = listSucursalesView.getItemAtPosition(position) as Sucursal
            latitud=sucurSelect.latitud!!
            longitud=sucurSelect.longitud!!
            botonVerUbicacion.visibility = VISIBLE
            infoSucursal.text = sucurSelect.imprimirDatosSucursal()
            botonVerUbicacion.setOnClickListener {
                Log.i("firebase", "$latitud ; $longitud ")
                abrirUbicacion(latitud,longitud,sucurSelect.nombreSucu!!, Ubicacion::class.java)
            }
            return@setOnItemClickListener
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo 
        posicionItemSeleccionado = info.position

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        adaptador.notifyDataSetChanged()
        val db = Firebase.firestore
        val referencia = db.collection("sucursal")
        val sucursalSeleccionada = listSucursalesView.getItemAtPosition(posicionItemSeleccionado) as Sucursal
        tituloSucursalSeleccionada = sucursalSeleccionada.nombreSucu!!
        idSucursalSeleccionada = sucursalSeleccionada.id!!
        listSucursalesView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("firebase", "Nombre Sucursal: $tituloSucursalSeleccionada")
            referencia.document(idSucursalSeleccionada)
                .delete()
                .addOnSuccessListener { Log.i("firebase", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.i("firebase", "Error deleting document", e) }
            sucursales.remove(sucursalSeleccionada)
            adaptador.notifyDataSetChanged()
            Toast.makeText(this, "Eliminada", Toast.LENGTH_SHORT).show()

            }
        return when (item.itemId) {
            R.id.menu_editar -> {
                abrirActividadConParametros(nombreEmpresa, idEmpresa, tituloSucursalSeleccionada, CrearSucursal::class.java)
                return true
            }
            R.id.menu_eliminar -> {
                val advertencia = AlertDialog.Builder(this)
                advertencia.setTitle("Eliminar")
                advertencia.setMessage("Seguro de eliminar?")
                advertencia.setNegativeButton(
                    "Cancelar",
                    DialogInterface.OnClickListener(function = cancelarClick)
                )
                advertencia.setPositiveButton(
                    "Eliminar", DialogInterface.OnClickListener(
                        function = eliminarClick
                    )
                )
                advertencia.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    private fun abrirUbicacion(latitud:Double, longitud: Double,titulo:String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("titulo", titulo)
        intentExplicito.putExtra("latitud", latitud)
        intentExplicito.putExtra("longitud", longitud)
        startActivity(intentExplicito)
    }

    private fun abrirActividadConParametros(empresa: String, idiEmpresa: String, nombre: String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreEmpresa", empresa)
        intentExplicito.putExtra("idEmpresa", idiEmpresa)
        intentExplicito.putExtra("nombreSucursal", nombre)
        startActivity(intentExplicito)
    }
}

