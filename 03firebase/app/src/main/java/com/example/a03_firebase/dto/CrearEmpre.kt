package com.example.a03_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CrearEmpre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_empresa)
        val nombre = findViewById<TextView>(R.id.txt_ingresoNombre)
        val ubicacion = findViewById<TextView>(R.id.txt_ingresoUbicacion)
        val numSucursales = findViewById<TextView>(R.id.txt_ingresoNumSucursales)
        val fecha = findViewById<TextView>(R.id.txt_ingresoDate)
        val activo = findViewById<CheckBox>(R.id.check_activo)
        val guardar = findViewById<Button>(R.id.btn_guardarEmpre)
        val datePickera = findViewById<DatePicker>(R.id.datePicker1)
        val checkFecha = findViewById<CheckBox>(R.id.checkFecha)
        val calendar = Calendar.getInstance()
        checkFecha.visibility = View.INVISIBLE
        datePickera.visibility = View.INVISIBLE
        fecha.setOnClickListener {
            checkFecha.isChecked = false
            fecha.visibility = View.INVISIBLE
            datePickera.maxDate = calendar.timeInMillis
            checkFecha.visibility = View.VISIBLE
            datePickera.visibility = View.VISIBLE
            checkFecha.setOnClickListener {
                calendar.set(datePickera.year, datePickera.month, datePickera.dayOfMonth)
                fecha.text = android.text.format.DateFormat.format("MM/dd/yyyy", calendar)
                datePickera.visibility = View.INVISIBLE
                checkFecha.visibility = View.INVISIBLE
                fecha.visibility = View.VISIBLE
            }
        }

        val nombreEmpresa = intent.getStringExtra("nombreEmpresa")
        val idEmpresa = intent.getStringExtra("idEmpresa")


        if (nombreEmpresa != null) {

            val tituloActualizarEmpresa = findViewById<TextView>(R.id.txt_registroEmpresa_Tit)
            tituloActualizarEmpresa.text = getString(R.string.actualizar_Empresa)

            val db = Firebase.firestore
            val referencia = db.collection("empresa").document(idEmpresa!!)
            referencia.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val empresaParaEditar = document.toObject(Empresa::class.java)
                        nombre.text = empresaParaEditar?.nombre
                        ubicacion.text = empresaParaEditar?.ubicacion
                        numSucursales.text = empresaParaEditar?.numSucursales.toString()
                        fecha.text = empresaParaEditar?.fundacion
                        if (empresaParaEditar?.activo == 1) activo.isChecked = true
                    } else {
                        Log.d("firebase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebase", "get failed with ", exception)
                }


            guardar.setOnClickListener {
                val nombreActualizado = nombre.text.toString()
                val ubicacionActualizada = ubicacion.text.toString()
                val numSucursalesActualizada = numSucursales.text.toString()
                val activoInt = if (activo.isChecked) 1 else 0

                if (!revisarTextLlenos(nombreActualizado, ubicacionActualizada, numSucursalesActualizada, fecha)) {
                    val empresaActualizado = hashMapOf<String, Any>(
                        "nombre" to nombreActualizado,
                        "ubicacion" to ubicacionActualizada,
                        "fundacion" to fecha.text.toString(),
                        "numSucursales" to numSucursalesActualizada.toInt(),
                        "activo" to activoInt
                    )
                    referencia.update(empresaActualizado)
                        .addOnSuccessListener { Log.i("firebase", "Transaccion completa") }
                        .addOnFailureListener { Log.i("firebase", "ERROR al actualizar") }


                    Toast.makeText(this, "Empresa actualizada", Toast.LENGTH_SHORT).show()
                    abrirActividad(Vista_Empresa::class.java)
                }
            }

        } else {
            guardar.setOnClickListener {
                val nombreAGuardar = nombre.text.toString()
                val ubicacionAGuardar = ubicacion.text.toString()
                val numSucursalesAGuardar = numSucursales.text.toString()
                val activoInt = if (activo.isChecked) 1 else 0
                if (!revisarTextLlenos(nombreAGuardar, ubicacionAGuardar, numSucursalesAGuardar, fecha)) {
                    val db = Firebase.firestore
                    val nuevaEmpresa = hashMapOf<String, Any>(
                        "nombre" to nombreAGuardar,
                        "ubicacion" to ubicacionAGuardar,
                        "fundacion" to fecha.text.toString(),
                        "numSucursales" to numSucursalesAGuardar.toInt(),
                        "activo" to activoInt
                    )
                    db.collection("empresa").add(nuevaEmpresa)
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "Empresa añadida")
                        }.addOnFailureListener {
                            Log.i("firebase-firestore", "Error al añadirEmpresa")
                        }
                    Toast.makeText(this, "Empresa guardado", Toast.LENGTH_SHORT).show()
                    abrirActividad(Vista_Empresa::class.java)
                }
            }
        }
    }

    private fun revisarTextLlenos(nombre: String, ubicacion: String, numSucursales: String, fecha: TextView): Boolean {
        return if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(ubicacion) || TextUtils.isEmpty(numSucursales) || fecha.text.equals("MM/DD/YYYY")) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            true
        } else {
            if (numSucursales.toInt() < 1) {
                Toast.makeText(this, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }
    }


    private fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        startActivity(intentExplicito)
    }

}