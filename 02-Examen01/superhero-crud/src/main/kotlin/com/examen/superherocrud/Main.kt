package org.example.com.examen.superherocrud

import com.examen.superherocrud.controller.SuperheroController
import com.examen.superherocrud.model.Power
import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.repository.SuperheroRepository
import com.examen.superherocrud.service.SuperheroService
import com.examen.superherocrud.xml.XmlParser
import java.time.LocalDate
import java.util.Scanner

fun main() {
    val filePath = "src/main/resources/xml/data.xml"

    val xmlParser = XmlParser(filePath)
    val repository = SuperheroRepository(xmlParser)
    val service = SuperheroService(repository)
    val controller = SuperheroController(service)

    // Inicializar el contador de IDs basado en los datos existentes
    Superhero.initializeIdCounter(controller.getAllSuperheroes())

    val scanner = Scanner(System.`in`)

    while (true) {
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
        val option = scanner.nextInt()
        scanner.nextLine()
        when (option) {
            1 -> {
                // Agregar Superhéroe
                println("\n--- Agregar Superhéroe ---")
                print("Nombre: ")
                val name = scanner.nextLine()
                print("¿Está activo? (true/false): ")
                val isActive = scanner.nextBoolean()
                scanner.nextLine()
                print("Fecha de debut (YYYY-MM-DD): ")
                val debutDate = LocalDate.parse(scanner.nextLine())
                print("Popularidad (decimal): ")
                val popularity = scanner.nextDouble()
                scanner.nextLine()

                // Agregar poderes
                val powers = mutableListOf<Power>()
                println("\n--- Agregar Poderes ---")
                while (true) {
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
                    powers.add(Power(powerName, description, isOffensive, effectiveness))

                    print("¿Agregar otro poder? (y/n): ")
                    if (scanner.next() != "y") break
                    scanner.nextLine()
                }

                val superhero = Superhero(name, isActive, debutDate, popularity, powers)
                controller.addSuperhero(superhero)
                println("Superhéroe agregado exitosamente.")
            }

            2 -> {
                // Listar Superhéroes
                println("\n--- Lista de Superhéroes ---")
                val superheroes = controller.getAllSuperheroes()
                if (superheroes.isEmpty()) {
                    println("No hay superhéroes registrados.")
                } else {
                    superheroes.forEach { println(it.showSuperheroDetails() + '\n') }
                }
            }

            3 -> {
                // Buscar Superhéroe por ID
                println("\n--- Buscar Superhéroe por ID ---")
                print("ID del Superhéroe: ")
                val id = scanner.nextInt()
                val superhero = controller.getSuperheroById(id)
                println("Superhéroe encontrado: ${superhero.showSuperheroDetails()}")
            }

            4 -> {
                // Actualizar Superhéroe
                println("\n--- Actualizar Superhéroe ---")
                print("ID del Superhéroe a actualizar: ")
                val id = scanner.nextInt()
                scanner.nextLine()
                val existingSuperhero = controller.getSuperheroById(id)
                print("Nuevo nombre (${existingSuperhero.getName()}): ")
                val name = scanner.nextLine()
                print("¿Está activo? (${existingSuperhero.isActive()}) (true/false): ")
                val isActive = scanner.nextBoolean()
                print("Fecha de debut (${existingSuperhero.getDebutDate()}) (YYYY-MM-DD): ")
                val debutDate = LocalDate.parse(scanner.next())
                print("Popularidad (${existingSuperhero.getPopularity()}): ")
                val popularity = scanner.nextDouble()

                val updatedSuperhero = existingSuperhero.copy(
                    name = name,
                    isActive = isActive,
                    debutDate = debutDate,
                    popularity = popularity
                )
                controller.updateSuperhero(updatedSuperhero)
                println("Superhéroe actualizado exitosamente.")
            }

            5 -> {
                // Eliminar Superhéroe
                println("\n--- Eliminar Superhéroe ---")
                print("ID del Superhéroe a eliminar: ")
                val id = scanner.nextInt()
                controller.deleteSuperhero(id)
                println("Superhéroe eliminado exitosamente.")
            }

            6 -> {
                // Agregar Poder a Superhéroe Existente
                println("\n--- Agregar Poder a Superhéroe Existente ---")
                print("ID del Superhéroe: ")
                val superheroId = scanner.nextInt()
                scanner.nextLine()
                val superhero = controller.getSuperheroById(superheroId)

                println("Agregando nuevo poder al superhéroe ${superhero.getName()}.")
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

                val newPower = Power(powerName, description, isOffensive, effectiveness)
                superhero.addPower(newPower)
                controller.updateSuperhero(superhero)
                println("Poder agregado exitosamente.")
            }

            7 -> {
                // Actualizar Poder de Superhéroe Existente
                println("\n--- Actualizar Poder de Superhéroe Existente ---")
                print("ID del Superhéroe: ")
                val superheroId = scanner.nextInt()
                val superhero = controller.getSuperheroById(superheroId)

                println("Poderes actuales de ${superhero.getName()}:")
                superhero.getPowers().forEach { println(it.showPowerDetails()) }

                print("ID del Poder a actualizar: ")
                val powerId = scanner.nextInt()
                val power = superhero.getPowers().find { it.getId() == powerId }

                if (power != null) {
                    scanner.nextLine()
                    print("Nuevo nombre del Poder: ")
                    val newName = scanner.nextLine()
                    print("Nueva descripción: ")
                    val newDescription = scanner.nextLine()
                    print("¿Es ofensivo? (true/false): ")
                    val newIsOffensive = scanner.nextBoolean()
                    print("Nueva efectividad (decimal): ")
                    val newEffectiveness = scanner.nextDouble()
                    scanner.nextLine()

                    val updatedPower = power.copy(
                        name = newName,
                        description = newDescription,
                        isOffensive = newIsOffensive,
                        effectiveness = newEffectiveness
                    )

                    superhero.updatePower(updatedPower)
                    controller.updateSuperhero(superhero)
                    println("Poder actualizado exitosamente.")
                } else {
                    println("Poder no encontrado.")
                }
            }

            8 -> {
                // Eliminar Poder de Superhéroe Existente
                println("\n--- Eliminar Poder de Superhéroe Existente ---")
                print("ID del Superhéroe: ")
                val superheroId = scanner.nextInt()
                val superhero = controller.getSuperheroById(superheroId)

                println("Poderes actuales de ${superhero.getName()}:")
                superhero.getPowers().forEach { println(it.showPowerDetails()) }

                print("ID del Poder a eliminar: ")
                val powerId = scanner.nextInt()
                superhero.removePower(powerId)
                controller.updateSuperhero(superhero)
                println("Poder eliminado exitosamente.")
            }

            9 -> {
                // Salir
                println("Saliendo del programa...")
                break
            }

            else -> println("Opción no válida. Intenta nuevamente.")
        }
    }
    scanner.close()
}

