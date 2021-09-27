package com.example.examen2bimestre

class Sucursal (
    var id: String?=null,
    var nombre: String?=null,
    var anioApertura: Int?=null,
    var numeroEmpleados: Int?=null,
    var valoracion: Float?=null,
    var categoria: String?=null,
    var empresa: String?=null,
    var latitud: Double?=null,
    var longitud: Double?=null,
    var ubicacion: String?=null,

) {

    override fun toString(): String {
        return nombre!!
    }

    fun imprimirDatosSucursal(): String {
        return """
                Nombre: $nombre 
                Año de Apertura: $anioApertura
                Numero de empleados: $numeroEmpleados
                Valoración: $valoracion 
                Categoria: $categoria
                ubicacion: $ubicacion
            """.trimIndent()
    }
}