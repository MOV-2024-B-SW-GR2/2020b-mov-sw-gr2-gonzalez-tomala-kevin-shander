package org.example

import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(arg: Array<String>) {
    //When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No reconocido")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueto = if (esSoltero) "Si" else "No" // if else chiquito

    imprimirNombre("Kevin")

    calcularSueldo(10.00) //Solo parametro requerido
    calcularSueldo(10.00, 15.00, 20.00) //parametro requerido, y sobreescribir parametros opcionales
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00) //usando el parametro bono especial en 2da posicion
    //gracias a los parametros nombrados
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
    //usando el parametro bonoEspecial en 1ra posicion
    //usando el parametro sueldo en 2da posicion
    //usando el parametro tasa en 3ra posicion
    //gracias a los parametros nombrados

    val sumaA = Suma(1, 1)
    val sumaB = Suma(null, 1)
    val sumaC = Suma(1, null)
    val sumaD = Suma(null, null)

    sumaA.sumar()
    sumaB.sumar()
    sumaC.sumar()
    sumaD.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // Arreglos
    // Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico);
    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico);
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico);

    // FOR EACH => Unit
    // Iterar un arreglo
    val respustasForEach: Unit = arregloDinamico.forEach {
        valorActual: Int ->
        println("Valor actual: $valorActual")
    }
    // "it" (en ingles "eso") significa el valor iterado
    arregloDinamico.forEach { println("Valor actual (it): $it") }

    // Map -> MUTA(Modifica cambios) el arreglo
    // 1) Enviemos el nuevo valor de la iteracion
    // 2) Nos devuelve un NUEVO arreglo con los valores modificados de las iteraciones
    val respuestaMap: List<Double> = arregloDinamico.map { valorActual: Int ->
        return@map valorActual.toDouble() + 100.00
    }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    // Filter -> FILTRA el arreglo
    // 1) Devuelve una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico.filter {
        valorActual: Int ->
        // Expresion o condicion
        val mayoresCinco: Boolean = valorActual > 5
        return@filter mayoresCinco
    }

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Alguno cumple?)
    // AND -> ALL (Todos cumplen?)
    val respuestaAny: Boolean = arregloDinamico.any {
        valorActual: Int ->
        return@any valorActual > 5
    }
    println(respuestaAny) //true
    val respuestaAll: Boolean = arregloDinamico.all {
        valorActual: Int ->
        return@all valorActual > 5
    }
    println(respuestaAll) //false

    //Reduce -> Valor acumulado
    // Valor acumulado = 0 (Siempre empieza en 0 en Kotlin)
    //[1, 2, 3, 4, 5] -> Acumular "SUMAR" estos valores del arreglo
    //valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion 1
    //valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 -> Iteracion 2
    //valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 -> Iteracion 3
    //valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4 = 10 -> Iteracion 4
    //valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 -> Iteracion 5
    val respuestaReduce = arregloDinamico.reduce { acumulado: Int, valorActual: Int ->
        return@reduce acumulado + valorActual //Cambiar o usar la logica de negocio
    }
    println(respuestaReduce) //78

}

fun imprimirNombre(nombre: String): Unit{
    fun otraFuncionAdentro(){
        println("Otra funcion adentro")
    }
    // Template String
    println("Nombre: $nombre") // Uso sin llaves
    println("Nombre: ${nombre}") // Uso con llaves
    println("Nombre: ${nombre + nombre}") // Uso con llaves concatenando
    println("Nombre: ${nombre.toString()}") // Uso con llaves funcion
    println("Nombre: $nombre.toString()") // INCORRECTO! No se puede usar sin llaves

    otraFuncionAdentro()
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null, //Opcional (nullable)
): Double {
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class Numeros( //Constructor primario
    //Caso 1) Parametro normal
    //uno: Int, (parametro sin modificador acceso)

    //Caso 2) Parametro y propiedades (atributo)
    //private var uno: Int (propiedad "instacia" privada)
    protected val numeroUno: Int, //instancia.numeroUno
    protected val numeroDos: Int, //instancia.numeroDos
    //parametroNoUsadoNopropiedadDeLaClase: Int //parametro sin propiedad
){
    init{
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma( //Constructor primario
    unoParametro: Int, //Parametro
    dosParametro: Int, //Parametro
) : Numeros ( //Clase papa, Numeros(extendiendo)
    unoParametro,
    dosParametro,
) {
    public val soyPublicoExplicito: String = "Publicas"
    val soyPublicoImplicito = "Publicas"

    init{ //bloque contructor primario
        this.numeroUno
        this.numeroDos
        numeroUno //this. OPCIONAL [propiedades, metodos]
        numeroDos //this. OPCIONAL [propiedades, metodos]
        this.soyPublicoImplicito
        soyPublicoExplicito
    }

    constructor( //Constructor secundario
        uno: Int?, // Enetro nullable
        dos: Int,
    ): this( //Llamada al constructor primario
        if(uno == null) 0 else uno,
        dos,
    ){
        //Bloque de codigo del constructor secundario
    }

    constructor( //Constructor secundario
        uno: Int,
        dos: Int?, //Entero nullable
    ): this( //Llamada al constructor primario
        uno,
        if(dos == null) 0 else dos,
    )

    constructor( //Constructor secundario
        uno: Int?, //Entero nullable
        dos: Int?, //Entero nullable
    ): this( //Llamada al constructor primario
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos,
    )

    fun sumar (): Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object { //Comparte entre todas las instancias, similar al STATIC
        //funciones, variables
        //NombreClase.metodo, NombreClase.funcion ->
        //Suma.pi
        val pi = 3.14
        //Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(nuevaSuma: Int){ //Suma.agregarHistorial
            historialSumas.add(nuevaSuma)
        }
    }
}

