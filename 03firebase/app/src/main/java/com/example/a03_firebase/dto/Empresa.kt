package com.example.a03_firebase

class Empresa (var id: String?=null,
               var nombre: String?=null,
               var ubicacion: String?=null,
               var fundacion: String?=null,
               var numSucursales: Int?=null,
               var activo: Int?=null) {

    override fun toString(): String {
        return nombre!!
    }

    fun imprimirDatosEmpresa(): String {
        var activo = "no"
        if (this.activo == 1) activo = "si"
        return """
                Nombre: $nombre 
                Ubicacion: $ubicacion
                Fecha de Fundacion: $fundacion
                Numero de Sucursales: $numSucursales
                Status: $activo
            """.trimIndent()
    }

}
