package com.example.personaje

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDeDatosGeneral(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "BaseDeDatosGeneral"
        private const val TABLA_PERSONAJE = "Personaje"
        private const val TABLA_MOCHILA = "Mochila"
        private const val TABLA_PERSONAJE_MOCHILA = "Personaje_mochila"
        private const val TABLA_MOCHILA_OBJETOS = "Mochila_objetos"
        private const val TABLA_OBJETO_ORIGEN = "Objeto_origen"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ID_PERSONAJE = "id_personaje"
        private const val COLUMN_ID_MOCHILA = "id_mochila"
        private const val COLUMN_ID_MOCHILA_PERSONAJE = "id_mochila_personaje"
        private const val COLUMN_ID_OBJETO = "id_objeto"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_TIPO_OBJETO_ORIGEN = "tipo_objeto_origen"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_ESTADO_VITAL = "estado_vital"
        private const val COLUMN_PESO = "peso"

        private const val COLUMN_IMAGEN = "Imagen"
        private const val COLUMN_NUMERO_DISPONIBLE = "unidadesDisponibles"
        private const val COLUMN_PRECIO = "precio"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTablePersonaje = "CREATE TABLE $TABLA_PERSONAJE ($COLUMN_ID INTEGER, $COLUMN_NOMBRE TEXT, $COLUMN_RAZA TEXT, $COLUMN_ESTADO_VITAL TEXT)"
        val createTableMochila = "CREATE TABLE $TABLA_MOCHILA ($COLUMN_ID INTEGER, $COLUMN_PESO TEXT, $COLUMN_NOMBRE TEXT)"
        val createTablePersonajeMochila = "CREATE TABLE $TABLA_PERSONAJE_MOCHILA ($COLUMN_ID INTEGER, $COLUMN_ID_PERSONAJE INTEGER, $COLUMN_ID_MOCHILA INTEGER)"
        val createTableMochilaObjetos = "CREATE TABLE $TABLA_MOCHILA_OBJETOS ($COLUMN_ID_MOCHILA_PERSONAJE INTEGER, $COLUMN_ID_OBJETO INTEGER)"

        //HAY QUE HACERLA IGUAL QUE OBJETOS_ALEATORIOS mix // val createTableObjetos = "CREATE TABLE $TABLA_MOCHILA_OBJETOS ($COLUMN_ID INTEGER, $COLUMN_ID_MOCHILA_PERSONAJE INTEGER, $COLUMN_ID_OBJETO INTEGER, $COLUMN_TIPO_OBJETO_ORIGEN INTEGER)"

        val createTableObjetoOrigen = "CREATE TABLE $TABLA_OBJETO_ORIGEN ($COLUMN_ID INTEGER, $COLUMN_NOMBRE TEXT,$COLUMN_TIPO_OBJETO_ORIGEN INTEGER)"

        db.execSQL(createTablePersonaje)
        db.execSQL(createTableMochila)
        db.execSQL(createTablePersonajeMochila)
        db.execSQL(createTableMochilaObjetos)
        db.execSQL(createTableObjetoOrigen)

        insertarDatos(db)
    }

    private fun insertarDatos(db: SQLiteDatabase) {



    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJE")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILA")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJE_MOCHILA")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILA_OBJETOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_OBJETO_ORIGEN")
        onCreate(db)
    }
}