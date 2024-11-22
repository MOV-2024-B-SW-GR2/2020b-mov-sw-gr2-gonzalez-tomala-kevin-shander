package com.examen.superherocrud.model

import java.time.LocalDate

data class Superhero(
    val id: Int,
    val name: String,
    val isActive: Boolean,
    val debutDate: LocalDate,
    val popularity: Double,
    val powers: List<Power> = emptyList()
) {
    init {
        require(name.isNotBlank()) {
            "El nombre del superhéroe no puede estar vacío."
        }
        require(popularity in 0.0..10.0) {
            "La popularidad debe estar entre 0 y 10."
        }
        require(powers.distinctBy { it.id }.size == powers.size) {
            "No se permiten poderes duplicados."
        }
    }

    companion object {
        private var superheroIdCounter = 1

        fun generateId(): Int = superheroIdCounter++

        fun initializeCounter(existingSuperheroes: List<Superhero>) {
            superheroIdCounter = (existingSuperheroes.maxOfOrNull { it.id } ?: 0) + 1
        }
    }

    fun showDetails(): String {
        val powerDetails = if (powers.isEmpty()) {
            "\tNo tiene poderes."
        } else {
            powers.joinToString(separator = "\n") { "\t- ${it.showDetails()}" }
        }
        return "Superhero(id=$id, name='$name', isActive=$isActive, debutDate=$debutDate, popularity=$popularity)\n$powerDetails\n"
    }

    fun addPower(newPower: Power): Result<Superhero> {
        return try {
            // Generar un nuevo ID si el ID actual es 0
            val powerWithId = if (newPower.id == 0) {
                val existingIds = powers.map { it.id }
                val newId = (existingIds.maxOrNull() ?: 0) + 1
                newPower.copy(id = newId)
            } else {
                newPower
            }
            // Agregar el nuevo poder
            Result.success(copy(powers = powers + powerWithId))
        } catch (e: Exception) {
            println("Error al agregar poder: ${e.message}")
            Result.failure(e)
        }
    }


    fun updatePower(updatedPower: Power): Result<Superhero> {
        return try {
            val index = powers.indexOfFirst { it.id == updatedPower.id }
            if (index == -1) {
                throw IllegalArgumentException("No se encontró un poder con el ID ${updatedPower.id}.")
            }
            val updatedPowers = powers.toMutableList().apply { this[index] = updatedPower }
            Result.success(copy(powers = updatedPowers))
        } catch (e: Exception) {
            println("Error al actualizar poder: ${e.message}")
            Result.failure(e)
        }
    }

    fun removePower(powerId: Int): Result<Superhero> {
        return try {
            if (powers.none { it.id == powerId }) {
                throw IllegalArgumentException("No se encontró un poder con el ID $powerId.")
            }
            Result.success(copy(powers = powers.filterNot { it.id == powerId }))
        } catch (e: Exception) {
            println("Error al eliminar poder: ${e.message}")
            Result.failure(e)
        }
    }
}
