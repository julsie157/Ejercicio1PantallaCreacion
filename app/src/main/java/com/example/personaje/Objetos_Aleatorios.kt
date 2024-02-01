package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
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
        addArticulo(db, "ESPADA", "ARMA", 15, 2, 3, 20)
        addArticulo(db, "MARTILLO", "ARMA", 12, 3, 1, 50)
        addArticulo(db, "GARRAS", "ARMA", 30, 4, 1, 60)
        addArticulo(db, "BASTON", "ARMA", 25, 5, 2, 40)
        addArticulo(db, "POCION", "OBJETO", 5, 6, 5, 5)
        addArticulo(db, "IRA", "OBJETO", 5, 7, 5, 5)
        addArticulo(db, "ESCUDO", "PROTECCION", 3, 8, 8, 10)
        addArticulo(db, "ARMADURA", "PROTECCION", 3, 9, 8, 10)
        addArticulo(db, "DAGA", "ARMA", 10, 10, 6, 25)

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

    @SuppressLint("Range")
    fun obtenerObjetoAleatorio(): Articulo? {
        val db = this.readableDatabase
        var articulo: Articulo? = null
        val selectQuery = "SELECT COLUMN_$TABLA_OBJETOS ORDER BY RANDOM() LIMIT 1"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val tipoArticulo = Articulo.TipoArticulo.valueOf(cursor.getString(cursor.getColumnIndex(
                COLUMN_TIPO)))
            val nombre = Articulo.Nombre.valueOf(cursor.getString(cursor.getColumnIndex(
                COLUMN_NOMBRE)))
            val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
            val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
            articulo = Articulo(tipoArticulo, nombre, peso, precio)        }
        cursor.close()
        return articulo
    }

}

