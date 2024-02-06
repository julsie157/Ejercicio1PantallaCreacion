package com.example.personaje

class Mochila(private var pesoMochila: Int){
    private var contenido=ArrayList<Articulo>()

    fun getPesoMochila():Int{
        return pesoMochila
    }
    fun addArticulo(articulo: Articulo) {
        if (articulo.getPeso() <= pesoMochila) {
            when (articulo.getTipoArticulo()) {
                Articulo.TipoArticulo.ARMA -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                        Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS -> {
                            contenido.add(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo ARMA.")
                    }
                }
                Articulo.TipoArticulo.OBJETO -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                            contenido.add(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                    }
                }
                Articulo.TipoArticulo.PROTECCION -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                            contenido.add(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                    }
                }

                Articulo.TipoArticulo.ORO -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.MONEDA -> {
                            contenido.add(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo ORO.")
                    }
                }
            }
        } else {
            println("El peso del artículo excede el límite de la mochila.")
        }
    }
    fun getContenido(): ArrayList<Articulo> {
        return contenido
    }
    fun findObjeto(nombre: Articulo.Nombre): Int {
        return contenido.indexOfFirst { it.getNombre() == nombre }
    }
    override fun toString(): String {
        return if (contenido.isEmpty()) {
            "Mochila vacía"
        } else {
            "Artículos en la mochila: ${contenido.joinToString("\n")}"
        }
    }
}