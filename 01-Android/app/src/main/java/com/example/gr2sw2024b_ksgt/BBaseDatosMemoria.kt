package com.example.gr2sw2024b_ksgt

class BBaseDatosMemoria {
    companion object{
        var arregloBEntrenador = arrayListOf<BEntrenador>()
        init{
            arregloBEntrenador.add(BEntrenador(1,"Kevin","a@a.com"))
            arregloBEntrenador.add(BEntrenador(2,"Shander","b@b.com"))
            arregloBEntrenador.add(BEntrenador(3,"Gonzalez","c@c.com"))
        }
    }
}