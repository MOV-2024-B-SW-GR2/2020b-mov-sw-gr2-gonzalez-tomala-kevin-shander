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

