package com.examen.superherocrud.model

data class Power(
    private val id: Int,                // Identificador único del poder
    private val name: String,           // Nombre del poder
    private val description: String,    // Descripción del poder
    private val isOffensive: Boolean,   // Indica si el poder es ofensivo
    private val effectiveness: Double   // Nivel de efectividad del poder
) {
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
