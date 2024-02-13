package com.example.personaje

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MercaderActivity : AppCompatActivity() {

    private lateinit var dbGeneral: BaseDeDatosGeneral
    private var idPersonaje: Long = -1
    private lateinit var panelComercio: LinearLayout
    private lateinit var panelArticulos: LinearLayout
    private lateinit var botonComerciar: Button
    private lateinit var botonComprar: Button
    private lateinit var botonCancelar: Button
    private lateinit var botonConfirmar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_mercader)

        dbGeneral = BaseDeDatosGeneral(this)
        idPersonaje = intent.getLongExtra("id_personaje", -1L)

        panelComercio = findViewById(R.id.panelComercio)
        panelArticulos = findViewById(R.id.panelArticulos)
        botonComerciar = findViewById(R.id.Botoncomerciar)
        botonComprar = findViewById(R.id.botonComprar)
        botonCancelar = findViewById(R.id.botonCancelar)
        botonConfirmar = findViewById(R.id.botonConfirmar)


        botonComerciar.setOnClickListener {
            mostrarPanelComercio()
        }

        botonCancelar.setOnClickListener {
            panelComercio.visibility = View.GONE
        }

        botonComprar.setOnClickListener {

        }

        botonConfirmar.setOnClickListener {
        }
    }

    private fun mostrarPanelComercio() {
        panelComercio.visibility = View.VISIBLE
        botonComprar.visibility = View.VISIBLE
        botonConfirmar.visibility = View.GONE
        panelArticulos.removeAllViews()
    }


}
