package com.example.personaje

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InteractuarMascotaActivity : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral
    private var idPersonaje: Long = -1L

    private var contadorClics = 0
    private lateinit var botonJugar: Button
    private lateinit var textoContador: TextView
    private lateinit var scrollViewComidas: ScrollView
    private lateinit var panelComidas: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_interactuarmascota)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)

        val idMascota = dbGeneral.obtenerIdMascotaPorPersonaje(idPersonaje)


        if (idMascota != -1L) {
            inicializarUI(idMascota)
        } else {
            Toast.makeText(this, "No tienes una mascota. Adopta una primero.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun inicializarUI(idMascota: Long) {
        val botonSalir: Button = findViewById(R.id.botonSalirdeInteractuar)
        val botonDarComida: Button = findViewById(R.id.botonDarComida)

        botonJugar = findViewById(R.id.botonJugar)
        textoContador = findViewById(R.id.textoContador)
        scrollViewComidas = findViewById(R.id.scrollViewComidas)
        panelComidas = findViewById(R.id.panelComidas)

        botonSalir.setOnClickListener {
            dbGeneral.actualizarFelicidadMascota(idMascota, -10)
            finish()
        }


        botonDarComida.setOnClickListener {
            mostrarComidas(idMascota)
        }


        botonJugar.setOnClickListener {
            iniciarJuego(idMascota)
        }
    }

    private fun mostrarComidas(idMascota: Long) {
        val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        val comidas = dbGeneral.obtenerComidasPorIdMochila(idMochila)

        if (comidas.isEmpty()) {
            dbGeneral.actualizarFelicidadMascota(idMascota, -20)
            Toast.makeText(this, "No tienes comida para tu mascota.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val scrollViewComidas: ScrollView = findViewById(R.id.scrollViewComidas)
        val panelComidas: LinearLayout = findViewById(R.id.panelComidas)
        panelComidas.removeAllViews()

        comidas.forEach { articulo ->
            val vistaArticulo = layoutInflater.inflate(R.layout.layout_aux, panelComidas, false)
            vistaArticulo.findViewById<TextView>(R.id.nombreArticulo).text = articulo.getNombre().toString()
            vistaArticulo.findViewById<ImageView>(R.id.imageArticulo).setImageResource(articulo.getImagenId())

            vistaArticulo.findViewById<Button>(R.id.botonConfirmarAux).setOnClickListener {
                dbGeneral.actualizarFelicidadMascota(idMascota, 20)
                dbGeneral.eliminarArticuloDeMochila(idMochila, articulo.getIdInventario())
                Toast.makeText(this, "Has alimentado a tu mascota.", Toast.LENGTH_SHORT).show()
                finish()
            }

            panelComidas.addView(vistaArticulo)
        }

        scrollViewComidas.visibility = View.VISIBLE
    }

    private fun iniciarJuego(idMascota: Long) {
        contadorClics = 0
        botonJugar.isEnabled = false

        object : CountDownTimer(20000, 1000) {
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
                finish()
            }
        }.start()
    }

    private fun calcularFelicidad(clics: Int): Int {
        return clics
    }
}
