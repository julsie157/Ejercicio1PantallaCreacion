package com.example.personaje

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InteractuarMascotaActivity : AppCompatActivity() {
    private lateinit var dbGeneral: BaseDeDatosGeneral
    private var idPersonaje: Long = -1L
    private var idMascota: Long = -1L
    private lateinit var mediaPlayer: MediaPlayer
    private var contadorClics = 0
    private lateinit var botonJugar: Button
    private lateinit var botonSalir: Button
    private lateinit var botonDarComida: Button
    private lateinit var textoContador: TextView
    private lateinit var progressBarFelicidad: ProgressBar
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_interactuarmascota)

        mediaPlayer = MediaPlayer.create(this, R.raw.undertale2)
        mediaPlayer.setVolume(0.1f, 0.1f)


        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)
        idMascota = dbGeneral.obtenerIdMascotaPorPersonaje(idPersonaje)

        if (idMascota == -1L) {
            Toast.makeText(this, "No tienes una mascota. Adopta una primero.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        inicializarUI()
        actualizarUI()
    }

    private fun inicializarUI() {
        botonSalir = findViewById(R.id.botonSalirdeInteractuar)
        botonDarComida = findViewById(R.id.botonDarComida)
        botonJugar = findViewById(R.id.botonJugar)
        textoContador = findViewById(R.id.textoContador)
        progressBarFelicidad = findViewById(R.id.progressBarFelicidad)
        imageView = findViewById(R.id.imagenMascotaInterac)
        var nombreImagen : String = "mascota"

        if (dbGeneral.obtenerNivelMascotaPorPersonaje(idPersonaje) == 2 ){
           nombreImagen = "mascotaevo"
        }
        imageView.setImageResource(this.resources.getIdentifier(nombreImagen, "drawable", this.packageName))


        botonSalir.setOnClickListener {
            actualizarFelicidadYVerificar(-10)
        }

        botonDarComida.setOnClickListener {
            manejarAlimentacionMascota()
        }

        botonJugar.setOnClickListener {
            iniciarJuego()
        }
    }

    private fun actualizarUI() {
        actualizarProgressBarFelicidad()
        actualizarTextoTieneComida()
    }

    private fun manejarAlimentacionMascota() {
        val tieneComida = dbGeneral.tengoComida(dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje))
        if (tieneComida) {
            dbGeneral.consumirTodaLaComidaDelInventario(dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje))
            actualizarFelicidadYVerificar(20)
            Toast.makeText(this, "Has alimentado a tu mascota.", Toast.LENGTH_SHORT).show()

        } else {
            actualizarFelicidadYVerificar(-20)
            Toast.makeText(this, "No tienes comida para tu mascota.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun iniciarJuego() {
        contadorClics = 0
        //botonJugar.isEnabled = false
        botonDarComida.isEnabled = false
        botonSalir.isEnabled = false

        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textoContador.text = "Tiempo restante: ${millisUntilFinished / 1000}"
                //botonJugar.isEnabled = true
                botonJugar.text = "Dame clicks!"
                botonJugar.setOnClickListener {
                    contadorClics++
                }
            }

            override fun onFinish() {
                textoContador.text = ""
                botonJugar.isEnabled = false
                botonJugar.text = "Jugar"
                val felicidadAdicional = calcularFelicidad(contadorClics)
                actualizarFelicidadYVerificar(felicidadAdicional)
                Toast.makeText(applicationContext, "Juego terminado. Felicidad aumentada en $felicidadAdicional.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun calcularFelicidad(clics: Int): Int = clics

    private fun actualizarFelicidadYVerificar(cambio: Int) {
        val felicidadActual = dbGeneral.obtenerFelicidadMascota(idMascota)
        val nuevaFelicidad = felicidadActual + cambio
        dbGeneral.actualizarFelicidadMascota(idMascota, nuevaFelicidad)
        actualizarProgressBarFelicidad()

        if (nuevaFelicidad <= 0) {
            dbGeneral.eliminarMascota(idMascota)
            navegarAPantallaDado()
            Toast.makeText(this, "Se te ha muerto la mascota", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
            }, 1400)
        }else{
            if (nuevaFelicidad >= 100){
                imageView = findViewById(R.id.imagenMascotaInterac)
                imageView.setImageResource(this.resources.getIdentifier("mascotaevo", "drawable", this.packageName))
                Toast.makeText(this, "Tu mascota ha subido de nivel", Toast.LENGTH_SHORT).show()
            }
            navegarAPantallaDado()
        }
    }

    private fun navegarAPantallaDado() {
        val intent = Intent(this, PantallaDado::class.java).apply {
            putExtra("intentExtraIdPersonaje", idPersonaje)
        }
        startActivity(intent)
        finish()
    }

    private fun actualizarProgressBarFelicidad() {
        progressBarFelicidad.progress = dbGeneral.obtenerFelicidadMascota(idMascota)
    }

    private fun actualizarTextoTieneComida() {
        val textoCantidadComidas: TextView = findViewById(R.id.textViewCantidadComidas)
        val tieneComida = dbGeneral.tengoComida(dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje))
        textoCantidadComidas.text = if (tieneComida) "Tienes comida en tu inventario." else "No tienes comida en tu inventario."
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
