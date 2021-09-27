package com.example.a03_firebase

class Sucursal (
    var id: String?=null,
    var nombreSucu: String?=null,
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
        return nombreSucu!!
    }

    fun imprimirDatosSucursal(): String {
        return """
                nombre: $nombreSucu 
                Año de Apertura: $anioApertura
                Numero de empleados: $numeroEmpleados
                Valoración: $valoracion 
                Categoria: $categoria
                ubicacion: $ubicacion
            """.trimIndent()
    }
}