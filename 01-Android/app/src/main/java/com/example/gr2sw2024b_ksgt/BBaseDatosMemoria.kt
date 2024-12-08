package com.example.gr2sw2024b_ksgt

class BBaseDatosMemoria {
    companion object {
        val arregloEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloEntrenador
                .add(BEntrenador(1, "Kenyi", "Descripcion Kenyi"))
            arregloEntrenador
                .add(BEntrenador(2, "Vicente", "Descripcion Vicente"))
            arregloEntrenador
                .add(BEntrenador(3, "Wendy", "Descripcion Wendy"))
        }
    }
}