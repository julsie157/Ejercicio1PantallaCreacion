package com.example.personaje


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.drawable.AdaptiveIconDrawable


class BaseDeDatosGeneral(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Mibasegeneral"


        private const val TABLA_PERSONAJES = "Personajes"
        private const val COLUMN_ID_PERSONAJE = "idPersonaje"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_CLASE = "clase"
        private const val COLUMN_ESTADO_VITAL = "estadoVital"
        private const val COLUMN_SALUD = "salud"
        private const val COLUMN_ATAQUE = "ataque"
        private const val COLUMN_DEFENSA = "defensa"
        private const val COLUMN_EXPERIENCIA = "experiencia"
        private const val COLUMN_NIVEL = "nivel"
        private const val COLUMN_SUERTE = "suerte"


        private const val TABLA_MOCHILAS = "Mochilas"
        private const val COLUMN_ID_MOCHILA = "idMochila"
        private const val COLUMN_PESO_MAXIMO = "pesoMaximo"


        private const val TABLA_ARTICULOS = "Articulos"
        private const val COLUMN_ID_ARTICULO = "idArticulo"
        private const val COLUMN_TIPO_ARTICULO = "tipoArticulo"
        private const val COLUMN_PESO_ARTICULO = "peso"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_DRAWABLE = "imagen"
        private const val COLUMN_ID_MOCHILA_ARTICULO = "idMochila"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTablePersonaje = ("CREATE TABLE $TABLA_PERSONAJES(" +
                "$COLUMN_ID_PERSONAJE INTEGER PRIMARY KEY," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_RAZA TEXT," +
                "$COLUMN_CLASE TEXT," +
                "$COLUMN_ESTADO_VITAL TEXT," +
                "$COLUMN_SALUD INTEGER," +
                "$COLUMN_ATAQUE INTEGER," +
                "$COLUMN_DEFENSA INTEGER," +
                "$COLUMN_EXPERIENCIA INTEGER," +
                "$COLUMN_NIVEL INTEGER," +
                "$COLUMN_SUERTE INTEGER)")

        val createTableMochila = ("CREATE TABLE $TABLA_MOCHILAS(" +
                "$COLUMN_ID_MOCHILA INTEGER PRIMARY KEY," +
                "$COLUMN_PESO_MAXIMO INTEGER," +
                "$COLUMN_ID_PERSONAJE INTEGER," +
                "FOREIGN KEY ($COLUMN_ID_PERSONAJE) REFERENCES $TABLA_PERSONAJES($COLUMN_ID_PERSONAJE))")

        val createTableArticulo = ("CREATE TABLE $TABLA_ARTICULOS(" +
                "$COLUMN_ID_ARTICULO INTEGER PRIMARY KEY," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_TIPO_ARTICULO TEXT," +
                "$COLUMN_PESO_ARTICULO INTEGER," +
                "$COLUMN_PRECIO INTEGER," +
                "$COLUMN_DRAWABLE INTEGER," +
                "$COLUMN_ID_MOCHILA_ARTICULO INTEGER," +
                "FOREIGN KEY ($COLUMN_ID_MOCHILA_ARTICULO) REFERENCES $TABLA_MOCHILAS($COLUMN_ID_MOCHILA))")

        db.execSQL(createTablePersonaje)
        db.execSQL(createTableMochila)
        db.execSQL(createTableArticulo)
        
        insertarArticulos(db)
    }

    private fun insertarArticulos(db: SQLiteDatabase) {

        addArticulo(db, "MONEDA", "ORO", 0, 15,1)
        addArticulo(db, "ESPADA", "ARMA", 20, 20,2)
        addArticulo(db, "MARTILLO", "ARMA", 12, 50,3)
        addArticulo(db, "GARRAS", "ARMA", 18, 60,4)
        addArticulo(db, "BASTON", "ARMA", 25, 40,5,)
        addArticulo(db, "POCION", "OBJETO", 5, 5,6)
        addArticulo(db, "IRA", "OBJETO", 5, 5,7)
        addArticulo(db, "ESCUDO", "PROTECCION", 20, 10,8)
        addArticulo(db, "ARMADURA", "PROTECCION", 40, 10,9)
        addArticulo(db, "DAGA", "ARMA", 10, 25,10)
    }

    private fun addArticulo(
        db: SQLiteDatabase,
        nombre: String,
        tipo: String,
        peso: Int,
        precio: Int,
        drawable: Int
    ) {
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_TIPO_ARTICULO, tipo)
            put(COLUMN_PESO_ARTICULO, peso)
            put(COLUMN_PRECIO, precio)
            put(COLUMN_DRAWABLE,drawable)
        }
        db.insert(TABLA_ARTICULOS, null, values)
    }

    private fun agregarPersonaje(nombre: String, raza: String, clase: String, estadoVital: String, salud: Int, ataque: Int, defensa: Int, experiencia: Int, nivel: Int, suerte: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_RAZA, raza)
            put(COLUMN_CLASE, clase)
            put(COLUMN_ESTADO_VITAL, estadoVital)
            put(COLUMN_SALUD, salud)
            put(COLUMN_ATAQUE, ataque)
            put(COLUMN_DEFENSA, defensa)
            put(COLUMN_EXPERIENCIA, experiencia)
            put(COLUMN_NIVEL, nivel)
            put(COLUMN_SUERTE, suerte)

        }
        db.insert(TABLA_PERSONAJES, null, values)
    }




    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJES")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_ARTICULOS")
        onCreate(db)
    }

    fun obtenerArticuloAleatorio(): Cursor? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLA_ARTICULOS ORDER BY RANDOM() LIMIT 1"
        return db.rawQuery(query, null)
    }

    fun actualizarEspacioMochila(idMochila: Int, espacioOcupado: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("espacioOcupado", espacioOcupado)
        db.update(TABLA_MOCHILAS, values, "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
    }

    @SuppressLint("Range")
    fun obtenerEspacioDisponibleMochila(idMochila: Int): Int {
        val db = this.readableDatabase
        val cursor = db.query(TABLA_MOCHILAS, arrayOf(COLUMN_PESO_MAXIMO, "espacioOcupado"), "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()), null, null, null)
        if (cursor.moveToFirst()) {
            val pesoMaximo = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO_MAXIMO))
            val espacioOcupado = cursor.getInt(cursor.getColumnIndex("espacioOcupado"))
            cursor.close()
            return pesoMaximo - espacioOcupado
        }
        cursor.close()
        return -1
    }






}
