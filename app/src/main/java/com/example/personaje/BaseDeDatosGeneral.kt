package com.example.personaje


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.math.max


class BaseDeDatosGeneral(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 43
        private const val DATABASE_NAME = "MiBaseGeneral.db"

        private const val TABLA_PERSONAJES = "Personajes"
        private const val COLUMN_ID_PERSONAJE = "idPersonaje"
        private const val COLUMN_EMAIL = "email"
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
        private const val COLUMN_IMAGEN_ID = "imagenId"



        private const val TABLA_MOCHILAS = "Mochilas"
        private const val COLUMN_ID_MOCHILA = "idMochila"
        private const val COLUMN_PESO_MAXIMO = "pesoMaximo"
        private const val COLUMN_ESPACIO_OCUPADO = "espacioOcupado"
        private const val COLUMN_ORO = "oro"

        private const val TABLA_ARTICULOS = "Articulos"
        private const val COLUMN_ID_ARTICULO = "idArticulo"
        private const val COLUMN_TIPO_ARTICULO = "tipoArticulo"
        private const val COLUMN_PESO_ARTICULO = "peso"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_DRAWABLE = "imagen"
        private const val COLUMN_ID_MOCHILA_ARTICULO = "idMochila"

        private const val TABLA_INVENTARIO = "Inventario"
        private const val COLUMN_ID_INVENTARIO = "idInventario"

        private const val TABLA_MASCOTA = "Mascotas"
        private const val COLUMN_ID_MASCOTA = "idMascota"
        private const val COLUMN_FELICIDAD = "felicidad"

    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTablePersonaje = "CREATE TABLE $TABLA_PERSONAJES (" +
                "$COLUMN_ID_PERSONAJE INTEGER PRIMARY KEY, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_RAZA TEXT, " +
                "$COLUMN_CLASE TEXT, " +
                "$COLUMN_ESTADO_VITAL TEXT, " +
                "$COLUMN_SALUD INTEGER, " +
                "$COLUMN_ATAQUE INTEGER, " +
                "$COLUMN_DEFENSA INTEGER, " +
                "$COLUMN_EXPERIENCIA INTEGER, " +
                "$COLUMN_NIVEL INTEGER, " +
                "$COLUMN_SUERTE INTEGER, " +
                "$COLUMN_IMAGEN_ID INTEGER)"

        val createTableMochila = "CREATE TABLE $TABLA_MOCHILAS (" +
                "$COLUMN_ID_MOCHILA INTEGER PRIMARY KEY, " +
                "$COLUMN_PESO_MAXIMO INTEGER, " +
                "$COLUMN_ESPACIO_OCUPADO INTEGER DEFAULT 0, " +
                "$COLUMN_ID_PERSONAJE INTEGER," +
                "$COLUMN_ORO INTEGER, " +
                "FOREIGN KEY ($COLUMN_ID_PERSONAJE) REFERENCES $TABLA_PERSONAJES($COLUMN_ID_PERSONAJE))"

        val createTableArticulo = "CREATE TABLE $TABLA_ARTICULOS (" +
                "$COLUMN_ID_ARTICULO INTEGER PRIMARY KEY, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_TIPO_ARTICULO TEXT, " +
                "$COLUMN_PESO_ARTICULO INTEGER, " +
                "$COLUMN_PRECIO INTEGER, " +
                "$COLUMN_DRAWABLE INTEGER, " +
                "$COLUMN_ID_MOCHILA_ARTICULO INTEGER)"

        val createTableInventario = "CREATE TABLE $TABLA_INVENTARIO (" +
                "$COLUMN_ID_INVENTARIO INTEGER PRIMARY KEY, " +
                "$COLUMN_ID_ARTICULO INTEGER, " +
                "$COLUMN_ID_MOCHILA INTEGER, "+
                "FOREIGN KEY ($COLUMN_ID_ARTICULO) REFERENCES $TABLA_ARTICULOS($COLUMN_ID_ARTICULO),"+
                "FOREIGN KEY ($COLUMN_ID_MOCHILA) REFERENCES $TABLA_MOCHILAS($COLUMN_ID_MOCHILA))"


        val createTableMascota = "CREATE TABLE $TABLA_MASCOTA (" +
                "$COLUMN_ID_MASCOTA INTEGER PRIMARY KEY, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_FELICIDAD INTEGER, " +
                "$COLUMN_NIVEL INTEGER, "+
                "$COLUMN_ID_PERSONAJE INTEGER," +
                "FOREIGN KEY ($COLUMN_ID_PERSONAJE) REFERENCES $TABLA_PERSONAJES ($COLUMN_ID_PERSONAJE))"



        db.execSQL(createTablePersonaje)
        db.execSQL(createTableMochila)
        db.execSQL(createTableArticulo)
        db.execSQL(createTableInventario)
        db.execSQL(createTableMascota)

        insertarArticulos(db)

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJES")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_ARTICULOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_INVENTARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MASCOTA")
        onCreate(db)
    }

    fun insertarArticulos(db: SQLiteDatabase) {
        addArticulo(db, "MONEDA", "ORO", 0, 15, 1,1)
        addArticulo(db, "ESPADA", "ARMA", 20, 20, 2,1)
        addArticulo(db, "MARTILLO", "ARMA", 12, 50, 3,1)
        addArticulo(db, "GARRAS", "ARMA", 18, 60, 4,1)
        addArticulo(db, "BASTON", "ARMA", 25, 40, 5,1)
        addArticulo(db, "POCION", "OBJETO", 5, 5, 6,1)
        addArticulo(db, "IRA", "OBJETO", 5, 5, 7,1)
        addArticulo(db, "ESCUDO", "PROTECCION", 20, 10, 8,1)
        addArticulo(db, "ARMADURA", "PROTECCION", 40, 10, 9,1)
        addArticulo(db, "DAGA", "ARMA", 10, 25, 10,1)
        addArticulo(db, "JAMON", "COMIDA", 1, 1, 11,1)

    }
    fun addArticulo(
        db: SQLiteDatabase,
        nombre: String,
        tipo: String,
        peso: Int,
        precio: Int,
        drawable: Int,
        idMochila: Int
    ) {
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_TIPO_ARTICULO, tipo)
            put(COLUMN_PESO_ARTICULO, peso)
            put(COLUMN_PRECIO, precio)
            put(COLUMN_DRAWABLE, drawable)
            put(COLUMN_ID_MOCHILA_ARTICULO, idMochila)
        }
        db.insert(TABLA_ARTICULOS, null, values)
    }


    @SuppressLint("Range")
    fun obtenerPersonajePorEmail(email: String): Personaje? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLA_PERSONAJES,
            arrayOf(COLUMN_ID_PERSONAJE, COLUMN_EMAIL, COLUMN_NOMBRE, COLUMN_RAZA, COLUMN_CLASE, COLUMN_ESTADO_VITAL, COLUMN_SALUD, COLUMN_ATAQUE, COLUMN_DEFENSA, COLUMN_EXPERIENCIA, COLUMN_NIVEL, COLUMN_SUERTE),
            "$COLUMN_EMAIL = ?",
            arrayOf(email.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            val idPersonaje = cursor.getLong(cursor.getColumnIndex(COLUMN_ID_PERSONAJE))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
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


            val personaje = Personaje(email, nombre, raza, clase, estadoVital).apply {
                setId(idPersonaje)
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

    @SuppressLint("Range")
    fun obtenerArticulosAleatoriosDelMercader(idMochila: Int): ArrayList<Articulo>{
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLA_ARTICULOS ORDER BY RANDOM() LIMIT 4", null)
        val listadoArticulo: ArrayList<Articulo> = ArrayList()

        cursor.moveToFirst()
        do {
            val idArticulo = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ARTICULO))
            val tipoArticulo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO))
            val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
            val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO_ARTICULO))
            val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
            val imagenId = cursor.getInt(cursor.getColumnIndex(COLUMN_DRAWABLE))

            val articulo: Articulo = Articulo(Articulo.TipoArticulo.valueOf(tipoArticulo), Articulo.Nombre.valueOf(nombre), peso, precio, imagenId)
            articulo.setIdArticulo(idArticulo)
            listadoArticulo.add(articulo)
        } while(cursor.moveToNext())
        cursor.close()
        return listadoArticulo
    }

    @SuppressLint("Range")
    fun obtenerArticulosPorIdMochila(idMochila: Int): ArrayList<Articulo> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $TABLA_INVENTARIO.$COLUMN_ID_INVENTARIO, $TABLA_ARTICULOS.* FROM $TABLA_ARTICULOS INNER JOIN $TABLA_INVENTARIO ON $TABLA_ARTICULOS.$COLUMN_ID_ARTICULO = $TABLA_INVENTARIO.$COLUMN_ID_ARTICULO WHERE $TABLA_INVENTARIO.$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))

        val listadoArticulo: ArrayList<Articulo> = ArrayList()

        if (cursor.moveToFirst()) {
            do {
                val idInventario = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_INVENTARIO))
                val idArticulo = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ARTICULO))
                val tipoArticulo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO_ARTICULO))
                val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
                val imagenId = cursor.getInt(cursor.getColumnIndex(COLUMN_DRAWABLE))

                val articulo: Articulo = Articulo(
                    Articulo.TipoArticulo.valueOf(tipoArticulo),
                    Articulo.Nombre.valueOf(nombre),
                    peso,
                    precio,
                    imagenId
                )
                articulo.setIdArticulo(idArticulo)
                articulo.setIdInventario(idInventario)
                listadoArticulo.add(articulo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listadoArticulo
    }



    fun obtenerArticuloAleatorio(): Articulo? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLA_ARTICULOS ORDER BY RANDOM() LIMIT 1", null)
        var articulo: Articulo? = null
        if (cursor.moveToFirst()) {
            articulo = cursorDeArticulo(cursor)
        }
        cursor.close()
        return articulo
    }

    @SuppressLint("Range")
    private fun cursorDeArticulo(cursor: Cursor): Articulo {
        val idArticulo = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ARTICULO))
        val tipoArticulo = Articulo.TipoArticulo.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO)))
        val nombre = Articulo.Nombre.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)))
        val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO_ARTICULO))
        val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
        val imagenId = cursor.getInt(cursor.getColumnIndex(COLUMN_DRAWABLE))
        return Articulo(tipoArticulo, nombre, peso, precio, imagenId).apply { setIdArticulo(idArticulo) }
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

    @SuppressLint("Range")
    fun obtenerPersonajePorId(idPersonaje: Long): Personaje? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLA_PERSONAJES,
            null,
            "$COLUMN_ID_PERSONAJE = ?",
            arrayOf(idPersonaje.toString()),
            null,
            null,
            null
        )
        var personaje: Personaje? = null
        if (cursor.moveToFirst()) {
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
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
            val imagenId = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGEN_ID))


            personaje = Personaje(email, nombre, raza, clase, estadoVital).apply {
                setId(idPersonaje)
                setSalud(salud)
                setAtaque(ataque)
                setDefensa(defensa)
                setExperiencia(experiencia)
                setNivel(nivel)
                setSuerte(suerte)
                setImagenId(imagenId)
            }
        }
        cursor.close()
        return personaje
    }

    @SuppressLint("Range")
    fun obtenerIdMascotaPorPersonaje(idPersonaje: Long): Long {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLA_MASCOTA,
            arrayOf(COLUMN_ID_MASCOTA),
            "$COLUMN_ID_PERSONAJE = ?",
            arrayOf(idPersonaje.toString()),
            null,
            null,
            null
        )
        var idMascota: Long = -1
        if (cursor.moveToFirst()) {
            idMascota = cursor.getLong(cursor.getColumnIndex(COLUMN_ID_MASCOTA))
        }
        cursor.close()
        return idMascota
    }

    @SuppressLint("Range")
    fun obtenerNivelMascotaPorPersonaje(idPersonaje: Long): Int {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLA_MASCOTA,
            arrayOf(COLUMN_NIVEL),
            "$COLUMN_ID_PERSONAJE = ?",
            arrayOf(idPersonaje.toString()),
            null,
            null,
            null
        )
        var nivelMascota: Int = -1
        if (cursor.moveToFirst()) {
            nivelMascota = cursor.getInt(cursor.getColumnIndex(COLUMN_NIVEL))
        }
        cursor.close()
        return nivelMascota
    }

    @SuppressLint("Range")
    fun obtenerNombreMascotaPorId(idMascota: Long): String? {
        val db = this.readableDatabase
        var nombreMascota: String? = null
        val cursor = db.query(
            TABLA_MASCOTA,
            arrayOf(COLUMN_NOMBRE),
            "$COLUMN_ID_MASCOTA = ?",
            arrayOf(idMascota.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            nombreMascota = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
        }
        cursor.close()
        return nombreMascota
    }


    fun tengoComida(idMochila: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLA_INVENTARIO JOIN $TABLA_ARTICULOS ON $TABLA_INVENTARIO.$COLUMN_ID_ARTICULO = $TABLA_ARTICULOS.$COLUMN_ID_ARTICULO WHERE $COLUMN_TIPO_ARTICULO = 'COMIDA' AND $TABLA_INVENTARIO.$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
        var tieneComida = false
        if (cursor.moveToFirst()) {
            tieneComida = cursor.getInt(0) > 0
        }
        cursor.close()
        return tieneComida
    }
    @SuppressLint("Range")
    fun obtenerFelicidadMascota(idMascota: Long): Int {
        val db = this.readableDatabase
        val cursor = db.query(TABLA_MASCOTA, arrayOf(COLUMN_FELICIDAD), "$COLUMN_ID_MASCOTA = ?", arrayOf(idMascota.toString()), null, null, null)
        var felicidad = 0
        if (cursor.moveToFirst()) {
            felicidad = cursor.getInt(cursor.getColumnIndex(COLUMN_FELICIDAD))
        }
        cursor.close()
        return felicidad
    }

    fun tengoMascota(idPersonaje: Long): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Mascotas WHERE idPersonaje = ?", arrayOf(idPersonaje.toString()))
        val tieneMascota = cursor.moveToFirst()
        cursor.close()
        return tieneMascota
    }






    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    //***************************************************************************//
    fun anadirArticuloAMochila(idMochila: Int, idArticulo: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID_MOCHILA, idMochila)
            put(COLUMN_ID_ARTICULO, idArticulo)
        }
        val resultado = db.insert(TABLA_INVENTARIO, null, values)
        return resultado != -1L
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


    fun actualizarOroMochila(idMochila: Int, oro: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ORO, oro)
        db.update(TABLA_MOCHILAS, values, "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
    }

    fun insertarMochila(idPersonaje: Long) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PESO_MAXIMO, 100)
            put(COLUMN_ESPACIO_OCUPADO, 0)
            put(COLUMN_ID_PERSONAJE, idPersonaje)
            put(COLUMN_ORO, 0)
        }
        db.insert(TABLA_MOCHILAS, null, values)
    }

    fun insertarPersonaje(personaje: Personaje): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, personaje.getEmail())
            put(COLUMN_NOMBRE, personaje.getNombre())
            put(COLUMN_RAZA, personaje.getRaza().toString())
            put(COLUMN_CLASE, personaje.getClase().toString())
            put(COLUMN_ESTADO_VITAL, personaje.getEstadoVital().toString())
            put(COLUMN_SALUD, personaje.getSalud())
            put(COLUMN_ATAQUE, personaje.getAtaque())
            put(COLUMN_DEFENSA, personaje.getDefensa())
            put(COLUMN_EXPERIENCIA, personaje.getExperiencia())
            put(COLUMN_NIVEL, personaje.getNivel())
            put(COLUMN_SUERTE, personaje.getSuerte())
            put(COLUMN_IMAGEN_ID, personaje.getImagenId())
        }
        return db.insert(TABLA_PERSONAJES, null, values)
    }


    fun eliminarArticuloDeMochila(idMochila: Int, idInventario: Int) {
        val db = this.writableDatabase
        db.delete(TABLA_INVENTARIO, "$COLUMN_ID_MOCHILA = ? AND $COLUMN_ID_INVENTARIO = ?", arrayOf(idMochila.toString(), idInventario.toString()))
    }


    fun eliminarPersonaje(idPersonaje: Long) {
        val db = this.writableDatabase
        db.beginTransaction()
        db.delete(TABLA_PERSONAJES, "$COLUMN_ID_PERSONAJE = ?", arrayOf(idPersonaje.toString()))
        val idMochila = obtenerIdMochilaPorPersonaje(idPersonaje)
        db.delete(TABLA_MOCHILAS, "$COLUMN_ID_PERSONAJE = ?", arrayOf(idPersonaje.toString()))
        db.delete(TABLA_INVENTARIO, "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
        db.delete(TABLA_MASCOTA, "$COLUMN_ID_PERSONAJE = ?", arrayOf(idPersonaje.toString()))
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    fun insertarMascota(nombre: String, felicidad: Int, idPersonaje: Long): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_FELICIDAD, felicidad)
            put(COLUMN_ID_PERSONAJE, idPersonaje)
            put(COLUMN_NIVEL,1)
        }
        return db.insert(TABLA_MASCOTA, null, values)
    }

    fun actualizarFelicidadMascota(idMascota: Long, nuevaFelicidad: Int) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            if (nuevaFelicidad >= 100){
                put(COLUMN_FELICIDAD, 25)
                put(COLUMN_NIVEL,2)
            }else{
                put(COLUMN_FELICIDAD, nuevaFelicidad)
            }
        }
        db.update(TABLA_MASCOTA, values, "$COLUMN_ID_MASCOTA = ?", arrayOf(idMascota.toString()))
    }

    fun eliminarMascota(idMascota: Long) {
        val db = this.writableDatabase
        db.delete(TABLA_MASCOTA, "$COLUMN_ID_MASCOTA = ?", arrayOf(idMascota.toString()))
    }

    @SuppressLint("Range")
    fun consumirTodaLaComidaDelInventario(idMochila: Int) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {

            val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLA_INVENTARIO JOIN $TABLA_ARTICULOS ON $TABLA_INVENTARIO.$COLUMN_ID_ARTICULO = $TABLA_ARTICULOS.$COLUMN_ID_ARTICULO WHERE $COLUMN_TIPO_ARTICULO = 'COMIDA' AND $TABLA_INVENTARIO.$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
            var cantidadComida = 0
            if (cursor.moveToFirst()) {
                cantidadComida = cursor.getInt(0)
            }
            cursor.close()
            if (cantidadComida > 0) {
                db.delete("$TABLA_INVENTARIO", "$COLUMN_ID_MOCHILA = ? AND $COLUMN_ID_ARTICULO IN (SELECT $COLUMN_ID_ARTICULO FROM $TABLA_ARTICULOS WHERE $COLUMN_TIPO_ARTICULO = 'COMIDA')", arrayOf(idMochila.toString()))
                actualizarEspacioMochilaAlEliminarComida(idMochila, cantidadComida)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun actualizarEspacioMochilaAlEliminarComida(idMochila: Int, pesoComida: Int) {
        val espacioOcupadoActual = obtenerEspacioOcupadoMochila(idMochila)
        val nuevoEspacioOcupado = maxOf(0, espacioOcupadoActual - pesoComida)
        val values = ContentValues().apply {
            put(COLUMN_ESPACIO_OCUPADO, nuevoEspacioOcupado)
        }
        writableDatabase.update(TABLA_MOCHILAS, values, "$COLUMN_ID_MOCHILA = ?", arrayOf(idMochila.toString()))
    }


}




