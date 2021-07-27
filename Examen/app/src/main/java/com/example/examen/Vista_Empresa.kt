package com.example.examen

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Vista_Empresa : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var nombreEmpresaSeleccionado = ""
    var idEmpresaSeleccionado = 0
    lateinit var empresas :ArrayList<Empresa>
    lateinit var adaptador: ArrayAdapter<Empresa>
    lateinit var listEmpresasView: ListView

    override fun onStart() {
        super.onStart()
        BaseDatos.Tablas = SQLiteHelper(this)
        empresas = BaseDatos.Tablas!!.consultarTodaEmpresa()
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, empresas)
        listEmpresasView = findViewById(R.id.listView_Empresa)
        adaptador.notifyDataSetChanged()
        listEmpresasView.adapter = adaptador
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_empresas)
        BaseDatos.Tablas = SQLiteHelper(this)
        onStart()
        val botonCrearEmpresa = findViewById<Button>(R.id.btn_crear_Empresa)
        val botonVerSucursal = findViewById<Button>(R.id.btn_verSucursal)
        botonVerSucursal.isEnabled = false
        botonCrearEmpresa.setOnClickListener { abrirActividad(CrearEmpre::class.java) }
        registerForContextMenu(listEmpresasView)
        val infoEmpresa = findViewById<TextView>(R.id.txt_InfoEmpresa)

        listEmpresasView.setOnItemClickListener { _, _, position, _ ->
            botonVerSucursal.isEnabled = true
            val dirSelec = listEmpresasView.getItemAtPosition(position) as Empresa
            infoEmpresa.text = dirSelec.imprimirDatosEmpresa()
            botonVerSucursal.setOnClickListener {
                abrirActividadConParametros(
                    dirSelec.nombre,
                    dirSelec.id,
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

        BaseDatos.Tablas = SQLiteHelper(this)
        onStart()
        val empresaSeleccionado = listEmpresasView.getItemAtPosition(posicionItemSeleccionado) as Empresa
        nombreEmpresaSeleccionado = empresaSeleccionado.nombre
        idEmpresaSeleccionado = empresaSeleccionado.id
        listEmpresasView.adapter = adaptador
        val cancelarClick = { _: DialogInterface, _: Int ->
            Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_SHORT).show()
        }
        val eliminarClick = { _: DialogInterface, _: Int ->
            Log.i("bdd", "Nombre empresa: $nombreEmpresaSeleccionado")
            BaseDatos.Tablas!!.eliminarEmpresa(nombreEmpresaSeleccionado)
            onStart()
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

    private fun abrirActividadConParametros(empresa: String, idEmpresa: Int, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreEmpresa", empresa)
        intentExplicito.putExtra("idEmpresa", idEmpresa)
        startActivity(intentExplicito)
    }


}