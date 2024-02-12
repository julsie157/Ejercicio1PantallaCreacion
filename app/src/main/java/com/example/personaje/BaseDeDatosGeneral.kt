package com.example.personaje


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class BaseDeDatosGeneral(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MiBaseGeneral.db"

        private const val TABLA_PERSONAJES = "Personajes"
        private const val COLUMN_ID_PERSONAJE = "idPersonaje"
        const val COLUMN_NOMBRE = "nombre"
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
        private const val COLUMN_ESPACIO_OCUPADO = "espacioOcupado"
        private const val COLUMN_ORO = "oro"

        private const val TABLA_ARTICULOS = "Articulos"
        private const val COLUMN_ID_ARTICULO = "idArticulo"
        private const val COLUMN_TIPO_ARTICULO = "tipoArticulo"
        const val COLUMN_PESO_ARTICULO = "peso"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_DRAWABLE = "imagen"
        private const val COLUMN_ID_MOCHILA_ARTICULO = "idMochila"


    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTablePersonaje = "CREATE TABLE $TABLA_PERSONAJES (" +
                "$COLUMN_ID_PERSONAJE INTEGER PRIMARY KEY, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_RAZA TEXT, " +
                "$COLUMN_CLASE TEXT, " +
                "$COLUMN_ESTADO_VITAL TEXT, " +
                "$COLUMN_SALUD INTEGER, " +
                "$COLUMN_ATAQUE INTEGER, " +
                "$COLUMN_DEFENSA INTEGER, " +
                "$COLUMN_EXPERIENCIA INTEGER, " +
                "$COLUMN_NIVEL INTEGER, " +
                "$COLUMN_SUERTE INTEGER)"

        val createTableMochila = "CREATE TABLE $TABLA_MOCHILAS (" +
                "$COLUMN_ID_MOCHILA INTEGER PRIMARY KEY, " +
                "$COLUMN_PESO_MAXIMO INTEGER, " +
                "$COLUMN_ESPACIO_OCUPADO INTEGER DEFAULT 0, " +
                "$COLUMN_ID_PERSONAJE INTEGCOLU," +
                "$COLUMN_ORO INTEGER, " +
                "FOREIGN KEY ($COLUMN_ID_PERSONAJE) REFERENCES $TABLA_PERSONAJES($COLUMN_ID_PERSONAJE))"

        val createTableArticulo = "CREATE TABLE $TABLA_ARTICULOS (" +
                "$COLUMN_ID_ARTICULO INTEGER PRIMARY KEY, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_TIPO_ARTICULO TEXT, " +
                "$COLUMN_PESO_ARTICULO INTEGER, " +
                "$COLUMN_PRECIO INTEGER, " +
                "$COLUMN_DRAWABLE INTEGER, " +
                "$COLUMN_ID_MOCHILA_ARTICULO INTEGER, " +
                "FOREIGN KEY ($COLUMN_ID_MOCHILA_ARTICULO) REFERENCES $TABLA_MOCHILAS($COLUMN_ID_MOCHILA))"

        db.execSQL(createTablePersonaje)
        db.execSQL(createTableMochila)
        db.execSQL(createTableArticulo)

        insertarArticulos(db)

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJES")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_ARTICULOS")
        onCreate(db)
    }


    fun insertarArticulos(db: SQLiteDatabase) {

        addArticulo(db, "MONEDA", "ORO", 0, 15, 1)
        addArticulo(db, "ESPADA", "ARMA", 20, 20, 2)
        addArticulo(db, "MARTILLO", "ARMA", 12, 50, 3)
        addArticulo(db, "GARRAS", "ARMA", 18, 60, 4)
        addArticulo(db, "BASTON", "ARMA", 25, 40, 5,)
        addArticulo(db, "POCION", "OBJETO", 5, 5, 6)
        addArticulo(db, "IRA", "OBJETO", 5, 5, 7)
        addArticulo(db, "ESCUDO", "PROTECCION", 20, 10, 8)
        addArticulo(db, "ARMADURA", "PROTECCION", 40, 10, 9)
        addArticulo(db, "DAGA", "ARMA", 10, 25, 10)
    }

    fun addArticulo(
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
            put(COLUMN_DRAWABLE, drawable)
        }
        db.insert(TABLA_ARTICULOS, null, values)
    }

    fun obtenerArticuloAleatorio(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLA_ARTICULOS ORDER BY RANDOM() LIMIT 1", null)
    }


    @SuppressLint("Range")
    fun obtenerEspacioDisponibleMochila(idMochila: Int): Int {
        val db = this.readableDatabase
        val cursor = db.query(TABLA_MOCHILAS, arrayOf(COLUMN_PESO_MAXIMO, COLUMN_ESPACIO_OCUPADO), "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()), null, null, null)
        if (cursor.moveToFirst()) {
            val pesoMaximo = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO_MAXIMO))
            val espacioOcupado = cursor.getInt(cursor.getColumnIndex(COLUMN_ESPACIO_OCUPADO))
            cursor.close()
            return pesoMaximo - espacioOcupado
        }
        cursor.close()
        return -1
    }

    fun actualizarEspacioMochila(idMochila: Int, pesoObjetoRecogido: Int) {
        val db = this.writableDatabase
        val espacioOcupadoActual = obtenerEspacioOcupadoMochila(idMochila)
        val nuevoEspacioOcupado = espacioOcupadoActual + pesoObjetoRecogido
        val values = ContentValues().apply {
            put(COLUMN_ESPACIO_OCUPADO, nuevoEspacioOcupado)
        }
        db.update(TABLA_MOCHILAS, values, "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
    }

    @SuppressLint("Range")
    fun obtenerEspacioOcupadoMochila(idMochila: Int): Int {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLA_MOCHILAS,
            arrayOf(COLUMN_ESPACIO_OCUPADO),
            "$COLUMN_ID_MOCHILA = ?",
            arrayOf(idMochila.toString()),
            null, null, null
        )
        var espacioOcupado = 0
        if (cursor.moveToFirst()) {
            espacioOcupado = cursor.getInt(cursor.getColumnIndex(COLUMN_ESPACIO_OCUPADO))
        }
        cursor.close()
        return espacioOcupado
    }


    @SuppressLint("Range")
    fun obtenerIdMochilaPorPersonaje(idPersonaje: Long): Int {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLA_MOCHILAS,
            arrayOf(COLUMN_ID_MOCHILA),
            "$COLUMN_ID_PERSONAJE = ?",
            arrayOf(idPersonaje.toString()),
            null, null, null
        )
        var idMochila = -1
        if (cursor.moveToFirst()) {
            idMochila = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_MOCHILA))
        }
        cursor.close()
        return idMochila
    }


    fun insertarMochila(idPersonaje: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PESO_MAXIMO, 100) // Asegúrate de que este valor es correcto
            put(COLUMN_ESPACIO_OCUPADO, 0) // Inicialmente sin ocupar
            put(COLUMN_ID_PERSONAJE, idPersonaje)
            put(COLUMN_ESPACIO_OCUPADO, 0)
        }
        db.insert(TABLA_MOCHILAS, null, values)
    }

    @SuppressLint("Range")
    fun obtenerPersonajePorId(id: Long): Personaje? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLA_PERSONAJES,
            arrayOf(COLUMN_ID_PERSONAJE, COLUMN_NOMBRE, COLUMN_RAZA, COLUMN_CLASE, COLUMN_ESTADO_VITAL, COLUMN_SALUD, COLUMN_ATAQUE, COLUMN_DEFENSA, COLUMN_EXPERIENCIA, COLUMN_NIVEL, COLUMN_SUERTE),
            "$COLUMN_ID_PERSONAJE = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
            val raza = Personaje.Raza.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_RAZA)))
            val clase = Personaje.Clase.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_CLASE)))
            val estadoVital = Personaje.EstadoVital.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_ESTADO_VITAL)))
            val salud = cursor.getInt(cursor.getColumnIndex(COLUMN_SALUD))
            val ataque = cursor.getInt(cursor.getColumnIndex(COLUMN_ATAQUE))
            val defensa = cursor.getInt(cursor.getColumnIndex(COLUMN_DEFENSA))
            val experiencia = cursor.getInt(cursor.getColumnIndex(COLUMN_EXPERIENCIA))
            val nivel = cursor.getInt(cursor.getColumnIndex(COLUMN_NIVEL))
            val suerte = cursor.getInt(cursor.getColumnIndex(COLUMN_SUERTE))

            // Asignación directa de valores obtenidos al constructor de Personaje
            val personaje = Personaje(nombre, raza, clase, estadoVital).apply {
                setSalud(salud)
                setAtaque(ataque)
                setDefensa(defensa)
                setExperiencia(experiencia)
                setNivel(nivel)
                setSuerte(suerte)
            }

            cursor.close()
            return personaje
        }
        cursor.close()
        return null
    }


    fun insertarPersonaje(
        nombre: String,
        raza: String,
        clase: String,
        estadoVital: String,
        salud: Int,
        ataque: Int,
        defensa: Int,
        experiencia: Int,
        nivel: Int,
        suerte: Int
    ): Long {
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

        return db.insert(TABLA_PERSONAJES, null, values)
    }


    fun actualizarOroMochila(idMochila: Int, oro: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ORO, oro)
        db.update(TABLA_MOCHILAS, values, "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
    }

    @SuppressLint("Range")
    fun obtenerOroMochila(idMochila: Int): Int {
        val db = this.readableDatabase
        val cursor = db.query(TABLA_MOCHILAS, arrayOf(COLUMN_ORO), "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()), null, null, null)
        var oro = 0
        if (cursor.moveToFirst()) {
            oro = cursor.getInt(cursor.getColumnIndex(COLUMN_ORO))
        }
        cursor.close()
        return oro
    }

}
