package com.example.personaje

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


    class ObjetoActivity : AppCompatActivity() {
        private var espacioMochilaDisponible: Int = 100
        private lateinit var dbObjetosAleatorios: Objetos_Aleatorios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_objeto)

        dbObjetosAleatorios = Objetos_Aleatorios(this)

        espacioMochilaDisponible = cargarPesoDisponible()

        val recogerButton = findViewById<Button>(R.id.Botonrecoger)
        recogerButton.setOnClickListener {
            recogerObjeto()
        }

        val continuarButton = findViewById<Button>(R.id.Botoncontobjeto)
        continuarButton.setOnClickListener {
            finish()
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
        private fun guardarPesoDisponible(peso: Int) {
            val sharedPreferences = getSharedPreferences("PreferenciasMochila", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("PesoDisponible", peso)
            editor.apply()
        }

        private fun cargarPesoDisponible(): Int {
            val sharedPreferences = getSharedPreferences("PreferenciasMochila", Context.MODE_PRIVATE)
            return sharedPreferences.getInt("PesoDisponible", 100)
        }

    @SuppressLint("Range")
    private fun recogerObjeto() {
        val cursor = dbObjetosAleatorios.obtenerObjetoAleatorio()
        if (cursor != null && cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndex(Objetos_Aleatorios.getColumnNombre()))
            val peso = cursor.getInt(cursor.getColumnIndex(Objetos_Aleatorios.getColumnPeso()))
            val idImagen = cursor.getInt(cursor.getColumnIndex(Objetos_Aleatorios.getColumnImagen()))
            val resourceId = obtenerIdImagenPorNumero(idImagen)

            cursor.close()

            if (peso <= espacioMochilaDisponible) {
                espacioMochilaDisponible -= peso


                val inflater = layoutInflater
                val layout = inflater.inflate(R.layout.toast_layout, null)
                val image = layout.findViewById<ImageView>(R.id.toast_image)
                val text = layout.findViewById<TextView>(R.id.toast_text)

                image.setImageResource(resourceId)
                text.text = "Has recogido: $nombre. Espacio restante: $espacioMochilaDisponible"

                with(Toast(applicationContext)) {
                    duration = Toast.LENGTH_SHORT
                    view = layout
                    show()
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 1600)

                guardarPesoDisponible(espacioMochilaDisponible)
            } else {
                Toast.makeText(this, "El objeto $nombre es demasiado pesado. Espacio disponible: $espacioMochilaDisponible", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No se pudo recoger un objeto.", Toast.LENGTH_SHORT).show()
        }

    }


    }
