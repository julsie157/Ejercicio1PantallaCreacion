package com.example.personaje

class Mochila(
    val idMochila: Int = -1,
    var pesoMaximo: Int,
    var espacioOcupado: Int = 0,
    var oro: Int = 0,
    val idPersonaje: Long = -1
)
{
    private var contenido=ArrayList<Articulo>()

    fun getContenido(): ArrayList<Articulo> {
        return contenido
    }
    fun findObjeto(nombre: Articulo.Nombre): Int {
        return contenido.indexOfFirst { it.getNombre() == nombre }
    }

    override fun toString(): String {
        return "Mochila(idMochila=$idMochila, pesoMaximo=$pesoMaximo, espacioOcupado=$espacioOcupado, oro=$oro, idPersonaje=$idPersonaje, contenido=$contenido)"
    }
}