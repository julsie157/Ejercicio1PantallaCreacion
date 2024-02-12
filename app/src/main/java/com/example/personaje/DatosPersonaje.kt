package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DatosPersonaje : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral // Asegúrate de inicializarlo
    private lateinit var playButton: Button
    private lateinit var nombreTextView: TextView
    private lateinit var razaTextView: TextView
    private lateinit var claseTextView: TextView
    private lateinit var estadoVitalTextView: TextView
    private lateinit var botonVolver: Button
    private lateinit var botonComenzarAventura: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_informacion)

        dbGeneral = BaseDeDatosGeneral(this)

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

        inicializarUI()
        configurarBotones()

        // Obtiene el ID del personaje pasado a través del Intent

        val idPersonaje = intent.getLongExtra("id_personaje", -1L) // Asegúrate de que el valor predeterminado coincide

        if (idPersonaje != -1L) {
            cargarDetallesPersonaje(idPersonaje)
        } else {
            Toast.makeText(this, "Error al cargar el personaje", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun inicializarUI() {
        nombreTextView = findViewById(R.id.nombreTextView)
        razaTextView = findViewById(R.id.razaTextView)
        claseTextView = findViewById(R.id.claseTextView)
        estadoVitalTextView = findViewById(R.id.estadoVitalTextView)
        botonVolver = findViewById(R.id.botonVolver)
        botonComenzarAventura = findViewById(R.id.botonComenzarAventura)
        playButton = findViewById(R.id.play_button)
    }

    private fun configurarBotones() {
        botonVolver.setOnClickListener {
            val intentVolver = Intent(this, Inicio::class.java)
            intentVolver.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentVolver)
        }

        botonComenzarAventura.setOnClickListener {

            val idPersonaje = intent.getLongExtra("id_personaje", -1L)


            val intentAventura = Intent(this, PantallaDado::class.java).apply {
                putExtra("id_personaje", idPersonaje)
            }
            startActivity(intentAventura)
        }
    }

    private fun cargarDetallesPersonaje(idPersonaje: Long) {
        val personaje = dbGeneral.obtenerPersonajePorId(idPersonaje)
        if (personaje != null) {
            nombreTextView.text = "Nombre: ${personaje.getNombre()}"
            razaTextView.text = "Raza: ${personaje.getRaza()}"
            claseTextView.text = "Clase: ${personaje.getClase()}"
            estadoVitalTextView.text = "Estado Vital: ${personaje.getEstadoVital()}"
        } else {
            Toast.makeText(this, "Personaje no encontrado.", Toast.LENGTH_SHORT).show()
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
