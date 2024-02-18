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
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_dado)



        MusicPlayer.init(this)
        playButton = findViewById<Button>(R.id.play_button)

        MusicPlayer.init(this)
        updatePlayButton()

        playButton.setOnClickListener {
            if (MusicPlayer.isPlaying()) {
                MusicPlayer.pause()
            } else {
                MusicPlayer.start()
            }
            updatePlayButton()
        }



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


    override fun onPause() {
        super.onPause()
        if (MusicPlayer.isPlaying()) {
            MusicPlayer.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (MusicPlayer.isPlaying()) {
            MusicPlayer.start()
        }
    }

    private fun updatePlayButton() {
        if (MusicPlayer.isPlaying()) {
            playButton.text = "Mute"
        } else {
            playButton.text = "Sound"
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        MusicPlayer.release()
    }
}

