package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class DatosPersonaje : AppCompatActivity() {
    private lateinit var playButton: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_informacion)



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



        val nombreTextView: TextView = findViewById(R.id.nombreTextView)
        val razaTextView: TextView = findViewById(R.id.razaTextView)
        val claseTextView: TextView = findViewById(R.id.claseTextView)
        val estadoVitalTextView: TextView = findViewById(R.id.estadoVitalTextView)
        val botonVolver: Button = findViewById(R.id.botonVolver)
        val botonComenzarAventura: Button = findViewById(R.id.botonComenzarAventura)


        val nombre = intent.getStringExtra("nombre_personaje")
        val raza = intent.getStringExtra("raza_personaje")
        val clase = intent.getStringExtra("clase_personaje")
        val estadoVital = intent.getStringExtra("estado_vital_personaje")


        nombreTextView.text = "Nombre: $nombre"
        razaTextView.text = "Raza: $raza"
        claseTextView.text = "Clase: $clase"
        estadoVitalTextView.text = "Estado Vital: $estadoVital"


        botonVolver.setOnClickListener {
            val intentVolver = Intent(this, Inicio::class.java)
            intentVolver.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentVolver)
        }


        botonComenzarAventura.setOnClickListener {
            val intentAventura = Intent(this, PantallaDado::class.java)
            startActivity(intentAventura)
        }

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

//para musica
    private fun updatePlayButton() {
        if (MusicPlayer.isPlaying()) {
            playButton.text = "Pause"
        } else {
            playButton.text = "Play"
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        MusicPlayer.release()
    }

}
