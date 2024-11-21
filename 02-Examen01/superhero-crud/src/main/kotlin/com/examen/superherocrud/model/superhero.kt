package com.examen.superherocrud.model

import java.time.LocalDate

data class Superhero(
    private val id: Int,
    private val name: String,
    // Indica si el superhéroe está activo
    private val isActive: Boolean,
    // Fecha de debut del superhéroe
    private val debutDate: LocalDate,
    // Nivel de popularidad del superhéroe
    private val popularity: Double,
    private val powers: MutableList<Power> = mutableListOf()
) {
    companion object {
        private var superheroIdCounter = 1 // Contador para los IDs de superhéroes

        fun generateId(): Int = superheroIdCounter++

        fun initializeIdCounter(existingSuperheroes: List<Superhero>) {
            superheroIdCounter = (existingSuperheroes.maxOfOrNull { it.getId() } ?: 0) + 1
        }
    }

    init {
        if(powers.isEmpty()) {
            Power.resetCounter()
        }
        require(popularity in 0.0..10.0) {
            "Popularity must be between 0 and 100."
        }
        require(name.isNotBlank()) {
            "Name cannot be blank."
        }
        require(powers.distinctBy { it.getId() }.size == powers.size) {
            "Duplicate powers are not allowed."
        }
    }

    constructor(
        name: String,
        isActive: Boolean,
        debutDate: LocalDate,
        popularity: Double,
        power: MutableList<Power>
    ) : this(
        id = generateId(),
        name = name,
        isActive = isActive,
        debutDate = debutDate,
        popularity = popularity,
        powers = power
    ) {
        if(powers.isEmpty()) {
            Power.resetCounter()
        }
    }

    // Métodos para acceder a los atributos privados
    fun getId() = id
    fun getName() = name
    fun isActive() = isActive
    fun getDebutDate() = debutDate
    fun getPopularity() = popularity
    fun getPowers() = powers.toList() // Devuelve una copia inmutable de la lista de poderes

    // Función para agregar un poder al superhéroe
    fun addPower(power: Power) {
        val existingIds = powers.map { it.getId() }
        val newId = (existingIds.maxOrNull() ?: 0) + 1 // Generar un nuevo ID único
        val newPower = Power(newId, power.getName(), power.getDescription(), power.isOffensive(), power.getEffectiveness())

        if (existingIds.contains(newId)) {
            throw IllegalArgumentException("Power with ID $newId already exists.")
        }

        powers.add(newPower)
    }


    // Actualizar un poder existente
    fun updatePower(updatedPower: Power) {
        val index = powers.indexOfFirst { it.getId() == updatedPower.getId() }
        if (index != -1) {
            powers[index] = updatedPower
        } else {
            throw IllegalArgumentException("Power with ID ${updatedPower.getId()} not found.")
        }
    }

    // Función para eliminar un poder del superhéroe
    fun removePower(powerId: Int) {
        if (!powers.removeIf { it.getId() == powerId }) {
            throw IllegalArgumentException("Power with ID $powerId not found.")
        }
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
