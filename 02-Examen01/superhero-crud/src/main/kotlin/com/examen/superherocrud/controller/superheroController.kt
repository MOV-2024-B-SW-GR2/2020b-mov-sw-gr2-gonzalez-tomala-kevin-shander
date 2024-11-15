package com.examen.superherocrud.controller


import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.service.SuperheroService

// Clase que se encarga de manejar las peticiones de los usuarios.
class SuperheroController(private val service: SuperheroService) {

    // Obtener todos los superhéroes
    fun getAllSuperheroes(): List<Superhero> = service.getAllSuperheroes()

    // Obtener un superhéroe por su ID
    fun getSuperheroById(id: Int): Superhero? = service.getSuperheroById(id)

    // Agregar un nuevo superhéroe
    fun addSuperhero(superhero: Superhero) = service.addSuperhero(superhero)

    // Actualizar un superhéroe
    fun updateSuperhero(superhero: Superhero) = service.updateSuperhero(superhero)

    // Eliminar un superhéroe
    fun deleteSuperhero(id: Int) = service.deleteSuperhero(id)
}