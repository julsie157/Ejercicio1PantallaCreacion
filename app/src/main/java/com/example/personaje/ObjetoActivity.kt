package com.example.personaje
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ObjetoActivity : AppCompatActivity() {
    private lateinit var objetoActual: Objeto
    private var espacioMochilaDisponible: Int = 100 // Capacidad máxima de la mochila (ajusta según tus necesidades)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_objeto)

        val recogerButton = findViewById<Button>(R.id.Botonrecoger)
        recogerButton.setOnClickListener {
            recogerObjeto()
        }

        val continuarButton = findViewById<Button>(R.id.Botoncontobjeto)
        continuarButton.setOnClickListener {
            finish()
        }
    }


    private fun recogerObjeto() {

        val dbHelper = ObjetoDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        // Verificar si hay espacio en la mochila
        if (espacioMochilaDisponible > 0) {
            // Actualizar la base de datos (restar una unidad al objeto)
            if (actualizarObjetoEnBaseDeDatos(db, objetoActual)) {
                // Reducir el espacio disponible en la mochila
                espacioMochilaDisponible--

                // Puedes realizar otras acciones aquí, como mostrar un mensaje de éxito, etc.
                // Por ejemplo, mostrar un mensaje de recogida exitosa
                Toast.makeText(this, "Objeto recogido exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                // Manejar el caso en que no se pueda actualizar la base de datos
                // Por ejemplo, mostrar un mensaje de error
                Toast.makeText(this, "No se pudo recoger el objeto", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Manejar el caso en que no hay espacio en la mochila
            // Por ejemplo, mostrar un mensaje de mochila llena
            Toast.makeText(this, "La mochila está llena", Toast.LENGTH_SHORT).show()
        }

        dbHelper.close()
    }
    private fun actualizarObjetoEnBaseDeDatos(db: SQLiteDatabase, objeto: Objeto): Boolean {
        val unidadesActuales = objeto.unidadesDisponibles

        if (unidadesActuales > 0) {
            val nuevasUnidades = unidadesActuales - 1

            val contentValues = ContentValues().apply {
                put("unidades_disponibles", nuevasUnidades)
            }

            val whereClause = "_id = ?"
            val whereArgs = arrayOf(objeto.id.toString())

            val rowsAffected = db.update("OBJETOS_ALEATORIOS", contentValues, whereClause, whereArgs)

            return rowsAffected > 0
        }

        return false
    }
}

 class Objeto(
    val id: Int,
    val nombre: String,
    val tipo: String,
    val peso: Double,
    val urlImagen: String,
    val unidadesDisponibles: Int,
    val precio: Int
)



class ObjetoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "objetos_aleatorios.db"

        private const val TABLE_NAME = "OBJETOS_ALEATORIOS"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_URL_IMAGEN = "url_imagen"
        private const val COLUMN_UNIDADES_DISPONIBLES = "unidades_disponibles"
        private const val COLUMN_PRECIO = "precio"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NOMBRE TEXT, " +
                    "$COLUMN_TIPO TEXT, " +
                    "$COLUMN_PESO REAL, " +
                    "$COLUMN_URL_IMAGEN TEXT, " +
                    "$COLUMN_UNIDADES_DISPONIBLES INTEGER, " +
                    "$COLUMN_PRECIO INTEGER)"
        db.execSQL(createTableQuery)

        // Insertar 10 objetos precargados
        insertObjeto(db, "Objeto1", "Tipo1", 1, "url1", 20, 10)
        insertObjeto(db, "Objeto2", "Tipo2", 2, "url2", 2, 12)
        insertObjeto(db, "Objeto3", "Tipo3", 1, "url3", 10, 15)
        insertObjeto(db, "Objeto4", "Tipo4", 5, "url4", 9, 5)
        insertObjeto(db, "Objeto5", "Tipo5", 3, "url5", 8, 1)
        insertObjeto(db, "Objeto6", "Tipo6", 1, "url6", 4, 10)
        insertObjeto(db, "Objeto7", "Tipo7", 4, "url7", 17, 22)
        insertObjeto(db, "Objeto8", "Tipo8", 5, "url8", 15, 11)
        insertObjeto(db, "Objeto9", "Tipo9", 1, "url9", 22, 3)
        insertObjeto(db, "Objeto10", "Tipo10", 2, "url10", 12, 7)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Puedes implementar lógica de actualización si es necesario
        // Por ejemplo, eliminar la tabla existente y crear una nueva versión
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    private fun insertObjeto(
        db: SQLiteDatabase,
        nombre: String,
        tipo: String,
        peso: Int,
        urlImagen: String,
        unidadesDisponibles: Int,
        precio: Int
    ) {
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_TIPO, tipo)
            put(COLUMN_PESO, peso)
            put(COLUMN_URL_IMAGEN, urlImagen)
            put(COLUMN_UNIDADES_DISPONIBLES, unidadesDisponibles)
            put(COLUMN_PRECIO, precio)
        }

        db.insert(TABLE_NAME, null, values)
    }
}




