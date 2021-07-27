package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlin.collections.ArrayList

class CrearSucursal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sucursal)
        val nombreEmpresa = intent.getStringExtra("nombreEmpresa")
        val idEmpresa = intent.getIntExtra("idEmpresa", 0)
        val txtEmpresa = findViewById<TextView>(R.id.txt_nombreEmpresa)
        txtEmpresa.text = nombreEmpresa

        val txtnombreSucursal = findViewById<TextView>(R.id.txt_nombreSucursal)
        val txtAnioApertura = findViewById<TextView>(R.id.txt_ingresoAnio)
        val txtNumeroEmpleados = findViewById<TextView>(R.id.txt_empleados)
        val txtCategoria = findViewById<TextView>(R.id.txt_selectCategoria)
        val valoracion = findViewById<RatingBar>(R.id.ratingBar)
        val guardar = findViewById<Button>(R.id.btn_guardarSucursal)
        val selectCategorias = ArrayList<Int>()
        val categoriaSeleccionada = BooleanArray(6) { false }
        txtCategoria.setOnClickListener {
            val opciones = resources.getStringArray(R.array.categorias_dialogo)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Categorias")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                opciones, categoriaSeleccionada
            ) { _, which, isChecked ->
                if (isChecked) {
                    selectCategorias.add(which)
                    selectCategorias.sort()
                } else {
                    selectCategorias.remove(which)
                }
                Log.i("bdd", "$which $isChecked")
            }
            builder.setPositiveButton("OK") { _, _ ->
                var categorias = ""
                selectCategorias.forEach { i -> categorias = categorias + opciones[i] + "/" }
                if (categorias == "") {
                    txtCategoria.text = getString(R.string.seleccione)
                } else {
                    txtCategoria.text = categorias
                }
            }
            builder.show()
        }

        val nombreSucursal = intent.getStringExtra("nombreSucursal")

        if (nombreSucursal != null && nombreSucursal != "") {
            Log.i("bdd","$nombreSucursal")

            val nombreActualizarEmpresa = findViewById<TextView>(R.id.txt_registroSucursal)
            nombreActualizarEmpresa.text = getString(R.string.actualizar_Sucursal)

            val sucursalParaEditar = BaseDatos.Tablas!!.consultarSucursalPorNombre(nombreSucursal)
            val idActualizar = sucursalParaEditar.id
            txtnombreSucursal.text = sucursalParaEditar.nombre
            txtAnioApertura.text = sucursalParaEditar.anioApertura.toString()
            txtNumeroEmpleados.text = sucursalParaEditar.numeroEmpleados.toString()
            valoracion.rating = sucursalParaEditar.valoracion
            txtCategoria.text = sucursalParaEditar.categoria

            guardar.setOnClickListener {
                val nombreActualizado = txtnombreSucursal.text.toString()
                val anioActualizada = txtAnioApertura.text.toString()
                val numeroEmpleadoActualizada = txtNumeroEmpleados.text.toString()
                val valoracionActualizada = valoracion.rating

                if (!revisarText(nombreActualizado, anioActualizada, numeroEmpleadoActualizada, txtCategoria)) {
                    BaseDatos.Tablas!!.actualizarSucursal(
                        nombreActualizado, anioActualizada.toInt(),
                        numeroEmpleadoActualizada.toInt(), valoracionActualizada, txtCategoria.text.toString(), idActualizar
                    )
                    Toast.makeText(this, "Sucursal Actualizada", Toast.LENGTH_SHORT).show()
                    if (nombreEmpresa != null) {
                        abrirActividadConParametros(nombreEmpresa, idEmpresa, Vista_Sucursal::class.java)
                    }
                }
            }

        } else {
            guardar.setOnClickListener {
                val tituloAGuardar = txtnombreSucursal.text.toString()
                val anioAGuardar = txtAnioApertura.text.toString()
                val numeroEmpleadoAGuardar = txtNumeroEmpleados.text.toString()
                val valoracionNum = valoracion.rating
                if (!revisarText(tituloAGuardar, anioAGuardar, numeroEmpleadoAGuardar, txtCategoria)) {
                    BaseDatos.Tablas!!.crearSucursal(
                        tituloAGuardar, anioAGuardar.toInt(),
                        numeroEmpleadoAGuardar.toInt(), valoracionNum, txtCategoria.text.toString(), idEmpresa
                    )
                    Toast.makeText(this, "Sucursal guardada", Toast.LENGTH_SHORT).show()
                    if (nombreEmpresa != null) {
                        abrirActividadConParametros(nombreEmpresa, idEmpresa, Vista_Sucursal::class.java)
                    }
                }
            }
        }
    }

    private fun revisarText(nombre: String, anio: String, numeroEmpleados: String, categoria: TextView): Boolean {
        return if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(anio) || TextUtils.isEmpty(numeroEmpleados) || categoria.text.equals("Seleccione:")) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            true
        } else {
            if (anio.toInt() > 2021 || anio.toInt() < 1 || numeroEmpleados.toInt() < 1) {
                Toast.makeText(this, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }
    }

    private fun abrirActividadConParametros(empresa: String, idEmpresa: Int, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreEmpresa", empresa)
        intentExplicito.putExtra("idEmpresas", idEmpresa)
        startActivity(intentExplicito)
    }

}