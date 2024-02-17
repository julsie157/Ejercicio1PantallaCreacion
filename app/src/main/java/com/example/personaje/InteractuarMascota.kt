package com.example.personaje

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InteractuarMascotaActivity : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral
    private var idPersonaje: Long = -1L
    private var idMascota: Long = -1L

    private var contadorClics = 0
    private lateinit var botonJugar: Button
    private lateinit var textoContador: TextView
    private lateinit var textoCantidadComidas: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_interactuarmascota)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)
        idMascota = dbGeneral.obtenerIdMascotaPorPersonaje(idPersonaje)

        if (idMascota != -1L) {
            inicializarUI()
        } else {
            Toast.makeText(this, "No tienes una mascota. Adopta una primero.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun inicializarUI() {
        val botonSalir: Button = findViewById(R.id.botonSalirdeInteractuar)
        val botonDarComida: Button = findViewById(R.id.botonDarComida)
        val botonJugar: Button = findViewById(R.id.botonJugar)
        val textoContador: TextView = findViewById(R.id.textoContador)
        val textoTieneComida: TextView = findViewById(R.id.textViewCantidadComidas)

        actualizarTextoTieneComida()

        botonSalir.setOnClickListener {
            dbGeneral.actualizarFelicidadMascota(idMascota, -10)
            verificarFelicidadYFinalizar(idMascota)
        }

        botonDarComida.setOnClickListener {
            val tieneComida = dbGeneral.obtenerCantidadComidasPorIdMochila(dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)) > 0
            if (tieneComida) {
                dbGeneral.consumirTodaLaComidaDelInventario(dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje))
                dbGeneral.actualizarFelicidadMascota(idMascota, 20)
                Toast.makeText(this, "Has alimentado a tu mascota.", Toast.LENGTH_SHORT).show()
            } else {
                dbGeneral.actualizarFelicidadMascota(idMascota, -20)
                Toast.makeText(this, "No tienes comida para tu mascota.", Toast.LENGTH_SHORT).show()
            }
            verificarFelicidadYFinalizar(idMascota)
        }

        botonJugar.setOnClickListener {
            iniciarJuego(idMascota)
        }
    }

    private fun actualizarTextoTieneComida() {
        val tieneComida = dbGeneral.obtenerCantidadComidasPorIdMochila(dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)) > 0
        val textoTieneComida: TextView = findViewById(R.id.textViewCantidadComidas)
        textoTieneComida.text = if (tieneComida) "Tienes comida en tu inventario." else "No tienes comida en tu inventario."
    }

    private fun verificarFelicidadYFinalizar(idMascota: Long) {
        val felicidadActual = dbGeneral.obtenerFelicidadMascota(idMascota)
        if (felicidadActual <= 0) {
            dbGeneral.eliminarMascota(idMascota)
            Toast.makeText(this, "Tu mascota ha sido eliminada debido a la falta de felicidad.", Toast.LENGTH_LONG).show()
        }
        navegarAPantallaDado()
    }

    private fun iniciarJuego(idMascota: Long) {
        contadorClics = 0
        botonJugar.isEnabled = false

        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textoContador.text = "Tiempo restante: ${millisUntilFinished / 1000}"
                botonJugar.isEnabled = true
                botonJugar.text = "Dame clicks!"
                botonJugar.setOnClickListener {
                    contadorClics++
                }
            }

            override fun onFinish() {
                botonJugar.isEnabled = false
                val felicidadAdicional = calcularFelicidad(contadorClics)
                dbGeneral.actualizarFelicidadMascota(idMascota, felicidadAdicional)
                Toast.makeText(applicationContext, "Juego terminado. Felicidad aumentada en $felicidadAdicional.", Toast.LENGTH_SHORT).show()
                navegarAPantallaDado()
            }
        }.start()
    }

    private fun calcularFelicidad(clics: Int): Int {
        return clics
    }

    private fun navegarAPantallaDado() {
        val intent = Intent(this, PantallaDado::class.java)
        intent.putExtra("intentExtraIdPersonaje", idPersonaje)
        startActivity(intent)
        finish()
    }
}
