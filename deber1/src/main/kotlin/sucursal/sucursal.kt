import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarSucursales(): ArrayList<Sucursal> {
    val archivoSucursales: File?
    var frSucursales: FileReader? = null
    val brSucursales: BufferedReader?
    val listaSucursales: ArrayList<Sucursal> = ArrayList()

    try {
        archivoSucursales = File("sucursales.csv")
        frSucursales = FileReader(archivoSucursales)
        brSucursales = BufferedReader(frSucursales)

        var linea: String?
        while (brSucursales.readLine().also { linea = it } != null) {
            var auxLin = 0
            var auxFecha = 0

            var diaSucursal = 0
            var anioSucursal = 0
            var mesSucursal = 0

            var sucursalID = ""
            var nombre = ""
            var categoria = ' '
            var fundacion = Date(2000, 1, 1)
            var estado = true

            val tokens = StringTokenizer(linea, ",")

            while (tokens.hasMoreTokens()) {
                when (auxLin) {
                    0 -> {sucursalID = tokens.nextToken()}
                    1 -> {nombre = tokens.nextToken()}
                    2 -> { categoria = tokens.nextToken().toCharArray()[0]}
                    3 -> {
                        val tokFecha = StringTokenizer(tokens.nextToken(), "/")
                        while (tokFecha.hasMoreTokens()) {
                            when (auxFecha) {
                                0 -> { anioSucursal = tokFecha.nextToken().toInt() }
                                1 -> { mesSucursal = tokFecha.nextToken().toInt()}
                                2 -> {diaSucursal = tokFecha.nextToken().toInt()}
                            }
                            auxFecha++
                        }
                        fundacion = Date(anioSucursal, mesSucursal, diaSucursal)
                    }
                    4 -> {
                        val a = tokens.nextToken()
                        if (a == "true") {
                            estado = true
                        } else if (a == "false") {
                            estado = false
                        }

                    }
                }
                auxLin++
            }
            listaSucursales.add(
                Sucursal(
                    sucursalID,
                    nombre,
                    categoria,
                    fundacion,
                    estado
                )
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frSucursales?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listaSucursales
}

fun registrarSucursal(): Sucursal? {
    var idSucursal: String? = ""
    var nombreSucursal: String? = ""
    var categoriaSucursal: Char? = null
    val fechaFundacionSucursal: String?

    var flag = true
    var fechaAux = 0

    var diaSucursal = 0
    var mesSucursal = 0
    var anioSucursal = 0
    try {
        println("Ingrese el identificador de la sucursal")
        idSucursal = readLine() as String
        println("Ingrese el nombre de la sucursal ")
        nombreSucursal = readLine() as String
        println("Ingrese la categoria de la sucursal")
        categoriaSucursal = readLine()?.toCharArray()?.get(0)
        println("Ingrese la fecha de fundacion de la sucursal aaaa/mm/dd")
        fechaFundacionSucursal = readLine()
        println("la Sucursal se encuentra como  \"ACTIVA\"")

        val tokFecha = StringTokenizer(fechaFundacionSucursal, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> { anioSucursal = tokFecha.nextToken().toInt() }
                1 -> {mesSucursal = tokFecha.nextToken().toInt() }
                2 -> { diaSucursal = tokFecha.nextToken().toInt() }
            }
            fechaAux++
        }

    } catch (eRead1: NumberFormatException) {
        imprimirConflictos(1)
        flag = false
    }
    if (flag) {
        return Sucursal(
            idSucursal,
            nombreSucursal,
            categoriaSucursal,
            Date(anioSucursal, mesSucursal, diaSucursal),
            true
        )
    }
    return null
}

fun imprimirSucursales(
    listaSucursales: ArrayList<Sucursal>
) {
    listaSucursales.forEach { sucursal ->
        println("Valor: $sucursal")
    }
}

fun actualizarSucursales(
    sucursal: Sucursal?
): Sucursal? {
    var nombreSucursal: String? = ""
    var cateoriaSucursal: Char? = null
    val fechaFundacionSucursal: String?

    val alAireSucursal: String?
    var alAire = false

    var flag = true
    var fechaAux = 0

    var diaSucursal = 0
    var mesSucursal = 0
    var anioSucursal = 0
    try {
        println("Ingrese el nuevo nombre de la Sucursal ")
        nombreSucursal = readLine() as String
        println("Ingrese la categoria de la Sucursal")
        cateoriaSucursal = readLine()?.toCharArray()?.get(0)
        println("Ingrese la fecha de fundacion de la sucursal aaaa/mm/dd")
        fechaFundacionSucursal = readLine()

        val tokFecha = StringTokenizer(fechaFundacionSucursal, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> {
                    anioSucursal = tokFecha.nextToken().toInt()
                }
                1 -> {
                    mesSucursal = tokFecha.nextToken().toInt()
                }
                2 -> {
                    diaSucursal = tokFecha.nextToken().toInt()
                }
            }
            fechaAux++
        }

        println(
            "¿Desea cambiar el estado de la sucursal?\n" +
                    "Estado actual: ${sucursal?.activo}\n" +
                    "Ingrese 1 si desea Activarlo\n" +
                    "Ingrese 0 si desea Desactivarlo"
        )
        alAireSucursal = readLine()
        if (alAireSucursal.equals("1")) {
            alAire = true
        }

    } catch (eRead1: NumberFormatException) {
        imprimirConflictos(1)
        flag = false
    }
    if (flag) {
        return Sucursal(
            sucursal?.idSucursal,
            nombreSucursal,
            cateoriaSucursal,
            Date(anioSucursal, mesSucursal, diaSucursal),
            alAire
        )
    }
    return null
}

fun guardarSucursales(
    listaSucursales: ArrayList<Sucursal>
) {
    var stringSucursales = ""
    listaSucursales.forEach { sucursal ->
        stringSucursales = stringSucursales + "" +
                "${sucursal.idSucursal}," +
                "${sucursal.nombreSucursal}," +
                "${sucursal.categoriaSucursal}," +
                "${sucursal.fechaFundacionSucursal?.year}/${sucursal.fechaFundacionSucursal?.month}/${sucursal.fechaFundacionSucursal?.date}," +
                "${sucursal.activo}\n"
    }
    var flwriter: FileWriter? = null
    try {
        flwriter =
            FileWriter("sucursales.csv")
        val bfwriter = BufferedWriter(flwriter)

        bfwriter.write(stringSucursales)
        bfwriter.close()
        println("Archivo de sucursales actualizado")
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (flwriter != null) {
            try {
                flwriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class Sucursal(
    val idSucursal: String?,
    val nombreSucursal: String?,
    val categoriaSucursal: Char?,
    val fechaFundacionSucursal: Date?,
    val activo: Boolean?

) {

    override fun toString(): String {
        return "\n\tId: \t\t$idSucursal\n" +
                "\tNombre: \t$nombreSucursal\n" +
                "\tClasificación: \t$categoriaSucursal\n" +
                "\tFecha de Inicio:  ( ${fechaFundacionSucursal?.year} / ${fechaFundacionSucursal?.month} / ${fechaFundacionSucursal?.date} )\n" +
                "\tEstado: \t$activo\n"
    }
}