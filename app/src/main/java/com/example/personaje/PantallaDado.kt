package com.example.personaje

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class PantallaDado : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dado)

        val dadoButton = findViewById<Button>(R.id.Botontirar)
        val idPersonaje = intent.getLongExtra("id_personaje", -1L) // Recupera el ID del personaje

        dadoButton.setOnClickListener {
            val encounter = randomEncounter()
            val intent = when (encounter) {
                "Objeto" -> Intent(this, ObjetoActivity::class.java)
                "Ciudad" -> Intent(this, CiudadActivity::class.java)
                "Mercader" -> Intent(this, MercaderActivity::class.java)
                "Enemigo" -> Intent(this, EnemigoActivity::class.java)
                else -> null
            }
            intent?.let {
                it.putExtra("id_personaje", idPersonaje)
                startActivity(it)
            }
        }
    }

    private fun randomEncounter(): String {
        val encounters = arrayOf("Objeto","Mercader")
        return encounters[Random.nextInt(encounters.size)]
    }
}

