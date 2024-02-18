package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GuaridaActivity : AppCompatActivity() {
    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    private lateinit var mediaPlayer: MediaPlayer
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_guarida)
        mediaPlayer = MediaPlayer.create(this, R.raw.mascota)
        mediaPlayer.setVolume(0.1f, 0.1f)
        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)

        val botonSalir: Button = findViewById(R.id.BotonSalirGuarida)
        botonSalir.setOnClickListener {
            volverAlDado()
        }

        val botonEntrar: Button = findViewById(R.id.BotonEntrarGuarida)
        botonEntrar.setOnClickListener {
            entrarOGuardar()
        }
    }

    private fun entrarOGuardar() {
        if (dbGeneral.tengoMascota(idPersonaje)) {
            Toast.makeText(
                this, "Ya tienes una mascota. No puedes adoptar otra.", Toast.LENGTH_LONG
            ).show()
            volverAlDado()
        } else {
            val intent = Intent(this, ReclutarMascotaActivity::class.java)
            intent.putExtra("intentExtraIdPersonaje", idPersonaje)
            startActivity(intent)
        }
    }
    private fun volverAlDado() {
        val intent = Intent(this, PantallaDado::class.java)
        intent.putExtra("intentExtraIdPersonaje", idPersonaje)
        startActivity(intent)
        finish()
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

