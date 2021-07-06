import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarEmpresa(): ArrayList<Empresa> {

    val archivoEmpresas: File?
    var frEmpresas: FileReader? = null
    val brEmpresas: BufferedReader?
    val listaEmpresas = ArrayList<Empresa>()

    try {
        archivoEmpresas = File("empresas.csv")
        frEmpresas = FileReader(archivoEmpresas)
        brEmpresas = BufferedReader(frEmpresas)

        var linea: String?
        while (brEmpresas.readLine().also { linea = it } != null) {
            var auxLin = 0
            var idEmpresa = 0
            var nombreEmpresa = ""
            var paisEmpresa = ""
            var anioEmpresa = 0.0

            val tokens = StringTokenizer(linea, ",")

            while (tokens.hasMoreTokens()) {
                when (auxLin) {
                    0 -> { idEmpresa = tokens.nextToken().toInt()}
                    1 -> { nombreEmpresa = tokens.nextToken()}
                    2 -> {paisEmpresa = tokens.nextToken()}
                    3 -> {anioEmpresa = tokens.nextToken().toDouble()}
                }
                auxLin++
            }

            listaEmpresas.add(
                Empresa(
                    idEmpresa,
                    nombreEmpresa,
                    paisEmpresa,
                    anioEmpresa,
                    )
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frEmpresas?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listaEmpresas
}

fun registrarEmpresa(): Empresa? {
    var id = 0
    var nombre = ""
    var pais = ""
    var anio = 0.0

    var flag = true

    try {
        println("Ingrese el id de la empresa")
        id = readLine()?.toInt() as Int
        println("Ingrese el nombre de la empresa")
        nombre = readLine() as String
        println("Ingrese el pais de la empresa")
        pais = readLine() as String
        println("Ingrese el año de fundacion de la empresa (0.0)")
        anio = readLine()?.toDouble()!!

    } catch (eRead1: NumberFormatException) {
        imprimirConflictos(1)
        flag = false
    }
    if (flag) {
        return Empresa(id, nombre, pais, anio)
    }
    return null
}

fun imprimirEmpresas(
    listaEmpresas: ArrayList<Empresa>
) {
    listaEmpresas.forEach { empresa ->
        println("Valor: $empresa")
    }
}

fun actualizarEmpresa(
    empresa: Empresa?
): Empresa? {
    var nombre = ""
    var pais = ""
    var anio = 0.0
    var flag = true

    try {
        println("Ingrese el nuevo nombre de la empresa ")
        nombre = readLine() as String
        println("Ingrese el nuevo pais de la empresa")
        pais = readLine() as String
        println("Ingrese el anio de la empresa")
        anio = readLine()?.toDouble()!!


    } catch (eRead1: NumberFormatException) {
        imprimirConflictos(1)
        flag = false
    }
    if (flag) {
        if (empresa != null) {
            return Empresa(empresa.idEmpresa, nombre, pais, anio)
        }
    }
    return null
}

fun guardarEmpresas(
    listaEmpresas: ArrayList<Empresa>
) {

    var stringEmpresa = ""
    listaEmpresas.forEach { empresa ->
        stringEmpresa = stringEmpresa + "" +
                "${empresa.idEmpresa}," +
                "${empresa.nombreEmpresa}," +
                "${empresa.paisEmpresa}," +
                "${empresa.anioEmpresa}\n"

    }
    var fWriter: FileWriter? = null
    try {
        fWriter =
            FileWriter("empresas.csv")
        val bfwriter = BufferedWriter(fWriter)

        bfwriter.write(stringEmpresa)
        bfwriter.close()
        println("Archivo de empresas actualizado")
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (fWriter != null) {
            try {
                fWriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class Empresa(
    val idEmpresa: Int,
    val nombreEmpresa: String,
    val paisEmpresa: String,
    val anioEmpresa: Double,

    ) {
    override fun toString(): String {
        return "\n" +
                "\tId:\t$idEmpresa\n" +
                "\tNombre completo:  \t$nombreEmpresa\n" +
                "\tGenero:  \t$paisEmpresa\n" +
                "\tAño:\t$anioEmpresa\n"

    }
}