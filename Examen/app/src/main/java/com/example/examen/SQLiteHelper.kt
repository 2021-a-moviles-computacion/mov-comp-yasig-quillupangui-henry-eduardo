package com.example.examen

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(contexto: Context?) : SQLiteOpenHelper(contexto, "examenMoviles02", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaEmpresa =
            """
                CREATE TABLE EMPRESA(ID_EMPRESA INTEGER PRIMARY KEY AUTOINCREMENT,
                NOMBRE TEXT NOT NULL, UBICACION TEXT NOT NULL,
                FECHAFUNDACION TEXT NOT NULL, NUMSUCURSALES INTEGER NOT NULL, 
                ACTIVIDAD INTEGER NOT NULL CHECK (ACTIVIDAD IN (0, 1)) 
                )""".trimIndent()

        val scriptCrearTablaSucursal=
            """
                CREATE TABLE SUCURSAL (ID_SUCURSAL INTEGER PRIMARY KEY AUTOINCREMENT,
                NOMBRE TEXT NOT NULL, ANIOAPERTURA INTEGER NOT NULL, 
                NUMEROEMPLEADOS INTEGER NOT NULL, VALORACION REAL NOT NULL, CATEGORIA TEXT NOT NULL,
                EMPRESA INTEGER NOT NULL,
                FOREIGN KEY (EMPRESA) REFERENCES EMPRESA (ID_EMPRESA) 
                )
            """.trimIndent()


        db?.execSQL(scriptCrearTablaEmpresa)
        Log.i("bdd", "Creación Tabla Empresa")
        db?.execSQL(scriptCrearTablaSucursal)
        Log.i("bdd", "Creación Tabla Sucursal")
        //        TODO("Not yet implemented")
    }


    fun crearEmpresa(
        nombre: String,
        ubicacion: String,
        fechaFundacion: String,
        numSucursales: Int,
        actividad: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("NOMBRE", nombre)
        valoresAGuardar.put("UBICACION", ubicacion)
        valoresAGuardar.put("FECHAFUNDACION", fechaFundacion)
        valoresAGuardar.put("NUMSUCURSALES", numSucursales)
        valoresAGuardar.put("ACTIVIDAD", actividad)

        val resultadoEscritura: Long = conexionEscritura.insert("EMPRESA", null, valoresAGuardar)
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1

    }

    fun crearSucursal(
        nombre: String,
        anioApertura: Int,
        numeroEmpleados: Int,
        valoracion: Float,
        categoria: String,
        empresa: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("NOMBRE", nombre)
        valoresAGuardar.put("ANIOAPERTURA", anioApertura)
        valoresAGuardar.put("NUMEROEMPLEADOS", numeroEmpleados)
        valoresAGuardar.put("VALORACION", valoracion)
        valoresAGuardar.put("CATEGORIA", categoria)
        valoresAGuardar.put("EMPRESA", empresa)

        val resultadoEscritura: Long = conexionEscritura.insert("SUCURSAL", null, valoresAGuardar)
        conexionEscritura.close()
        return resultadoEscritura.toInt() != -1

    }

    fun consultarEmpresaPorNombre(nombreEmpresa: String): Empresa {
        val scriptConsultarEmpresa = "SELECT * FROM EMPRESA WHERE NOMBRE=\"$nombreEmpresa\""
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarEmpresa, null)
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = Empresa(0, "", "", "", 0, 0)
        if (existeUsuario) {
            do {
                val id = resultadoConsultaLectura.getInt(0)//ID
                val nombre = resultadoConsultaLectura.getString(1)
                val ubicacion = resultadoConsultaLectura.getString(2)
                val fechaFundacion = resultadoConsultaLectura.getString(3)
                val numSucursales = resultadoConsultaLectura.getInt(4)
                val actividad = resultadoConsultaLectura.getInt(5)

                usuarioEncontrado.id = id
                usuarioEncontrado.nombre = nombre
                usuarioEncontrado.ubicacion = ubicacion
                usuarioEncontrado.fundacion = fechaFundacion
                usuarioEncontrado.numSucursales = numSucursales
                usuarioEncontrado.actividad = actividad

            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun consultarTodaEmpresa(): ArrayList<Empresa> {
        val scriptConsultarEmpresa = "SELECT * FROM EMPRESA"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarEmpresa, null)
        val empresas = ArrayList<Empresa>()
        resultadoConsultaLectura.moveToFirst()
        while (!resultadoConsultaLectura.isAfterLast) {
            val empresa = Empresa(0, "", "", "", 0, 0)
            empresa.id = resultadoConsultaLectura.getInt(0)//ID
            empresa.nombre = resultadoConsultaLectura.getString(1)
            empresa.ubicacion = resultadoConsultaLectura.getString(2)
            empresa.fundacion = resultadoConsultaLectura.getString(3)
            empresa.numSucursales = resultadoConsultaLectura.getInt(4)
            empresa.actividad = resultadoConsultaLectura.getInt(5)
            empresas.add(empresa)
            resultadoConsultaLectura.moveToNext()
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return empresas
    }

    fun consultarTodasSucursalesdeEmpresas(idEmpresa:Int): ArrayList<Sucursal> {
        val scriptConsultarSucursales = "SELECT * FROM SUCURSAL WHERE EMPRESA=$idEmpresa"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarSucursales, null)
        val sucursales = ArrayList<Sucursal>()
        resultadoConsultaLectura.moveToFirst()
        while (!resultadoConsultaLectura.isAfterLast) {
            val sucursal = Sucursal(0, "", 0, 0, 0f, "", 0)
            sucursal.id = resultadoConsultaLectura.getInt(0)//ID
            sucursal.nombre = resultadoConsultaLectura.getString(1)
            sucursal.anioApertura = resultadoConsultaLectura.getInt(2)
            sucursal.numeroEmpleados = resultadoConsultaLectura.getInt(3)
            sucursal.valoracion = resultadoConsultaLectura.getFloat(4)
            sucursal.categoria = resultadoConsultaLectura.getString(5)
            sucursal.empresa = resultadoConsultaLectura.getInt(6)
            sucursales.add(sucursal)
            resultadoConsultaLectura.moveToNext()
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return sucursales
    }

    fun consultarSucursalPorNombre(NombreSucursal: String): Sucursal {
        val scriptConsultarSucursales = "SELECT * FROM SUCURSAL WHERE NOMBRE=\"$NombreSucursal\""
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarSucursales, null)
        resultadoConsultaLectura.moveToFirst()
        val sucursal = Sucursal(0, "", 0, 0, 0f, "", 0)
        while (!resultadoConsultaLectura.isAfterLast) {
            sucursal.id = resultadoConsultaLectura.getInt(0)//ID
            sucursal.nombre = resultadoConsultaLectura.getString(1)
            sucursal.anioApertura = resultadoConsultaLectura.getInt(2)
            sucursal.numeroEmpleados = resultadoConsultaLectura.getInt(3)
            sucursal.valoracion = resultadoConsultaLectura.getFloat(4)
            sucursal.categoria = resultadoConsultaLectura.getString(5)
            sucursal.empresa = resultadoConsultaLectura.getInt(6)
            resultadoConsultaLectura.moveToNext()
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return sucursal
    }


    fun eliminarEmpresa(nombre: String): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion =
            conexionEscritura.delete("EMPRESA", "NOMBRE=?", arrayOf(nombre))
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun eliminarSucursal(nombre: String): Boolean {
        val conexionEscritura = writableDatabase
        val resultadoEliminacion =
            conexionEscritura.delete("SUCURSAL", "NOMBRE=?", arrayOf(nombre))
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarEmpresa(
        nombre: String,
        ubicacion: String,
        fechaFundacion: String,
        numSucursales: Int,
        actividad: Int,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("NOMBRE", nombre)
        valoresAActualizar.put("UBICACION", ubicacion)
        valoresAActualizar.put("FECHAFUNDACION", fechaFundacion)
        valoresAActualizar.put("NUMSUCURSALES", numSucursales)
        valoresAActualizar.put("ACTIVIDAD", actividad)
        val resultadoActualizacion = conexionEscritura.update(
            "EMPRESA",
            valoresAActualizar,
            "ID_EMPRESA=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    fun actualizarSucursal(
        nombre: String,
        anioApertura: Int,
        numeroEmpleados: Int,
        valoracion: Float,
        categoria: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("NOMBRE", nombre)
        valoresAActualizar.put("ANIOAPERTURA", anioApertura)
        valoresAActualizar.put("NUMEROEMPLEADOS", numeroEmpleados)
        valoresAActualizar.put("VALORACION", valoracion)
        valoresAActualizar.put("CATEGORIA", categoria)
        val resultadoActualizacion = conexionEscritura.update(
            "SUCURSAL",
            valoresAActualizar,
            "ID_SUCURSAL=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}
