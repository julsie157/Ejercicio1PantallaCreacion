package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DatosPersonaje : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral
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

        val nombre = intent.getStringExtra("intentExtraNombre").toString()
        val email = intent.getStringExtra("intentExtraEmail").toString()
        val raza = intent.getStringExtra("intentExtraRaza").toString()
        val clase = intent.getStringExtra("intentExtraClase").toString()
        val estadoVital = intent.getStringExtra("intentExtraEstadoVital").toString()
        val imagenId = intent.getIntExtra("intentExtraImagenId", R.drawable.gnomopocho)



        inicializarUI()
        configurarBotones(email, nombre, raza, clase, estadoVital,imagenId)

        cargarDetallesPersonaje(nombre, raza, clase, estadoVital)

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

    private fun configurarBotones(
        email: String,
        nombre: String,
        raza: String,
        clase: String,
        estadoVital: String,
        imagenId: Int
    ) {

        botonVolver.setOnClickListener {
            val intentVolver = Intent(this, Inicio::class.java)
            intentVolver.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentVolver)
        }

        botonComenzarAventura.setOnClickListener {
            val personajeNuevo : Personaje = Personaje(email,nombre, Personaje.Raza.valueOf(raza), Personaje.Clase.valueOf(clase), Personaje.EstadoVital.valueOf(estadoVital), imagenId)
            val idPersonajeNuevo = dbGeneral.insertarPersonaje(personajeNuevo)
            if(idPersonajeNuevo > 0) {
                dbGeneral.insertarMochila(idPersonajeNuevo)
                val intent = Intent(this, PantallaDado::class.java).apply {
                    putExtra("intentExtraIdPersonaje", idPersonajeNuevo)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al crear el personaje. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarDetallesPersonaje(nombre:String, raza:String, clase:String, estadoVital:String) {

        nombreTextView.text = "Nombre: ${nombre}"
        razaTextView.text = "Raza: ${raza}"
        claseTextView.text = "Clase: ${clase}"
        estadoVitalTextView.text = "Estado Vital: ${estadoVital}"

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