package com.example.examen2bimestre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearSucursal : AppCompatActivity() {
    var latitud = 0.0
    var longitud =0.0
    var ciudadSeleccionado = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_sucursal)
        val nombreEmpresa = intent.getStringExtra("nombreEmpresa")
        val idEmpresa = intent.getStringExtra("idEmpresa")
        val idSucursal = intent.getStringExtra("idSucursal")

        val ciudadades = arrayOf("Amsterdam", "Berlin", "Bogotá", "London", "Madrid", "México DC", "New York",
            "Paris", "Quito", "Rio de Janeiro", "Tokyo", "Vienna", "Zürich")
        val latitudes = arrayOf(52.35573356081986, 52.5192880232342, 4.708507195958197, 51.509162838374365, 40.41800636913152, 19.432671298400965,
            40.73378562762449, 48.85901815928653, -0.21922483476220822,-22.938658330236088,35.68614253218143,48.217399805696736,47.36894099516527)
        val longitudes = arrayOf(4.881766688070693, 13.409614318972272, -74.05425716885155, -0.12767985390655445, -3.7077094446139935, -99.12723505493796,
            -73.99320893340935,2.296522110492033, -78.51152304364815, -43.228146943991, 139.78434424093086, 16.399427792363813, 8.550536094895179)
        val adapterCiudad: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ciudadades)
        val spinnerCiudades = findViewById<Spinner>(R.id.spinner_ubicacion)
        adapterCiudad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCiudades.adapter = adapterCiudad
        spinnerCiudades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                ciudadSeleccionado = parent?.getItemAtPosition(position) as String
                latitud=latitudes[position]
                longitud=longitudes[position]
                Log.i("ciudades", "$position")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.i("ciudades", "Item no seleccionado")
            }
        }

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
                Log.i("firestore", "$which $isChecked")
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

            val nombreActualizarEmpresa = findViewById<TextView>(R.id.txt_registroSucursal)
            nombreActualizarEmpresa.text = getString(R.string.actualizar_Sucursal)

            val db = Firebase.firestore
            val referencia = db.collection("sucursal").document(idSucursal!!)
            referencia.get()
                .addOnSuccessListener { document ->
                    if (document !=null){
                        val sucursalParaEditar = document.toObject(Sucursal::class.java)
                        txtnombreSucursal.text = sucursalParaEditar?.nombre
                        txtAnioApertura.text = sucursalParaEditar?.anioApertura.toString()
                        txtNumeroEmpleados.text = sucursalParaEditar?.numeroEmpleados.toString()
                        valoracion.rating = sucursalParaEditar?.valoracion!!
                        txtCategoria.text = sucursalParaEditar?.categoria
                    }else{
                        Log.d("firebase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebase", "get failed with ", exception)
                }


            guardar.setOnClickListener {
                val nombreActualizado = txtnombreSucursal.text.toString()
                val anioActualizada = txtAnioApertura.text.toString()
                val numeroEmpleadoActualizada = txtNumeroEmpleados.text.toString()
                val valoracionActualizada = valoracion.rating

                if (!revisarText(nombreActualizado, anioActualizada, numeroEmpleadoActualizada, txtCategoria)) {
                    val sucursalActualizada = hashMapOf<String, Any>(
                        "Nombre" to nombreActualizado,
                        "anioFundacion" to anioActualizada.toInt(),
                        "numero empleados" to numeroEmpleadoActualizada.toInt(),
                        "valoracion" to valoracionActualizada,
                        "latitud" to latitud,
                        "longitud" to longitud,
                        "ubicacion" to ciudadSeleccionado
                    )
                    referencia.update(sucursalActualizada)
                        .addOnSuccessListener { Log.i("firebase", "Transaccion completa") }
                        .addOnFailureListener { Log.i("firebase", "ERROR al actualizar") }

                    Toast.makeText(this, "Sucursal guardada", Toast.LENGTH_SHORT).show()
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
                    val db = Firebase.firestore
                    val nuevaSucursal = hashMapOf<String, Any>(
                        "empresa" to idEmpresa!!,
                        "nombreSucu" to tituloAGuardar,
                        "anioFundacion" to anioAGuardar.toInt(),
                        "numeroEmpleados" to numeroEmpleadoAGuardar.toInt(),
                        "valoracion" to valoracionNum,
                        "latitud" to latitud,
                        "longitud" to longitud,
                        "ubicacion" to ciudadSeleccionado
                    )
                    db.collection("sucursal").add(nuevaSucursal)
                        .addOnSuccessListener {
                            Log.i("firebase-firestore", "Sucursal añadida")
                        }.addOnFailureListener {
                            Log.i("firebase-firestore", "Error al añadirSucursal")
                        }
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

    private fun abrirActividadConParametros(empresa: String, idEmpresa: String?, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreEmpresa", empresa)
        intentExplicito.putExtra("idEmpresas", idEmpresa)
        startActivity(intentExplicito)
    }

}