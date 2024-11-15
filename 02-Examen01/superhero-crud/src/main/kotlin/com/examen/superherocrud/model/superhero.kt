package com.examen.superherocrud.model

import java.time.LocalDate

data class Superhero(
    private val id: Int,                        // Identificador único del superhéroe
    private val name: String,                   // Nombre del superhéroe
    private val isActive: Boolean,              // Indica si el superhéroe está activo
    private val debutDate: LocalDate,           // Fecha de debut del superhéroe
    private val popularity: Double,             // Nivel de popularidad del superhéroe
    private val powers: MutableList<Power> = mutableListOf() // Lista de poderes del superhéroe
) {
    // Métodos para acceder a los atributos privados
    fun getId() = id
    fun getName() = name
    fun isActive() = isActive
    fun getDebutDate() = debutDate
    fun getPopularity() = popularity
    fun getPowers() = powers.toList() // Devuelve una copia inmutable de la lista de poderes

    // Función para agregar un poder al superhéroe
    fun addPower(power: Power) {
        powers.add(power)
    }

    // Actualizar un poder existente
    fun updatePower(updatedPower: Power) {
        val index = powers.indexOfFirst { it.getId() == updatedPower.getId() }
        if (index != -1) {
            powers[index] = updatedPower
        }
    }

    // Función para eliminar un poder del superhéroe
    fun removePower(power: Power) {
        powers.remove(power)
    }

    // Función para mostrar todos los poderes del superhéroe
    private fun showAllPowers(): String {
        return powers.joinToString(separator = "\n") { "- ${it.showPowerDetails()}" }
    }

    // Función para mostrar detalles del superhéroe
    fun showSuperheroDetails(): String {
        return "Superhero(id=$id, name='$name', isActive=$isActive, debutDate=$debutDate, popularity=$popularity, powers:\n${showAllPowers()})"
    }
}
