package com.example.moviles_computacion_2021_b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
            CREATE TABLE USUARIO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion varchar(50)
            )
        """.trimIndent()
        Log.i("bbd", "Creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)
    }


    fun crearUsuarioFormulario(
        nombre: String,
        descripcion: String
    ): Boolean {
//        val conexionExcritura = this.writableDatabase
        val conexionExcritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoEscritura: Long = conexionExcritura
            .insert(
                "USUARIO",
                null,
                valoresAGuardar
            )
        conexionExcritura.close()
        return if (resultadoEscritura.toInt() == -1) false else true
    }

    fun consultarUsuarioPorId(id: Int): EUsuarioBDD {

        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE ID = ${id}"
//        val baseDatosLectura = this.readableDatabase
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        // val arregloUsuario = arrayListOf<EUsuarioBDD>()
        val usuarioEncontrado = EUsuarioBDD(0, "", "")
        if (existeUsuario) {
            do {
                val id = resultaConsultaLectura.getInt(0) // Columna indice 0 -> ID
                val nombre = resultaConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val descripcion =
                    resultaConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
                if(id!=null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                    // arregloUsuario.add(usuarioEncontrado)
                }
            } while (resultaConsultaLectura.moveToNext())
        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}