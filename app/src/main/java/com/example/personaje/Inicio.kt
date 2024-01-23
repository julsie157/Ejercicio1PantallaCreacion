package com.example.personaje

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class Inicio : AppCompatActivity() {

    private lateinit var imagen: ImageView
    private lateinit var spinnerRaza: Spinner
    private lateinit var spinnerClase: Spinner
    private lateinit var spinnerEstadoVital: Spinner

    // Define un mapa
    private val imagenes = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_inicio)


        imagen = findViewById(R.id.Imagen)
        spinnerRaza = findViewById(R.id.spinnerRaza)
        spinnerClase = findViewById(R.id.spinnerClase)
        spinnerEstadoVital = findViewById(R.id.spinnerEstadoVital)

        val nombreEditText = findViewById<EditText>(R.id.Nombre)
        val continuarButton = findViewById<Button>(R.id.Continuar)

        // Configura spinners
        val razaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Seleccionar Raza", "Enano", "Humano", "Elfo", "Maldito"))
        val claseAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Seleccionar Clase", "Mago", "Brujo", "Guerrero"))
        val estadoVitalAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("Seleccionar Estado", "Joven", "Adulto", "Anciano"))

        spinnerRaza.adapter = razaAdapter
        spinnerClase.adapter = claseAdapter
        spinnerEstadoVital.adapter = estadoVitalAdapter

        llenarMapaDeImagenes()

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    actualizarImagen()
                } else {
                    imagen.setImageResource(R.drawable.gnomopocho)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinnerRaza.onItemSelectedListener = spinnerListener
        spinnerClase.onItemSelectedListener = spinnerListener
        spinnerEstadoVital.onItemSelectedListener = spinnerListener


        imagen.setImageResource(R.drawable.gnomopocho)



        continuarButton.setOnClickListener {
            // Obtener valores seleccionados
            val nombre = nombreEditText.text.toString()
            val raza = spinnerRaza.selectedItem.toString()
            val estadoVital = spinnerEstadoVital.selectedItem.toString()
            val pesoMochila = 15.0

            // Crear el objeto Personaje
            val personaje = Personaje(raza, nombre, estadoVital, pesoMochila)

            // Crear Intent y pasar el objeto Personaje a MainActivity2
            val intent = Intent(this, DatosPersonaje::class.java)
            intent.putExtra("personaje", personaje)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    private fun llenarMapaDeImagenes() {
        // Enanos
        imagenes["Enano, Mago, Joven"] = R.drawable.magoenanojoven
        imagenes["Enano, Mago, Adulto"] = R.drawable.magoenanoadulto
        imagenes["Enano, Mago, Anciano"] = R.drawable.magoenanoanciano

        imagenes["Enano, Brujo, Joven"] = R.drawable.brujoenanojoven
        imagenes["Enano, Brujo, Adulto"] = R.drawable.brujoenanoadulto
        imagenes["Enano, Brujo, Anciano"] = R.drawable.brujoenanoanciano

        imagenes["Enano, Guerrero, Joven"] = R.drawable.guerreroenanojoven
        imagenes["Enano, Guerrero, Adulto"] = R.drawable.guerreroenanoadulto
        imagenes["Enano, Guerrero, Anciano"] = R.drawable.guerreroenanoanciano

        //Humanos
        imagenes["Humano, Mago, Joven"] = R.drawable.magohumanojoven
        imagenes["Humano, Mago, Adulto"] = R.drawable.magohumanoadulto
        imagenes["Humano, Mago, Anciano"] = R.drawable.magohumanoanciano

        imagenes["Humano, Brujo, Joven"] = R.drawable.brujohumanojoven
        imagenes["Humano, Brujo, Adulto"] = R.drawable.brujohumanoadulto
        imagenes["Humano, Brujo, Anciano"] = R.drawable.brujohumanoanciano

        imagenes["Humano, Guerrero, Joven"] = R.drawable.guerrerohumanojoven
        imagenes["Humano, Guerrero, Adulto"] = R.drawable.guerrerohumanoadulto
        imagenes["Humano, Guerrero, Anciano"] = R.drawable.guerrerohumanoanciano

        //Elfos
        imagenes["Elfo, Mago, Joven"] = R.drawable.magoelfojoven
        imagenes["Elfo, Mago, Adulto"] = R.drawable.magoelfoadulto
        imagenes["Elfo, Mago, Anciano"] = R.drawable.magoelfoanciano

        imagenes["Elfo, Brujo, Joven"] = R.drawable.brujoelfojoven
        imagenes["Elfo, Brujo, Adulto"] = R.drawable.brujoelfoadulto
        imagenes["Elfo, Brujo, Anciano"] = R.drawable.brujoelfoanciano

        imagenes["Elfo, Guerrero, Joven"] = R.drawable.guerreroelfojoven
        imagenes["Elfo, Guerrero, Adulto"] = R.drawable.guerreroelfoadulto
        imagenes["Elfo, Guerrero, Anciano"] = R.drawable.guerreroelfoanciano

        //Malditos
        imagenes["Maldito, Mago, Joven"] = R.drawable.magomalditojoven
        imagenes["Maldito, Mago, Adulto"] = R.drawable.magomalditoanciano
        imagenes["Maldito, Mago, Anciano"] = R.drawable.magomalditoanciano

        imagenes["Maldito, Brujo, Joven"] = R.drawable.brujomalditojoven
        imagenes["Maldito, Brujo, Adulto"] = R.drawable.brujomalditoadulto
        imagenes["Maldito, Brujo, Anciano"] = R.drawable.brujomalditoanciano

        imagenes["Maldito, Guerrero, Joven"] = R.drawable.guerreromalditojoven
        imagenes["Maldito, Guerrero, Adulto"] = R.drawable.guerreromalditoadulto
        imagenes["Maldito, Guerrero, Anciano"] = R.drawable.guerreromalditoanciano

    }

    private fun actualizarImagen() {
        val selectedRaza = spinnerRaza.selectedItem.toString()
        val selectedClase = spinnerClase.selectedItem.toString()
        val selectedEstado = spinnerEstadoVital.selectedItem.toString()

        val key = "$selectedRaza, $selectedClase, $selectedEstado"
        val imagenId = imagenes[key]

        if (imagenId != null) {
            imagen.setImageResource(imagenId)
        }
    }

}
