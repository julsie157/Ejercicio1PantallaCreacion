package com.example.personaje

import android.os.Parcel
import android.os.Parcelable

class Personaje(
   private val raza: String,
    private val nombre: String,
    private val estadoVital: String,
    private val pesoMochila: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(raza)
        parcel.writeString(nombre)
        parcel.writeString(estadoVital)
        parcel.writeDouble(pesoMochila)
    }

    companion object CREATOR : Parcelable.Creator<Personaje> {
        override fun createFromParcel(parcel: Parcel): Personaje {
            return Personaje(parcel)
        }

        override fun newArray(size: Int): Array<Personaje?> {
            return arrayOfNulls(size)
        }
    }
    fun getNombre(): String? {
        return nombre
    }

    fun getRaza(): String? {
        return raza
    }

    fun getEstadoVital(): String? {
        return estadoVital
    }

    fun getPesoMochila(): Double {
        return pesoMochila
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Articulo(private var tipoArticulo: TipoArticulo, private var nombre: Nombre, private var peso: Int,private var precio: Int) {

    enum class TipoArticulo { ARMA, OBJETO, PROTECCION, ORO}
    enum class Nombre { BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA,MONEDA}

    fun getPeso(): Int {
        return peso
    }

    fun getPrecio(): Int {
        return precio
    }
    fun getNombre(): Nombre {
        return nombre
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
            else -> 0 // Para otros tipos de armas no especificados
        }
    }
    fun getAumentoDefensa(): Int {
        return when (nombre) {
            Nombre.ESCUDO -> 10
            Nombre.ARMADURA -> 20
            else -> 0 // Para otros tipos de protecciones no especificados
        }
    }
    fun getAumentoVida(): Int {
        return when (nombre) {
            Nombre.POCION -> 100
            else -> 0 // Para otros tipos de objetos no especificados
        }
    }

    override fun toString(): String {
        return "Articulo(tipoArticulo=$tipoArticulo, nombre=$nombre, peso=$peso, precio=$precio)"
    }
}

