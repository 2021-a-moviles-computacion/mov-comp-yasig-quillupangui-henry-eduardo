package com.example.examen

class Sucursal (
    var id: Int,
    var nombre: String,
    var anioApertura: Int,
    var numeroEmpleados: Int,
    var valoracion: Float,
    var categoria: String,
    var empresa: Int
) {

    override fun toString(): String {
        return nombre
    }

    fun imprimirDatosSucursal(): String {
        return """
                Nombre: $nombre 
                Año de Apertura: $anioApertura
                Numero de empleados: $numeroEmpleados
                Valoración: $valoracion 
                Categoria: $categoria
            """.trimIndent()
    }
}