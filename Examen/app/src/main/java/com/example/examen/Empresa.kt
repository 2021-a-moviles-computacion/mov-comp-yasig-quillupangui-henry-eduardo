package com.example.examen

class Empresa (var id: Int,
               var nombre: String,
               var ubicacion: String,
               var fundacion: String,
               var numSucursales: Int,
               var actividad: Int) {

    override fun toString(): String {
        return nombre
    }

    fun imprimirDatosEmpresa(): String {
        var activo = "no"
        if (actividad == 1) activo = "si"
        return """
                Nombre: $nombre 
                Ubicacion: $ubicacion
                Fecha de Fundacion: $fundacion
                Numero de Sucursales: $numSucursales
                Actividad: $activo
            """.trimIndent()
    }

}