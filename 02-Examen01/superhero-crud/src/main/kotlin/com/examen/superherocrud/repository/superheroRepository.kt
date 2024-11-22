package com.examen.superherocrud.repository

import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.persistence.XmlParser

// Clase que se encarga de la persistencia de datos usando el archivo XML.
class SuperheroRepository(private val xmlParser: XmlParser) {

    fun getAllSuperheroes(): List<Superhero> = xmlParser.readAllSuperheroes()

    fun getSuperheroById(id: Int): Superhero? = getAllSuperheroes().find { it.id == id }

    fun addSuperhero(superhero: Superhero) {
        val superheroes = getAllSuperheroes().toMutableList()
        superheroes.add(superhero)
        xmlParser.writeAllSuperheroes(superheroes)
    }

    fun updateSuperhero(superhero: Superhero) {
        val superheroes = getAllSuperheroes().map {
            if (it.id == superhero.id) superhero else it
        }
        xmlParser.writeAllSuperheroes(superheroes)
    }

    fun deleteSuperhero(id: Int) {
        val superheroes = getAllSuperheroes().filter { it.id != id }
        xmlParser.writeAllSuperheroes(superheroes)
    }
}