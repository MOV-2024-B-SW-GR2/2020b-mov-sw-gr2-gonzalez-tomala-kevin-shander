package org.example.com.examen.superherocrud

import com.examen.superherocrud.controller.SuperheroController
import com.examen.superherocrud.model.Power
import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.repository.SuperheroRepository
import com.examen.superherocrud.service.SuperheroService
import com.examen.superherocrud.persistence.XmlParser
import java.time.LocalDate
import java.util.Scanner

fun main() {
    val filePath = "src/main/resources/xml/data.xml"

    val xmlParser = XmlParser(filePath)
    val repository = SuperheroRepository(xmlParser)
    val service = SuperheroService(repository)
    val controller = SuperheroController(service)

    // Inicializar el contador de IDs basado en los datos existentes
    Superhero.initializeCounter(controller.getAllSuperheroes())

    val scanner = Scanner(System.`in`)
    while (true) {
        try {
            showMenu()
            val option = readInt(scanner)
            when (option) {
                1 -> addSuperhero(scanner, controller)
                2 -> listSuperheroes(controller)
                3 -> findSuperhero(scanner, controller)
                4 -> updateSuperhero(scanner, controller)
                5 -> deleteSuperhero(scanner, controller)
                6 -> addPowerToSuperhero(scanner, controller)
                7 -> updateSuperheroPower(scanner, controller)
                8 -> deletePowerFromSuperhero(scanner, controller)
                9 -> {
                    println("Saliendo del programa...")
                    break
                }
                else -> println("Opción no válida. Por favor, intenta nuevamente.")
            }
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}. Intenta nuevamente.")
        }
    }
    scanner.close()
}

fun showMenu() {
    println("\n--- Menú CRUD de Superhéroes ---")
    println("1. Agregar Superhéroe")
    println("2. Listar Superhéroes")
    println("3. Buscar Superhéroe por ID")
    println("4. Actualizar Superhéroe")
    println("5. Eliminar Superhéroe")
    println("6. Agregar Poder a Superhéroe Existente")
    println("7. Actualizar Poder de Superhéroe Existente")
    println("8. Eliminar Poder de Superhéroe Existente")
    println("9. Salir")
    print("Elige una opción: ")
}

fun readInt(scanner: Scanner): Int? {
    return try {
        val input = scanner.nextLine()
        input.toIntOrNull() // Intenta convertir la entrada a un número entero
    } catch (e: Exception) {
        null // Retorna null si ocurre un error
    }
}

fun addSuperhero(scanner: Scanner, controller: SuperheroController) {
    try {
        println("\n--- Agregar Superhéroe ---")
        print("Nombre: ")
        val name = scanner.nextLine().takeIf { it.isNotBlank() } ?: throw IllegalArgumentException("El nombre no puede estar vacío.")
        print("¿Está activo? (true/false): ")
        val isActive = scanner.nextBoolean()
        scanner.nextLine()
        print("Fecha de debut (YYYY-MM-DD): ")
        val debutDate = LocalDate.parse(scanner.nextLine())
        print("Popularidad (decimal): ")
        val popularity = scanner.nextDouble()
        scanner.nextLine()

        val powers = mutableListOf<Power>()
        while (true) {
            println("\n--- Agregar Poder ---")
            print("Nombre del Poder: ")
            val powerName = scanner.nextLine().takeIf { it.isNotBlank() } ?: throw IllegalArgumentException("El nombre del poder no puede estar vacío.")
            print("Descripción del Poder: ")
            val description = scanner.nextLine()
            print("¿Es ofensivo? (true/false): ")
            val isOffensive = scanner.nextBoolean()
            scanner.nextLine()
            print("Efectividad (decimal): ")
            val effectiveness = scanner.nextDouble()
            scanner.nextLine()
            powers.add(Power(Power.generateId(), powerName, description, isOffensive, effectiveness))

            print("¿Agregar otro poder? (y/n): ")
            if (scanner.next().lowercase() != "y") {
                scanner.nextLine()
                break
            } else {
                scanner.nextLine()
            }
        }

        val superhero = Superhero(0, name, isActive, debutDate, popularity, powers)
        controller.addSuperhero(superhero)
    } catch (e: Exception) {
        println("Error al agregar superhéroe: ${e.message}")
    }
}

fun listSuperheroes(controller: SuperheroController) {
    println("\n--- Lista de Superhéroes ---")
    val superheroes = controller.getAllSuperheroes()
    if (superheroes.isEmpty()) {
        println("No hay superhéroes registrados.")
    } else {
        superheroes.forEach { println(it.showDetails()) }
    }
}

fun findSuperhero(scanner: Scanner, controller: SuperheroController) {
    println("\n--- Buscar Superhéroe por ID ---")
    try {
        print("ID del Superhéroe: ")
        val id = scanner.nextInt()
        scanner.nextLine()
        val superhero = controller.getSuperheroById(id)
        if (superhero != null) {
            println("Superhéroe encontrado:\n${superhero.showDetails()}")
        }
    } catch (e: Exception) {
        println("Error al buscar superhéroe: ${e.message}")
    }
}

fun updateSuperhero(scanner: Scanner, controller: SuperheroController) {
    println("\n--- Actualizar Superhéroe ---")
    try {
        print("ID del Superhéroe a actualizar: ")
        val id = scanner.nextInt()
        scanner.nextLine()
        val superhero = controller.getSuperheroById(id) ?: return

        print("Nuevo nombre (${superhero.name}): ")
        val newName = scanner.nextLine().takeIf { it.isNotBlank() } ?: superhero.name
        print("¿Está activo? (${superhero.isActive}) (true/false): ")
        val isActive = scanner.nextBoolean()
        scanner.nextLine()
        print("Nueva popularidad (${superhero.popularity}): ")
        val popularity = scanner.nextDouble()
        scanner.nextLine()

        val updatedSuperhero = superhero.copy(name = newName, isActive = isActive, popularity = popularity)
        controller.updateSuperhero(updatedSuperhero)
    } catch (e: Exception) {
        println("Error al actualizar superhéroe: ${e.message}")
    }
}

fun deleteSuperhero(scanner: Scanner, controller: SuperheroController) {
    println("\n--- Eliminar Superhéroe ---")
    try {
        print("ID del Superhéroe a eliminar: ")
        val id = scanner.nextInt()
        scanner.nextLine()
        controller.deleteSuperhero(id)
    } catch (e: Exception) {
        println("Error al eliminar superhéroe: ${e.message}")
    }
}

fun addPowerToSuperhero(scanner: Scanner, controller: SuperheroController) {
    println("\n--- Agregar Poder a Superhéroe Existente ---")
    try {
        print("ID del Superhéroe: ")
        val id = scanner.nextInt()
        scanner.nextLine()
        val superhero = controller.getSuperheroById(id)
        if (superhero == null) {
            println("Superhéroe no encontrado.")
            return
        }

        println("Agregando nuevo poder al superhéroe ${superhero.name}.")
        print("Nombre del Poder: ")
        val powerName = scanner.nextLine()
        print("Descripción del Poder: ")
        val description = scanner.nextLine()
        print("¿Es ofensivo? (true/false): ")
        val isOffensive = scanner.nextBoolean()
        scanner.nextLine()
        print("Efectividad (decimal): ")
        val effectiveness = scanner.nextDouble()
        scanner.nextLine()

        val newPower = Power(0, powerName, description, isOffensive, effectiveness)
        val result = superhero.addPower(newPower)
        result.onSuccess { updatedSuperhero ->
            controller.updateSuperhero(updatedSuperhero)
            println("Poder agregado exitosamente.")
        }.onFailure { error ->
            println("Error al agregar el poder: ${error.message}")
        }
    } catch (e: Exception) {
        println("Error al agregar poder: ${e.message}")
    }
}

fun updateSuperheroPower(scanner: Scanner, controller: SuperheroController) {
    println("\n--- Actualizar Poder de Superhéroe Existente ---")
    try {
        print("ID del Superhéroe: ")
        val id = scanner.nextInt()
        scanner.nextLine()
        val superhero = controller.getSuperheroById(id)
        if (superhero == null) {
            println("Superhéroe no encontrado.")
            return
        }

        println("Poderes actuales de ${superhero.name}:")
        superhero.powers.forEach { println(it.showDetails()) }

        print("ID del Poder a actualizar: ")
        val powerId = scanner.nextInt()
        scanner.nextLine()
        val power = superhero.powers.find { it.id == powerId }
        if (power == null) {
            println("Poder no encontrado.")
            return
        }

        print("Nuevo nombre del Poder: ")
        val newName = scanner.nextLine()
        print("Nueva descripción: ")
        val newDescription = scanner.nextLine()
        print("¿Es ofensivo? (true/false): ")
        val newIsOffensive = scanner.nextBoolean()
        scanner.nextLine()
        print("Nueva efectividad (decimal): ")
        val newEffectiveness = scanner.nextDouble()
        scanner.nextLine()

        val updatedPower = power.copy(
            name = newName,
            description = newDescription,
            isOffensive = newIsOffensive,
            effectiveness = newEffectiveness
        )
        val result = superhero.updatePower(updatedPower)
        result.onSuccess { updatedSuperhero ->
            controller.updateSuperhero(updatedSuperhero)
            println("Poder actualizado exitosamente.")
        }.onFailure { error ->
            println("Error al actualizar el poder: ${error.message}")
        }
    } catch (e: Exception) {
        println("Error al actualizar poder: ${e.message}")
    }
}

fun deletePowerFromSuperhero(scanner: Scanner, controller: SuperheroController) {
    println("\n--- Eliminar Poder de Superhéroe Existente ---")
    try {
        print("ID del Superhéroe: ")
        val id = scanner.nextInt()
        scanner.nextLine()
        val superhero = controller.getSuperheroById(id)
        if (superhero == null) {
            println("Superhéroe no encontrado.")
            return
        }

        println("Poderes actuales de ${superhero.name}:")
        superhero.powers.forEach { println(it.showDetails()) }

        print("ID del Poder a eliminar: ")
        val powerId = scanner.nextInt()
        scanner.nextLine()
        val result = superhero.removePower(powerId)
        result.onSuccess { updatedSuperhero ->
            controller.updateSuperhero(updatedSuperhero)
            println("Poder eliminado exitosamente.")
        }.onFailure { error ->
            println("Error al eliminar el poder: ${error.message}")
        }
    } catch (e: Exception) {
        println("Error al eliminar poder: ${e.message}")
    }
}
