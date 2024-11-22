package org.example.com.examen.superherocrud

import com.examen.superherocrud.controller.SuperheroController
import com.examen.superherocrud.model.Power
import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.repository.SuperheroRepository
import com.examen.superherocrud.service.SuperheroService
import com.examen.superherocrud.persistence.XmlParser
import java.time.LocalDate
import javax.swing.JOptionPane

fun main() {
    val filePath = "src/main/resources/xml/data.xml"

    val xmlParser = XmlParser(filePath)
    val repository = SuperheroRepository(xmlParser)
    val service = SuperheroService(repository)
    val controller = SuperheroController(service)

    // Inicializar el contador de IDs basado en los datos existentes
    Superhero.initializeCounter(controller.getAllSuperheroes())

    while (true) {
        try {
            val option = showMenu()
            when (option) {
                1 -> addSuperhero(controller)
                2 -> listSuperheroes(controller)
                3 -> findSuperhero(controller)
                4 -> updateSuperhero(controller)
                5 -> deleteSuperhero(controller)
                6 -> addPowerToSuperhero(controller)
                7 -> updateSuperheroPower(controller)
                8 -> deletePowerFromSuperhero(controller)
                9 -> {
                    JOptionPane.showMessageDialog(null, "Saliendo del programa...")
                    return
                }
                else -> JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intenta nuevamente.")
            }
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(null, "Error inesperado: ${e.message}. Intenta nuevamente.")
        }
    }
}

fun showMenu(): Int {
    val menu = """
        --- Menú CRUD de Superhéroes ---
        1. Agregar Superhéroe
        2. Listar Superhéroes
        3. Buscar Superhéroe por ID
        4. Actualizar Superhéroe
        5. Eliminar Superhéroe
        6. Agregar Poder a Superhéroe Existente
        7. Actualizar Poder de Superhéroe Existente
        8. Eliminar Poder de Superhéroe Existente
        9. Salir
    """.trimIndent()
    val input = JOptionPane.showInputDialog(menu)
    return input?.toIntOrNull() ?: -1
}

fun addSuperhero(controller: SuperheroController) {
    try {
        val name = JOptionPane.showInputDialog("Nombre del Superhéroe:") ?: return
        val isActive = JOptionPane.showInputDialog("¿Está activo? (true/false):")?.toBoolean() ?: return
        val debutDate = LocalDate.parse(JOptionPane.showInputDialog("Fecha de debut (YYYY-MM-DD):") ?: return)
        val popularity = JOptionPane.showInputDialog("Popularidad (decimal):")?.toDouble() ?: return

        val powers = mutableListOf<Power>()
        while (true) {
            val powerName = JOptionPane.showInputDialog("Nombre del Poder:") ?: break
            val description = JOptionPane.showInputDialog("Descripción del Poder:") ?: ""
            val isOffensive = JOptionPane.showInputDialog("¿Es ofensivo? (true/false):")?.toBoolean() ?: false
            val effectiveness = JOptionPane.showInputDialog("Efectividad (decimal):")?.toDouble() ?: 0.0
            powers.add(Power(Power.generateId(), powerName, description, isOffensive, effectiveness))

            val another = JOptionPane.showConfirmDialog(null, "¿Agregar otro poder?", "Poderes", JOptionPane.YES_NO_OPTION)
            if (another == JOptionPane.NO_OPTION) break
        }

        val superhero = Superhero(0, name, isActive, debutDate, popularity, powers)
        controller.addSuperhero(superhero)
        JOptionPane.showMessageDialog(null, "Superhéroe agregado exitosamente.")
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al agregar superhéroe: ${e.message}")
    }
}

fun listSuperheroes(controller: SuperheroController) {
    val superheroes = controller.getAllSuperheroes()
    if (superheroes.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No hay superhéroes registrados.")
    } else {
        val message = superheroes.joinToString("\n") { it.showDetails() }
        JOptionPane.showMessageDialog(null, message, "Lista de Superhéroes", JOptionPane.INFORMATION_MESSAGE)
    }
}

fun findSuperhero(controller: SuperheroController) {
    try {
        val id = JOptionPane.showInputDialog("ID del Superhéroe:")?.toInt() ?: return
        val superhero = controller.getSuperheroById(id)
        if (superhero != null) {
            JOptionPane.showMessageDialog(null, "Superhéroe encontrado:\n${superhero.showDetails()}")
        } else {
            JOptionPane.showMessageDialog(null, "Superhéroe no encontrado.")
        }
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al buscar superhéroe: ${e.message}")
    }
}

fun updateSuperhero(controller: SuperheroController) {
    try {
        val id = JOptionPane.showInputDialog("ID del Superhéroe a actualizar:")?.toInt() ?: return
        val superhero = controller.getSuperheroById(id) ?: run {
            JOptionPane.showMessageDialog(null, "Superhéroe no encontrado.")
            return
        }

        val newName = JOptionPane.showInputDialog("Nuevo nombre (${superhero.name}):") ?: superhero.name
        val isActive = JOptionPane.showInputDialog("¿Está activo? (${superhero.isActive}) (true/false):")?.toBoolean() ?: superhero.isActive
        val popularity = JOptionPane.showInputDialog("Nueva popularidad (${superhero.popularity}):")?.toDouble() ?: superhero.popularity

        val updatedSuperhero = superhero.copy(name = newName, isActive = isActive, popularity = popularity)
        controller.updateSuperhero(updatedSuperhero)
        JOptionPane.showMessageDialog(null, "Superhéroe actualizado exitosamente.")
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al actualizar superhéroe: ${e.message}")
    }
}

fun deleteSuperhero(controller: SuperheroController) {
    try {
        val id = JOptionPane.showInputDialog("ID del Superhéroe a eliminar:")?.toInt() ?: return
        controller.deleteSuperhero(id)
        JOptionPane.showMessageDialog(null, "Superhéroe eliminado exitosamente.")
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al eliminar superhéroe: ${e.message}")
    }
}

fun addPowerToSuperhero(controller: SuperheroController) {
    try {
        val id = JOptionPane.showInputDialog("ID del Superhéroe:")?.toInt() ?: return
        val superhero = controller.getSuperheroById(id)
        if (superhero == null) {
            JOptionPane.showMessageDialog(null, "Superhéroe no encontrado.")
            return
        }

        val powerName = JOptionPane.showInputDialog("Nombre del Poder:") ?: return
        val description = JOptionPane.showInputDialog("Descripción del Poder:") ?: ""
        val isOffensive = JOptionPane.showInputDialog("¿Es ofensivo? (true/false):")?.toBoolean() ?: false
        val effectiveness = JOptionPane.showInputDialog("Efectividad (decimal):")?.toDouble() ?: 0.0

        val newPower = Power(0, powerName, description, isOffensive, effectiveness)
        val result = superhero.addPower(newPower)
        result.onSuccess { updatedSuperhero ->
            controller.updateSuperhero(updatedSuperhero)
            JOptionPane.showMessageDialog(null, "Poder agregado exitosamente.")
        }.onFailure { error ->
            JOptionPane.showMessageDialog(null, "Error al agregar el poder: ${error.message}")
        }
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al agregar poder: ${e.message}")
    }
}

fun updateSuperheroPower(controller: SuperheroController) {
    try {
        val id = JOptionPane.showInputDialog("ID del Superhéroe:")?.toInt() ?: return
        val superhero = controller.getSuperheroById(id)
        if (superhero == null) {
            JOptionPane.showMessageDialog(null, "Superhéroe no encontrado.")
            return
        }

        val powerId = JOptionPane.showInputDialog("ID del Poder a actualizar:")?.toInt() ?: return
        val power = superhero.powers.find { it.id == powerId }
        if (power == null) {
            JOptionPane.showMessageDialog(null, "Poder no encontrado.")
            return
        }

        val newName = JOptionPane.showInputDialog("Nuevo nombre del Poder:") ?: power.name
        val newDescription = JOptionPane.showInputDialog("Nueva descripción:") ?: power.description
        val newIsOffensive = JOptionPane.showInputDialog("¿Es ofensivo? (true/false):")?.toBoolean() ?: power.isOffensive
        val newEffectiveness = JOptionPane.showInputDialog("Nueva efectividad (decimal):")?.toDouble() ?: power.effectiveness

        val updatedPower = power.copy(
            name = newName,
            description = newDescription,
            isOffensive = newIsOffensive,
            effectiveness = newEffectiveness
        )
        val result = superhero.updatePower(updatedPower)
        result.onSuccess { updatedSuperhero ->
            controller.updateSuperhero(updatedSuperhero)
            JOptionPane.showMessageDialog(null, "Poder actualizado exitosamente.")
        }.onFailure { error ->
            JOptionPane.showMessageDialog(null, "Error al actualizar el poder: ${error.message}")
        }
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al actualizar poder: ${e.message}")
    }
}

fun deletePowerFromSuperhero(controller: SuperheroController) {
    try {
        val id = JOptionPane.showInputDialog("ID del Superhéroe:")?.toInt() ?: return
        val superhero = controller.getSuperheroById(id)
        if (superhero == null) {
            JOptionPane.showMessageDialog(null, "Superhéroe no encontrado.")
            return
        }

        val powerId = JOptionPane.showInputDialog("ID del Poder a eliminar:")?.toInt() ?: return
        val result = superhero.removePower(powerId)
        result.onSuccess { updatedSuperhero ->
            controller.updateSuperhero(updatedSuperhero)
            JOptionPane.showMessageDialog(null, "Poder eliminado exitosamente.")
        }.onFailure { error ->
            JOptionPane.showMessageDialog(null, "Error al eliminar el poder: ${error.message}")
        }
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Error al eliminar poder: ${e.message}")
    }
}
