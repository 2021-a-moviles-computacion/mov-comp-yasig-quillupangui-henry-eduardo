package com.example.a03_firebase

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Vista_Empresa : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var nombreEmpresaSeleccionado = ""
    var idEmpresaSeleccionado = ""
    lateinit var empresas :ArrayList<Empresa>
    lateinit var adaptador: ArrayAdapter<Empresa>
    lateinit var listEmpresasView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_empresas)
        listEmpresasView = findViewById(R.id.listView_Empresa)

        val arregloEmpresas = mutableListOf<Empresa>()
        val db = Firebase.firestore
        val referencia = db.collection("empresa")
        referencia.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val empresa = document.toObject(Empresa::class.java)
                empresa.id = document.id
                arregloEmpresas.add(empresa)
                adaptador.notifyDataSetChanged()
            }
        }.addOnFailureListener {
            Log.i("firebase-firestore", "Error leyendo coleccion")
        }
        empresas = arregloEmpresas as ArrayList<Empresa>
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, empresas)



        listEmpresasView.adapter = adaptador
        val botonCrearEmpresa = findViewById<Button>(R.id.btn_crear_Empresa)
        val botonVerSucursal = findViewById<Button>(R.id.btn_verSucursal)
        botonVerSucursal.isEnabled = false
        botonCrearEmpresa.setOnClickListener { abrirActividad(CrearEmpre::class.java) }
        registerForContextMenu(listEmpresasView)
        val infoEmpresa = findViewById<TextView>(R.id.txt_InfoEmpresa)
        adaptador.notifyDataSetChanged()
        listEmpresasView.setOnItemClickListener { _, _, position, _ ->
            botonVerSucursal.isEnabled = true
            val dirSelec = listEmpresasView.getItemAtPosition(position) as Empresa
            infoEmpresa.text = dirSelec.imprimirDatosEmpresa()
            botonVerSucursal.setOnClickListener {
                abrirActividadConParametros(
                    dirSelec.nombre!!,
                    dirSelec.id!!,
                    Vista_Sucursal::class.java
                )
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
        val referencia = db.collection("empresa")
        val empresaSeleccionado = listEmpresasView.getItemAtPosition(posicionItemSeleccionado) as Empresa
        nombreEmpresaSeleccionado = empresaSeleccionado.nombre!!
        idEmpresaSeleccionado = empresaSeleccionado.id!!
        listEmpresasView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("FIREBASE", "Nombre empresa: $nombreEmpresaSeleccionado")
            referencia.document(idEmpresaSeleccionado)
                .delete()
                .addOnSuccessListener { Log.i("firebase", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.i("firebase", "Error deleting document", e) }
            empresas.remove(empresaSeleccionado)
            adaptador.notifyDataSetChanged()
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()

        }
        return when (item.itemId) {
            R.id.menu_editar -> {
                abrirActividadConParametros(nombreEmpresaSeleccionado, idEmpresaSeleccionado, CrearEmpre::class.java)
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

    fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }

    private fun abrirActividadConParametros(empresa: String, idEmpresa: String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreEmpresa", empresa)
        intentExplicito.putExtra("idEmpresa", idEmpresa)
        startActivity(intentExplicito)
    }


}