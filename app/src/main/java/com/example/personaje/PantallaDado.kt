package com.example.personaje

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class PantallaDado : AppCompatActivity() {
    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_dado)

        val dadoButton = findViewById<Button>(R.id.Botontirar)
        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje",-1L)

        dadoButton.setOnClickListener {
            val encounter = randomEncounter()
            val intent = when (encounter) {
                "Objeto" -> Intent(this, ObjetoActivity::class.java)
                "Ciudad" -> Intent(this, CiudadActivity::class.java)
                "Mercader" -> Intent(this, MercaderActivity::class.java)
                "Enemigo" -> Intent(this, EnemigoActivity::class.java)
                "InteractuarMascota" -> Intent(this, InteractuarMascotaActivity::class.java)
                "ChatBot"-> Intent(this, ChatbotActivity::class.java)
                else -> null
            }
            intent?.let {
                it.putExtra("intentExtraIdPersonaje", idPersonaje)
                startActivity(it)
            }
        }
    }

    private fun randomEncounter(): String {
        val encounters = arrayOf("Objeto","Ciudad","Mercader","Enemigo","InteractuarMascota","ChatBot")
        return encounters[Random.nextInt(encounters.size)]
    }
}

