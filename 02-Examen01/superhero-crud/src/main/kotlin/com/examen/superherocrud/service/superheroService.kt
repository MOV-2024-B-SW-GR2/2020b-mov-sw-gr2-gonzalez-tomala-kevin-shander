package com.examen.superherocrud.service

import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.repository.SuperheroRepository

// Clase que se encarga de la lógica de negocio y validaciones.
class SuperheroService(private val repository: SuperheroRepository) {

    fun getAllSuperheroes(): List<Superhero> = repository.getAllSuperheroes()

    fun getSuperheroById(id: Int): Superhero? = repository.getSuperheroById(id)

    fun addSuperhero(superhero: Superhero) {
        if (repository.getSuperheroById(superhero.getId()) == null) {
            repository.addSuperhero(superhero)
        } else {
            throw IllegalArgumentException("Superhero with ID ${superhero.getId()} already exists.")
        }
    }

    fun updateSuperhero(superhero: Superhero) {
        if (repository.getSuperheroById(superhero.getId()) != null) {
            repository.updateSuperhero(superhero)
        } else {
            throw IllegalArgumentException("Superhero with ID ${superhero.getId()} does not exist.")
        }
    }

    fun deleteSuperhero(id: Int) {
        if (repository.getSuperheroById(id) != null) {
            repository.deleteSuperhero(id)
        } else {
            throw IllegalArgumentException("Superhero with ID $id does not exist.")
        }
    }
}