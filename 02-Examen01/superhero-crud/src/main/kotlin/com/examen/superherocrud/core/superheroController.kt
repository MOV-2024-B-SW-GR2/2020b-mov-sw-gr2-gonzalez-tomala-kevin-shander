package com.examen.superherocrud.core

import com.examen.superherocrud.model.Superhero

// Clase que se encarga de manejar las peticiones de los usuarios.
class SuperheroController(private val service: SuperheroService) {

    // Obtener todos los superhéroes
    fun getAllSuperheroes(): List<Superhero> {
        val superheroes = service.getAllSuperheroes()
        if (superheroes.isEmpty()) {
            println("No hay superhéroes registrados.")
        }
        return superheroes
    }

    // Obtener un superhéroe por su ID
    fun getSuperheroById(id: Int): Superhero? {
        val superhero = service.getSuperheroById(id)
        if (superhero == null) {
            println("Superhéroe con ID $id no encontrado.")
        }
        return superhero
    }

    // Agregar un nuevo superhéroe
    fun addSuperhero(superhero: Superhero): Boolean {
        val success = service.addSuperhero(superhero)
        if (!success) {
            println("No se pudo agregar el superhéroe.")
        } else {
            println("Superhéroe agregado exitosamente.")
        }
        return success
    }

    // Actualizar un superhéroe
    fun updateSuperhero(superhero: Superhero): Boolean {
        val success = service.updateSuperhero(superhero)
        if (!success) {
            println("No se pudo actualizar el superhéroe.")
        } else {
            println("Superhéroe actualizado exitosamente.")
        }
        return success
    }

    // Eliminar un superhéroe
    fun deleteSuperhero(id: Int): Boolean {
        val success = service.deleteSuperhero(id)
        if (!success) {
            println("No se pudo eliminar el superhéroe.")
        } else {
            println("Superhéroe eliminado exitosamente.")
        }
        return success
    }
}
