package com.example.personaje


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class MercaderActivity : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral
    private var idPersonaje: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mercader)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("id_personaje", -1)

        val botonComerciar = findViewById<Button>(R.id.Botoncomerciar)
        val botonContinuar = findViewById<Button>(R.id.Botoncontmercader)
        val panelComercio = findViewById<LinearLayout>(R.id.panelComercio)

        botonComerciar.setOnClickListener {
            panelComercio.visibility = View.VISIBLE
        }

        botonContinuar.setOnClickListener {
            finish()
        }
    }

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
