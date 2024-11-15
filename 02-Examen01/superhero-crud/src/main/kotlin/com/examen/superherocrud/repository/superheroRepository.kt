package com.examen.superherocrud.repository

import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.xml.XmlParser

// Clase que se encarga de la persistencia de datos usando el archivo XML.
class SuperheroRepository(private val xmlParser: XmlParser) {

    fun getAllSuperheroes(): List<Superhero> = xmlParser.readAllSuperheroes()

    fun getSuperheroById(id: Int): Superhero? = getAllSuperheroes().find { it.getId() == id }

    fun addSuperhero(superhero: Superhero) {
        val superheroes = getAllSuperheroes().toMutableList()
        superheroes.add(superhero)
        xmlParser.writeAllSuperheroes(superheroes)
    }

    fun updateSuperhero(superhero: Superhero) {
        val superheroes = getAllSuperheroes().map {
            if (it.getId() == superhero.getId()) superhero else it
        }
        xmlParser.writeAllSuperheroes(superheroes)
    }

    fun deleteSuperhero(id: Int) {
        val superheroes = getAllSuperheroes().filter { it.getId() != id }
        xmlParser.writeAllSuperheroes(superheroes)
    }
}