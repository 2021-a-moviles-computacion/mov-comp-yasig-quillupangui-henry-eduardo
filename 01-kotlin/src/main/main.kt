fun main() {
    println("hola mundo")
    var edadProfesor =32
    imprimirNombre("Henry")
    calcularSueldo(100.00)
    calcularSueldo(100.00, 14.00)
    calcularSueldo(100.00, 14.00, 25.00)


    //val arregloEstatico:Array<Int> =arrayf(1,2,3);

    //val respuestaAny:Boolean= arregloDinamico
      //  .any{valorActual:Int ->
        //    return@any(valorActual > 5)
       // }
   // println(respuestaAny) //true

    //REDUCE -> VALOR ACUMULADO
    // 1)devuelve el acumulado
    // 2)En que valor empieza
    // [1,2,3,4,5]
    //0=0+1 ->Iteracion 1
    //1=1+2 ->Iteracion 2
    //3=3+3 ->Iteracion 3

   // val respuestaReduce: Int = arregloDinamico
     //   .reduce{
       //     acumulado:Int->
         //   return@reduce (acumulado+valorActual)//->logica negocio
       // }
   // println(respuestaReduce)

    val arregloDanio = arrayListOf<Int>(12,15,8,10)
    val respuestaReduceFold = arregloDanio
        .fold(
            100
        ) { acumulado, valorActualIteracion ->
            return@fold acumulado - valorActualIteracion
        }
    println(respuestaReduceFold)

   // val vidaActual:Double=arregloDinamico
    //    .map {it*2.3}
      //  .filter{it>20}
        //.fold(100.00,{acc,i ->acc-i})
       // .also{println(it)}
   // println("valor vida actual ${vidaActual}")

    abstract class NumerosJava{
        protected val numeroUno: Int
       // private val numeroDOS: Int
        constructor(
                uno: Int,
                dos:Int
        ){
            numeroUno=uno
            //numeroDos=dos
            println("Inicializar")
        }
    }
}

fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double = 12.00, //opcional
    bonoEspecial: Double? = null, //pueden tener valores nulos
    //bonoEspecial:Double?=null
): Double {
    if (bonoEspecial == null) { // para indentar ctrl+a ; ctrl+alt+l
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) + bonoEspecial
    }

    //NAMED PARAMETER
    calcularSueldo(
        bonoEspecial = 15.00,
                sueldo =150.00,
    )
    calcularSueldo(
        tasa= 14.00,
        bonoEspecial = 15.00,
        sueldo =150.00,
    )


}

