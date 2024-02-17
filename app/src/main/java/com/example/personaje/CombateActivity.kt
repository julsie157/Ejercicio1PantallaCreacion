package com.example.personaje

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.max

class CombateActivity : AppCompatActivity() {



    private lateinit var personaje: Personaje
    private lateinit var monstruo: Monstruo
    private lateinit var barraVidaMonstruo: ProgressBar
    private lateinit var barraVidaJugador: ProgressBar
    private var idPersonaje: Long = -1L
    private lateinit var dbGeneral: BaseDeDatosGeneral
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_combate)


        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)
        monstruo = crearMonstruoAleatorio()
        barraVidaMonstruo = findViewById(R.id.barraVidaMonstruo)
        barraVidaJugador = findViewById(R.id.barraVidaJugador)
        configurarCombate()
    }

    private fun configurarCombate() {

        personaje = dbGeneral.obtenerPersonajePorId(idPersonaje) ?: return


        barraVidaMonstruo.max = monstruo.getSalud()
        barraVidaMonstruo.progress = monstruo.getSalud()

        barraVidaJugador.max = personaje.getSalud()
        barraVidaJugador.progress = personaje.getSalud()

        val botonAtacar: Button = findViewById(R.id.botonAtacar)
        botonAtacar.setOnClickListener {
            AtacarAMonstruo()
        }

        val botonUsarObjeto: Button = findViewById(R.id.botonUsarObjeto)
        botonUsarObjeto.setOnClickListener {
            UsarObjeto()
        }
    }

    private fun AtacarAMonstruo() {
        val danoAlMonstruo = max(0, personaje.getAtaque() + (personaje.getAtaque() / 10) * 5)
        monstruo.setSalud(max(0, monstruo.getSalud() - danoAlMonstruo))

        if (monstruo.getSalud() > 0) {
            val danoAlJugador = max(0, monstruo.getAtaque() - (personaje.getDefensa() / 10) * 5)
            personaje.setSalud(max(0, personaje.getSalud() - danoAlJugador))
        }

        actualizarBarrasDeVida()
        verificarEstadoCombate()
    }

    /*
    private fun UsarObjeto() {
        val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        val inventario = dbGeneral.obtenerArticulosPorIdMochila(idMochila)
        val panelObjetos: LinearLayout = findViewById(R.id.panelObjetos)
        panelObjetos.removeAllViews()


        inventario.forEach { articulo ->
            val vistaArticulo = layoutInflater.inflate(R.layout.layout_aux, panelObjetos, false)
            vistaArticulo.findViewById<TextView>(R.id.nombreArticulo).text = articulo.getNombre().toString()
            vistaArticulo.findViewById<TextView>(R.id.pesoArticulo).text = "Tipo: ${articulo.getTipoArticulo().toString()}"
            vistaArticulo.findViewById<ImageView>(R.id.imageArticulo).setImageResource(obtenerIdImagenPorNumero(articulo.getImagenId()))


            vistaArticulo.findViewById<Button>(R.id.botonConfirmarAux).apply {text = "Usar"
                setOnClickListener {
                    when (articulo.getTipoArticulo()) {
                        Articulo.TipoArticulo.ARMA -> personaje.setAtaque(personaje.getAtaque() + 20)
                        Articulo.TipoArticulo.OBJETO -> personaje.setSalud(personaje.getSalud() + 25)
                        Articulo.TipoArticulo.PROTECCION -> personaje.setDefensa(personaje.getDefensa() + 20)
                        else -> {}
                    }

                    dbGeneral.eliminarArticuloDeMochila(idMochila, articulo.getIdInventario())
                    actualizarBarrasDeVida()
                    findViewById<ScrollView>(R.id.scrollViewObjetos).visibility = View.GONE
                    verificarEstadoCombate()

                    if (monstruo.getSalud() > 0) {
                        recibirContraataque()
                    }
                }
            }
            panelObjetos.addView(vistaArticulo)
        }
        findViewById<ScrollView>(R.id.scrollViewObjetos).visibility = View.VISIBLE
    }

     */

    private fun UsarObjeto() {
        val botonAtacar: Button = findViewById(R.id.botonAtacar)
        val botonUsarObjeto: Button = findViewById(R.id.botonUsarObjeto)
        val scrollViewObjetos: ScrollView = findViewById(R.id.scrollViewObjetos)
        val panelObjetos: LinearLayout = findViewById(R.id.panelObjetos)

        botonAtacar.visibility = View.GONE
        botonUsarObjeto.visibility = View.GONE
        scrollViewObjetos.visibility = View.VISIBLE

        val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        val inventario = dbGeneral.obtenerArticulosPorIdMochila(idMochila)

        if (inventario.isEmpty()) {
            // Si no hay objetos, permitir que el monstruo ataque automáticamente y volver a mostrar los botones.
            recibirContraataque()
            botonAtacar.visibility = View.VISIBLE
            botonUsarObjeto.visibility = View.VISIBLE
            scrollViewObjetos.visibility = View.GONE
            return
        }

        panelObjetos.removeAllViews()
        inventario.forEach { articulo ->
            val vistaArticulo = layoutInflater.inflate(R.layout.layout_aux, panelObjetos, false)
            vistaArticulo.findViewById<TextView>(R.id.nombreArticulo).text = articulo.getNombre().toString()
            vistaArticulo.findViewById<TextView>(R.id.pesoArticulo).text = "Tipo: ${articulo.getTipoArticulo().toString()}"
            vistaArticulo.findViewById<ImageView>(R.id.imageArticulo).setImageResource(obtenerIdImagenPorNumero(articulo.getImagenId()))
            setupVistaArticulo(vistaArticulo, articulo, idMochila)
            panelObjetos.addView(vistaArticulo)
        }
    }

    private fun setupVistaArticulo(vista: View, articulo: Articulo, idMochila: Int) {

        vista.findViewById<Button>(R.id.botonConfirmarAux).setOnClickListener {
            usarArticulo(articulo, idMochila)
        }
    }

    private fun usarArticulo(articulo: Articulo, idMochila: Int) {
        when (articulo.getTipoArticulo()) {
            Articulo.TipoArticulo.ARMA -> {
                personaje.setAtaque(personaje.getAtaque() + 20) // Asume que cada arma aumenta el ataque en 20
            }
            Articulo.TipoArticulo.OBJETO -> {
                val nuevaSalud = (personaje.getSalud() + 25)
                personaje.setSalud(nuevaSalud)
            }
            Articulo.TipoArticulo.PROTECCION -> {
                personaje.setDefensa(personaje.getDefensa() + 20) // Asume que cada protección aumenta la defensa en 20
            }
            else -> {}
        }

        dbGeneral.eliminarArticuloDeMochila(idMochila, articulo.getIdInventario())

        actualizarBarrasDeVida()
        findViewById<ScrollView>(R.id.scrollViewObjetos).visibility = View.GONE
        findViewById<Button>(R.id.botonAtacar).visibility = View.VISIBLE
        findViewById<Button>(R.id.botonUsarObjeto).visibility = View.VISIBLE

        if (monstruo.getSalud() > 0) {
            recibirContraataque()
        }
    }
    private fun verificarEstadoCombate() {
        if (monstruo.getSalud() <= 0) {
            val intent = Intent(this, LootMascotaActivity::class.java)
            startActivity(intent)
            finish()
        } else if (personaje.getSalud() <= 0) {
            dbGeneral.eliminarPersonaje(idPersonaje)
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun actualizarBarrasDeVida() {
        val porcentajeVidaMonstruo = monstruo.getSalud() * 100
        val porcentajeVidaJugador = personaje.getSalud() * 100

        barraVidaMonstruo.progress = monstruo.getSalud()
        barraVidaJugador.progress = personaje.getSalud()

        // Actualiza el color de la barra de vida del monstruo
        barraVidaMonstruo.progressDrawable.colorFilter = PorterDuffColorFilter(getColorBasedOnHealth(porcentajeVidaMonstruo), PorterDuff.Mode.SRC_IN)

        // Actualiza el color de la barra de vida del jugador
        barraVidaJugador.progressDrawable.colorFilter = PorterDuffColorFilter(getColorBasedOnHealth(porcentajeVidaJugador), PorterDuff.Mode.SRC_IN)

        verificarEstadoCombate()
    }

    private fun getColorBasedOnHealth(healthPercentage: Int): Int {
        return when {
            healthPercentage > 75 -> Color.GREEN
            healthPercentage > 30 -> Color.YELLOW
            else -> Color.RED
        }
    }


    private fun crearMonstruoAleatorio(): Monstruo {
        return Monstruo("Goblin", 1)
    }

    private fun recibirContraataque() {
        val danoAlJugador = max(0, monstruo.getAtaque() - (personaje.getDefensa() / 10) * 5)
        personaje.setSalud(max(0, personaje.getSalud() - danoAlJugador))
        actualizarBarrasDeVida()
        verificarEstadoCombate()
    }


    private fun obtenerIdImagenPorNumero(numero: Int): Int {
        return when (numero) {
            1 -> R.drawable.moneda
            2 -> R.drawable.espada
            3 -> R.drawable.martillo
            4 -> R.drawable.garras
            5 -> R.drawable.baston
            6 -> R.drawable.pocion
            7 -> R.drawable.ira
            8 -> R.drawable.escudo
            9 -> R.drawable.armadura
            10 -> R.drawable.daga
            else -> R.drawable.cofre
        }
    }
}

