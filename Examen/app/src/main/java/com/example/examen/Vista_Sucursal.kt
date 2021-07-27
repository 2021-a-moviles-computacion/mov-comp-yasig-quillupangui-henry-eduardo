package com.example.examen

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

class Vista_Sucursal : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var tituloSucursalSeleccionada = ""
    var nombreEmpresa = ""
    var idEmpresa = 0
    lateinit var sucursales :ArrayList<Sucursal>
    lateinit var adaptador: ArrayAdapter<Sucursal>
    lateinit var listSucursalesView: ListView

    override fun onStart() {
        super.onStart()
        BaseDatos.Tablas = SQLiteHelper(this)
        sucursales = BaseDatos.Tablas!!.consultarTodasSucursalesdeEmpresas(idEmpresa)
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, sucursales)
        listSucursalesView = findViewById<ListView>(R.id.listview_Sucursales)
        adaptador.notifyDataSetChanged()
        listSucursalesView.adapter = adaptador

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_sucursales)
        val txtEmpresa = findViewById<TextView>(R.id.txt_NombreSucursalE)
        nombreEmpresa = intent.getStringExtra("nombreEmpresa").toString()
        idEmpresa = intent.getIntExtra("idEmpresa", 0)
        txtEmpresa.text = nombreEmpresa
        onStart()
        val botonCrearSucursal = findViewById<Button>(R.id.btn_crear_Sucursal)
        botonCrearSucursal.setOnClickListener {
            abrirActividadConParametros(nombreEmpresa, idEmpresa, "", CrearSucursal::class.java)
        }
        registerForContextMenu(listSucursalesView)
        val infoSucursal = findViewById<TextView>(R.id.txt_infoSucursal)
        listSucursalesView.setOnItemClickListener { _, _, position, _ ->
            val sucurSelect = listSucursalesView.getItemAtPosition(position) as Sucursal
            infoSucursal.text = sucurSelect.imprimirDatosSucursal()
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

        BaseDatos.Tablas = SQLiteHelper(this)
        onStart()
        val sucursalSeleccionada = listSucursalesView.getItemAtPosition(posicionItemSeleccionado) as Sucursal
        tituloSucursalSeleccionada = sucursalSeleccionada.nombre
        listSucursalesView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("bdd", "Nombre Sucursal: $tituloSucursalSeleccionada")
            BaseDatos.Tablas!!.eliminarSucursal(tituloSucursalSeleccionada)
            onStart()
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

    private fun abrirActividadConParametros(empresa: String, idEmpresa: Int, nombre: String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreEmpresa", empresa)
        intentExplicito.putExtra("idEmpresa", idEmpresa)
        intentExplicito.putExtra("nombreSucursal", nombre)
        startActivity(intentExplicito)
    }
}