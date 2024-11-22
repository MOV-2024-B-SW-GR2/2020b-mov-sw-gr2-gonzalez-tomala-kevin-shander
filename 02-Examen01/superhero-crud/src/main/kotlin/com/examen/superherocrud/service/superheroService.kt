package com.examen.superherocrud.service

import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.model.Power
import com.examen.superherocrud.repository.SuperheroRepository

class SuperheroService(private val repository: SuperheroRepository) {

    fun getAllSuperheroes(): List<Superhero> {
        return try {
            repository.getAllSuperheroes()
        } catch (e: Exception) {
            println("Error al obtener la lista de superhéroes: ${e.message}")
            emptyList()
        }
    }

    fun getSuperheroById(id: Int): Superhero? {
        return try {
            repository.getSuperheroById(id)
        } catch (e: IllegalArgumentException) {
            println(e.message)
            null
        } catch (e: Exception) {
            println("Error inesperado al buscar el superhéroe: ${e.message}")
            null
        }
    }

    fun addSuperhero(superhero: Superhero): Boolean {
        return try {
            Power.resetCounter()
            val newSuperhero = superhero.copy(id = Superhero.generateId())
            repository.addSuperhero(newSuperhero)
            true
        } catch (e: Exception) {
            println("Error al agregar el superhéroe: ${e.message}")
            false
        }
    }

    fun updateSuperhero(superhero: Superhero): Boolean {
        return try {
            repository.getSuperheroById(superhero.id)
                ?: throw IllegalArgumentException("Superhéroe con ID ${superhero.id} no encontrado.")
            repository.updateSuperhero(superhero)
            true
        } catch (e: Exception) {
            println("Error inesperado al actualizar el superhéroe: ${e.message}")
            false
        }
    }

    fun deleteSuperhero(id: Int): Boolean {
        return try {
            repository.getSuperheroById(id)
                ?: throw IllegalArgumentException("Superhéroe con ID $id no encontrado.")
            repository.deleteSuperhero(id)
            true
        } catch (e: Exception) {
            println("Error inesperado al eliminar el superhéroe: ${e.message}")
            false
        }
    }
}
