package com.example.personaje


class Articulo(private var tipoArticulo: TipoArticulo, private var nombre: Nombre, private var peso: Int, private var precio: Int, private var imagenId: Int) {

    private var idArticulo: Int = -1
    private var idInventario: Int = -1

    enum class TipoArticulo { ARMA, OBJETO, PROTECCION, ORO , COMIDA,TRAMPA}
    enum class Nombre { BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA, MONEDA, JAMON,MIMICO }

    fun getIdInventario(): Int {
        return idInventario
    }

    fun setIdInventario(idInventario: Int) {
        this.idInventario = idInventario
    }
    fun getIdArticulo(): Int {
        return idArticulo
    }

    fun setIdArticulo(idArticulo: Int) {
        this.idArticulo = idArticulo
    }

    fun getPeso(): Int {
        return peso
    }
    fun getNombre(): Nombre {
        return nombre
    }

    fun getPrecio(): Int {
        return precio
    }

    fun getImagenId(): Int {
        return imagenId
    }

    fun getPrecioAumentado(): Double {
        return precio * 1.5
    }
    fun getTipoArticulo(): TipoArticulo {
        return tipoArticulo
    }
    fun getAumentoAtaque(): Int {
        return when (nombre) {
            Nombre.BASTON -> 10
            Nombre.ESPADA -> 20
            Nombre.DAGA -> 15
            Nombre.MARTILLO -> 25
            Nombre.GARRAS -> 30
            Nombre.IRA -> 80
            else -> 0
        }
    }
    fun getAumentoDefensa(): Int {
        return when (nombre) {
            Nombre.ESCUDO -> 10
            Nombre.ARMADURA -> 20
            else -> 0
        }
    }
    fun getAumentoVida(): Int {
        return when (nombre) {
            Nombre.POCION -> 100
            else -> 0
        }
    }
    override fun toString(): String {
        return "[Id Inventario:$idInventario, Id Articulo:$idArticulo, Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso, Precio:$precio, ImagenId:$imagenId]"
    }

}
