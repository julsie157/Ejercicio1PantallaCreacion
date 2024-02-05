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
