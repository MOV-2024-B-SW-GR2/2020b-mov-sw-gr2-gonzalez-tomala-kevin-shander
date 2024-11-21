package com.examen.superherocrud.service

import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.repository.SuperheroRepository

// Clase que se encarga de la lógica de negocio y validaciones.
class SuperheroService(private val repository: SuperheroRepository) {

    fun getAllSuperheroes(): List<Superhero> = repository.getAllSuperheroes()

//    fun getSuperheroById(id: Int): Superhero? = repository.getSuperheroById(id)

    fun getSuperheroById(id: Int): Superhero {
        return repository.getSuperheroById(id)
            ?: throw IllegalArgumentException("Superhero with ID $id not found.")
    }

    fun addSuperhero(superhero: Superhero) {
        if (repository.getSuperheroById(superhero.getId()) != null) {
            throw IllegalArgumentException("Superhero with ID ${superhero.getId()} already exists.")
        }
        repository.addSuperhero(superhero)
    }

    fun updateSuperhero(superhero: Superhero) {
        repository.getSuperheroById(superhero.getId())
            ?: throw IllegalArgumentException("Superhero with ID ${superhero.getId()} does not exist.")
        repository.updateSuperhero(superhero)
    }

    fun deleteSuperhero(id: Int) {
        repository.getSuperheroById(id)
            ?: throw IllegalArgumentException("Superhero with ID $id does not exist.")
        repository.deleteSuperhero(id)
    }
}