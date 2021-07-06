import kotlin.collections.ArrayList
import kotlin.system.exitProcess

fun main() {

    //Creacion de listas y carga inicial
    val listaSucursal: ArrayList<Libro> = cargarSucursal()
    val listaEmpresa: ArrayList<Autor> = cargarEmpresa()


    var seleccion:Int


    println("Empresa y Sucursales")
    do {
        println("Seleccione una opción: ")
        println(
                    "1- Ingresar una nueva Empresa - Sucursal\n" +
                    "2- Mostrar empresa o sucursal\n" +
                    "3- Actualizar una empresa o sucursal \n" +
                    "4- Eliminar una empresa o sucursal \n" +
                    "5- Salir \n"

        )
        try {
            seleccion = readLine()?.toInt() as Int
            when (seleccion) {
                1 -> {
                    crear(listaSucursal, listaEmpresa)
                }
                2 -> {
                    leer(listaSucursal, listaEmpresa)
                }
                3 -> {
                    actualizar(listaSucursal, listaEmpresa)
                }
                4 -> {
                    borrar(listaSucursal, listaEmpresa)
                }
                5 -> {
                    guardarLibros(listaSucursal)
                    guardarAutores(listaEmpresa)

                    exitProcess(0)
                }
                else -> imprimirError(0)
            }

        } catch (e2: Exception) {
            imprimirError(1)
        }

    } while (true)
}

fun imprimirGuardado(opcion: Int = 0) {
    when (opcion) {
        0 -> {
            println("Se ha guardado los datos\n")
        }
        1 -> {
            println("Consulta exitosa\n")
        }
        2 -> {
            println("Actualización exitosa\n" )
        }
        else -> {
            println("¡¡ Ingrese los datos correctos !!\n")
        }
    }

    readLine()
}

fun imprimirConflictos(opcion: Int = 0) {
    when (opcion) {
        0 -> {
            println("Opción no encontrada\n")
        }
        1 -> {
            println("La opcion ingresada no es correcta)
        }
        2 -> {
            println("Elemento no encontrado\n")
        }
        3 -> {
            println("Operación cancelada\n" )
        }
        else -> {
            println("Error desconocido\n")
        }
    }

    readLine()
}


fun buscarSucursalID(
    listaSucursal: ArrayList<Sucursal>,
    idSucursal: String
): Sucursal? {
    var sucursalRespuesta: Sucursal? = null
    listaSucursal.forEach { serie ->
        if (serie.idSucursal.equals(idSucursal)) {
            sucursalRespuesta = serie
        }
    }
    return sucursalRespuesta
}

fun buscarEmpresaID(
    listaEmpresas: ArrayList<Empresa>,
    idEmpresa: Int
): Empresa? {
    var empresaRespuesta: Empresa? = null
    listaEmpresas.forEach { empresa ->
        if (empresa.idEmpresa == idEmpresa) {
            empresaRespuesta = empresa
        }
    }
    return empresaRespuesta
}