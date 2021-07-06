fun crear(
    listaSucursales: ArrayList<Sucursal>,
    listaEmpresas: ArrayList<Empresa>

){    println(
        "¿Qué desea ingresar? " +
                "\n1- Sucursales" +
                "\n2- Empresas"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {crearSucu(listaSucursales) }
            2 -> {crearEmpre(listaEmpresas) }

            else -> { imprimirConflictos(0) }
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
    }
}

fun crearSucu(
    listaSucursales: ArrayList<Sucursal>
){
    val sucursalAux: Sucursal? = registrarSucursal()
    if (sucursalAux != null) {
        listaSucursales.add(sucursalAux)
        imprimirGuardado(0)
    }
}

fun crearEmpre(
    listaEmpresas: ArrayList<Empresa>
){
    val empresaAux: Empresa? = registrarEmpresa()
    if (empresaAux != null) {
        listaEmpresas.add(empresaAux)
        imprimirGuardado(0)
    }
}

fun borrar(
    listaSucursales: ArrayList<Sucursal>,
    listaEmpresas: ArrayList<Empresa>
){
    println(
        "Desea Eliminar: " +
                "\n1- Sucursales" +
                "\n2- Empresas"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {eliminarSucu(listaSucursales, listaEmpresas)}
            2 -> { eliminarEmpre(listaEmpresas) }
            else -> imprimirConflictos(0)
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
    }
}

fun eliminarSucu(
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
                    listaSucursales.removeIf { sucursal ->
                        (sucursal.idSucursal.equals(ingreso))
                    }
                } else {
                    imprimirConflictos(3)
                }
            } catch (err: Exception) {
                imprimirConflictos(1)
            }
        } else {
            imprimirConflictos(2)
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
    }
}

fun eliminarEmpre(
    listaEmpresas: ArrayList<Empresa>,
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
                    listaEmpresas.removeIf { empresa ->
                        (empresa.idEmpresa == entrada)
                    }
                } else {
                    imprimirConflictos(3)
                }
            } catch (err: Exception) {
                imprimirConflictos(1)
            }
        } else {
            imprimirConflictos(2)
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
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
                actualizarSucu(listaSucursales)
            }
            2 -> {
                actualizarEmpre(listaEmpresas)
            }
            else -> {
                imprimirConflictos(0)
            }
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
    }
}

fun actualizarSucu(
    listaSucursales: ArrayList<Sucursal>,
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
                        SucursalAux = sucursal
                    }
                }
                listaSucursales.removeIf { reparto ->
                    reparto.idSucursal.equals(updateSucursal.idSucursal)
                }
                if (SucursalAux != null) {
                    println(
                        "Informacion Actualizada:\n" +
                                "+${updateSucursal}"
                    )
                    imprimirGuardado(2)
                }
            }
        } else {
            imprimirConflictos(2)
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
    }
}

fun actualizarEmpre(
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
            listaEmpresas.removeIf { empresa1 ->
                (empresa1.idEmpresa == empresa)
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
                println(
                    "Informacion actualizada:\n" +
                            "+${updateEmpresa}"
                )
            }
        } else {
            imprimirConflictos(2)
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
    }
}

fun buscar(
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
                opcionSucursales(listaSucursales)
            }
            2 -> {
                opcionEmpresas(listaEmpresas)
            }

            else -> {
                imprimirConflictos(0)
            }
        }
    } catch (err: Exception) {
        imprimirConflictos(1)
    }
}

fun opcionSucursales(
    listaSucursales: ArrayList<Sucursal>
){
    println("Lista Sucursales")
    imprimirSucursales(listaSucursales)
    imprimirGuardado(1)
}

fun opcionEmpresas(
    listaEmpresas: ArrayList<Empresa>
){
    println("Lista Empresas")
    imprimirEmpresas(listaEmpresas)
    imprimirGuardado(1)
}