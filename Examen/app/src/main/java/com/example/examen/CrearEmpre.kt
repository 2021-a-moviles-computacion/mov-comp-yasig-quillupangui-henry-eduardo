package com.example.examen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class CrearEmpre : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseDatos.Tablas = SQLiteHelper(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_empresa)
        val nombre = findViewById<TextView>(R.id.txt_ingresoNombre)
        val ubicacion = findViewById<TextView>(R.id.txt_ingresoUbicacion)
        val numSucur = findViewById<TextView>(R.id.txt_ingresoNumSucursales)
        val fecha = findViewById<TextView>(R.id.txt_ingresoDate)
        val activo = findViewById<CheckBox>(R.id.check_actividad)
        val guardar = findViewById<Button>(R.id.btn_guardarEmpresa)
        val datePickera = findViewById<DatePicker>(R.id.datePicker1)
        val checkFecha = findViewById<CheckBox>(R.id.checkFecha)
        val calendar = Calendar.getInstance()
        checkFecha.visibility = INVISIBLE
        datePickera.visibility = INVISIBLE
        fecha.setOnClickListener {
            checkFecha.isChecked = false
            fecha.visibility = INVISIBLE
            datePickera.maxDate = calendar.timeInMillis
            checkFecha.visibility = VISIBLE
            datePickera.visibility = VISIBLE
            checkFecha.setOnClickListener {
                calendar.set(datePickera.year, datePickera.month, datePickera.dayOfMonth)
                fecha.text = android.text.format.DateFormat.format("MM/dd/yyyy", calendar)
                datePickera.visibility = INVISIBLE
                checkFecha.visibility = INVISIBLE
                fecha.visibility = VISIBLE
            }
        }

        val nombreEmpresa = intent.getStringExtra("nombreEmpresa")


        if (nombreEmpresa != null) {
            //Cambio Título de la Actividad de Registrar a Actualizar
            val tituloActualizarEmpresa = findViewById<TextView>(R.id.txt_registroEmpresa_Tit)
            tituloActualizarEmpresa.text = getString(R.string.actualizar_Empresa)

            val empresaParaEditar = BaseDatos.Tablas!!.consultarEmpresaPorNombre(nombreEmpresa)
            val idActualizar = empresaParaEditar.id
            nombre.text = empresaParaEditar.nombre
            ubicacion.text = empresaParaEditar.ubicacion
            numSucur.text = empresaParaEditar.numSucursales.toString()
            fecha.text = empresaParaEditar.fundacion
            if (empresaParaEditar.actividad == 1) activo.isChecked = true

            guardar.setOnClickListener {
                val nombreActualizado = nombre.text.toString()
                val ubicacionActualizada = ubicacion.text.toString()
                val numSucursalesActualizada = numSucur.text.toString()
                val activoInt = if (activo.isChecked) 1 else 0

                if (!revisarTextLlenos(nombreActualizado,ubicacionActualizada , numSucursalesActualizada, fecha)) {
                    BaseDatos.Tablas!!.actualizarEmpresa(
                        nombreActualizado,
                        ubicacionActualizada,
                        fecha.text.toString(),
                        numSucursalesActualizada.toInt(),
                        activoInt,
                        idActualizar
                    )
                    Toast.makeText(this, "Empresa actualizada", Toast.LENGTH_SHORT).show()
                    abrirActividad(Vista_Empresa::class.java)
                }
            }

        } else {
            guardar.setOnClickListener {
                val nombreAGuardar = nombre.text.toString()
                val ubicacionAGuardar = ubicacion.text.toString()
                val numSucursalesAGuardar = numSucur.text.toString()
                val activoInt = if (activo.isChecked) 1 else 0
                if (!revisarTextLlenos(nombreAGuardar, ubicacionAGuardar, numSucursalesAGuardar, fecha)) {
                    BaseDatos.Tablas!!.crearEmpresa(
                        nombreAGuardar,
                        ubicacionAGuardar,
                        fecha.text.toString(),
                        numSucursalesAGuardar.toInt(),
                        activoInt
                    )
                    Toast.makeText(this, "Empresa guardada", Toast.LENGTH_SHORT).show()
                    abrirActividad(Vista_Empresa::class.java)
                }
            }
        }
    }


    private fun  revisarTextLlenos(nombre: String, ubicacion: String, numSucur: String, fecha: TextView): Boolean {
        return if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(ubicacion) || TextUtils.isEmpty(numSucur) || fecha.text.equals("MM/DD/YYYY")) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            true
        } else {
            if(numSucur.toInt()<1){
                Toast.makeText(this, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
                true
            }else{
                false
            }
        }

    }

    private fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }
}