package com.examen.superherocrud.model

data class Power(
    val id: Int,
    val name: String,
    val description: String,
    val isOffensive: Boolean,
    val effectiveness: Double
) {
    init {
        require(effectiveness in 0.0..10.0) {
            "La efectividad debe estar entre 0 y 10."
        }
        require(name.isNotBlank()) {
            "El nombre no puede estar vacío."
        }
        require(description.isNotBlank()) {
            "La descripción no puede estar vacía."
        }
    }

    companion object {
        private var powerIdCounter = 1

        fun generateId(): Int = powerIdCounter++

        fun resetCounter() {
            powerIdCounter = 1
        }
    }

    fun showDetails(): String {
        return "Power(id=$id, name='$name', description='$description', isOffensive=$isOffensive, effectiveness=$effectiveness)"
    }
}
