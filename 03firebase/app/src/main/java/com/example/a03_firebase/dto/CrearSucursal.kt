package com.example.a03_firebase

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
        //**************************************************************************************************
        //**************************************************************************************************
        //**************************************************************************************************
        val ciudadades = arrayOf("Cuenca", "Guaranda", "Azogues", "Tulcan", "Riobamba", "Latacunga", "Machala",
            "Esmeraldas", "Galapagos", "Guayaquil", "Ibarra", "Loja", "Babahoyo","Portoviejo","Macas","Tena",
        "Francisco de Orellana","Puyo","Quito","Santa Elena","Santo Domingo","Nueva Loja","Ambato","Zamora")
        val latitudes = arrayOf(-2.9005500,-1.5926300,-2.7396900,0.8118700,-1.6709800,-0.9352100,-3.2586100,0.9592000,
            0.3500000,-2.2058400, 0.3517100,-3.9931300,-1.8021700,-1.0545800,-2.3086800,
            -0.9938000,-0.933333,-1.4836900,-0.2298500,-2.2262200,-0.2530500,
            -0.083333,-1.2490800,41.5063300)
        val longitudes = arrayOf(-79.0045300,-79.0009800,-78.8486000,-77.7172700,
            -78.6471200,-78.6155400,-79.9605300,-79.6539700,-78.7333300,
            -79.9079500,-78.1223300,-79.2042200,-79.5344300,-80.4544500,
            -78.1113500,-77.8128600,-75.666667,-78.0025700,-78.5249500,
            -80.8587300, -79.1753600,-76.883333,-78.6167500,-5.7445600)
        //**************************************************************************************************
        //**************************************************************************************************
        //**************************************************************************************************
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
        //***************************************************************************************************


        val txtEmpresa = findViewById<TextView>(R.id.txt_nombreEmpresa)
        txtEmpresa.text = nombreEmpresa
        val txttituloSucursal = findViewById<TextView>(R.id.txt_tituloSucursal)
        val txtAnioApertura = findViewById<TextView>(R.id.txt_ingresoAnio)
        val txtNumeroEmpleados = findViewById<TextView>(R.id.txt_numEmpleados)
        val txtCategoria = findViewById<TextView>(R.id.txt_selectCategoria)
        val valoracion = findViewById<RatingBar>(R.id.ratingBar)
        val guardar = findViewById<Button>(R.id.btn_guardarSucursal)
        val selectCategoria = ArrayList<Int>()
        val categoriasSeleccionadas = BooleanArray(6) { false }
        txtCategoria.setOnClickListener {
            val opciones = resources.getStringArray(R.array.categorias_dialogo)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Categorias")
            builder.setCancelable(false)//NO se puede cancelar
            builder.setMultiChoiceItems(
                opciones, categoriasSeleccionadas
            ) { _, which, isChecked ->
                if (isChecked) {
                    selectCategoria.add(which)
                    selectCategoria.sort()
                } else {
                    selectCategoria.remove(which)
                }
                Log.i("firestore", "$which $isChecked")
            }
            builder.setPositiveButton("OK") { _, _ ->
                var categoria = ""
                selectCategoria.forEach { i -> categoria = categoria + opciones[i] + "/" }
                if (categoria == "") {
                    txtCategoria.text = getString(R.string.seleccione)
                } else {
                    txtCategoria.text = categoria
                }
            }
            builder.show()
        }


        val tituloSucursal = intent.getStringExtra("nombreSucursal")

        if (tituloSucursal != null && tituloSucursal != "") {

            val tituloActualizarEmpresa = findViewById<TextView>(R.id.txt_registroSucursal)
            tituloActualizarEmpresa.text = getString(R.string.actualizar_Sucursal)

            val db = Firebase.firestore
            val referencia = db.collection("sucursal").document(idSucursal!!)
            referencia.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val sucursalParaEditar = document.toObject(Sucursal::class.java)
                        txttituloSucursal.text = sucursalParaEditar?.nombreSucu
                        txtAnioApertura.text = sucursalParaEditar?.anioApertura.toString()
                        txtNumeroEmpleados.text = sucursalParaEditar?.numeroEmpleados.toString()
                        valoracion.rating = sucursalParaEditar?.valoracion!!
                        txtCategoria.text = sucursalParaEditar?.categoria
                    } else {
                        Log.d("firebase", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("firebase", "get failed with ", exception)
                }

            guardar.setOnClickListener {
                val tituloActualizado = txttituloSucursal.text.toString()
                val anioActualizada = txtAnioApertura.text.toString()
                val numeroEmpleadosActualizada = txtNumeroEmpleados.text.toString()
                val valoracionActualizada = valoracion.rating
                //Reviso si los TextFields están llenos
                if (!revisarText(tituloActualizado, anioActualizada, numeroEmpleadosActualizada, txtCategoria)) {
                    val sucursalActualizada = hashMapOf<String, Any>(
                        "nombreSucu" to tituloActualizado,
                        "anioApertura" to anioActualizada.toInt(),
                        "empleados" to numeroEmpleadosActualizada.toInt(),
                        "valoracion" to valoracionActualizada,
                        "categoria" to txtCategoria.text.toString(),
                        "latitud" to latitud,
                        "longitud" to longitud,
                        "ubicacion" to ciudadSeleccionado
                    )
                    referencia.update(sucursalActualizada)
                        .addOnSuccessListener { Log.i("firebase", "Transaccion completa") }
                        .addOnFailureListener { Log.i("firebase", "ERROR al actualizar") }


                    Toast.makeText(this, "Sucursal Actualizada", Toast.LENGTH_SHORT).show()
                    if (nombreEmpresa != null) {
                        abrirActividadConParametros(nombreEmpresa, idEmpresa!!, Vista_Sucursal::class.java)
                    }
                }
            }

        } else {
            guardar.setOnClickListener {
                val tituloAGuardar = txttituloSucursal.text.toString()
                val anioAGuardar = txtAnioApertura.text.toString()
                val numeroEmpleadosAGuardar = txtNumeroEmpleados.text.toString()
                val valoracionNum = valoracion.rating
                if (!revisarText(tituloAGuardar, anioAGuardar, numeroEmpleadosAGuardar, txtCategoria)) {
                    val db = Firebase.firestore
                    //no puedo poner ID
                    val nuevaSucursal = hashMapOf<String, Any>(
                        "empresa" to idEmpresa!!,
                        "nombreSucu" to tituloAGuardar,
                        "anioApertura" to anioAGuardar.toInt(),
                        "empleados" to numeroEmpleadosAGuardar.toInt(),
                        "valoracion" to valoracionNum,
                        "categoria" to txtCategoria.text.toString(),
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


    private fun revisarText(nombre: String, anio: String, empleados: String, categoria: TextView): Boolean {
        return if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(anio) || TextUtils.isEmpty(empleados) || categoria.text.equals("Seleccione:")) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            true
        } else {
            if (anio.toInt() > 2021 || anio.toInt() < 1 || empleados.toInt() < 1) {
                Toast.makeText(this, "Ingrese valores válidos", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }
    }

    private fun abrirActividadConParametros(empresa: String, idiEmpresa: String, clase: Class<*>) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("nombreEmpresa", empresa)
        intentExplicito.putExtra("idEmpresa", idiEmpresa)
        startActivity(intentExplicito)
    }
}