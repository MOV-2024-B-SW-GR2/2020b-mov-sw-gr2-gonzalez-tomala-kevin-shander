package com.examen.superherocrud.xml

import com.examen.superherocrud.model.Superhero
import com.examen.superherocrud.model.Power
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import java.time.LocalDate
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XmlParser(private val filePath: String) {

    //Lee todos los superhéroes y sus poderes del archivo XML.
    fun readAllSuperheroes(): List<Superhero> {
        val superheroes = mutableListOf<Superhero>()
        val file = File(filePath)

        if (!file.exists()) return superheroes // Si el archivo no existe, devolver una lista vacía

        try {
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
            val nodes = document.getElementsByTagName("superhero")

            for (i in 0 until nodes.length) {
                val element = nodes.item(i) as Element
                val id = element.getTagContent("id").toInt()
                val name = element.getTagContent("name")
                val isActive = element.getTagContent("isActive").toBoolean()
                val debutDate = LocalDate.parse(element.getTagContent("debutDate"))
                val popularity = element.getTagContent("popularity").toDouble()

                // Leer los poderes de este superhéroe
                val powers = mutableListOf<Power>()
                val powerNodes = element.getElementsByTagName("power")
                for (j in 0 until powerNodes.length) {
                    val powerElement = powerNodes.item(j) as Element
                    val powerId = powerElement.getTagContent("id").toInt()
                    val powerName = powerElement.getTagContent("name")
                    val description = powerElement.getTagContent("description")
                    val isOffensive = powerElement.getTagContent("isOffensive").toBoolean()
                    val effectiveness = powerElement.getTagContent("effectiveness").toDouble()

                    powers.add(Power(powerId, powerName, description, isOffensive, effectiveness))
                }

                // Crear el superhéroe con sus poderes y añadirlo a la lista
                superheroes.add(Superhero(id, name, isActive, debutDate, popularity, powers))
            }
        } catch (e: Exception) {
            println("Error al leer el archivo XML: ${e.message}")
        }
        return superheroes
    }

    //Escribe todos los superhéroes y sus poderes en el archivo XML.
    fun writeAllSuperheroes(superheroes: List<Superhero>) {
        try {
            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
            val root = document.createElement("superheroes")
            document.appendChild(root)

            for (superhero in superheroes) {
                val superheroElement = document.createElement("superhero")

                superheroElement.appendChild(createElement(document, "id", superhero.id.toString()))
                superheroElement.appendChild(createElement(document, "name", superhero.name))
                superheroElement.appendChild(createElement(document, "isActive", superhero.isActive.toString()))
                superheroElement.appendChild(createElement(document, "debutDate", superhero.debutDate.toString()))
                superheroElement.appendChild(createElement(document, "popularity", superhero.popularity.toString()))

                // Crear el contenedor de poderes y agregar cada poder como un elemento hijo
                val powersElement = document.createElement("powers")
                for (power in superhero.powers) {
                    val powerElement = document.createElement("power")

                    powerElement.appendChild(createElement(document, "id", power.id.toString()))
                    powerElement.appendChild(createElement(document, "name", power.name))
                    powerElement.appendChild(createElement(document, "description", power.description))
                    powerElement.appendChild(createElement(document, "isOffensive", power.isOffensive.toString()))
                    powerElement.appendChild(createElement(document, "effectiveness", power.effectiveness.toString()))

                    powersElement.appendChild(powerElement) // Agregar cada poder al contenedor de poderes
                }
                superheroElement.appendChild(powersElement) // Agregar contenedor de poderes al superhéroe

                root.appendChild(superheroElement)
            }

            // Configurar el transformador para aplicar sangrías
            val transformer = TransformerFactory.newInstance().newTransformer().apply {
                setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml")
                setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes")
                setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4")
                setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "no")
            }

            val source = DOMSource(document)
            val result = StreamResult(File(filePath))
            transformer.transform(source, result)

        } catch (e: Exception) {
            println("Error al escribir en el archivo XML: ${e.message}")
        }
    }

    //Helper para crear un elemento XML.
    private fun createElement(document: Document, tagName: String, value: String): Element {
        val element = document.createElement(tagName)
        element.appendChild(document.createTextNode(value))
        return element
    }

    //Extensión para obtener el contenido de una etiqueta XML.
    private fun Element.getTagContent(tagName: String): String {
        return this.getElementsByTagName(tagName).item(0).textContent
    }
}
