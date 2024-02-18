package com.example.personaje

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ReclutarMascotaActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_reclutar)
        mediaPlayer = MediaPlayer.create(this, R.raw.undertale)
        mediaPlayer.setVolume(0.1f, 0.1f)
        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)

        val editTextNombreMascota: EditText = findViewById(R.id.nombreMascotaEditText)
        val botonConfirmar: Button = findViewById(R.id.botonAdoptarMascota)

        botonConfirmar.setOnClickListener {
            val nombreMascota = editTextNombreMascota.text.toString()
            dbGeneral.insertarMascota(nombreMascota,30 , idPersonaje)

            val intent = Intent(this, PantallaDado::class.java)
            intent.putExtra("intentExtraIdPersonaje", idPersonaje)
            startActivity(intent)
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        mediaPlayer.start()
    }
    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
    }
}

