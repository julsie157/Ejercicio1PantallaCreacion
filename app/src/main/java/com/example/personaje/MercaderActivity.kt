package com.example.personaje


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MercaderActivity : AppCompatActivity() {
/*
aaaa
    private lateinit var botonComerciar: Button
    private lateinit var botonContinuar: Button
    private lateinit var panelComercio: LinearLayout
    private lateinit var botonComprar: Button
    private lateinit var botonVender: Button
    private lateinit var botonCancelar: Button
    private lateinit var mercaderImageView: ImageView

    //private lateinit var dbObjetosMercader: Objetos_mercader


    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mercader)

        //dbObjetosMercader = Objetos_mercader(this)

        botonComerciar = findViewById(R.id.Botoncomerciar)
        botonContinuar = findViewById(R.id.Botoncontmercader)
        mercaderImageView = findViewById(R.id.Mercader)
        panelComercio = findViewById(R.id.panelComercio)
        botonComprar = findViewById(R.id.botonComprar)
        botonVender = findViewById(R.id.botonVender)
        botonCancelar = findViewById(R.id.botonCancelar)

        val precioTextView = findViewById<TextView>(R.id.precioTextView)
        val botonConfirmarCompra = findViewById<Button>(R.id.botonConfirmarCompra)
        val botonCancelarCompra = findViewById<Button>(R.id.botonCancelarCompra)

        botonComerciar.setOnClickListener {
            botonComerciar.visibility = View.GONE
            botonContinuar.visibility = View.GONE
            panelComercio.visibility = View.VISIBLE
        }

        botonContinuar.setOnClickListener {
            finish()
        }

        botonCancelar.setOnClickListener {
            botonComerciar.visibility = View.VISIBLE
            botonContinuar.visibility = View.VISIBLE
            panelComercio.visibility = View.GONE
            resetearVista()
        }

        botonComprar.setOnClickListener {
            val cursor = dbObjetosMercader.MostrarObjetosDisponibles()
            val items = ArrayList<String>()
            val precios = ArrayList<Int>()
            val itemIds = ArrayList<Int>()
            while (cursor.moveToNext()) {
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val precio = cursor.getInt(cursor.getColumnIndex(Objetos_mercader.getColumnPrecio()))
                val imagenId = cursor.getInt(cursor.getColumnIndex(Objetos_mercader.getColumnImagen()))
                items.add(nombre)
                precios.add(precio)
                itemIds.add(imagenId)
            }
            cursor.close()

            botonComprar.visibility = View.GONE
            botonVender.visibility = View.GONE
            botonCancelar.visibility = View.GONE

            val adapter = ArrayAdapter(this, android.R.layout.select_dialog_singlechoice, items)
            AlertDialog.Builder(this)
                .setTitle("Selecciona un objeto para comprar")
                .setAdapter(adapter) { dialog, which ->

                    val imagenId = itemIds[which]
                    val resourceId = obtenerIdImagenPorNumero(imagenId)
                    mercaderImageView.setImageResource(resourceId)


                    precioTextView.text = "Precio: ${precios[which]}"
                    precioTextView.visibility = View.VISIBLE


                    botonConfirmarCompra.visibility = View.VISIBLE
                    botonCancelarCompra.visibility = View.VISIBLE


                    botonConfirmarCompra.setOnClickListener {
                        precioTextView.visibility = View.GONE
                        botonConfirmarCompra.visibility = View.GONE
                        botonCancelarCompra.visibility = View.GONE

                        resetearVista()

                    }

                    botonCancelarCompra.setOnClickListener {

                        precioTextView.visibility = View.GONE
                        botonConfirmarCompra.visibility = View.GONE
                        botonCancelarCompra.visibility = View.GONE
                        resetearVista()

                    }

                }
                .show()
        }

        botonVender.setOnClickListener {
            mercaderImageView.setImageResource(R.drawable.mochila)

        }



    }

    private fun resetearVista() {
            mercaderImageView.setImageResource(R.drawable.mercader)
            botonComprar.visibility = View.VISIBLE
            botonVender.visibility = View.VISIBLE
            botonCancelar.visibility = View.VISIBLE
        }
    }




    private fun obtenerIdImagenPorNumero(numero: Int): Int {
        return when (numero) {
            1 -> R.drawable.moneda
            2 -> R.drawable.espada
            3 -> R.drawable.martillo
            4 -> R.drawable.objeto
            5 -> R.drawable.baston
            6 -> R.drawable.pocion
            7 -> R.drawable.ira
            8 -> R.drawable.escudo
            9 -> R.drawable.armadura
            10 -> R.drawable.daga
            else -> R.drawable.cofre
        }
    }

 */
}