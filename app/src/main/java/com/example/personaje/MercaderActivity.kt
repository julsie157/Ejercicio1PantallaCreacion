package com.example.personaje


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MercaderActivity : AppCompatActivity() {

    private lateinit var botonComerciar: Button
    private lateinit var botonContinuar: Button
    private lateinit var botonComprar: Button
    private lateinit var botonVender: Button
    private lateinit var botonCancelar: Button
    private lateinit var mercaderImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mercader)

        botonComerciar = findViewById(R.id.Botoncomerciar)
        botonContinuar = findViewById(R.id.Botoncontmercader)
        botonComprar = findViewById(R.id.BotonComprar)
        botonVender = findViewById(R.id.BotonVender)
        botonCancelar = findViewById(R.id.BotonCancelar)
        mercaderImageView = findViewById(R.id.Mercader)

        botonComerciar.setOnClickListener {
            mostrarBotonesComprarVender()
        }

        botonContinuar.setOnClickListener {
            finish()
        }

        botonComprar.setOnClickListener {
            cambiarImagenObjetoMercader()

        }

        botonVender.setOnClickListener {
            cambiarImagenMochila()

        }

        botonCancelar.setOnClickListener {
            ocultarBotonesComprarVender()

        }
    }

    private fun mostrarBotonesComprarVender() {
        botonComerciar.visibility = View.GONE
        botonContinuar.visibility = View.GONE
        botonComprar.visibility = View.VISIBLE
        botonVender.visibility = View.VISIBLE
        botonCancelar.visibility = View.VISIBLE
    }

    private fun ocultarBotonesComprarVender() {
        botonComerciar.visibility = View.VISIBLE
        botonContinuar.visibility = View.VISIBLE
        botonComprar.visibility = View.GONE
        botonVender.visibility = View.GONE
        botonCancelar.visibility = View.GONE
    }

    private fun cambiarImagenObjetoMercader() {
        mercaderImageView.setImageResource(R.drawable.espada)
        mercaderImageView.setImageResource(R.drawable.objeto)
        mercaderImageView.setImageResource(R.drawable.daga)
        mercaderImageView.setImageResource(R.drawable.martillo)
        mercaderImageView.setImageResource(R.drawable.baston)
        mercaderImageView.setImageResource(R.drawable.moneda)
        mercaderImageView.setImageResource(R.drawable.escudo)
        mercaderImageView.setImageResource(R.drawable.armadura)
        mercaderImageView.setImageResource(R.drawable.ira)
        mercaderImageView.setImageResource(R.drawable.pocion)
    }

    private fun cambiarImagenMochila() {
        mercaderImageView.setImageResource(R.drawable.mochila)


        mercaderImageView.setImageResource(R.drawable.espada)
        mercaderImageView.setImageResource(R.drawable.objeto)
        mercaderImageView.setImageResource(R.drawable.daga)
        mercaderImageView.setImageResource(R.drawable.martillo)
        mercaderImageView.setImageResource(R.drawable.baston)
        mercaderImageView.setImageResource(R.drawable.moneda)
        mercaderImageView.setImageResource(R.drawable.escudo)
        mercaderImageView.setImageResource(R.drawable.armadura)
        mercaderImageView.setImageResource(R.drawable.ira)
        mercaderImageView.setImageResource(R.drawable.pocion)

    }


}



class MercaderDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "objetos_mercader.db"

        private const val TABLE_NAME = "OBJETOS_MERCADER"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_URL_IMAGEN = "url_imagen"
        private const val COLUMN_UNIDADES_DISPONIBLES = "unidades_disponibles"
        // Columna opRecio
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

        // Insert 10 objetos
        insertObjeto(db, "ObjetoMercader1", "TipoMercader1", 1, "urlMercader1", 20, 10)
        insertObjeto(db, "ObjetoMercader2", "TipoMercader2", 2, "urlMercader2", 2, 12)
        insertObjeto(db, "ObjetoMercader3", "TipoMercader3", 1, "urlMercader3", 10, 15)
        insertObjeto(db, "ObjetoMercader4", "TipoMercader4", 5, "urlMercader4", 9, 5)
        insertObjeto(db, "ObjetoMercader5", "TipoMercader5", 3, "urlMercader5", 8, 1)
        insertObjeto(db, "ObjetoMercader6", "TipoMercader6", 1, "urlMercader6", 4, 10)
        insertObjeto(db, "ObjetoMercader7", "TipoMercader7", 4, "urlMercader7", 17, 22)
        insertObjeto(db, "ObjetoMercader8", "TipoMercader8", 5, "urlMercader8", 15, 11)
        insertObjeto(db, "ObjetoMercader9", "TipoMercader9", 1, "urlMercader9", 22, 3)
        insertObjeto(db, "ObjetoMercader10", "TipoMercader10", 2, "urlMercader10", 12, 7)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
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
