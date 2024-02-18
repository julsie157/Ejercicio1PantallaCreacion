package com.example.personaje


import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ObjetoActivity : AppCompatActivity() {

    private lateinit var botonRecoger: Button
    private lateinit var botonContinuar: Button
    private lateinit var imagenObjeto: ImageView
    private lateinit var dbGeneral: BaseDeDatosGeneral
    private var idPersonaje: Long = -1L
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_objeto)


        mediaPlayer = MediaPlayer.create(this, R.raw.goroncity)
        mediaPlayer.setVolume(0.1f, 0.1f)


        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("intentExtraIdPersonaje", -1L)

        botonRecoger = findViewById(R.id.Botonrecoger)
        botonContinuar = findViewById(R.id.Botoncontobjeto)
        imagenObjeto = findViewById(R.id.Objeto)

        botonRecoger.setOnClickListener {
            recogerObjeto()
        }

        botonContinuar.setOnClickListener {
            finish()
        }
    }
    private fun recogerObjeto() {
        val idMochila = dbGeneral.obtenerIdMochilaPorPersonaje(idPersonaje)
        if (idMochila == -1) {
            Toast.makeText(this, "Mochila no encontrada para el personaje.", Toast.LENGTH_LONG).show()
            return
        }

        val articulo = dbGeneral.obtenerArticuloAleatorio()
        if (articulo != null) {
            val nombre = articulo.getNombre().name
            val peso = articulo.getPeso()
            val idImagen = articulo.getImagenId()
            val resourceId = obtenerIdImagenPorNumero(idImagen)

            val espacioDisponible = dbGeneral.obtenerEspacioDisponibleMochila(idMochila)
            if (peso <= espacioDisponible) {
                dbGeneral.actualizarEspacioMochila(idMochila, peso)
                dbGeneral.anadirArticuloAMochila(idMochila, articulo.getIdArticulo())
                mostrarToastRecogida(nombre, espacioDisponible - peso, resourceId)
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                },1400)
            } else {
                Toast.makeText(this, "No hay suficiente espacio para recoger $nombre.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                },1400)
            }
        } else {
            Toast.makeText(this, "No se pudo recoger un objeto.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            },1400)
        }
    }



    private fun mostrarToastRecogida(nombre: String, nuevoEspacioDisponible: Int, resourceId: Int) {
        imagenObjeto.setImageResource(resourceId)
        val toastText = "Has recogido: $nombre. Espacio restante: $nuevoEspacioDisponible"
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
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
            11 -> R.drawable.jamon
            else -> R.drawable.cofre
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




