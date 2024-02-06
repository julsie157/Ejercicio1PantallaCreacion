

package com.example.personaje
/*
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Objetos_Aleatorios(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ObjetosAleatoriosDB"
        private const val TABLA_OBJETOS = "OBJETOS_ALEATORIOS"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_IMAGEN = "Imagen"
        private const val COLUMN_NUMERO_DISPONIBLE = "unidadesDisponibles"
        private const val COLUMN_PRECIO = "precio"

        fun getColumnNombre() = COLUMN_NOMBRE
        fun getColumnPeso() = COLUMN_PESO
        fun getColumnImagen() = COLUMN_IMAGEN


    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE $TABLA_OBJETOS ($COLUMN_NOMBRE TEXT, $COLUMN_TIPO TEXT, $COLUMN_PESO INTEGER, $COLUMN_IMAGEN TEXT, $COLUMN_NUMERO_DISPONIBLE INTEGER, $COLUMN_PRECIO INTEGER)"
        db.execSQL(createTable)
        insertarDatos(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_OBJETOS")
        onCreate(db)
    }

    private fun insertarDatos(db: SQLiteDatabase) {
        //10 objetos
        addArticulo(db, "MONEDA", "ORO", 0, 1, 10, 15)
        addArticulo(db, "ESPADA", "ARMA", 20, 2, 10, 20)
        addArticulo(db, "MARTILLO", "ARMA", 12, 3, 10, 50)
        addArticulo(db, "GARRAS", "ARMA", 18, 4, 10, 60)
        addArticulo(db, "BASTON", "ARMA", 25, 5, 10, 40)
        addArticulo(db, "POCION", "OBJETO", 5, 6, 10, 5)
        addArticulo(db, "IRA", "OBJETO", 5, 7, 10, 5)
        addArticulo(db, "ESCUDO", "PROTECCION", 20, 8, 10, 10)
        addArticulo(db, "ARMADURA", "PROTECCION", 40, 9, 10, 10)
        addArticulo(db, "DAGA", "ARMA", 10, 10, 10, 25)

    }

    private fun addArticulo(
        db: SQLiteDatabase,
        nombre: String,
        tipo: String,
        peso: Int,
        nombreDrawable: Int,
        unidadesDisponibles: Int,
        precio: Int
    ) {
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_TIPO, tipo)
        values.put(COLUMN_PESO, peso)
        values.put(COLUMN_IMAGEN, nombreDrawable)
        values.put(COLUMN_NUMERO_DISPONIBLE, unidadesDisponibles)
        values.put(COLUMN_PRECIO, precio)

        db.insert(TABLA_OBJETOS, null, values)
    }

    fun obtenerObjetoAleatorio(): Cursor? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLA_OBJETOS ORDER BY RANDOM() LIMIT 1"
        return db.rawQuery(query, null)
    }



}
*/
