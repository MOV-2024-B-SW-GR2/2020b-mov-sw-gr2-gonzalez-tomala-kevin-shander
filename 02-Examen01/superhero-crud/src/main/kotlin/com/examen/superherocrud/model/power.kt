package com.examen.superherocrud.model

data class Power(
    private val id: Int,
    private val name: String,
    private val description: String,
    // Indica si el poder es ofensivo
    private val isOffensive: Boolean,
    // Nivel de efectividad del poder
    private val effectiveness: Double
) {

    companion object {
        private var powerIdCounter = 1 // Contador para los IDs de poderes

        // Método para generar el próximo ID
        fun generateId(): Int {
            return powerIdCounter++
        }

        // Método para reiniciar el contador
        fun resetCounter() {
            powerIdCounter = 1
        }
    }

    init {
        require(effectiveness in 0.0..10.0) {
            "Effectiveness must be between 0 and 100."
        }
        require(name.isNotBlank()) {
            "Name cannot be blank."
        }
        require(description.isNotBlank()) {
            "Description cannot be blank."
        }
    }

    constructor(
        name: String,
        description: String,
        isOffensive: Boolean,
        effectiveness: Double
    ) : this(
        id = generateId(),
        name = name,
        description = description,
        isOffensive = isOffensive,
        effectiveness = effectiveness
    )

    // Métodos de acceso (getters) para acceder a los atributos privados
    fun getId() = id
    fun getName() = name
    fun getDescription() = description
    fun isOffensive() = isOffensive
    fun getEffectiveness() = effectiveness

    // Función para mostrar detalles del poder
    fun showPowerDetails(): String {
        return "Power(id=$id, name='$name', description='$description', isOffensive=$isOffensive, effectiveness=$effectiveness)"
    }
}
