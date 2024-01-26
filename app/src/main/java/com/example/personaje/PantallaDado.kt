package com.example.personaje


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PantallaDado : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dado)

        val dadoButton = findViewById<Button>(R.id.Botontirar)
        dadoButton.setOnClickListener {
            val encounter = randomEncounter()
            when (encounter) {
                "Objeto" -> startActivity(Intent(this, ObjetoActivity::class.java))
                "Ciudad" -> startActivity(Intent(this, CiudadActivity::class.java))
                "Mercader" -> startActivity(Intent(this, MercaderActivity::class.java))
                "Enemigo" -> startActivity(Intent(this, EnemigoActivity::class.java))
            }
        }
    }

    private fun randomEncounter(): String {
        val encounters = arrayOf("Objeto", "Mercader", "Mercader", "Objeto")
        return encounters.random()
    }
}
