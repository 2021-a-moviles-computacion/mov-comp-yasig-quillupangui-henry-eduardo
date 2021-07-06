fun crear(
    listaSucursales: ArrayList<Sucursal>,
    listaEmpresas: ArrayList<AEmpresa>

){
    println(
        "¿Qué desea ingresar? " +
                "\n1- Sucursales" +
                "\n2- Empresas"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                crearUno(listaSucursales)
            }
            2 -> {
                crearDos(listaEmpresas)
            }

            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }

}

fun crearUno(
    listaSucursales: ArrayList<Sucursal>
){
    val sucursalAux: Sucursal? = registrarSucursal()
    if (sucursalAux != null) {
        listaSucursales.add(sucursalAux)
        imprimirExito(0)
    }
}

fun crearDos(
    listaEmpresas: ArrayList<Empresa>
){
    val empresaAux: Empresa? = registrarEmpresa()
    if (empresaAux != null) {
        listaEmpresas.add(empresaAux)
        imprimirExito(0)
    }
}

fun borrar(
    listaSucursales: ArrayList<Sucursales>,
    listaEmpresas: ArrayList<Empresa>
){
    println(
        "Desea Eliminar: " +
                "\n1- Sucursales" +
                "\n2- Empresas"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                eliminarUno(listaEmpresas, listaSucursales)
            }
            2 -> {
                eliminarDos(listaEmpresas)
            }
            else -> imprimirError(0)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun eliminarUno(
    listaSucursales: ArrayList<Sucursal>,
    listaEmpresas: ArrayList<Empresa>
){
    println("Ingrese el id de la empresa a eliminar:\n")
    try {
        val ingreso = readLine() as String
        val sucursalID: Sucursal? = buscarSucursalID(listaSucursales, ingreso)
        val confirmacion: String?
        if (sucursalID != null) {
            println("Informacion de la empresa que se va eliminar:")
            println(sucursalID)
            try {
                println(
                    "¿Está seguro de eliminar la sucursal?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 para cancelar"
                )
                confirmacion = readLine()
                if (confirmacion.equals("1")) {
//                    listaAutores.removeIf { autores ->
//                        autores.libro.idLibro.equals(ingreso)
//                    }
                    listaSucursales.removeIf { sucursal ->
                        (sucursal.idSucursal.equals(ingreso))
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun eliminarDos(
    listaEmpresas: ArrayList<Empresa>,
    // listaReparto: ArrayList<Reparto>
){
    println("Ingrese el id de la empresa que desea eliminar\n")
    imprimirEmpresas(listaEmpresas)
    try {
        val entrada = readLine()?.toInt() as Int
        val empresaID: Empresa? = buscarEmpresaID(listaEmpresas, entrada)
        val seguro: String?
        if (empresaID != null) {
            println("Información actual de la empresa:")
            println(empresaID)
            try {
                println(
                    "¿Está seguro de eliminar la empresa?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 para cancelar"
                )
                seguro = readLine()
                if (seguro.equals("1")) {
//                    listaAutores.removeIf { reparto ->
//                        reparto.autor.idAutor == entrada
//                    }
                    listaEmpresas.removeIf { empresa ->
                        (empresa.idEmpresa == entrada)
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun actualizar(
    listaSucursales: ArrayList<Sucursal>,
    listaEmpresas: ArrayList<Empresa>
){
    println(
        "Desea actualizar: " +
                "\n1- Sucursales" +
                "\n2- Empresas"
    )
    try {

        when (readLine()?.toInt() as Int) {
            1 -> {
                actualizarUno(listaSucursales)
            }
            2 -> {
                actualizarDos(listaEmpresas)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun actualizarUno(
    listaSucursales: ArrayList<Sucursal>,
    //  listaAutores: ArrayList<Autor>
){
    println("Ingrese el id de la sucursal a actualizar:\n")

    try {

        val entrada = readLine() as String
        val sucursalID: Sucursal? = buscarSucursalID(listaSucursales, entrada)
        val updateSucursal: Sucursal?
        if (sucursalID != null) {
            println("Información actual de la sucursal:")
            println(sucursalID)
            updateSucursal = actualizarSucursales(sucursalID)

            listaSucursales.removeIf { sucursal ->
                (sucursal.idSucursal.equals(entrada))
            }
            if (updateSucursal != null) {
                var SucursalAux: Sucursal? = null
                listaSucursales.add(updateSucursal)
                listaSucursales.forEach { sucursal ->
                    if (sucursal.idSucursal.equals(updateSucursal.idSucursal)) {
                        sucursalAux = sucursal
                    }
                }
                listaSucursales.removeIf { reparto ->
                    reparto.idSucursal.equals(updateSucursal.idSucursal)
                }
                if (sucursalAux != null) {
//                    listaLibros.add(
//                        Libro(
//                          //  updateLibro,
//                        //    libroAux!!.tituloLibro,
//                           // libroAux!!.fechaIngreso,
//                            //libroAux!!.comentario
//                        )
//                    )
                    println(
                        "Informacion Actualizada:\n" +
                                "+${updateSucursal}"
                    )
                    imprimirExito(2)
                }
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun actualizarDos(
    listaEmpresas: ArrayList<Empresa>,

    ){
    println("Ingrese el id de la empresa que desea actualizar\n")
    try {

        val empresa = readLine()?.toInt() as Int
        val empresaID: Empresa? = buscarEmpresaID(listaEmpresas, empresa)
        val updateEmpresa: Empresa?
        if (empresaID != null) {
            println("Información actual de la empresa:")
            println(empresaID)
            updateEmpresa = actualizarEmpresa(empresaID)
            listaEmpresas.removeIf { actor1 ->
                (actor1.idEmpresas == empresa)
            }
            if (updateEmpresa != null) {
                var repartoAux: Empresa? = null
                listaEmpresas.add(updateEmpresa)

                listaEmpresas.forEach { reparto ->
                    if (reparto.idEmpresa == updateEmpresa.idEmpresa) {
                        repartoAux = reparto
                    }
                }
                listaEmpresas.removeIf { reparto ->
                    reparto.idEmpresa == updateEmpresa.idEmpresa
                }
//
                println(
                    "Informacion actualizada:\n" +
                            "+${updateEmpresa}"
                )
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

//



////////////////*****************************buscar********

fun leer(
    listaSucursales: ArrayList<Sucursal>,
    listaEmpresas: ArrayList<Empresa>
){
    println(
        "¿Qué desea ver?: " +
                "\n1- Sucursales" +
                "\n2- Empresas"

    )
    try {

        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionR1(listaSucursales)
            }
            2 -> {
                opcionR2(listaEmpresas)
            }

            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}



fun opcionR1(
    listaSucursales: ArrayList<Sucursal>
){
    println("Lista Sucursales")
    imprimirSucursales(listaSucursales)
    imprimirExito(1)
}

fun opcionR2(
    listaEmpresas: ArrayList<Empresa>
){
    println("Lista Empresas")
    imprimirEmpresas(listaEmpresas)
    imprimirExito(1)
}